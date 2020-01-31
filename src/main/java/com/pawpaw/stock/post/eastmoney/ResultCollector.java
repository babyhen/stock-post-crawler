package com.pawpaw.stock.post.eastmoney;

import lombok.Getter;
import lombok.Setter;
import sun.security.jca.GetInstance;

import java.util.List;

/**
 * @author liujixin
 * @date 2020-01-31
 * @description
 */
public class ResultCollector {

    private volatile static ResultCollector instance;

    @Getter
    @Setter
    private List<NormalPost> result;

    private ResultCollector() {

    }

    public static ResultCollector getInstance() {
        if (instance == null) {
            synchronized (ResultCollector.class) {
                if (instance == null) {
                    instance = new ResultCollector();
                }
            }
        }
        return instance;
    }


}
