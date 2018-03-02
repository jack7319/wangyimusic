package com.bizideal.mn.entity;

import com.bizideal.mn.biz.PlayListService;
import com.bizideal.mn.biz.SongService;
import com.bizideal.mn.config.SpringUtil;
import com.bizideal.mn.crawler.SongListCrawler;
import com.bizideal.mn.enums.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author : liulq
 * @date: 创建时间: 2017/11/9 10:12
 * @version: 1.0
 * @Description:
 */
public class SongCommentsThread extends Thread {

    private Logger logger = LoggerFactory.getLogger(SongCommentsThread.class);

    private SongService songService;

    private Integer id;  // 歌曲id
    private String url; // 歌曲url

    public SongCommentsThread(Integer id, String url, SongService songService) {
        this.id = id;
        this.url = url;
        this.songService = songService;
    }

    @Override
    public void run() {
        try {
            Integer commonts = SongListCrawler.getCommonts(String.valueOf(id));
            Song song = new Song();
            song.setId(this.id);
            song.setAmountOfPlay(commonts);
            song.setStatus(Status.CRAWLED.name());
            songService.updateSelective(song);
            logger.debug("get commonts success " + this.id);
        } catch (Exception e) {
            logger.error("获取歌曲评论数失败", e);
        }
    }
}
