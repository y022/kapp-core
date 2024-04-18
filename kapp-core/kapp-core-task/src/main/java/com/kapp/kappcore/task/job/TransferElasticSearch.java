package com.kapp.kappcore.task.job;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.kapp.kappcore.biz.note.dto.ComplexSearchResultDTO;
import com.kapp.kappcore.biz.note.search.index.TagIndex;
import com.kapp.kappcore.domain.repository.LineMsItemRepository;
import com.kapp.kappcore.model.dto.LineMsDTO;
import com.kapp.kappcore.model.entity.LineMsItem;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Slf4j
public class TransferElasticSearch {
    private final LineMsItemRepository lineMsItemRepository;
    private final AsyncTaskExecutor asyncTaskExecutor;
    private final RestHighLevelClient restHighLevelClient;
    private final ObjectMapper objectMapper;

    public TransferElasticSearch(LineMsItemRepository lineMsItemRepository, AsyncTaskExecutor asyncTaskExecutor, RestHighLevelClient restHighLevelClient, ObjectMapper objectMapper) throws IOException {
        this.lineMsItemRepository = lineMsItemRepository;
        this.asyncTaskExecutor = asyncTaskExecutor;
        this.restHighLevelClient = restHighLevelClient;
        this.objectMapper = objectMapper;
        asyncTaskExecutor.execute(() -> {
            try {
//                start();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }


    public void transfer(List<LineMsItem> items) throws IOException {
        Map<String, LineMsDTO> data = items.stream().map(item -> {
            LineMsDTO lineMsDTO = new LineMsDTO();
            lineMsDTO.setTag(item.getTag());
            String originContent = item.getContent();

            if (originContent.length() >= 4) {
                originContent = originContent.substring(3);
            }
            String content = originContent.replace(" ", "");

            lineMsDTO.setTitle(content.length() > 4 ? content.substring(0, 3) : content);

            lineMsDTO.setOwner("yiyi");

            lineMsDTO.setDate(item.getDate());

            lineMsDTO.setDocId(item.getId());

            lineMsDTO.setBody(Arrays.stream(content.split("。")).toArray(String[]::new));

            lineMsDTO.setBodyLength(originContent.length());
            lineMsDTO.setVersion(item.getVersion());

            return lineMsDTO;
        }).collect(Collectors.toMap(LineMsDTO::getDocId, Function.identity()));


        BulkRequest bulkRequest = new BulkRequest();

        data.forEach((k, v) -> {
            IndexRequest indexRequest = new IndexRequest(TagIndex.FR.getIndex(), "_doc");
            try {
                IndexRequest source = indexRequest.source(objectMapper.writeValueAsString(v).getBytes(), XContentType.JSON);
                bulkRequest.add(source);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });

        BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        if (bulk.hasFailures()) {
            log.error("error occur," + bulk.buildFailureMessage());
        } else {
            log.info("一批数据成功处理完毕，数量：" + data.size());
        }
    }


    public void start() throws Exception {
        Sort sort = Sort.by("id");
        long count = lineMsItemRepository.count();

        long chunk = count / 200;

        for (int i = 1; i <= chunk; i++) {
            PageRequest pageRequest = PageRequest.of(i, 200, sort);
            Page<LineMsItem> all = lineMsItemRepository.findAll(pageRequest);


            Map<String, LineMsDTO> data = all.stream().map(item -> {


                LineMsDTO lineMsDTO = new LineMsDTO();
                lineMsDTO.setTag("FR");
                String originContent = item.getContent();


                if (originContent.length() >= 4) {
                    originContent = originContent.substring(3);
                }

                String content = originContent.replace(" ", "");

                lineMsDTO.setTitle(content.length() > 4 ? content.substring(0, 3) : content);

                lineMsDTO.setOwner("yiyi");

                lineMsDTO.setDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss SSS")));

                lineMsDTO.setDocId(item.getId());

                lineMsDTO.setBody(Arrays.stream(content.split("。")).toArray(String[]::new));

                lineMsDTO.setBodyLength(originContent.length());
                lineMsDTO.setVersion(item.getVersion());

                return lineMsDTO;
            }).collect(Collectors.toMap(LineMsDTO::getDocId, Function.identity()));


            BulkRequest bulkRequest = new BulkRequest();

            data.forEach((k, v) -> {
                IndexRequest indexRequest = new IndexRequest(TagIndex.FR.getIndex(), "_doc");
                try {
                    IndexRequest source = indexRequest.source(objectMapper.writeValueAsString(v).getBytes(), XContentType.JSON);
                    bulkRequest.add(source);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            });

            BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
            if (bulk.hasFailures()) {
                log.error("error occur," + bulk.buildFailureMessage());
            } else {
                log.info("一批数据成功处理完毕，数量：" + data.size());
            }
        }
    }

}
