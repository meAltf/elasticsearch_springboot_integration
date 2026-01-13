package com.learn.elasticdb.Repository;

import com.learn.elasticdb.Entity.ProductDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends ElasticsearchRepository<ProductDocument, String> {

    ProductDocument save(ProductDocument product);

    Optional<ProductDocument> findById(String name);

    // Full-text search on name OR description
    List<ProductDocument> findByNameContainingOrDescriptionContaining(String name,
                                                                      String description);
    /**
     Spring data converts it into an Elasticsearch match query
     {
     "multi_match": {
        "query": "macbook",
        "fields": ["name", "description"]
     }
     }
     */
}
