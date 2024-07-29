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

public class BaseServerConnectorTestCase {
    private static final String HOST = "47.108.75.6";
    protected static RestHighLevelClient restHighLevelClient;

    public static void initConnector() {
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(
                AuthScope.ANY,
                new UsernamePasswordCredentials(
                        "elastic",
                        "y1039390833"
                )
        );

        RestClientBuilder rcb = RestClient.builder(new HttpHost(HOST, 9200, "http"))
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
