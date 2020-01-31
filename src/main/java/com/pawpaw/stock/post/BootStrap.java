package com.pawpaw.stock.post;

import com.pawpaw.framework.core.common.util.TimeUtil;
import com.pawpaw.framework.web.PawpawWebApplication;
import com.pawpaw.stock.post.eastmoney.EastMoneySpider;
import com.pawpaw.stock.post.eastmoney.NormalPost;
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
        List<NormalPost> post = spider.start();
        //按照日期分组
        TreeMap<Date, List<NormalPost>> groupedMap = post.stream().collect(Collectors.groupingBy(e -> {
            Date date = TimeUtil.midnightTime(e.getDateTime());
            return date;

        }, TreeMap::new, Collectors.toList()));

        System.out.println("finish parse " + stockCode);
        System.out.println(stockCode + "结果,从" + TimeUtil.format8(begin) + "到" + TimeUtil.format8(end));
        for (Map.Entry<Date, List<NormalPost>> entry : groupedMap.entrySet()) {
            System.out.println("日期：" + TimeUtil.format8(entry.getKey()) + ",数量:" + entry.getValue().size());
        }
    }
}
