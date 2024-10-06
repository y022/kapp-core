package com.kapp.kappcore.configuration;

import com.kapp.kappcore.fastdfs.FastDfsClient;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.FileNotFoundException;

/**
 * Author:Heping
 * Date: 2024/9/27 18:58
 */
@Configuration
//@ConditionalOnClass(value = {StorageClient.class, StorageServer.class})
public class FastDfsAutoConfiguration {
    private static final Logger log = LoggerFactory.getLogger(FastDfsAutoConfiguration.class);
    private static final String FAST_DFS_CONFIG_FILE = "fastdfs.properties";

    @Bean
    public FastDfsClient fastDfsClient() throws Exception {
        ClassPathResource classPathResource = new ClassPathResource(FAST_DFS_CONFIG_FILE);
        if (!classPathResource.exists()) {
            throw new FileNotFoundException(FAST_DFS_CONFIG_FILE);
        }
        try {
            ClientGlobal.init(classPathResource.getPath());
            StorageClient sc = new StorageClient(new TrackerClient().getTrackerServer());
            return new FastDfsClient(sc);
        } catch (Exception e) {
            log.error("fastDfsClient init error!");
            throw e;
        }
    }

}
