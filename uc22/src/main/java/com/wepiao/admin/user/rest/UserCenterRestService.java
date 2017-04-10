package com.wepiao.admin.user.rest;

import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

//import org.glassfish.jersey.server.spring.scope.RequestContextFilter;

public class UserCenterRestService extends ResourceConfig {

    private String              apiPath    = "/user-center/uc/v1";

    private static final String basePakage = "com.wepiao.admin.user.rest.controller";

    public UserCenterRestService() {
        // register(RequestContextFilter.class);
        packages(basePakage).register(MyObjectMapperProvider.class).register(JacksonFeature.class) //
                .register(ApiListingResource.class).register(SwaggerSerializers.class);

        BeanConfig config = new BeanConfig();
        config.setConfigId("uc-center");
        config.setTitle("uc-center api docment");
        config.setVersion("v1");
        config.setContact("jinsong");
        config.setSchemes(new String[] { "http", "https" });
        config.setBasePath(this.apiPath);
        config.setResourcePackage(basePakage);
        config.setPrettyPrint(true);
        config.setScan(true);

    }
}
