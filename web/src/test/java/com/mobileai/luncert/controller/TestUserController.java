package com.mobileai.luncert.controller;

import java.security.NoSuchAlgorithmException;
import java.util.Date;

import com.mobileai.luncert.utils.CyperHelper;

import org.junit.Test;

public class TestUserController {

    private String genMailId() throws NoSuchAlgorithmException {
        StringBuilder builder = new StringBuilder();
        builder.append(new Date().toString()).append(this.hashCode());
        return CyperHelper.genKeyFromString(builder.toString());
    }

    @Test
    public void testGenMailId() throws NoSuchAlgorithmException {
        System.out.println(genMailId());
    }

}