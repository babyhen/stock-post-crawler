package com.pawpaw.stock.post;

import com.pawpaw.framework.core.common.util.TimeUtil;
import com.pawpaw.framework.web.PawpawWebApplication;
import com.pawpaw.stock.post.eastmoney.EastMoneySpider;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static com.pawpaw.framework.core.common.util.TimeUtil.TIME_FORMAT_8;

@SpringBootApplication
public class BootStrap {

    public static void main(String[] args) {
        PawpawWebApplication application = new PawpawWebApplication();
        application.run(BootStrap.class);
        String stockCode = "300324";
        String startDate = "20200127";
        String endDate = "20200128";
        //
        EastMoneySpider spider = new EastMoneySpider(stockCode);
        spider.start(TimeUtil.parse(startDate, TIME_FORMAT_8), TimeUtil.parse(endDate, TIME_FORMAT_8));
        System.out.println("finish parse "+stockCode);
    }
}
