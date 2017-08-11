package com.sxk.bootstrap;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ResourceBundle;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
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
//@Configuration
//@MapperScan(basePackages = MybatisConfiguration2.PACKAGE)
public class MybatisConfiguration2 {

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

        String url = jdbcBundle.getString("jdbc.idBackup.url");
        String username = jdbcBundle.getString("jdbc.idBackup.username");
        String password = jdbcBundle.getString("jdbc.idBackup.password");

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

    /**
        * 多数据源的情况下，必须有默认数据源 @Primary
        */
    @Bean
    @Primary
    public DruidDataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(jdbcBundle.getString("jdbc.url"));
        dataSource.setUsername(jdbcBundle.getString("jdbc.username"));
        dataSource.setPassword(jdbcBundle.getString("jdbc.password"));
        setDataSourceBaseParams(dataSource);
        return dataSource;
    }

    @Bean
    public DruidDataSource dataSource1() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(jdbcBundle.getString("jdbc.1.url"));
        dataSource.setUsername(jdbcBundle.getString("jdbc.1.username"));
        dataSource.setPassword(jdbcBundle.getString("jdbc.1.password"));
        setDataSourceBaseParams(dataSource);
        return dataSource;
    }

    @Bean
    public DruidDataSource dataSource2() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(jdbcBundle.getString("jdbc.2.url"));
        dataSource.setUsername(jdbcBundle.getString("jdbc.2.username"));
        dataSource.setPassword(jdbcBundle.getString("jdbc.2.password"));
        setDataSourceBaseParams(dataSource);
        return dataSource;
    }

    @Bean
    public DruidDataSource dataSource3() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(jdbcBundle.getString("jdbc.3.url"));
        dataSource.setUsername(jdbcBundle.getString("jdbc.3.username"));
        dataSource.setPassword(jdbcBundle.getString("jdbc.3.password"));
        setDataSourceBaseParams(dataSource);
        return dataSource;
    }

    @Bean
    public DruidDataSource dataSource4() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(jdbcBundle.getString("jdbc.4.url"));
        dataSource.setUsername(jdbcBundle.getString("jdbc.4.username"));
        dataSource.setPassword(jdbcBundle.getString("jdbc.4.password"));
        setDataSourceBaseParams(dataSource);
        return dataSource;
    }

    @Bean
    public DruidDataSource dataSource5() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(jdbcBundle.getString("jdbc.5.url"));
        dataSource.setUsername(jdbcBundle.getString("jdbc.5.username"));
        dataSource.setPassword(jdbcBundle.getString("jdbc.5.password"));
        setDataSourceBaseParams(dataSource);
        return dataSource;
    }

    @Bean
    public DruidDataSource dataSource6() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(jdbcBundle.getString("jdbc.6.url"));
        dataSource.setUsername(jdbcBundle.getString("jdbc.6.username"));
        dataSource.setPassword(jdbcBundle.getString("jdbc.6.password"));
        setDataSourceBaseParams(dataSource);
        return dataSource;
    }

    @Bean
    public DruidDataSource dataSource7() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(jdbcBundle.getString("jdbc.7.url"));
        dataSource.setUsername(jdbcBundle.getString("jdbc.7.username"));
        dataSource.setPassword(jdbcBundle.getString("jdbc.7.password"));
        setDataSourceBaseParams(dataSource);
        return dataSource;
    }

    @Bean(name = "sqlSessionLegacyUsers")
    public SqlSession sqlSessionLegacyUsers(@Qualifier("dataSourceLegacy") DruidDataSource dataSourceLegacy) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSourceLegacy);
        setSessionFactoryScanPkg(sessionFactory);
        return new SqlSessionTemplate(sessionFactory.getObject());
    }

    @Bean(name = "sqlSessions")
    public SqlSession[] sqlSessions(@Qualifier("dataSource") DruidDataSource dataSource, @Qualifier("dataSource1") DruidDataSource dataSource1,
            @Qualifier("dataSource2") DruidDataSource dataSource2, @Qualifier("dataSource3") DruidDataSource dataSource3,
            @Qualifier("dataSource4") DruidDataSource dataSource4, @Qualifier("dataSource5") DruidDataSource dataSource5,
            @Qualifier("dataSource6") DruidDataSource dataSource6, @Qualifier("dataSource7") DruidDataSource dataSource7) throws Exception {

        SqlSession[] sqlSessions = new SqlSession[8];

        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        setSessionFactoryScanPkg(sessionFactory);
        sqlSessions[0] = new SqlSessionTemplate(sessionFactory.getObject());

        final SqlSessionFactoryBean sessionFactory1 = new SqlSessionFactoryBean();
        sessionFactory1.setDataSource(dataSource1);
        setSessionFactoryScanPkg(sessionFactory1);
        sqlSessions[1] = new SqlSessionTemplate(sessionFactory1.getObject());

        final SqlSessionFactoryBean sessionFactory2 = new SqlSessionFactoryBean();
        sessionFactory2.setDataSource(dataSource2);
        setSessionFactoryScanPkg(sessionFactory2);
        sqlSessions[2] = new SqlSessionTemplate(sessionFactory2.getObject());

        final SqlSessionFactoryBean sessionFactory3 = new SqlSessionFactoryBean();
        sessionFactory3.setDataSource(dataSource3);
        setSessionFactoryScanPkg(sessionFactory3);
        sqlSessions[3] = new SqlSessionTemplate(sessionFactory3.getObject());

        final SqlSessionFactoryBean sessionFactory4 = new SqlSessionFactoryBean();
        sessionFactory4.setDataSource(dataSource4);
        setSessionFactoryScanPkg(sessionFactory4);
        sqlSessions[4] = new SqlSessionTemplate(sessionFactory4.getObject());

        final SqlSessionFactoryBean sessionFactory5 = new SqlSessionFactoryBean();
        sessionFactory5.setDataSource(dataSource5);
        setSessionFactoryScanPkg(sessionFactory5);
        sqlSessions[5] = new SqlSessionTemplate(sessionFactory5.getObject());

        final SqlSessionFactoryBean sessionFactory6 = new SqlSessionFactoryBean();
        sessionFactory6.setDataSource(dataSource6);
        setSessionFactoryScanPkg(sessionFactory6);
        sqlSessions[6] = new SqlSessionTemplate(sessionFactory6.getObject());

        final SqlSessionFactoryBean sessionFactory7 = new SqlSessionFactoryBean();
        sessionFactory7.setDataSource(dataSource7);
        setSessionFactoryScanPkg(sessionFactory7);
        sqlSessions[7] = new SqlSessionTemplate(sessionFactory7.getObject());
        return sqlSessions;
    }
}