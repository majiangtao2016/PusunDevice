package com.health.pusun.device.vo;

import java.io.Serializable;

/**
 * Created by majiangtao on 2018/10/20.
 */

public class UserBaseVo implements Serializable{

    private String UserID;
    private String NickName;
    private String HeadPortrait;

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String nickName) {
        NickName = nickName;
    }

    public String getHeadPortrait() {
        return HeadPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        HeadPortrait = headPortrait;
    }
}
