package com.bizideal.mn.mapper;

import com.bizideal.mn.entity.Song;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author : liulq
 * @date: 创建时间: 2017/10/31 9:48
 * @version: 1.0
 * @Description:
 */
public interface SongMapper extends Mapper<Song> {

    Integer getCount();

    Integer updateAllStatus(@Param("status") String status);

    List<Song> getByPage(@Param("startRow") Integer startRow, @Param("endRow") Integer endRow, @Param("status") String status);

    List<Song> getSongs(@Param("startRow") Integer startRow, @Param("endRow") Integer endRow);

    Integer updateStatus(@Param("songIds") List<Integer> songIds, @Param("status") String status);
}
