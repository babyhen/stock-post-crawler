package com.pawpaw.stock.post;

import com.pawpaw.framework.core.common.util.BrowserUtil;
import com.pawpaw.framework.core.common.util.TimeUtil;
import com.pawpaw.framework.web.PawpawWebApplication;
import com.pawpaw.stock.post.eastmoney.EastMoneySpider;
import com.pawpaw.stock.post.eastmoney.NormalPost;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import static com.pawpaw.framework.core.common.util.TimeUtil.TIME_FORMAT_8;

@SpringBootApplication
public class BootStrap {


    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("useage:");
            System.out.println("stockcode startDate endDate");
            System.out.println("for example: 300324 20190101,20190201");
            return;
        }

        PawpawWebApplication application = new PawpawWebApplication();
        application.run(BootStrap.class);
        String stockCode = args[0];
        String startDate = args[1];
        String endDate = args[2];
        Date begin = TimeUtil.parseDate(startDate, TIME_FORMAT_8);
        Date end = TimeUtil.parseDate(endDate, TIME_FORMAT_8);


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
