package com.pawpaw.stock.post.eastmoney;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import us.codecraft.webmagic.Spider;

import java.util.Date;

/**
 * @author liujixin
 * @date 2020-01-28
 * @description
 */
@AllArgsConstructor
@Slf4j
public class EastMoneySpider {
    private final String stockCode;

    public void start(Date begin, Date end) {
        StringBuilder startUrl = new StringBuilder();
        startUrl.append("http://guba.eastmoney.com/list,");
        startUrl.append(this.stockCode);
        //按照发帖时间倒叙排列，否则默认会按照最后回复时间排列
        startUrl.append(",f");
        startUrl.append(".html");
        log.info("起始地址为:{}", startUrl);

        Spider.create(new EastMoneyPageProcessor())
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
