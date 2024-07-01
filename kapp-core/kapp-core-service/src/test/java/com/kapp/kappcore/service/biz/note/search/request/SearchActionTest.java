package com.kapp.kappcore.service.biz.note.search.request;

import com.kapp.kappcore.model.biz.domain.search.SearchResult;
import com.kapp.kappcore.service.biz.note.search.context.DocSearchContext;
import com.kapp.kappcore.service.biz.note.search.context.GroupSearchContext;
import com.kapp.kappcore.service.biz.note.search.context.obj.SearchSource;
import com.kapp.kappcore.service.biz.note.search.core.search.KappStandardSearcher;
import com.kapp.kappcore.service.biz.note.search.support.interceptor.register.DefaultSearchInterceptManager;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

@ExtendWith(MockitoExtension.class)
class SearchActionTest {


    private static final String HOST = "47.108.75.6";
    private static RestHighLevelClient restHighLevelClient;
    private static DefaultSearchInterceptManager searchInterceptManager;
    private static KappStandardSearcher kappStandardSearcher;

    @BeforeAll
    public static void setup() {
        restHighLevelClient = new RestHighLevelClient(RestClient.builder(new HttpHost(HOST, 9200, "http")));
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
        searchInterceptManager = new DefaultSearchInterceptManager(null);
        searchInterceptManager.intercept(false);
        kappStandardSearcher = new KappStandardSearcher(searchInterceptManager, restHighLevelClient);
    }

    @AfterAll
    public static void close() {
        if (restHighLevelClient != null) {
            try {
                restHighLevelClient.close();
            } catch (Exception e) {

            }
        }
    }

    @Test
    void testQuery() {
        DocSearchContext context = new DocSearchContext();
        SearchSource searchSource = new SearchSource();
        searchSource.setTag("FR");
        context.setSource(searchSource);
        SearchResult<?> result = kappStandardSearcher.search(context);
        Assertions.assertNotNull(result);
    }

    @Test
    void testQuery_value() {
        DocSearchContext context = new DocSearchContext();
        SearchSource searchSource = new SearchSource();
        searchSource.setTag("FR");
        searchSource.setTitle("通山猿");
        context.setSource(searchSource);
        SearchResult<?> result = kappStandardSearcher.search(context);
        Assertions.assertTrue(null != result.getItems() && !result.getItems().isEmpty());
    }

    @Test
    void testGroup() {
        GroupSearchContext context = new GroupSearchContext();
        SearchSource searchSource = new SearchSource();
        searchSource.setTag("FR");
        context.setGroupFields(Set.of("tag"));
        context.setSource(searchSource);
        SearchResult<?> result = kappStandardSearcher.group(context);
    }

}