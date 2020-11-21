package com.github.java.fileoper.utils;

import java.io.File;

/**
 * @author pengfei.zhao
 * @date 2020/11/21 16:00
 */
public class FileUtils {

    public static String getPath() {
        return FileUtils.class.getResource("/").getPath();
    }

    public static File readFile(String pathName) {
        return new File(getPath(), pathName);
    }
}
