package com.kapp.kappcore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.ssssssss.magicapi.spring.boot.starter.MagicAPIAutoConfiguration;

@EnableScheduling
@SpringBootApplication(exclude = {MagicAPIAutoConfiguration.class})
@EnableTransactionManagement
public class KappCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(KappCoreApplication.class, args);
    }

}
