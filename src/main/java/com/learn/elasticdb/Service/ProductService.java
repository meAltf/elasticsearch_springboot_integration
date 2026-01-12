package com.learn.elasticdb.Service;

import com.learn.elasticdb.Entity.ProductDocument;
import com.learn.elasticdb.Repository.ProductRepository;
import jakarta.annotation.PostConstruct;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    // After adding @RequiredArgsConstructor -> no need constructor injection

    /**
     * public ProductService(ProductRepository repository) {
     * this.productRepository = repository;
     * }
     **/

    public ProductDocument saveProduct(ProductDocument product) {
        return productRepository.save(product);
    }

    public Iterable<ProductDocument> getAllProducts() {
        return productRepository.findAll();
    }

    public ProductDocument getProductById(String id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Product not found with id in index: " + id));
    }

    // To test is product repository is able to inject successfully or not
    @PostConstruct
    public void test() {
        System.out.println("ProductRepository injected successfully");
    }
}
