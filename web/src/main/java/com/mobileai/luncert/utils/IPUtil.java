package com.mobileai.luncert.utils;


/**
 * 小端模式
 */
public class IPUtil {

    public static String ipToString(int ip) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 4; i++) builder.append('.').append((ip >> (8 * i) & 0xff));
        return builder.substring(1);
    }

    public static int ipToInt(String ip) {
        String[] buf = ip.split("\\.");
        int ret = 0;
        for (int i = 0; i < 4; i++) ret += Long.valueOf(buf[i]) << (8 * i);
        return ret;
    }

}