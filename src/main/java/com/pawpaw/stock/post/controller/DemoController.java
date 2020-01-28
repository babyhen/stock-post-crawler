package com.pawpaw.stock.post.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liujixin
 * @date 2020-01-28
 * @description
 */
@RestController
public class DemoController {


    @GetMapping("/demo")
    public Object demo() {
        return new Object();
    }
}
