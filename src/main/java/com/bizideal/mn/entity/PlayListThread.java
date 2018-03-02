package com.bizideal.mn.entity;

import com.bizideal.mn.biz.PlayListService;
import com.bizideal.mn.config.SpringUtil;
import com.bizideal.mn.crawler.PlayListCrawler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * @author : liulq
 * @date: 创建时间: 2017/10/30 13:25
 * @version: 1.0
 * @Description: 用于爬取所有的歌单
 */
public class PlayListThread implements Runnable {

    private Logger logger = LoggerFactory.getLogger(PlayListThread.class);

    private PlayListService playListService;

    private String category; // 歌单分类

    public PlayListThread(String category) {
        this.category = category;
        // new出来的实体无法用@autowired注入
        this.playListService = (PlayListService) SpringUtil.getBean("playListService");
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public void run() {
        try {
            Integer totalPages = PlayListCrawler.getTotalPages(this.category);
            for (int i = 1; i <= totalPages; i++) {
                List<PlayList> playList = PlayListCrawler.getPlayList(this.category, i);
                if (!playList.isEmpty()) {
                    playList.forEach(p -> {
                        PlayList playList1 = playListService.queryById(p.getId());
                        if (null == playList1)
                            playListService.save(p);
                        else
                            playListService.update(p);
                    });
                }
            }
        } catch (IOException e) {
            logger.error("爬取所有的歌单线程出错", e);
        }
    }
}
