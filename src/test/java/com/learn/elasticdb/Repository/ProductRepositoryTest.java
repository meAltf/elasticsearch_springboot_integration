package com.learn.elasticdb.Repository;

import com.learn.elasticdb.Entity.ProductDocument;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.junit.jupiter.Container;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
public class ProductRepositoryTest {

    /**
     Repository test: @SpringBootTest / Testcontainers only
     ✔ Validates:
     Index mapping, Serialization, ES interaction, Repository wiring
     */

    @Autowired
    Environment env;

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
    void saveAndFindById_shouldWork() {
        ProductDocument product = new ProductDocument();
        product.setId("xy23");
        product.setName("Macbook");
        product.setPrice(99000);

        productRepository.save(product);
        Optional<ProductDocument> result = productRepository.findById("xy23");

        assertTrue(result.isPresent());
        assertEquals("Macbook", result.get().getName());
        assertEquals(99000, result.get().getPrice());
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

        var pageable = org.springframework.data.domain.PageRequest.of(0, 1, Sort.by("price").ascending()
        );

        var page = productRepository.findByNameContainingOrDescriptionContaining("MacBook", "MacBook", pageable);

        assertThat(page.getContent()).hasSize(1);
        assertThat(page.getContent().get(0).getName())
                .isEqualTo("MacBook Air");
    }

/**
    @Test
    void findByNameContainingOrDescriptionContaining_matchByName(){
        ProductDocument product1 = new ProductDocument();
        product1.setId("1");
        product1.setName("Apple iPhone");
        product1.setDescription("Smartphone");

        ProductDocument product2 = new ProductDocument();
        product2.setId("2");
        product2.setName("Samsung TV");
        product2.setDescription("Television");

        productRepository.saveAll(List.of(product1, product2));

        List<ProductDocument> productResult = productRepository.findByNameContainingOrDescriptionContaining(
                "Apple", "Apple");

        assertEquals(1, productResult.size());
        assertEquals("Apple iPhone", productResult.get(0).getName());
    }

    @Test
    void findByNameContainingOrDescriptionContaining_matchByDescription() {
        ProductDocument product = new ProductDocument();
        product.setId("1");
        product.setName("Laptop");
        product.setDescription("Gaming Laptop");

        productRepository.saveAll(List.of(product));

        List<ProductDocument> productResult = productRepository.findByNameContainingOrDescriptionContaining(
                "Phone", "Gaming");

        assertEquals(1, productResult.size());
        assertEquals("Laptop", productResult.get(0).getName());
    }

    @Test
    void findByNameContainingOrDescriptionContaining_noMatch() {
        ProductDocument product = new ProductDocument();
        product.setId("4");
        product.setName("Tablet");
        product.setDescription("Android device");

        productRepository.save(product);

        List<ProductDocument> result =
                productRepository.findByNameContainingOrDescriptionContaining(
                        "iPhone", "iPhone"
                );

        assertTrue(result.isEmpty());
    }
**/
}
