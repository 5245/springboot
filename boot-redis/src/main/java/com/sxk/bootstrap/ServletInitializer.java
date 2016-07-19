package com.sxk.bootstrap;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

@Configuration
public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(BootRedisApplication.class);
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {

        AnnotationConfigWebApplicationContext annotationConfig = new AnnotationConfigWebApplicationContext();
        annotationConfig.scan("com.sxk.web.controller");
        //applicationContext.register(BootRedisApplication.class);

        DispatcherServlet dispatcherServlet = new DispatcherServlet();
        dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
        dispatcherServlet.setApplicationContext(annotationConfig);

        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("ApiDispatcherServlet", dispatcherServlet);
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/api/*");

        super.onStartup(servletContext);
    }

}
