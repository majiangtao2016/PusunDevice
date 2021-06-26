package com.health.pusun.device.vo;

/**
 * Created by majiangtao on 2018/3/12.
 */

public  class RequestCallVo {
    private int Type;
    private String Message;
    private String Title;
    private Object Data;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public Object getData() {
        return Data;
    }

    public void setData(Object data) {
        Data = data;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    @Override
    public String toString() {
        return "RequestCallVo{" +
                "Type='" + Type+ '\'' +
                ", Title='" + Title + '\'' +
                ", Message='" + Message + '\'' +
                ", Data=" + Data +
                '}';
    }

}
