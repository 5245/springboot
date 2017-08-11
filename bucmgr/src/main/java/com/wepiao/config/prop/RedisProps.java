package com.wepiao.config.prop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 接收application.yml中的redisProps下面的属性
 * @description 
 * @author sxk
 * @email sxk5245@126.com
 * @date 2017年7月27日
 */

@Data
@Component
@ConfigurationProperties(prefix = "redisProps.sharded")
public class RedisProps {

    @Value(value = "${redisProps.pool.maxActive}")
    private int                 maxActive;
    @Value(value = "${redisProps.pool.maxIdle}")
    private int                 maxIdle;
    @Value(value = "${redisProps.pool.minIdle}")
    private int                 minIdle;
    @Value(value = "${redisProps.number}")
    private int                 number;

    private String[]            arr;
    private int                 timeout;
    private boolean             testOnBorrow;
    private boolean             passwordNeed;
    private List<String>        ucHost      = new ArrayList<>(); //接收host里面的属性值
    private List<String>        clusterHost = new ArrayList<>(); //接收host里面的属性值
    private List<String>        tagHost     = new ArrayList<>(); //接收host里面的属性值
    private Map<String, String> dtagHost    = new HashMap<>();  //接收host里面的属性值

}
