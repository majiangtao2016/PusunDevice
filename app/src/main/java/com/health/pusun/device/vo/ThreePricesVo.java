package com.health.pusun.device.vo;

import java.io.Serializable;

/**
 * Created by majiangtao on 2018/10/20.
 */

public class ThreePricesVo implements Serializable{

    private String DevNumber;
    private int Price1;
    private int Price2;
    private int Price3;

    public String getDevNumber() {
        return DevNumber;
    }

    public void setDevNumber(String devNumber) {
        DevNumber = devNumber;
    }

    public int getPrice1() {
        return Price1;
    }

    public void setPrice1(int price1) {
        Price1 = price1;
    }

    public int getPrice2() {
        return Price2;
    }

    public void setPrice2(int price2) {
        Price2 = price2;
    }

    public int getPrice3() {
        return Price3;
    }

    public void setPrice3(int price3) {
        Price3 = price3;
    }
}
