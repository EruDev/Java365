package com.github.java.environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

/**
 * @author pengfei.zhao
 * @date 2020/10/17 14:35
 */
public class PropertiesDemo {
    public static void main(String[] args) throws IOException {
        Properties properties = new Properties();
        // 1.获取resources下的配置文件
        InputStream is = PropertiesDemo.class.getClassLoader().getResourceAsStream("application.properties");
        properties.load(is);
        System.out.println(properties);

        // 2.先获取到文件 再转换成流
        URL url = PropertiesDemo.class.getClassLoader().getResource("application.properties");
        File file = null;
        if (url != null){
            file = new File(url.getFile());
        }
        properties.load(new FileInputStream(file));
        System.out.println(properties);
    }
}
