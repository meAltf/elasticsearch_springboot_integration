package com.learn.elasticdb.learnelasticintest.sec04.entity;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Mapping;

@Document(indexName = "articles")
@Mapping(mappingPath = "sec04/index-mapping.json")
@Data
@ToString
public class Article {

    @Id
    private String id;
    private String title;
    private String body;
}