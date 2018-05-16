package com.mobileai.luncert.controller;

import com.mobileai.luncert.annotation.AuthCheck;
import com.mobileai.luncert.service.EventRecords;
import com.mobileai.luncert.utils.ResponseWrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/event")
public class EventController {

    @Autowired
    EventRecords eventRecords;

    // @AuthCheck
    @RequestMapping("/getLatest")
    public String getLatest() {
        return ResponseWrapper.response(200, null, eventRecords.getLatest());
    }

}