package com.mobileai.luncert.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ResourceHelper {

    public static String getResource(String path) throws IOException {
        File file = new File(ResourceHelper.class.getClassLoader().getResource(path).getPath());
        byte[] buf = new byte[new Long(file.length()).intValue()];
        FileInputStream in = new FileInputStream(file);
        in.read(buf);
        in.close();
        return new String(buf);
    }

}