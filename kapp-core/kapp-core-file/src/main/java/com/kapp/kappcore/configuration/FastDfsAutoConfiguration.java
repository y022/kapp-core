package com.kapp.kappcore.configuration;

import com.kapp.kappcore.fastdfs.FastDfsClient;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

/**
 * Author:Heping
 * Date: 2024/9/27 18:58
 */
@Configuration
@ConditionalOnClass(value = {StorageClient.class, StorageServer.class})
@ConditionalOnProperty(name = "fastdfs.enabled", havingValue = "true")
public class FastDfsAutoConfiguration {
    private static final Logger log = LoggerFactory.getLogger(FastDfsAutoConfiguration.class);

    @Bean
    public FastDfsClient fastDfsClient() throws Exception {
        ClassPathResource classPathResource = new ClassPathResource("fastdfs.conf");
        try {
            ClientGlobal.init(classPathResource.getPath());
            StorageClient sc = new StorageClient(new TrackerClient().getTrackerServer());
            return new FastDfsClient(sc);
        } catch (Exception e) {
            log.error("fastDfsClient init error!");
            throw e;
        }
    }


//    public static void main(String[] args) throws Exception {
//        FastDfsAutoConfiguration fastDfsAutoConfiguration = new FastDfsAutoConfiguration();
//        FastDfsClient fastDfsClient = fastDfsAutoConfiguration.fastDfsClient();
//        FileInputStream fileInputStream = new FileInputStream("C:\\doucment\\Book\\小说\\霸天武魂.txt");
//        FileInfo upload = fastDfsClient.upload(fileInputStream, "霸天武魂.txt");
//    }

}
