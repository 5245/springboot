package com.sxk.bootstrap;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ResourceBundle;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * 
 * @description mybatis 多数据源配置
 * @author sxk
 * @date 2016年7月21日
 *
 */
@Configuration
@MapperScan(basePackages = MybatisConfiguration.PACKAGE)
public class MybatisConfiguration {

    public static final String          PACKAGE         = "com.sxk.dao.mapper";

    public static final String          mapperXml       = "classpath:com/sxk/dao/mapper/*Mapper.xml";

    public static final int             dbSize          = 8;

    private static final ResourceBundle jdbcBundle      = ResourceBundle.getBundle("jdbc");

    private static Resource[]           mapperLocations = null;

    static {
        try {
            mapperLocations = new PathMatchingResourcePatternResolver().getResources(mapperXml);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //设置基本参数
    private void setDataSourceBaseParams(DruidDataSource dataSource) {
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setInitialSize(1);
        dataSource.setMinIdle(1);
        dataSource.setMaxActive(20);
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
        sessionFactory.setTypeAliasesPackage(PACKAGE);// 指定基包
        sessionFactory.setMapperLocations(mapperLocations);
    }

    @Bean(name = "sqlSessionLegacyUsers")
    public SqlSession sqlSessionLegacyUsers() throws Exception {
        //        String url = jdbcBundle.getString("jdbc.legacy.url");
        //        String username = jdbcBundle.getString("jdbc.legacy.username");
        //        String password = jdbcBundle.getString("jdbc.legacy.password");

        String url = jdbcBundle.getString("jdbc.url");
        String username = jdbcBundle.getString("jdbc.username");
        String password = jdbcBundle.getString("jdbc.password");

        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        setDataSourceBaseParams(dataSource);

        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        setSessionFactoryScanPkg(sessionFactory);
        return new SqlSessionTemplate(sessionFactory.getObject());
    }

    @Bean(name = "sqlSessions")
    public SqlSession[] sqlSessions() throws Exception {
        SqlSession[] sqlSessions = new SqlSession[dbSize];
        String url = null;
        String username = null;
        String password = null;
        for (int i = 0; i < dbSize; i++) {
            if (i == 0) {
                url = jdbcBundle.getString("jdbc.url");
                username = jdbcBundle.getString("jdbc.username");
                password = jdbcBundle.getString("jdbc.password");
            } else {
                url = jdbcBundle.getString("jdbc." + i + ".url");
                username = jdbcBundle.getString("jdbc." + i + ".username");
                password = jdbcBundle.getString("jdbc." + i + ".password");
            }
            DruidDataSource dataSource = new DruidDataSource();
            dataSource.setUrl(url);
            dataSource.setUsername(username);
            dataSource.setPassword(password);

            setDataSourceBaseParams(dataSource);

            final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
            sessionFactory.setDataSource(dataSource);
            setSessionFactoryScanPkg(sessionFactory);
            sqlSessions[i] = new SqlSessionTemplate(sessionFactory.getObject());
        }
        return sqlSessions;
    }
}