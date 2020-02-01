package com.pawpaw.stock.post.controller;

import lombok.Data;

import java.util.LinkedList;
import java.util.List;

/**
 * @author liujixin
 * @date 2020-02-01
 * @description
 */
@Data
public class EastMoneyPostNumberAggregate {

    private List<String> dates = new LinkedList<>();
    private List<Integer> numbers = new LinkedList<>();

    public void add(String date, int number) {
        this.dates.add(date);
        this.numbers.add(number);
    }
}
