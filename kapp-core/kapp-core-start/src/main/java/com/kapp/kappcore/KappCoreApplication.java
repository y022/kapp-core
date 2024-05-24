package com.kapp.kappcore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

@SpringBootApplication(exclude = {RedisAutoConfiguration.class})
public class KappCoreApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(KappCoreApplication.class, args);
        ConfigurableEnvironment environment = run.getEnvironment();
        System.out.println(environment.getPropertySources().toString());
    }

}
