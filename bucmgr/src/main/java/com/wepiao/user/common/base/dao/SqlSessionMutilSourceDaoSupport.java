package com.wepiao.user.common.base.dao;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DaoSupport;
import org.springframework.stereotype.Component;

import com.wepiao.user.common.util.DBHash;

/**
 * @author Jin Song
 * 分表规则
 * 端口0：0~127
 * 端口1：128~255
 * 端口2：256~383
 * 端口3：384~511
 * ...
 */
@Component
public class SqlSessionMutilSourceDaoSupport extends DaoSupport {
    /**
     * 库个数
     */
    private int          dbCount    = 8;
    /**
     * 表个数
     */
    private int          tableCount = 128;

    @Autowired
    private SqlSession   sqlSessionsIdBackup;

    //可以注入bean,也可以注入List<T>,也可以注入数组
    @Resource
    private SqlSession[] sqlSessions;

    public SqlSession getSqlSessionLegacyUsers() {
        return sqlSessionsIdBackup;
    }
    public SqlSession getSqlSessionsIdBackup() {
        return sqlSessionsIdBackup;
    }

    public SqlSession[] getSqlSessionList() {
        return this.sqlSessions;
    }

    public SqlSession getSqlSession(String key) {
        return this.sqlSessions[getDbIndex(key)];
    }

    public SqlSession getSqlSession(long key) {
        return this.sqlSessions[getDbIndex(key)];
    }

    public SqlSession getSqlSessionByIndex(Integer index) {
        return this.sqlSessions[index];
    }

    /**
     * 根据key(String类型)获取数据库的编号
     * @param key 用于分库的key
     * @return 数据库的编号
     */
    public int getDbIndex(String key) {
        return getTableIndex(key) / tableCount;
    }

    /**
     * 根据key(long 类型)获取数据库的编号
     * @param key 用于分库的key
     * @return 数据库的编号
     */
    public int getDbIndex(long key) {
        return getTableIndex(key) / tableCount;
    }

    /**
     * 根据key(String类型)获取表的编号
     * @param key 用于分表的key
     * @return 表的编号
     */
    public int getTableIndex(String key) {
        return DBHash.getHash((key + "").getBytes(), dbCount, tableCount);
    }

    /**
     * 根据key(long 类型)获取表的编号
     * @param key 用于分表的key
     * @return 表的编号
     */
    public int getTableIndex(long key) {
        return DBHash.getHash(Long.toString(key).getBytes(), dbCount, tableCount);
    }

    public int getDbCount() {
        return dbCount;
    }

    public int getTableCount() {
        return tableCount;
    }

    @Override
    protected void checkDaoConfig() throws IllegalArgumentException {

    }

    @Override
    protected void initDao() throws Exception {

    }

}