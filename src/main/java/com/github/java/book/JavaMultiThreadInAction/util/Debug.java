package com.github.java.book.JavaMultiThreadInAction.util;

import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author pengfei.zhao
 * @date 2020/10/18 16:53
 */
public class Debug {
    private static ThreadLocal<SimpleDateFormat> sdfWrapper = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        }

    };

    enum Label {
        INFO("INFO"),
        ERR("ERROR");
        String name;

        Label(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    // public static void info(String message) {
    // printf(Label.INFO, "%s", message);
    // }

    public static void info(String format, Object... args) {
        printf(Label.INFO, format, args);
    }

    public static void info(boolean message) {
        info("%s", message);
    }

    public static void info(int message) {
        info("%d", message);
    }

    public static void error(String message, Object... args) {
        printf(Label.ERR, message, args);
    }

    public static void printf(Label label, String format, Object... args) {
        SimpleDateFormat sdf = sdfWrapper.get();
        @SuppressWarnings("resource") final PrintStream ps = label == Label.INFO ? System.out : System.err;
        ps.printf('[' + sdf.format(new Date()) + "][" + label.getName()
                + "]["
                + Thread.currentThread().getName() + "]:" + format + " %n", args);
    }
}
