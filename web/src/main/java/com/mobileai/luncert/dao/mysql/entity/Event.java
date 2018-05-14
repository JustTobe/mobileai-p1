package com.mobileai.luncert.dao.mysql.entity;

import java.util.Date;

import com.mobileai.luncert.utils.IPUtil;

/**
 * mysql Event 实体类
 */
public class Event {

    private int id;

    private Date time;

    private int source;

    private int target;

    private int eventId;

    private boolean alert;

    public Event() {}

    public Event(Date time, int source, int target, int eventId, boolean alert) {
        this.time = time;
        this.source = source;
        this.target = target;
        this.eventId = eventId;
        this.alert = alert;
    }

    public void setId(int id) { this.id = id; }

    public void setTime(Date time) { this.time = time; }

    public void setSource(int source) { this.source = source; }

    public void setTarget(int target) { this.target = target; }

    public void setEventId(int eventId) { this.eventId = eventId; }

    public void setAlert(boolean alert) { this.alert = alert; }

    public int getId() { return id; }

    public Date getTime() { return time; }

    public int getSource() { return source; }

    public String getSourceString() { return IPUtil.ipToString(source); }

    public int getTarget() { return target; }

    public String getTargetString() { return IPUtil.ipToString(target); }

    public int getEventId() { return eventId; }

    public boolean getAlert() { return alert; }
    
}