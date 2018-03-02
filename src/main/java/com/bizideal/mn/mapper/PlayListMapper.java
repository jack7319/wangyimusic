package com.bizideal.mn.mapper;

import com.bizideal.mn.entity.PlayList;
import com.bizideal.mn.utils.MyMapper;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author : liulq
 * @date: 创建时间: 2017/10/30 14:47
 * @version: 1.0
 * @Description:
 */
public interface PlayListMapper extends MyMapper<PlayList> {

    Integer insertList1(@Param("playLists") List<PlayList> playLists);

    List<PlayList> queryByPage(@Param("startRow") Integer startRow, @Param("pageSize") Integer pageSize, @Param("status") String status);

    Integer updateStatus(@Param("playListIds") List<Integer> playListIds, @Param("status") String status);

    List<PlayList> queryByStatus(@Param("status") String status);

    Integer updateAllStatus(@Param("status") String status);
}