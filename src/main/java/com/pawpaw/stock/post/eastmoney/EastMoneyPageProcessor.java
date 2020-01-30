package com.pawpaw.stock.post.eastmoney;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author liujixin
 * @date 2020-01-28
 * @description
 */
public class EastMoneyPageProcessor implements PageProcessor {

    // 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site.me()
            .setRetryTimes(3)
            .setSleepTime(1000)
            .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.130 Safari/537.36");

    @Override
    // process是定制爬虫逻辑的核心接口，在这里编写抽取逻辑
    public void process(Page page) {
        //普通的用户发的帖子
        Selectable normalPostHtml = page.getHtml().xpath("//div[@class='normal_post']");
        if (!normalPostHtml.match()) {
            return;
        }
        List<NormalPost> normalPostList = normalPostHtml.nodes().stream().map(e -> {
            String title = e.xpath("//span[@class='l3']//a/text()").get();
            String author = e.xpath("//span[@class='l4']//font/text()").get();
            String dateTime = e.xpath("//span[@class='l5']/text()").get();

            NormalPost post = new NormalPost();
            post.setAuthor(author);
            post.setDateTime(dateTime);
            post.setTitle(title);
            return post;
        }).collect(Collectors.toList());

        page.putField("postList", normalPostList);
        // 部分三：从页面发现后续的url地址来抓取
        // page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/[\\w\\-]+/[\\w\\-]+)").all());
    }

    @Override
    public Site getSite() {
        return site;
    }


}