package com.kapp.kappcore.search.core;

import com.kapp.kappcore.search.common.SearchRequestDTO;
import com.kapp.kappcore.search.core.search.StandardKappSearchActor;
import com.kapp.kappcore.search.support.factory.impl.ParamConstructor;
import com.kapp.kappcore.search.support.model.param.GroupParamUnit;
import com.kapp.kappcore.search.support.model.param.SearchParam;
import com.kapp.kappcore.search.support.option.ContHitStrategy;
import com.kapp.kappcore.search.support.option.DocOption;
import com.kapp.kappcore.search.support.option.GroupOption;
import com.kapp.kappcore.search.support.option.ViewType;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.assertj.core.util.Lists;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

/**
 * Author:Heping
 * Date: 2024/6/27 11:25
 */
@Slf4j
@ExtendWith(MockitoExtension.class)
class StandardKappSearchActorTest {

    private static final String HOST = "47.108.75.6";
    private static RestHighLevelClient restHighLevelClient;
    private static StandardKappSearchActor searcher;

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
        searcher = new StandardKappSearchActor(restHighLevelClient, null);
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

    private static SearchResponse collect(SearchResponse response) {
        log.info("result:{}", response.toString());
        return null;
    }

    @Test
    void search() {
        SearchRequestDTO searchRequestDTO = new SearchRequestDTO();
        searchRequestDTO.setSearchAll(false);
        searchRequestDTO.setTag(Set.of("fr"));
        searchRequestDTO.setViewType(ViewType.TIP.getCode());
        searchRequestDTO.setDocOption(DocOption.GROUP.getCode());
        searchRequestDTO.setContHitStrategy(ContHitStrategy.ACCURATE.getCode());

        GroupParamUnit paramUnit = new GroupParamUnit("tag", GroupOption.BUCKET);
        searchRequestDTO.setGroupParamUnits(Lists.list(paramUnit));
        searchRequestDTO.setPageNum(1);
        searchRequestDTO.setPageSize(10);
        ParamConstructor paramConstructor = ParamConstructor.instance();
        SearchParam searchParam = paramConstructor.create(searchRequestDTO);
        searcher.search(searchParam, StandardKappSearchActorTest::collect);
    }

    @Test
    void search_metric() {
        SearchRequestDTO searchRequestDTO = new SearchRequestDTO();
        searchRequestDTO.setSearchAll(false);
        searchRequestDTO.setTag(Set.of("fr"));
        searchRequestDTO.setViewType(ViewType.NONE.getCode());
        searchRequestDTO.setDocOption(DocOption.GROUP.getCode());
        searchRequestDTO.setContHitStrategy(ContHitStrategy.ACCURATE.getCode());

        GroupParamUnit paramUnit_max = new GroupParamUnit("bodyLength", GroupOption.MAX);
        GroupParamUnit paramUnit_min = new GroupParamUnit("bodyLength", GroupOption.MIN);
        GroupParamUnit paramUnit_avg = new GroupParamUnit("bodyLength", GroupOption.AVG);
        GroupParamUnit paramUnit_sum = new GroupParamUnit("bodyLength", GroupOption.SUM);
        GroupParamUnit paramUnit_count = new GroupParamUnit("bodyLength", GroupOption.COUNT);

        searchRequestDTO.setGroupParamUnits(Lists.list(paramUnit_max, paramUnit_min, paramUnit_avg, paramUnit_sum, paramUnit_count));
        searchRequestDTO.setPageNum(1);
        searchRequestDTO.setPageSize(10);
        ParamConstructor paramConstructor = ParamConstructor.instance();
        SearchParam searchParam = paramConstructor.create(searchRequestDTO);
        searcher.search(searchParam, StandardKappSearchActorTest::collect);
    }
}