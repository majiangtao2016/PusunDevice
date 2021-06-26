package com.health.pusun.device.vo;

import java.io.Serializable;

/**
 * Created by majiangtao on 2018/5/7.
 */

public class UserBookInfo implements Serializable {

    private String StartTime;
    private String EndTime;
    private String UserId;
    private String NickName;
    private String HeadImg;

    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String startTime) {
        StartTime = startTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getHeadImg() {
        return HeadImg;
    }

    public void setHeadImg(String headImg) {
        HeadImg = headImg;
    }

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String nickName) {
        NickName = nickName;
    }

}
