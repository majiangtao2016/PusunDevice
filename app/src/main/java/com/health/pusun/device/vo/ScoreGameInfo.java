package com.health.pusun.device.vo;

import java.io.Serializable;

public class ScoreGameInfo implements Serializable {

    private int Score;
    private int ServeNum;
    private int HitNum;
    private double HitRate;
    private int IsPass;
    private int topLevel;
    private  int Level;

    public int getScore() {
        return Score;
    }

    public void setScore(int score) {
        Score = score;
    }

    public int getServeNum() {
        return ServeNum;
    }

    public void setServeNum(int serveNum) {
        ServeNum = serveNum;
    }

    public int getHitNum() {
        return HitNum;
    }

    public void setHitNum(int hitNum) {
        HitNum = hitNum;
    }

    public double getHitRate() {
        return HitRate;
    }

    public void setHitRate(double hitRate) {
        HitRate = hitRate;
    }

    public int getIsPass() {
        return IsPass;
    }

    public void setIsPass(int isPass) {
        IsPass = isPass;
    }

    public int getTopLevel() {
        return topLevel;
    }

    public void setTopLevel(int topLevel) {
        this.topLevel = topLevel;
    }

    public int getLevel() {
        return Level;
    }

    public void setLevel(int level) {
        Level = level;
    }
}
