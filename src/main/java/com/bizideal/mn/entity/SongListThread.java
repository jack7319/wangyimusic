package com.bizideal.mn.entity;

import com.bizideal.mn.biz.PlayListService;
import com.bizideal.mn.biz.SongService;
import com.bizideal.mn.config.SpringUtil;
import com.bizideal.mn.crawler.SongListCrawler;
import com.bizideal.mn.enums.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @author : liulq
 * @date: 创建时间: 2017/10/30 16:39
 * @version: 1.0
 * @Description: 用于爬取歌曲列表
 */
public class SongListThread implements Runnable {

    private Logger logger = LoggerFactory.getLogger(SongListThread.class);

    private SongService songService;
    private PlayListService playListService;

    private Integer id; // 歌单id
    private String url; // 歌单url

    public SongListThread(Integer id, String url, SongService songService, PlayListService playListService) {
        this.id = id;
        this.url = url;
        this.songService = songService;
        this.playListService = playListService;
    }

    @Override
    public void run() {
        try {
            List<Song> songs = SongListCrawler.dealSongList(this.id, this.url); // 全部是一个歌单下的歌曲
            songs.forEach(song -> {
                Song song1 = songService.queryById(song.getId());
                if (null == song1)
                    songService.save(song);
                else {
//                    Date createTime = song1.getCreateTime();
//                    if (System.currentTimeMillis() - createTime.getTime() > 10 * 60 * 1000) { // 10个小时之内不作更新
//                        songService.updateSelective(song);
//                    }
                }
            });
            playListService.updateStatus(this.id, Status.CRAWLED.name()); // 歌单爬完后，更新状态
            logger.debug("done " + this.id);
        } catch (IOException e) {
            logger.error("爬取歌曲列表线程出错.", e);
        }
    }
}
