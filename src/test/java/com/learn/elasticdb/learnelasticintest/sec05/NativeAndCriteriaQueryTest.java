package com.learn.elasticdb.learnelasticintest.sec05;

import com.fasterxml.jackson.core.type.TypeReference;
import com.learn.elasticdb.learnelasticintest.AbstractTest;
import com.learn.elasticdb.learnelasticintest.sec05.entity.Garment;
import com.learn.elasticdb.learnelasticintest.sec05.repository.GarmentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;

import java.util.List;

public class NativeAndCriteriaQueryTest extends AbstractTest {

    @Autowired
    private GarmentRepository repository;

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    @BeforeAll
    public void dataSetup() {
        var garments = this.readResource("sec05/garments.json", new TypeReference<List<Garment>>() {
        });
        this.repository.saveAll(garments);
        Assertions.assertEquals(20, this.repository.count());
    }

    /*
        Sometimes we need to build a complex query programmatically with "and" "or" "not" conditions etc.
        We might not be able to use the hard coded @Query. In those cases, criteria query could be useful.
     */

    @Test
    public void criteriaQuery() {

        var nameIsShirt = Criteria.where("name").is("shirt");
        this.verify(nameIsShirt, 1);

        var priceAbove100 = Criteria.where("price").greaterThan(100);
        this.verify(priceAbove100, 5);

        this.verify(nameIsShirt.or(priceAbove100), 6);

        var brandIsZara = Criteria.where("brand").is("Zara");
        this.verify(priceAbove100.and(brandIsZara.not()), 3);

        var fuzzyMatchShort = Criteria.where("name").fuzzy("short");
        this.verify(fuzzyMatchShort, 1);

        // We can boost
        // Criteria.where("brand").is("Zara").boost(3.0)

        // We can also do geo point
        // Criteria.where("location").within(point, distance)

    }

    private void verify(Criteria criteria, int expectedResultsCount) {
        var query = CriteriaQuery.builder(criteria).build();
        var searchHits = this.elasticsearchOperations.search(query, Garment.class);
        searchHits.forEach(this.print());
        Assertions.assertEquals(expectedResultsCount, searchHits.getTotalHits());
    }
}
