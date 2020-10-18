package com.github.java.environment;

import java.util.Map;

/**
 * @author pengfei.zhao
 * @date 2020/10/17 14:52
 */
public class EnvMap {
    public static void main(String[] args) {
        Map<String, String> envs = System.getenv();
        for (String key : envs.keySet()) {
            System.out.format("%s=%s%n", key, envs.get(key));
        }
    }
}
