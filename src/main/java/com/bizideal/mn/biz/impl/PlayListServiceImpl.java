package com.bizideal.mn.biz.impl;

import com.bizideal.mn.biz.PlayListService;
import com.bizideal.mn.entity.PlayList;
import com.bizideal.mn.enums.Status;
import com.bizideal.mn.mapper.PlayListMapper;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author : liulq
 * @date: 创建时间: 2017/10/30 15:24
 * @version: 1.0
 * @Description:
 */
@Service("playListService")
public class PlayListServiceImpl extends BaseBizImpl<PlayList> implements PlayListService {

    private ReentrantLock lock = new ReentrantLock();

    @Autowired
    private PlayListMapper playListMapper;

    @Override
    @Transactional
    public Integer insertList(List<PlayList> playLists) {
        return playListMapper.insertList1(playLists);
    }

    @Override
    @Transactional
    public List<PlayList> queryAndUpdateStatus(Integer pageIndex, Integer pageSize, String status) {
        lock.lock();
        List<PlayList> playLists = playListMapper.queryByPage((pageIndex - 1) * pageSize, pageSize, status);
        List<Integer> playListIds = new ArrayList<>();
        Date date = new Date();
        playLists.forEach(playList -> {
            playList.setUpdateTime(date);
            playListIds.add(playList.getId());
        });
        playListMapper.updateStatus(playListIds, Status.CRAWLING.name());
        lock.unlock();
        return playLists;
    }

    @Override
    @Transactional
    public Integer updateStatus(Integer id, String status) {
        return playListMapper.updateStatus(new ArrayList<Integer>() {{
            add(id);
        }}, status);
    }

    @Override
    public List<PlayList> queryByStatus(String status) {
        return playListMapper.queryByStatus(status);
    }

    @Override
    @Transactional
    public Integer updateAllStatus(String status) {
        return playListMapper.updateAllStatus(status);
    }
}
