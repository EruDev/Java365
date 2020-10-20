package com.github.java.book.JavaMultiThreadInAction.ch3;

import com.github.java.book.JavaMultiThreadInAction.util.Debug;
import com.github.java.book.JavaMultiThreadInAction.util.Tools;

import java.util.HashMap;
import java.util.Map;

/**
 * @author pengfei.zhao
 * @date 2020/10/20 13:53
 */
public class StaticVisibilityExample {
    private static Map<String, String> taskConfigs;

    static {
        Debug.info("The class being initialized...");
        taskConfigs = new HashMap<>(); // 1
        taskConfigs.put("url", "https://github.com/EruDev"); // 2
        taskConfigs.put("timeout", String.valueOf(60 * 1000)); // 3
    }

    public static void changeConfig(String url, int timeout) {
        taskConfigs = new HashMap<>(); // 4
        taskConfigs.put("url", url); // 5
        taskConfigs.put("timeout", String.valueOf(timeout)); // 6
    }

    public static void init() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                // 能看到 1~3 的操作结果, 但不能保证看到 4~6 的操作结果
                String url = taskConfigs.get("url");
                String timeout = taskConfigs.get("timeout");
                doTask(url, Integer.valueOf(timeout));
            }
        });
        thread.start();
    }

    private static void doTask(String url, Integer timeout) {
        // 模拟业务操作耗时
        Tools.randomPause(50);
    }
}
