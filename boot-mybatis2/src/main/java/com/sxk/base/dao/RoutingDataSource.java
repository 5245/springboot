package com.sxk.base.dao;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import com.sxk.utils.DBHash;

public class RoutingDataSource extends AbstractRoutingDataSource {

    private static final ThreadLocal<String> dataSourceKey       = new InheritableThreadLocal<String>();

    private static final int                 dbCount             = 8;

    private static final int                 tableCount          = 128;

    private static final String              dataSourceKeyPrefix = "dataSource";

    public static void setDataSourceKey(String dataSource) {
        dataSourceKey.set(dataSource);
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return dataSourceKey.get();
    }

    public static String getDbIndex(String key) {
        return dataSourceKeyPrefix + getTableIndex(key) / tableCount;
    }

    public static String getDbIndex(long key) {
        return dataSourceKeyPrefix + getTableIndex(key) / tableCount;
    }

    /**
     * 根据key(String类型)获取表的编号
     * @param key 用于分表的key
     * @return 表的编号
     */
    public static int getTableIndex(String key) {
        return DBHash.getHash((key + "").getBytes(), dbCount, tableCount);
    }

    /**
     * 根据key(long 类型)获取表的编号
     * @param key 用于分表的key
     * @return 表的编号
     */
    public static int getTableIndex(long key) {
        return DBHash.getHash(Long.toString(key).getBytes(), dbCount, tableCount);
    }
}
