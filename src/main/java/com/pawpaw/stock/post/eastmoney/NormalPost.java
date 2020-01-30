package com.pawpaw.stock.post.eastmoney;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * @author liujixin
 * @date 2020-01-30
 * @description
 */
@Data
public class NormalPost {
    private  String author;
    private Date dateTime;
    private  String title;
}
