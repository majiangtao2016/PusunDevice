package com.health.pusun.device.utils;


import com.health.pusun.device.vo.RequestCallVo;

/**
 * Created by majiangtao on 2018-03-12.
 */
public abstract class MyJsonCallbalk {
    public void onBefore() {
    }

    public abstract void onError(Exception e, int code);

    public abstract void onResponse(RequestCallVo requestCallVo);

    public void onAfter() {
    }
}
