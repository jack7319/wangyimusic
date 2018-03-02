package com.bizideal.mn.schedule;

import com.bizideal.mn.biz.PlayListService;
import com.bizideal.mn.crawler.PlayListCrawler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author : liulq
 * @date: 创建时间: 2017/10/31 9:11
 * @version: 1.0
 * @Description:
 */
@Component
public class PlayListSchedule {

    private Logger logger = LoggerFactory.getLogger(PlayListSchedule.class);

    @Autowired
    private PlayListService playListService;

//    @Scheduled(cron = "0 0 22 * * ?") // 每天晚上10点钟执行
    @Scheduled(cron = "0 0 22 ? * THU") // 每周五晚上10点钟执行
    public void deal() {
        logger.info("start to refresh all of the playlists");
        PlayListCrawler.start();
    }
}
