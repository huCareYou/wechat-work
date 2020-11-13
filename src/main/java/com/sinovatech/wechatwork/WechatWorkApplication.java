package com.sinovatech.wechatwork;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@Configuration
@EnableScheduling
public class WechatWorkApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(WechatWorkApplication.class, args);
    }

}
