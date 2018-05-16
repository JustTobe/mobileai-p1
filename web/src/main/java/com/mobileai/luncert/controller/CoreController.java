package com.mobileai.luncert.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("data")
public class CoreController {


    // administor interfaces
    @RequestMapping("/getVirusGraph")
    public String getVirusGraph() {
        return null;
    }

}