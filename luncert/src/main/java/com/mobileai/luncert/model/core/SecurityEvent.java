package com.mobileai.luncert.model.core;

import com.mobileai.luncert.model.normal.NormalEvent;

public class SecurityEvent extends NormalEvent {

    // 源ip
    private int source;

    // 目的ip
    private int target;

    public SecurityEvent(int source, int target, int eventId, String eventType) {
        this.source = source;
        this.target = target;
        this.eventId = eventId;
        this.eventType = eventType;
    }
    
    public void setSource(int source) { this.source = source; }

    public void setTarget(int target) { this.target = target; }

    public int getSource() { return source; }

    public int getTarget() { return target; }

}