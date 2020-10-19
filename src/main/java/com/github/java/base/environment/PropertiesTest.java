package com.github.java.environment;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

/**
 * @author pengfei.zhao
 * @date 2020/10/17 15:11
 */
public class PropertiesTest {
    public static void main(String[] args) throws IOException {
        URL url = PropertiesTest.class.getClassLoader().getResource("myProperties.txt");
        FileInputStream fis = new FileInputStream(url.getFile());
        Properties properties = new Properties();
        properties.load(fis);
        System.setProperties(properties);
        System.getProperties().list(System.out);
    }
}
