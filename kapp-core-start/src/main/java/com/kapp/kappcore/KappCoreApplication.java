package com.kapp.kappcore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchDataAutoConfiguration;

@SpringBootApplication(exclude = ElasticsearchDataAutoConfiguration.class)
public class KappCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(KappCoreApplication.class, args);
    }

}
