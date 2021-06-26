package com.health.pusun.device.vo;

import java.io.Serializable;

/**
 * Created by majiangtao on 2018/3/31.
 */

public class NewsVo implements Serializable {

    private String ID;
    private String Title;
    private String Substance;
    private String UserID;
    private String CreateTime;
    private String Image1;
    private int ReadCount;

    public int getReadCount() {
        return ReadCount;
    }

    public void setReadCount(int readCount) {
        ReadCount = readCount;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getSubstance() {
        return Substance;
    }

    public void setSubstance(String substance) {
        Substance = substance;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public String getImage1() {
        return Image1;
    }

    public void setImage1(String image1) {
        Image1 = image1;
    }
}
