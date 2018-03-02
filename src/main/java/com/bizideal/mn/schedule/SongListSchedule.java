package com.bizideal.mn.schedule;

import com.bizideal.mn.biz.PlayListService;
import com.bizideal.mn.biz.SongService;
import com.bizideal.mn.crawler.PlayListCrawler;
import com.bizideal.mn.crawler.SongListCrawler;
import com.bizideal.mn.entity.PlayList;
import com.bizideal.mn.entity.Song;
import com.bizideal.mn.entity.SongCommentsThread;
import com.bizideal.mn.entity.SongListThread;
import com.bizideal.mn.enums.Status;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @author : liulq
 * @date: 创建时间: 2017/10/30 17:33
 * @version: 1.0
 * @Description:
 */
@Component
public class SongListSchedule {

    private Logger logger = LoggerFactory.getLogger(SongListSchedule.class);

    @Autowired
    private PlayListService playListService;
    @Autowired
    private SongService songService;

    @Scheduled(fixedRate = 120000) // 2分钟执行一次
    public void dealUncrawleredList() {
        logger.info("歌曲列表定时任务开始执行...");
        List<PlayList> playLists = playListService.queryAndUpdateStatus(1, 100, Status.UNCRAWL.name());
        playLists.forEach(p -> {
            SongListThread songListThread = new SongListThread(p.getId(), p.getUrl(), songService, playListService);
            SongListCrawler.executorService.execute(songListThread);
        });
    }

    @Scheduled(fixedRate = 120000) // 2分钟执行一次
    public void dealSong() {
        logger.info("歌曲评论数定时任务开始执行..");
        List<Song> songList = songService.getByStatus(1, 2000, Status.UNCRAWL.name());
        songList.forEach(song -> {
            SongCommentsThread songCommentsThread = new SongCommentsThread(song.getId(), song.getUrl(), songService);
            SongListCrawler.songExecutors.execute(songCommentsThread);
        });
    }

    // 超过10分钟没有完成的任务恢复成CRAWLING状态
    @Scheduled(fixedRate = 120000) // 2分半钟执行一次
    public void dealCrawlingList() {
        logger.info(".....");
        List<PlayList> playLists = playListService.queryByStatus(Status.CRAWLING.name());
        for (PlayList playList : playLists) {
            Date updateTime = playList.getUpdateTime();
            if (updateTime == null || System.currentTimeMillis() - updateTime.getTime() > 10 * 60 * 1000) {
                playListService.updateStatus(playList.getId(), Status.UNCRAWL.name());
            }
        }
    }

    // 更新所有歌曲评论数
    @Scheduled(cron = "0 0 22 * * ?") // 每天晚上10点钟执行
    public void deal() {
        songService.updateAllStatus(Status.UNCRAWL.name());
    }
}
