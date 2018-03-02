package com.bizideal.mn.biz.impl;

import com.bizideal.mn.biz.SongService;
import com.bizideal.mn.entity.Song;
import com.bizideal.mn.enums.Status;
import com.bizideal.mn.mapper.SongMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author : liulq
 * @date: 创建时间: 2017/10/31 9:49
 * @version: 1.0
 * @Description:
 */
@Service("songService")
public class SongServiceImpl extends BaseBizImpl<Song> implements SongService {

    private ReentrantLock lock = new ReentrantLock();

    @Autowired
    private SongMapper songMapper;

    @Override
    @Cacheable(value = "songsCache", key = "'songsCache'+#page")
    public List<Song> getSongs(Integer page, Integer size) {
        List<Song> songs = songMapper.getSongs((page - 1) * size, size);
        return songs;
    }

    @Override
    @Cacheable(value = "songsCache", key = "'songsCount'")
    public Integer getCount() {
        return songMapper.getCount();
    }

    @Override
    @Transactional
    public List<Song> getByStatus(Integer page, Integer size, String status) {
        lock.lock();
        List<Song> songs = songMapper.getByPage((page - 1) * size, size, status);
        List<Integer> songIds = new ArrayList<>();
        for (int i = 1; i <= songs.size(); i++) {
            songIds.add(songs.get(i - 1).getId());
            if (songIds.size() % 100 == 0) {
                songMapper.updateStatus(songIds, Status.CRAWLING.name());
                songIds = new ArrayList<>();
            }
        }
        lock.unlock();
        return songs;
    }

    @Override
    @Transactional
    public Integer updateAllStatus(String status) {
        return songMapper.updateAllStatus(status);
    }
}
