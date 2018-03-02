package com.bizideal.mn.biz;

import com.bizideal.mn.entity.PlayList;
import com.bizideal.mn.enums.Status;

import java.util.List;

/**
 * @author : liulq
 * @date: 创建时间: 2017/10/30 15:24
 * @version: 1.0
 * @Description:
 */
public interface PlayListService extends BaseBiz<PlayList> {

    public Integer insertList(List<PlayList> playLists);

    public List<PlayList> queryAndUpdateStatus(Integer pageIndex, Integer pageSize, String status);

    Integer updateStatus(Integer id, String status);

    List<PlayList> queryByStatus(String status);

    Integer updateAllStatus(String status);
}