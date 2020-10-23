package com.github.java.book.JavaMultiThreadInAction.ch3;

import com.github.java.Debug;

import java.util.Map;

/**
 * @author pengfei.zhao
 * @date 2020/10/21 8:41
 */
public class SafeObjPublishWhenStartingThread {
    private final Map<String, String> objectState;

    public SafeObjPublishWhenStartingThread(Map<String, String> objectState) {
        this.objectState = objectState;
        // 不在构造器中启动工作线程, 避免this溢出
    }

    private void init() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String value = objectState.get("someKey");
                Debug.info(value);
            }
        }).start();
    }

    public static SafeObjPublishWhenStartingThread newInstance(Map<String, String> objectState) {
        final SafeObjPublishWhenStartingThread instance = new SafeObjPublishWhenStartingThread(objectState);
        instance.init();
        return instance;
    }
}
