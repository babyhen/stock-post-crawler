package com.pawpaw.stock.post.eastmoney;

import com.pawpaw.framework.core.common.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static com.pawpaw.framework.core.common.util.TimeUtil.TIME_FORMAT_10;
import static com.pawpaw.framework.core.common.util.TimeUtil.TIME_FORMAT_19;

/**
 * @author liujixin
 * @date 2020-01-28
 * @description
 */
@Slf4j
public class EastMoneyPageProcessor implements PageProcessor {
    @NotNull
    private final String stockCode;
    @NotNull
    private final Date begin;
    @NotNull
    private final Date end;

    private final AtomicInteger currPage;
    public static final String keyForPostList = "postList";
    //来计算发帖时间属于哪一年
    private volatile int currYear = LocalDate.now().getYear();
    private volatile int currMonth = LocalDate.now().getMonthValue();


    public EastMoneyPageProcessor(String stockCode, int startPage, Date begin, Date end) {
        this.stockCode = stockCode;
        this.begin = begin;
        this.end = end;
        this.currPage = new AtomicInteger(startPage);
    }


    // 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site.me()
            .setRetryTimes(3)
            .setSleepTime(1000)
            .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.130 Safari/537.36");


    @Override
    // process是定制爬虫逻辑的核心接口，在这里编写抽取逻辑
    public void process(Page page) {
        //普通的用户发的帖子
        Selectable normalPostHtml = page.getHtml().xpath("//div[@class='normal_post']");
        if (!normalPostHtml.match()) {
            return;
        }
        List<NormalPost> normalPostList = normalPostHtml.nodes().stream().map(e -> {
            String title = e.xpath("//span[@class='l3']//a/text()").get();
            String author = e.xpath("//span[@class='l4']//font/text()").get();
            //01-30 22:15
            String dateTime = e.xpath("//span[@class='l5']/text()").get();
            //可能进入新的1年，这里需要猜测下
            this.freshYear(dateTime);
            String date = currYear + "-" + dateTime + ":00";
            Date d = TimeUtil.parseDateTime(date, TIME_FORMAT_19);
            NormalPost post = new NormalPost();
            post.setAuthor(author);
            post.setDateTime(d);
            post.setTitle(title);
            return post;
        }).collect(Collectors.toList());

        page.putField(keyForPostList, normalPostList);

        //从页面发现后续的url地址来抓取,如果比开始时间早，那么抓去就结束了
        NormalPost last = normalPostList.get(normalPostList.size() - 1);
        if (TimeUtil.afterOrEqual(last.getDateTime(), begin)) {
            int nextPage = currPage.incrementAndGet();
            String nextUrl = new PageUrlGenerator(this.stockCode, nextPage).getPageUrl();
            List<String> urlList = new LinkedList<>();
            urlList.add(nextUrl);
            log.info("抓取新的页面:{}", urlList);
            page.addTargetRequests(urlList);

        }
        //
    }

    @Override
    public Site getSite() {
        return site;
    }


    private void freshYear(String date) {
        String month = StringUtils.substring(date, 0, 2);
        int monthInt = Integer.parseInt(month);
        if (monthInt < currMonth) {
            //小于的话，更新月份
            synchronized (this) {
                if (monthInt < currMonth) {
                    this.currMonth = monthInt;
                }
            }

            return;
        } else if (monthInt > currMonth) {
            //大于的话，说明进入了新的1年，需要更新年份和月份
            synchronized (this) {
                if (monthInt > currMonth) {
                    this.currYear--;
                    this.currMonth = monthInt;
                }

            }
        } else {
            //noop
        }


    }

}