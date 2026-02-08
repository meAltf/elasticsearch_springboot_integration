package com.learn.elasticdb.learnelasticintest.sec05.entity;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Mapping;

import java.util.List;

@Document(indexName = "garments")
@Mapping(mappingPath = "sec05/index-mapping.json")
@Data
@ToString
public class Garment {

    private String id;
    private String name;
    private Integer price;
    private List<String> color;
    private List<String> size;
    private String material;
    private String brand;
    private String occasion;
    private String neckStyle;

}