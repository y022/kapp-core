package com.kapp.kappcore.search;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class BaseServerConnectorTestCase {

    protected static RestHighLevelClient restHighLevelClient;
    private static final Properties PROPERTIES = new Properties();
    private final static String PROPERTIES_FILE_NAME = "application.properties";

    public static void initConnector() {
        try (InputStream is = BaseServerConnectorTestCase.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE_NAME)) {
            if (is == null) {
                throw new IllegalArgumentException("Configuration file not found: " + PROPERTIES_FILE_NAME);
            }
            PROPERTIES.load(is);
        } catch (Exception e) {
            throw new RuntimeException("Error loading configuration file: " + PROPERTIES_FILE_NAME, e);
        }
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(
                AuthScope.ANY,
                new UsernamePasswordCredentials(
                        PROPERTIES.getProperty("spring.elasticsearch.rest.username"),
                        "spring.elasticsearch.rest.password"
                )
        );
        RestClientBuilder rcb = RestClient.builder(new HttpHost(PROPERTIES.getProperty("spring.elasticsearch.rest.host"), 9200, "http"))
                .setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider));
        restHighLevelClient = new RestHighLevelClient(rcb);
    }

    public static void closeConnector() {
        if (restHighLevelClient != null) {
            try {
                restHighLevelClient.close();
            } catch (IOException e) {
                System.err.println("Error closing the client: " + e.getMessage());
            }
        }
    }
}
