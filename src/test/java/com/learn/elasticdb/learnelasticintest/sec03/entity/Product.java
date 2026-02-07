package com.learn.elasticdb.learnelasticintest.sec03.entity;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Mapping;

@Document(indexName = "products")
@Mapping(mappingPath = "sec03/index-mapping.json")
@Data
@ToString
public class Product {

    @Id
    private Integer id;
    private String name;
    private String brand;
    private String category;
    private Integer price;
    private Integer quantity;

}
