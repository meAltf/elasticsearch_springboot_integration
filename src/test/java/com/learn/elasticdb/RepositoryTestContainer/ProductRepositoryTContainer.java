package com.learn.elasticdb.RepositoryTestContainer;

import com.learn.elasticdb.Entity.ProductDocument;
import com.learn.elasticdb.Repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.elasticsearch.DataElasticsearchTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@DataElasticsearchTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProductRepositoryTContainer {

    @Autowired
    private ProductRepository productRepository;

    @Container
    static final ElasticsearchContainer elasticContainer =
            new ElasticsearchContainer("docker.elastic.co/elasticsearch/elasticsearch:8.13.4")
                    .withReuse(false);

    @DynamicPropertySource
    static void elasticsearchProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.elasticsearch.uris", elasticContainer::getHttpHostAddress);
    }

    @Test
    void search_with_pagination_and_sorting() {
        productRepository.deleteAll();
        ProductDocument p1 = new ProductDocument();
        p1.setName("MacBook Pro");
        p1.setDescription("Apple laptop");
        p1.setPrice(200000);

        ProductDocument p2 = new ProductDocument();
        p2.setName("MacBook Air");
        p2.setDescription("Apple laptop");
        p2.setPrice(100000);

        productRepository.saveAll(List.of(p1, p2));

        var pageable = org.springframework.data.domain.PageRequest.of(
                0, 1,
                org.springframework.data.domain.Sort.by("price").ascending()
        );

        var page =
                productRepository.findByNameContainingOrDescriptionContaining(
                        "MacBook", "MacBook", pageable);

        assertThat(page.getContent()).hasSize(1);
        assertThat(page.getContent().get(0).getName())
                .isEqualTo("MacBook Air");
    }


}
