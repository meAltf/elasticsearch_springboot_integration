package com.learn.elasticdb.learnelasticintest.sec03;

import com.fasterxml.jackson.core.type.TypeReference;
import com.learn.elasticdb.learnelasticintest.AbstractTest;
import com.learn.elasticdb.learnelasticintest.sec03.entity.Product;
import com.learn.elasticdb.learnelasticintest.sec03.repository.ProductQueryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class QueryMethodsTest extends AbstractTest {

    @Autowired
    private ProductQueryRepository repository;

    @BeforeAll
    public void dataSetup() {
        var products = this.readResource("sec03/products.json", new TypeReference<List<Product>>() {
        });
        this.repository.saveAll(products);
        Assertions.assertEquals(23, this.repository.count());
    }

    @Test
    public void findByCategory() {
        var searchHits = this.repository.findByCategory("Furniture");
        searchHits.forEach(this.print());
        Assertions.assertEquals(4, searchHits.getTotalHits());
    }

    @Test
    public void findByCategories() {
        var searchHits = this.repository.findByCategoryIn(List.of("Furniture", "Beauty"));
        searchHits.forEach(this.print());
        Assertions.assertEquals(8, searchHits.getTotalHits());
    }
}
