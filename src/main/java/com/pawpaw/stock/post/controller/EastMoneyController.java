package com.pawpaw.stock.post.controller;

import com.pawpaw.framework.core.common.util.TimeUtil;
import com.pawpaw.stock.post.eastmoney.NormalPost;
import com.pawpaw.stock.post.eastmoney.ResultCollector;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import sun.nio.ch.sctp.ResultContainer;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * @author liujixin
 * @date 2020-01-28
 * @description
 */
@Controller
@RequestMapping("/eastMoney")
public class EastMoneyController {


    @GetMapping("/aggregateResult")
    public ModelAndView aggregateResult() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("eastMoneyAggregateResult");
        //从日期纬度统计
        //按照日期分组
        List<NormalPost> post = ResultCollector.getInstance().getResult();
        TreeMap<Date, List<NormalPost>> groupedMap = post.stream().collect(Collectors.groupingBy(e -> {
            Date date = TimeUtil.midnightTime(e.getDateTime());
            return date;

        }, TreeMap::new, Collectors.toList()));
        mv.addObject(groupedMap);
        return mv;
    }
}
