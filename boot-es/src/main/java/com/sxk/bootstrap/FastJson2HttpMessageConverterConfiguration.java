package com.sxk.bootstrap;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter4;

@Configuration
@ConditionalOnClass({ FastJsonHttpMessageConverter4.class })
@ConditionalOnProperty(name = { "spring.http.converters.preferred-json-mapper" }, havingValue = "fastjson", matchIfMissing = true)
public class FastJson2HttpMessageConverterConfiguration {

    private static final SerializerFeature[] features = { SerializerFeature.PrettyFormat, SerializerFeature.WriteEnumUsingToString,
            SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty };

    protected FastJson2HttpMessageConverterConfiguration() {
    }

    /* 
     * 官方文档说的1.2.10以后，会有两个方法支持HttpMessageconvert
     * 一个是FastJsonHttpMessageConverter 支持4.2以下的版本
     * 一个是FastJsonHttpMessageConverter4 支持4.2以上的版本
     */

    @Bean
    @ConditionalOnMissingBean({ FastJsonHttpMessageConverter4.class })
    public FastJsonHttpMessageConverter4 fastJsonHttpMessageConverter() {
        FastJsonHttpMessageConverter4 converter = new FastJsonHttpMessageConverter4();
        ValueFilter valueFilter = new ValueFilter() {
            public Object process(Object o, String s, Object o1) {
                if (null == o1) {
                    o1 = "";
                }
                return o1;
            }
        };
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(features);
        fastJsonConfig.setSerializeFilters(valueFilter);

        //fastJsonConfig.getSerializeConfig().put(Json.class, SwaggerJsonSerializer.instance);

        converter.setFastJsonConfig(fastJsonConfig);

        return converter;
    }

    /* @Bean
     @ConditionalOnMissingBean({ FastJsonHttpMessageConverter.class })
     public FastJsonHttpMessageConverter fastJsonHttpMessageConverter() {
         FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
         ValueFilter valueFilter = new ValueFilter() {
             public Object process(Object o, String s, Object o1) {
                 if (null == o1) {
                     o1 = "";
                 }
                 return o1;
             }
         };
         converter.setFilters(valueFilter);
         converter.setFeatures(features);
         return converter;
     }*/

}
