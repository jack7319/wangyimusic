package com.bizideal.mn.enums;

/**
 * @author : liulq
 * @date: 创建时间: 2017/10/30 10:24
 * @version: 1.0
 * @Description: 处理状态
 */
public enum Status {

    UNCRAWL("未爬"),
    CRAWLING("进行中"),
    CRAWLED("已爬");

    public String statusName;

    private Status(String statusName) {
        this.statusName = statusName;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
}
