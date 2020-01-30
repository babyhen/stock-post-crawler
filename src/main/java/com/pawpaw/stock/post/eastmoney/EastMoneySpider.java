package com.pawpaw.stock.post.eastmoney;

import com.pawpaw.framework.core.common.util.TimeUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import us.codecraft.webmagic.Spider;

import java.util.Date;

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

    public EastMoneySpider(String stockCode, Date begin, Date end) {
        this.stockCode = stockCode;
        this.begin = TimeUtil.midnightTime(begin);
        this.end = TimeUtil.lastTimeOfDay(end);

    }

    public void start() {
        Date oneYearAgo = TimeUtil.minusYear(new Date(), 1);
        if (begin.before(oneYearAgo)) {
            throw new RuntimeException("开始时间不能抓取1年之前");
        }


        String startUrl = new PageUrlGenerator(this.stockCode, 1).getPageUrl();
        log.info("起始地址为:{}", startUrl);

        Spider.create(new EastMoneyPageProcessor(stockCode, begin, end))
                //从"https://github.com/code4craft"开始抓
                .addUrl(startUrl.toString())
                //开启5个线程抓取
                .thread(5)
                //把下载下来的html源码打印下，帮助排查问题
                .setDownloader(new ConsoleLogHttpClientDownloader())
                //打印出来数据
                .addPipeline(new ConsolePipeline())
                .addPipeline(new AggregatePipeLine())
                //启动爬虫
                .run();
    }
}
