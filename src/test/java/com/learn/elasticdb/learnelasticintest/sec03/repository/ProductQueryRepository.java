package com.learn.elasticdb.learnelasticintest.sec03.repository;

import com.learn.elasticdb.learnelasticintest.sec03.entity.Product;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductQueryRepository extends ElasticsearchRepository<Product, Integer> {

    SearchHits<Product> findByCategory(String category);

    SearchHits<Product> findByCategoryIn(List<String> categories);
}
