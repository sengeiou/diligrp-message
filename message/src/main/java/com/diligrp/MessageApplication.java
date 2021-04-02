package com.diligrp;

import com.dili.ss.retrofitful.annotation.RestfulScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 由MyBatis Generator工具自动生成
 * @author yuehongbo
 */
@SpringBootApplication
@EnableTransactionManagement
@EnableRetry
@EnableScheduling
@ComponentScan(basePackages = {"com.dili.*","com.diligrp.*"})
@RestfulScan({"com.dili.uap.sdk.rpc"})
@MapperScan({"com.dili.ss.dao"})
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.diligrp.message.rpc"})
public class MessageApplication extends SpringBootServletInitializer {

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        System.setProperty("druid.mysql.usePingMethod","false");
        SpringApplication.run(MessageApplication.class, args);
    }
}
