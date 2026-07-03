package com.digixmed.icu.dao;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDbFactory;


@Configuration
public class MongoConfig {
    @Value("${spring.data.mongodb.smartcare.uri}")
    private String suri;

    @Value("${spring.data.mongodb.datacenter.uri}")
    private String duri;

    @Bean(name = {"smartcareMongoClient"})
    public MongoClient smartcareMongoClient() {
        return MongoClients.create(this.suri);
    }

    @Bean(name = {"smartcareFactory"})
    @Primary
    public SimpleMongoClientDbFactory smartcareFactory(@Qualifier("smartcareMongoClient") MongoClient smartcareMongoClient) {
        return new SimpleMongoClientDbFactory(smartcareMongoClient, "SmartCare");
    }

    @Bean(name = {"smartcareMongoTemplate"})
    @Primary
    public MongoTemplate smartcareMongoTemplate(@Qualifier("smartcareFactory") SimpleMongoClientDbFactory smartcareFactory) {
        return new MongoTemplate(smartcareFactory);
    }

    @Bean(name = {"datacenterMongoClient"})
    public MongoClient datacenterMongoClient() {
        return MongoClients.create(this.duri);
    }

    @Bean(name = {"datacenterFactory"})
    public SimpleMongoClientDbFactory datacenterFactory(@Qualifier("datacenterMongoClient") MongoClient datacenterMongoClient) {
        return new SimpleMongoClientDbFactory(datacenterMongoClient, "DataCenter");
    }

    @Bean(name = {"datacenterMongoTemplate"})
    public MongoTemplate datacenterMongoTemplate(@Qualifier("datacenterFactory") SimpleMongoClientDbFactory datacenterFactory) {
        return new MongoTemplate(datacenterFactory);
    }

}
