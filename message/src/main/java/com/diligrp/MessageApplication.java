package com.diligrp;

import com.dili.ss.retrofitful.annotation.RestfulScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 由MyBatis Generator工具自动生成
 * @author yuehongbo
 */
@SpringBootApplication
@EnableTransactionManagement
@EnableRetry
@EnableScheduling
@ComponentScan(basePackages={"com.dili.ss","com.diligrp.message","com.dili.uap.sdk"})
@RestfulScan({"com.diligrp.message.rpc", "com.dili.uap.sdk.rpc"})
@MapperScan({"com.dili.ss.dao"})
/**
 * 除了内嵌容器的部署模式，Spring Boot也支持将应用部署至已有的Tomcat容器, 或JBoss, WebLogic等传统Java EE应用服务器。
 * 以Maven为例，首先需要将<packaging>从jar改成war，然后取消spring-boot-maven-plugin，然后修改Application.java
 * 继承SpringBootServletInitializer
 */
public class MessageApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
            SpringApplication.run(MessageApplication.class, args);
    }


}
