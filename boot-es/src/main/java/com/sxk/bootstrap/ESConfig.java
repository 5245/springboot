package com.sxk.bootstrap;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

@Configuration
public class ESConfig {

    @Bean
    public ElasticsearchTemplate elasticsearchTemplate() {
//
//        Settings.Builder sBuidler = Settings.builder();
//        sBuidler.put("", "");
//
//        TransportClient.Builder tscBuidler = TransportClient.builder();
//        tscBuidler.settings(sBuidler.build());
//
//        tscBuidler.addPlugin(new EsPluginTest<EsPlugin>());
//
//        Client esClient = tscBuidler.build();
//        return new ElasticsearchTemplate(esClient);
        return null;
    }
}
