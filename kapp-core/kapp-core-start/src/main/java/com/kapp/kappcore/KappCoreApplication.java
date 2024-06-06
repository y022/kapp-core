package com.kapp.kappcore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.ssssssss.magicapi.spring.boot.starter.MagicAPIAutoConfiguration;

@SpringBootApplication(exclude = {MagicAPIAutoConfiguration.class})
@EnableTransactionManagement
public class KappCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(KappCoreApplication.class, args);
    }

}
