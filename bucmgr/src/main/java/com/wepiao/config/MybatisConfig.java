package com.wepiao.config;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.alibaba.druid.pool.DruidDataSource;
import com.wepiao.config.prop.MysqlProps;
import com.wepiao.config.prop.MysqlProps.JdbcProp;

/**
 * 
 * @description mybatis 多数据源配置
 * @author sxk
 * @date 2016年7月21日
 *
 */
@Configuration
@MapperScan(basePackages = MybatisConfig.PACKAGE)
public class MybatisConfig {

    public static final String PACKAGE   = "com.wepiao.user.common.dao.mapper";
    
    public static final String mapperXml = "classpath:com/wepiao/user/common/dao/mapper/*Mapper.xml";

    private static Resource[]  mapperLocations;
    static {
        try {
            mapperLocations = new PathMatchingResourcePatternResolver().getResources(mapperXml);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Autowired
    private MysqlProps         mysqlProps;

    //设置基本参数
    private void setDataSourceBaseParams(DruidDataSource dataSource) {
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setInitialSize(1);
        dataSource.setMinIdle(1);
        dataSource.setMaxActive(20);
        dataSource.setMaxWait(30000);
        dataSource.setTimeBetweenEvictionRunsMillis(6000);
        dataSource.setMinEvictableIdleTimeMillis(300000);
        dataSource.setValidationQuery("select 'x'");
        dataSource.setTestWhileIdle(true);
        dataSource.setTestOnBorrow(true);
        dataSource.setTestOnReturn(false);
        try {
            dataSource.setFilters("stat");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置sessionFactory扫描Mapper.xml文件路径
     * @param sessionFactory
     *
     */
    private void setSessionFactoryScanPkg(SqlSessionFactoryBean sessionFactory) {
        //下边两句仅仅用于*.xml文件，如果整个持久层操作不需要使用到xml文件的话（只用注解就可以搞定），则不加
        sessionFactory.setTypeAliasesPackage(PACKAGE); //指定基包
        sessionFactory.setMapperLocations(mapperLocations);
    }

    @Bean(name = "sqlSessionsIdBackup")
    public SqlSession sqlSessionsIdBackup() throws Exception {
        JdbcProp jdbcProp = mysqlProps.getIdBackup();

        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(jdbcProp.getUrl());
        dataSource.setUsername(jdbcProp.getUsername());
        dataSource.setPassword(jdbcProp.getPassword());

        setDataSourceBaseParams(dataSource);

        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        setSessionFactoryScanPkg(sessionFactory);
        return new SqlSessionTemplate(sessionFactory.getObject());
    }

    @Bean(name = "sqlSessions")
    public SqlSession[] sqlSessions() throws Exception {
        List<JdbcProp> ucProp = mysqlProps.getUc();
        int dbCount = mysqlProps.getDbCount();
        if (ucProp.size() != mysqlProps.getDbCount()) {
            throw new Exception("数据库连接和dbCount不等");
        }
        SqlSession[] sqlSessions = new SqlSession[dbCount];
        for (int i = 0; i < dbCount; i++) {
            JdbcProp jdbcProp = ucProp.get(i);
            DruidDataSource dataSource = new DruidDataSource();
            dataSource.setUrl(jdbcProp.getUrl());
            dataSource.setUsername(jdbcProp.getUsername());
            dataSource.setPassword(jdbcProp.getPassword());

            setDataSourceBaseParams(dataSource);

            final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
            sessionFactory.setDataSource(dataSource);
            setSessionFactoryScanPkg(sessionFactory);
            sqlSessions[i] = new SqlSessionTemplate(sessionFactory.getObject());
        }
        return sqlSessions;
    }
}