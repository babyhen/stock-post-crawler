package com.pawpaw.stock.post.eastmoney;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.Collection;
import java.util.Map;

/**
 * @author liujixin
 * @date 2020-01-30
 * @description
 */
public class ConsolePipeline implements Pipeline {

    @Override
    public void process(ResultItems resultItems, Task task) {
        System.out.println("get page: " + resultItems.getRequest().getUrl());
        for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
            System.out.println(entry.getKey() + ":");
            Object value = entry.getValue();
            if (value instanceof Collection) {
                Collection c = (Collection) value;
                for (Object o : c) {
                    System.out.println("\t" + o);
                }
            } else {
                System.out.println(value);
            }
            System.out.println(entry.getKey() + ":\t" + entry.getValue());
        }
    }
}
