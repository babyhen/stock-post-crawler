package com.pawpaw.stock.post.eastmoney;

import com.pawpaw.framework.core.common.util.TimeUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author liujixin
 * @date 2020-01-30
 * @description
 */

public class DateMatchPipeLine implements Pipeline {

    private final Date begin;
    private final Date end;

    public DateMatchPipeLine(Date begin, Date end) {
        this.begin = begin;
        this.end = end;
    }

    @Getter
    private List<NormalPost> match = new LinkedList<>();

    @Override
    public void process(ResultItems resultItems, Task task) {
        List<NormalPost> normalPosts = resultItems.get(EastMoneyPageProcessor.keyForPostList);
        if (normalPosts == null) {
            return;
        }
        //通过时间过滤
        List<NormalPost> matched = normalPosts.stream().filter(e -> {
            Date dateTime = e.getDateTime();
            return TimeUtil.afterOrEqual(dateTime, begin) && TimeUtil.beforeOrEqual(dateTime, end);
        }).collect(Collectors.toList());
        this.match.addAll(matched);

    }
}
