package com.health.pusun.device.vo;

import java.io.Serializable;

/**
 * Created by majiangtao on 2018/9/7.
 */

public class ActivityInfoVo implements Serializable {

    private String ActivitIinfo;
    private String EndTime;
    private String VenueID;

    public String getActivitIinfo() {
        return ActivitIinfo;
    }

    public void setActivitIinfo(String activitIinfo) {
        ActivitIinfo = activitIinfo;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
    }

    public String getVenueID() {
        return VenueID;
    }

    public void setVenueID(String venueID) {
        VenueID = venueID;
    }
}
