package com.learn.elasticdb.Repository;

import com.learn.elasticdb.Entity.ProductDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends ElasticsearchRepository<ProductDocument, String> {

    ProductDocument save(ProductDocument product);

    Optional<ProductDocument> findById(String name);
}
