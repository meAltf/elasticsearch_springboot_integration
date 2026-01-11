package com.learn.elasticdb.Controller;

import com.learn.elasticdb.Entity.ProductDocument;
import com.learn.elasticdb.Service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // After adding @RequiredArgsConstructor -> no need constructor injection

    /**
     * public ProductController(ProductService service) {
     * this.productService = service;
     * }
     **/

    @PostMapping("/save")
    public ProductDocument saveProduct(@RequestBody ProductDocument product) {
        return productService.saveProduct(product);
    }

    @GetMapping("/get")
    public Iterable<ProductDocument> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ProductDocument getProductById(@PathVariable String id) {
        return productService.getProductById(id);
    }

    // To test, is springboot able to map the request or not
    @GetMapping("/test")
    public String test() {
        return "OK";
    }
}
