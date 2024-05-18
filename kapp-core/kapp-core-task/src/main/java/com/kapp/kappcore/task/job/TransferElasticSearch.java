package com.kapp.kappcore.task.job;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kapp.kappcore.biz.note.search.index.TagIndex;
import com.kapp.kappcore.model.dto.LineMsDTO;
import com.kapp.kappcore.model.entity.LineMsItem;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Slf4j
public class TransferElasticSearch {
    private final RestHighLevelClient restHighLevelClient;
    private final ObjectMapper objectMapper;
    private final String OWNER_NAME = "yiyi";

    public TransferElasticSearch(RestHighLevelClient restHighLevelClient, ObjectMapper objectMapper) throws IOException {
        this.restHighLevelClient = restHighLevelClient;
        this.objectMapper = objectMapper;

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

            lineMsDTO.setOwner(OWNER_NAME);

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

        restHighLevelClient.bulkAsync(bulkRequest, RequestOptions.DEFAULT, new ActionListener<>() {
            @Override
            public void onResponse(BulkResponse bulkItemResponses) {
                log.info("一批数据成功处理完毕，数量：" + data.size());
            }

            @Override
            public void onFailure(Exception e) {
                log.error("error occur," + e);
            }
        });
    }

}
