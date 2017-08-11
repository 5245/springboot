/**
 * 
 */
package com.wepiao.user.common.util;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jin song
 *
 */
public class ConfigPropertiesUtil {

    private Logger                      logger   = LoggerFactory.getLogger(getClass());

    private Map<String, String>         configs;

    private static final Object         lock     = new Object();
    private static ConfigPropertiesUtil instance;
    private static boolean              initDone = false;

    private ConfigPropertiesUtil() {
    }

    public static ConfigPropertiesUtil getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new ConfigPropertiesUtil();
                    instance.init();
                    initDone = true;
                }
            }
        }
        return instance;
    }

    public void init() {
        if (initDone) {
            return;
        }
        Properties props = new Properties();
        try {
            InputStream in = this.getClass().getClassLoader().getResourceAsStream("conf/config.properties");
            props.load(in);
            Enumeration<?> en = props.propertyNames();
            configs = new ConcurrentHashMap<String, String>();
            while (en.hasMoreElements()) {
                String key = (String) en.nextElement();
                String value = props.getProperty(key);
                configs.put(key, value);
            }

            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Map<String, String> getConfigs() {
        return configs;
    }

    public String get(String key) {
        return configs.get(key);
    }

    public String get(String key, String defaultStr) {
        String result = configs.get(key);
        if (result == null) {
            result = defaultStr;
        }
        return result;
    }

    public int get(String key, int defaultInteger) {
        int result = 0;
        String resultStr = configs.get(key);
        if (resultStr == null) {
            result = defaultInteger;
        } else {
            result = Integer.parseInt(resultStr);
        }
        return result;
    }

    public static void main(String[] args) {
        ConfigPropertiesUtil config = ConfigPropertiesUtil.getInstance();
        System.out.println(config.get("rabbitmq.order.server.host"));
    }
}
