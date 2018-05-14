package com.mobileai.utils;

import com.mobileai.luncert.utils.IPUtil;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class TestIPUtil {
    
    @Test
    public void test() {
        String ipStr = "127.0.0.1";
        int ip = IPUtil.ipToInt(ipStr);

        System.out.println(ip);
        System.out.println(IPUtil.ipToString(ip));
    }
    
}