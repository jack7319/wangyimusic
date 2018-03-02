package com.bizideal.mn.entity;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author : liulq
 * @date: 创建时间: 2017/10/30 10:28
 * @version: 1.0
 * @Description: 所有的歌单列表/每天爬虫更新前会全部删除，重新爬取
 */
@Table(name = "play_list")
public class PlayList implements Serializable {

    private static final long serialVersionUID = 5169487439643329587L;

    // 先取到所有的歌单。线程
    // 通过歌单拿到所有的歌曲。线程
    // 进入歌曲页面。线程
    @Id
    private Integer id; // 歌单id，网易云id
    private String name; // 歌单名
    private String category; // 歌单分类
    private Integer amountOfPlay; // 播放量
    private String owner; // 创建者
    private String url; // 歌单url地址
    private Date createTime; // 收集时间
    private Date updateTime; // 更新时间
    private String status; // 状态 CRAWLED/UNCRAWL

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAmountOfPlay() {
        return amountOfPlay;
    }

    public void setAmountOfPlay(Integer amountOfPlay) {
        this.amountOfPlay = amountOfPlay;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
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

    public static void main(String[] args) {
        System.out.println(javax.crypto.Cipher.class.getProtectionDomain());
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"id\":")
                .append(id);
        sb.append(",\"name\":\"")
                .append(name).append('\"');
        sb.append(",\"category\":\"")
                .append(category).append('\"');
        sb.append(",\"amountOfPlay\":")
                .append(amountOfPlay);
        sb.append(",\"owner\":\"")
                .append(owner).append('\"');
        sb.append(",\"url\":\"")
                .append(url).append('\"');
        sb.append(",\"createTime\":\"")
                .append(createTime).append('\"');
        sb.append(",\"updateTime\":\"")
                .append(updateTime).append('\"');
        sb.append(",\"status\":\"")
                .append(status).append('\"');
        sb.append('}');
        return sb.toString();
    }
}
