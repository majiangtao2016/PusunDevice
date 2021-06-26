package com.health.pusun.device.utils;

/**
 * Created by majiangtao on 2018/12/10.
 */

public class Update {

    /**
     * v : 1.3
     * url : http://m.leesche.cn/aaa.apk
     * content : 1.123
     * 2. abadd
     */

    private Double Version;
    private String DownLoad;
    private String Remark;
    private Double IsUpdate;

    public Double getIsUpdate() {
        return IsUpdate;
    }

    public void setIsUpdate(Double isUpdate) {
        IsUpdate = isUpdate;
    }

    public Double getVersion() {
        return Version;
    }

    public void setVersion(Double version) {
        Version = version;
    }

    public String getDownLoad() {
        return DownLoad;
    }

    public void setDownLoad(String downLoad) {
        DownLoad = downLoad;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }
}
