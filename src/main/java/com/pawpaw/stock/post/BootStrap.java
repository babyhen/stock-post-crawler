package com.pawpaw.stock.post;

import com.pawpaw.framework.core.common.util.BrowserUtil;
import com.pawpaw.framework.core.common.util.TimeUtil;
import com.pawpaw.framework.web.PawpawWebApplication;
import com.pawpaw.stock.post.eastmoney.EastMoneySpider;
import com.pawpaw.stock.post.eastmoney.NormalPost;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import static com.pawpaw.framework.core.common.util.TimeUtil.TIME_FORMAT_8;

@SpringBootApplication
public class BootStrap {


    public static void main(String[] args) {
        PawpawWebApplication application = new PawpawWebApplication();
        application.run(BootStrap.class);
        String stockCode = "300324";
        String startDate = "20200127";
        String endDate = "20200128";
        Date begin = TimeUtil.parseDate(startDate, TIME_FORMAT_8);
        Date end = TimeUtil.parseDate(endDate, TIME_FORMAT_8);
        //
        EastMoneySpider spider = new EastMoneySpider(stockCode, begin, end);
        spider.start();
        System.out.println("finish parse " + stockCode);
        //打开浏览器
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append("http://localhost:8080/eastMoney/aggregateResult");
        BrowserUtil.openBrownse(urlBuilder.toString());
    }
}
