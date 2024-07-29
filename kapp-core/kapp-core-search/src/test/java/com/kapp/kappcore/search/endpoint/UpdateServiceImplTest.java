package com.kapp.kappcore.search.endpoint;

import com.kapp.kappcore.search.BaseServerConnectorTestCase;
import com.kapp.kappcore.search.common.ExtSearchRequest;
import com.kapp.kappcore.search.support.option.DocOption;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class UpdateServiceImplTest extends BaseServerConnectorTestCase {
    private static UpdateServiceImpl updateService;

    @BeforeAll
    public static void setup() {
        initConnector();
        updateService = new UpdateServiceImpl(restHighLevelClient);
    }

    @AfterAll
    public static void close() {
        closeConnector();
    }

    @Test
    void deleteById() {
        ExtSearchRequest extSearchRequest = new ExtSearchRequest();
        extSearchRequest.setDeleteIds(Set.of("6f67289d40e25adcb56b78989a60cee5"));
        extSearchRequest.setTag("book");
        extSearchRequest.setDocOption(DocOption.DELETE.getCode());
        updateService.deleteById(extSearchRequest, "book");
    }
}