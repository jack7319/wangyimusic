package com.bizideal.mn.entity;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author : liulq
 * @date: 创建时间: 2017/10/30 16:23
 * @version: 1.0
 * @Description:
 */
@Table(name = "song")
public class Song {

    @Id
    private Integer id; // 歌曲id
    private Integer playListId; // 歌单id
    private String name; // 歌曲名
    private String singer; // 歌手
    private String url; // url
    private Integer amountOfPlay; // 评论数量
    private Date createTime; // 收集时间
    private String status; // 状态 CRAWLED/UNCRAWL

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPlayListId() {
        return playListId;
    }

    public void setPlayListId(Integer playListId) {
        this.playListId = playListId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getAmountOfPlay() {
        return amountOfPlay;
    }

    public void setAmountOfPlay(Integer amountOfPlay) {
        this.amountOfPlay = amountOfPlay;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"id\":")
                .append(id);
        sb.append(",\"playListId\":")
                .append(playListId);
        sb.append(",\"name\":\"")
                .append(name).append('\"');
        sb.append(",\"singer\":\"")
                .append(singer).append('\"');
        sb.append(",\"url\":\"")
                .append(url).append('\"');
        sb.append(",\"amountOfPlay\":\"")
                .append(amountOfPlay).append('\"');
        sb.append(",\"createTime\":\"")
                .append(createTime).append('\"');
        sb.append(",\"status\":\"")
                .append(status).append('\"');
        sb.append('}');
        return sb.toString();
    }

}
