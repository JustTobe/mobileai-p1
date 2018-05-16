package com.mobileai.luncert.service;


import java.util.List;

import com.mobileai.luncert.model.mysql.EventMapper;
import com.mobileai.luncert.model.mysql.entity.Event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventRecords {

    @Autowired
    EventMapper eventMapper;

    public Event getLatest() {
        List<Event> ret = eventMapper.fetchN(1);
        if (ret != null && ret.size() == 1) return ret.get(0);
        else return null;
    }
    
}