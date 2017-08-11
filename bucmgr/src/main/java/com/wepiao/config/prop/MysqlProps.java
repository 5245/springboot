package com.wepiao.config.prop;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 接收application.yml中的msyqlProps下面的属性
 * @description 
 * @author sxk
 * @email sxk5245@126.com
 * @date 2017年7月27日
 */

@Data
@Component
@ConfigurationProperties(prefix = "mysqlProps")
public class MysqlProps {
    private int            dbCount;
    private int            tableCount;
    private JdbcProp       idBackup;
    private List<JdbcProp> uc = new ArrayList<>();

    @Data
    public static class JdbcProp {
        private String url;
        private String username;
        private String password;
    }

}
