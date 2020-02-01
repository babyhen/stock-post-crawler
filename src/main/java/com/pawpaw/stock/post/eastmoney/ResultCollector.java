package com.pawpaw.stock.post.eastmoney;

import lombok.Getter;
import lombok.Setter;
import sun.security.jca.GetInstance;

import java.util.LinkedList;
import java.util.List;

/**
 * @author liujixin
 * @date 2020-01-31
 * @description
 */
public class ResultCollector {

    private volatile static ResultCollector instance;


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

    public List<NormalPost> getResult() {
        if (this.result == null) {
            this.result = new LinkedList<>();
        }
        return this.result;
    }

}
