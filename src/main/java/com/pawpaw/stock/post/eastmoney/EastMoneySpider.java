package com.pawpaw.stock.post.eastmoney;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.example.GithubRepoPageProcessor;

import javax.annotation.PostConstruct;
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
        startUrl.append(".html");
        log.info("起始地址为:{}", startUrl);

        Spider.create(new EastMoneyPageProcessor())
                //从"https://github.com/code4craft"开始抓
                .addUrl(startUrl.toString())
                //开启5个线程抓取
                .thread(5)
                //启动爬虫

                .run();
    }
}
