package com.pawpaw.stock.post.eastmoney;

import com.pawpaw.framework.core.common.util.TimeUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import us.codecraft.webmagic.Spider;

import java.util.Date;
import java.util.List;

/**
 * @author liujixin
 * @date 2020-01-28
 * @description
 */

@Slf4j
public class EastMoneySpider {
    private final String stockCode;
    private Date begin;
    private Date end;
    //页码必须从1开始，否则时间就乱套了。没法猜测年份
    private int startPage;

    public EastMoneySpider(String stockCode, Date begin, Date end) {
        this(stockCode, 1, begin, end);

    }

    @Deprecated
    public EastMoneySpider(String stockCode, int startPage, Date begin, Date end) {
        this.stockCode = stockCode;
        this.end = end;
        this.begin = begin;
        this.startPage = startPage;
    }

    public void start() {
        Date oneYearAgo = TimeUtil.minusYear(new Date(), 1);
        if (begin.before(oneYearAgo)) {
            throw new RuntimeException("开始时间不能抓取1年之前");
        }


        String startUrl = new PageUrlGenerator(this.stockCode, this.startPage).getPageUrl();
        log.info("起始地址为:{}", startUrl);
        DateMatchPipeLine dateMatchPipeLine = new DateMatchPipeLine(begin, end);
        Spider.create(new EastMoneyPageProcessor(stockCode, this.startPage, begin, end))
                //从这个地址开始抓取
                .addUrl(startUrl.toString())
                //开启5个线程抓取
                .thread(1)
                //把下载下来的html源码打印下，帮助排查问题
                .setDownloader(new ConsoleLogHttpClientDownloader())
                //打印出来数据
                .addPipeline(new ConsolePipeline())
                .addPipeline(dateMatchPipeLine)
                //启动爬虫
                .run();
        ResultCollector.getInstance().setResult(dateMatchPipeLine.getMatch());

    }
}
