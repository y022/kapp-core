package com.kapp.kappcore.biz.note.search.core.update;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kapp.kappcore.biz.note.search.request.RequestConstructor;
import com.kapp.kappcore.biz.note.search.request.index.TagIndexChooser;
import com.kapp.kappcore.model.dto.LineMsDTO;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.rest.RestStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class UpdateService {
    private static final Logger log = LoggerFactory.getLogger(UpdateService.class);
    private final RestHighLevelClient restHighLevelClient;
    private final ObjectMapper objectMapper;

    public void insert(LineMsDTO lineMsDTO) {
        Objects.requireNonNull(lineMsDTO, "content can not be null!");
        String index = TagIndexChooser.indexName(lineMsDTO.getTag());
        RequestConstructor constructor = RequestConstructor.CONSTRUCTOR;
        IndexRequest request = constructor.add(index, lineMsDTO.getDocId(), constructor.convertMap(lineMsDTO));
        try {
            IndexResponse response = restHighLevelClient.index(request, RequestOptions.DEFAULT);
            if (!RestStatus.OK.equals(response.status())) {
                throw new ElasticsearchException("elasticSearch server index request error,response status: " + response.status());
            }
        } catch (IOException e) {
            log.error("elasticSearch server request error :", e);
            throw new ElasticsearchException("新增文档失败！");
        }
    }


    public void insert(List<LineMsDTO> lineMsDTOs, String tag) {
        if (lineMsDTOs == null || lineMsDTOs.isEmpty()) {
            return;
        }

    }


    public void update(LineMsDTO lineMsDTO) {
        Objects.requireNonNull(lineMsDTO, "content can not be null!");
        Objects.requireNonNull(lineMsDTO.getTag(), "tag can not be null!");
        Objects.requireNonNull(lineMsDTO.getDocId(), "id can not be null!");
        RequestConstructor constructor = RequestConstructor.CONSTRUCTOR;
        String index = TagIndexChooser.indexName(lineMsDTO.getTag());
        UpdateRequest update = constructor.update(index, lineMsDTO.getDocId(), constructor.convertMap(lineMsDTO));
        try {
            restHighLevelClient.update(update, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("elasticSearch server request error :", e);
            throw new ElasticsearchException("更新文档失败!");
        }
    }


    public void batchUpdate(List<LineMsDTO> lineMsDTO, String tag) {
        Objects.requireNonNull(lineMsDTO, "content can not be null!");
        String indexName = TagIndexChooser.indexName(tag);
        RequestConstructor constructor = RequestConstructor.CONSTRUCTOR;
    }

}
