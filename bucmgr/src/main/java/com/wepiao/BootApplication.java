package com.wepiao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.aop.AopAutoConfiguration;
import org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration;
import org.springframework.boot.autoconfigure.dao.PersistenceExceptionTranslationAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.boot.autoconfigure.web.DispatcherServletAutoConfiguration;
import org.springframework.boot.autoconfigure.web.EmbeddedServletContainerAutoConfiguration;
import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.web.HttpEncodingAutoConfiguration;
import org.springframework.boot.autoconfigure.web.HttpMessageConvertersAutoConfiguration;
import org.springframework.boot.autoconfigure.web.MultipartAutoConfiguration;
import org.springframework.boot.autoconfigure.web.ServerPropertiesAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebClientAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.websocket.WebSocketAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

//@SpringBootApplication(excludeName = { "org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration" })
//@SpringBootApplication(exclude=DataSourceAutoConfiguration.class)
//@SpringBootApplication
@ServletComponentScan //扫描filter和servlet
@ComponentScan
@Configuration
@Import({
    AopAutoConfiguration.class,
    //DataSourceAutoConfiguration.class,
    //DataSourceTransactionManagerAutoConfiguration.class,
    DispatcherServletAutoConfiguration.class,
    EmbeddedServletContainerAutoConfiguration.class,
    ErrorMvcAutoConfiguration.class,
    HttpEncodingAutoConfiguration.class,
    HttpMessageConvertersAutoConfiguration.class,
    //MultipartAutoConfiguration.class,
    //PersistenceExceptionTranslationAutoConfiguration.class,
    //PropertyPlaceholderAutoConfiguration.class,
    RedisAutoConfiguration.class,
    ServerPropertiesAutoConfiguration.class,
    //SpringDataWebAutoConfiguration.class,
    //TransactionAutoConfiguration.class,
    ValidationAutoConfiguration.class,
    //WebClientAutoConfiguration.class,
    //WebSocketAutoConfiguration.class,
    WebMvcAutoConfiguration.class
})
public class BootApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(BootApplication.class, args);
    }
}
