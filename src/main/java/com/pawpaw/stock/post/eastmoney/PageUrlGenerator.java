package com.pawpaw.stock.post.eastmoney;

import lombok.AllArgsConstructor;

/**
 * @author liujixin
 * @date 2020-01-31
 * @description
 */
@AllArgsConstructor
public class PageUrlGenerator {

    private String stockCode;
    private int pageNumber;

    public static final String domainAndprefix = "http://guba.eastmoney.com/list,";

    public String getPageUrl() {
        StringBuilder pageUrl = new StringBuilder();
        pageUrl.append(domainAndprefix);
        pageUrl.append(this.stockCode);
        //按照发帖时间倒叙排列，否则默认会按照最后回复时间排列
        pageUrl.append(",f");
        pageUrl.append("_");
        pageUrl.append(this.pageNumber);
        pageUrl.append(".html");
        return pageUrl.toString();
    }


}
