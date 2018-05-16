package com.mobileai.luncert.utils;

import net.sf.json.JSONObject;

public class ResponseWrapper {

    public static String response(int code, String desc, Object data) {
        JSONObject json = new JSONObject();
        json.put("code", code);
        if (desc != null) json.put("desc", desc);
        if (data != null) json.put("data", data);
        return json.toString();
    }

}