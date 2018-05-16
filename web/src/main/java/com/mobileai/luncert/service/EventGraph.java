package com.mobileai.luncert.service;

import java.util.List;

import com.mobileai.luncert.model.neo4j.EventRepository;
import com.mobileai.luncert.model.neo4j.node.Event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EventGraph {

    @Autowired
    EventRepository eventRepository;

    @Transactional
    public List<Event> fetchAllNode() {
        return null;
    }
}