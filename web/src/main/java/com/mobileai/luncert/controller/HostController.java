package com.mobileai.luncert.controller;

import com.mobileai.luncert.dao.neo4j.HostRepository;
import com.mobileai.luncert.dao.neo4j.node.Host;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@RestController
@RequestMapping("/host")
public class HostController {

    @Autowired
    private HostRepository repo;

    private String response(int status, String data) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", status);
        jsonObject.put("data", data);
        return jsonObject.toString();
    }
    
    @RequestMapping("/all")
    public String fetchAll() {
        JSONArray data = new JSONArray();
        for (Host host : repo.findAll()) data.add(host);
        return response(200, data.toString());
    }
}