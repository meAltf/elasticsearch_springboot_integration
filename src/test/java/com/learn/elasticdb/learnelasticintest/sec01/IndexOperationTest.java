package com.learn.elasticdb.learnelasticintest.sec01;

import com.learn.elasticdb.learnelasticintest.AbstractTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;

public class IndexOperationTest extends AbstractTest {

    private static final Logger log = LoggerFactory.getLogger(IndexOperationTest.class);

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    @Test
    public void createIndex(){
        var indexOperations = this.elasticsearchOperations.indexOps(IndexCoordinates.of("test1"));

        Assertions.assertTrue(indexOperations.create());
        log.info("settings: {}", indexOperations.getSettings());
    }
}
