package com.health.pusun.device.vo;

import java.io.Serializable;

/**
 * Created by majiangtao on 2018/10/20.
 */

public class UserRankVo implements Serializable{

    private String UserName;
    private int Ranking;
    private String HeadPortrait;
    private int Score;

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public int getRanking() {
        return Ranking;
    }

    public void setRanking(int ranking) {
        Ranking = ranking;
    }

    public String getHeadPortrait() {
        return HeadPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        HeadPortrait = headPortrait;
    }

    public int getScore() {
        return Score;
    }

    public void setScore(int score) {
        Score = score;
    }
}
