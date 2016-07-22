package com.sxk.bootstrap;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.alibaba.druid.pool.DruidDataSource;
import com.sxk.base.dao.RoutingDataSource;

/**
 * 
 * @description mybatis 多数据源配置
 * @author sxk
 * @date 2016年7月21日
 *
 */
@Configuration
@MapperScan(basePackages = MybatisConfiguration4RoutingDataSource.PACKAGE)
public class MybatisConfiguration4RoutingDataSource {

    public static final String          PACKAGE         = "com.sxk.dao";

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

    @Bean(name = "dataSourceLegacy")
    public DruidDataSource dataSourceLegacy() throws SQLException {
        DruidDataSource dataSourceLegacy = new DruidDataSource();
        // dataSourceLegacy.setUrl(jdbcBundle.getString("jdbc.legacy.url"));
        // dataSourceLegacy.setUsername(jdbcBundle.getString("jdbc.legacy.username"));
        // dataSourceLegacy.setPassword(jdbcBundle.getString("jdbc.legacy.password"));
        dataSourceLegacy.setUrl(jdbcBundle.getString("jdbc.url"));
        dataSourceLegacy.setUsername(jdbcBundle.getString("jdbc.username"));
        dataSourceLegacy.setPassword(jdbcBundle.getString("jdbc.password"));
        setDataSourceBaseParams(dataSourceLegacy);
        return dataSourceLegacy;
    }

    /**
     * 多数据源的情况下，必须有默认数据源 @Primary
     */
    @Bean
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

    /**
     * 多数据源的情况下，必须有默认数据源 @Primary
     */
    @Bean(name = "routingDataSource")
    //@Primary
    public RoutingDataSource routingDataSource(@Qualifier("dataSourceLegacy") DruidDataSource dataSourceLegacy,
            @Qualifier("dataSource") DruidDataSource dataSource, @Qualifier("dataSource1") DruidDataSource dataSource1,
            @Qualifier("dataSource2") DruidDataSource dataSource2, @Qualifier("dataSource3") DruidDataSource dataSource3,
            @Qualifier("dataSource4") DruidDataSource dataSource4, @Qualifier("dataSource5") DruidDataSource dataSource5,
            @Qualifier("dataSource6") DruidDataSource dataSource6, @Qualifier("dataSource7") DruidDataSource dataSource7) throws Exception {

        Map<Object, Object> targetDataSources = new HashMap<Object, Object>();

        targetDataSources.put("dataSourceLegacy", dataSourceLegacy);
        targetDataSources.put("dataSource0", dataSource);
        targetDataSources.put("dataSource1", dataSource1);
        targetDataSources.put("dataSource2", dataSource2);
        targetDataSources.put("dataSource3", dataSource3);
        targetDataSources.put("dataSource4", dataSource4);
        targetDataSources.put("dataSource5", dataSource5);
        targetDataSources.put("dataSource6", dataSource6);
        targetDataSources.put("dataSource7", dataSource7);

        RoutingDataSource routingDataSource = new RoutingDataSource();
        routingDataSource.setTargetDataSources(targetDataSources);// 该方法是AbstractRoutingDataSource的方法
        routingDataSource.setDefaultTargetDataSource(dataSource);// 默认的datasource设置为dataSource

        return routingDataSource;
    }

    //    @Bean
    //    @Primary
    //    public RoutingDataSource routingDataSource() throws Exception {
    //
    //        RoutingDataSource routingDataSource = new RoutingDataSource();
    //
    //        Map<Object, Object> targetDataSources = new HashMap<Object, Object>();
    //
    //        String url = null;
    //        String username = null;
    //        String password = null;
    //        for (int i = 0; i < dbSize; i++) {
    //            if (i == 0) {
    //                url = jdbcBundle.getString("jdbc.url");
    //                username = jdbcBundle.getString("jdbc.username");
    //                password = jdbcBundle.getString("jdbc.password");
    //            } else {
    //                url = jdbcBundle.getString("jdbc." + i + ".url");
    //                username = jdbcBundle.getString("jdbc." + i + ".username");
    //                password = jdbcBundle.getString("jdbc." + i + ".password");
    //            }
    //            DruidDataSource dataSource = new DruidDataSource();
    //            dataSource.setUrl(url);
    //            dataSource.setUsername(username);
    //            dataSource.setPassword(password);
    //
    //            setDataSourceBaseParams(dataSource);
    //            targetDataSources.put("dataSource" + i, dataSource);
    //            if (i == 0) {
    //                routingDataSource.setDefaultTargetDataSource(dataSource);// 默认的datasource设置为dataSource
    //            }
    //        }
    //
    //        routingDataSource.setTargetDataSources(targetDataSources);// 该方法是AbstractRoutingDataSource的方法
    //
    //        return routingDataSource;
    //    }

    /**
     * 根据数据源创建SqlSessionFactory
     * @param ds
     * @return
     * @throws Exception
     */
    @Bean
    @Primary
    public SqlSessionFactory sqlSessionFactory(@Qualifier("routingDataSource") RoutingDataSource routingDataSource) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(routingDataSource);// 指定数据源(这个必须有，否则报错)

        setSessionFactoryScanPkg(sessionFactory);

        return sessionFactory.getObject();
    }

}