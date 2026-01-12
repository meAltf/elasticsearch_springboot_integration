package com.learn.elasticdb.Service;

import com.learn.elasticdb.Entity.ProductDocument;
import com.learn.elasticdb.Repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    /**
     * Repository → @Mock | Mockito creates a dummy implementation | No DB, Elasticsearch, real save
     * 👉 because it is a dependency
     * <p>
     * Service → @InjectMocks | Creates a real instance of ProductService
     * 👉 because it is the class under test
     * Equivalent to: productService = new ProductService(productRepository);
     */

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void saveProduct_shouldReturnSavedProduct() {
        ProductDocument product = new ProductDocument();
        product.setName("Macbook");
        product.setDescription("Apple laptop");

        when(productRepository.save(any(ProductDocument.class))).thenReturn(product);

        ProductDocument resultProduct = productService.saveProduct(product);

        assertNotNull(resultProduct);
        assertEquals("Macbook", product.getName());
        assertEquals("Apple laptop", product.getDescription());
    }

    @Test
    void getAllProducts_shouldReturnAllProducts() {
        ProductDocument product1 = new ProductDocument();
        product1.setName("product");
        product1.setDescription("Description");
        product1.setPrice(1000);

        ProductDocument product2 = new ProductDocument();
        product2.setName("product2");
        product2.setDescription("Description2");
        product2.setPrice(2000);

        Iterable<ProductDocument> productDocuments = List.of(product1, product2);

        when(productRepository.findAll()).thenReturn(productDocuments);

        Iterable<ProductDocument> productDocumentList = productService.getAllProducts();

        assertNotNull(productDocumentList);
        assertEquals("product", product1.getName());
        assertEquals("Description2", product2.getDescription());
    }

    @Test
    void getProductById_shouldReturnProductById() {
        ProductDocument product = new ProductDocument();
        product.setId("abc123");
        product.setName("Iphone");

        when(productRepository.findById(anyString())).thenReturn(Optional.of(product));

        ProductDocument resultProduct = productService.getProductById("abc123");

        assertNotNull(resultProduct);
        assertEquals("abc123", product.getId());
        assertEquals("Iphone", product.getName());
    }
}
