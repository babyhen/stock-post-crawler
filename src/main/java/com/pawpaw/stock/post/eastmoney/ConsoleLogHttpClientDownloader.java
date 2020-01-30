package com.pawpaw.stock.post.eastmoney;

import lombok.extern.slf4j.Slf4j;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * @author liujixin
 * @date 2020-01-30
 * @description
 */
@Slf4j
public class ConsoleLogHttpClientDownloader extends HttpClientDownloader {
    @Override
    public Page download(Request request, Task task) {
        Page page = super.download(request, task);
        log.info("页面的代码长度：{}", page.getRawText().length());
        return page;
    }
}
