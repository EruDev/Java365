package com.github.java.fileoper.dao;

import com.github.java.fileoper.entity.DemoData;
import com.mysql.cj.jdbc.Driver;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author pengfei.zhao
 * @date 2020/11/21 16:28
 */
@Service
public class DemoDAO {

    private JdbcTemplate jdbcTemplate;

    {
        HikariConfig config = new HikariConfig();
        config.setUsername("root");
        config.setPassword("root");
        config.setJdbcUrl("jdbc:mysql://localhost:3306/demo_data?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&useSSL=false");
        config.setDriverClassName(Driver.class.getName());
        HikariDataSource hikariDataSource = new HikariDataSource(config);
        this.jdbcTemplate = new JdbcTemplate(hikariDataSource);
    }

    public DemoDAO() {

    }

    public void save(List<DemoData> list) {
        // 如果是mybatis,尽量别直接调用多次insert,自己写一个mapper里面新增一个方法batchInsert,所有数据一次性插入
        String item = "('%s','%s','%s','%s')";
        StringBuilder sql = new StringBuilder("INSERT INTO demo_data(lf_sid,fee_name,amount,country) VALUES ");
        for (int i = 0; i < list.size(); i++) {
            DemoData data = list.get(i);
            sql.append(String.format(item, data.getLfSid(), data.getFeeName(), data.getAmount(), data.getCountry()))
                    .append(",");
        }
        jdbcTemplate.batchUpdate(sql.substring(0, sql.lastIndexOf(",")));
    }
}
