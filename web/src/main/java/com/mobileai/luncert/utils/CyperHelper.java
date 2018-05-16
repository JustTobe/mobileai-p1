package com.mobileai.luncert.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CyperHelper {

    public static String genKeyFromString(String raw) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] input = raw.getBytes();
        md.update(input);
        byte[] md5Bytes = md.digest();
        BigInteger bigInteger = new BigInteger(1, md5Bytes);
        return bigInteger.toString(16);
    }
}