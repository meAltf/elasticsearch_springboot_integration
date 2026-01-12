package com.learn.elasticdb.Repository;

import com.learn.elasticdb.Entity.ProductDocument;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class ProductRepositoryTest {

    /**
     Repository test: @SpringBootTest / Testcontainers only
     ✔ Validates:
     Index mapping, Serialization, ES interaction, Repository wiring
     */

    @Autowired
    private ProductRepository productRepository;

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
}
