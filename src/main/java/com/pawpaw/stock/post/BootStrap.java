package com.pawpaw.stock.post;

import com.pawpaw.framework.core.common.util.BrowserUtil;
import com.pawpaw.framework.core.common.util.TimeUtil;
import com.pawpaw.framework.web.PawpawWebApplication;
import com.pawpaw.stock.post.eastmoney.EastMoneySpider;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;

import static com.pawpaw.framework.core.common.util.TimeUtil.TIME_FORMAT_10;

@SpringBootApplication
public class BootStrap {


    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("useage:抓去指定的开始时间至今的帖子数量");
            System.out.println("stockcode startDate");
            System.out.println("for example: 300324 2019-01-01");
            return;
        }

        PawpawWebApplication application = new PawpawWebApplication();
        application.run(BootStrap.class);
        String stockCode = args[0];
        String startDate = args[1];
        Date begin = TimeUtil.parseDate(startDate, TIME_FORMAT_10);
        Date end = new Date();


        // EastMoneySpider spider = new EastMoneySpider(stockCode);
        EastMoneySpider spider = new EastMoneySpider(stockCode, begin, end);
        spider.start();
        System.out.println("finish parse " + stockCode);
        //打开浏览器
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append("http://localhost:8080/eastMoney/aggregateResultPage");
        BrowserUtil.openBrownse(urlBuilder.toString());
    }
}
