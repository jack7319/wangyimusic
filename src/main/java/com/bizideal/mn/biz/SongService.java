package com.bizideal.mn.biz;

import com.bizideal.mn.entity.Song;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author : liulq
 * @date: 创建时间: 2017/10/31 9:49
 * @version: 1.0
 * @Description:
 */
public interface SongService extends BaseBiz<Song> {

    List<Song> getSongs(Integer page, Integer size);

    Integer getCount();

    List<Song> getByStatus(Integer page, Integer size, String status);

    Integer updateAllStatus(String status);
}
