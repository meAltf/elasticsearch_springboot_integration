package com.learn.elasticdb.Controller;

import com.learn.elasticdb.Entity.ProductDocument;
import com.learn.elasticdb.Service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    @Test
    void saveProduct_shouldReturn200() throws Exception {
        ProductDocument product = new ProductDocument();
        product.setName("Test");
        product.setDescription("Description");
        product.setPrice(1000);

        when(productService.saveProduct(any(ProductDocument.class)))
                .thenReturn(product);

        mockMvc.perform(post("/products/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "Test",
                                  "description": "Description",
                                  "price": 1000
                                }
                                """))
                .andExpect(status().isOk());
    }

    @Test
    void getProducts_shouldReturnAllProducts() throws Exception {
        ProductDocument product1 = new ProductDocument();
        product1.setName("product");
        product1.setDescription("Description");
        product1.setPrice(1000);

        ProductDocument product2 = new ProductDocument();
        product2.setName("product2");
        product2.setDescription("Description2");
        product2.setPrice(2000);

        Iterable<ProductDocument> productList = List.of(product1, product2);

        when(productService.getAllProducts()).thenReturn(productList);

        // validate json fields as well
        mockMvc.perform(get("/products/get"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("product"))
                .andExpect(jsonPath("$[1].price").value(2000));
    }

    @Test
    void getProductById_shouldReturnProduct() throws Exception {
        ProductDocument product = new ProductDocument();
        product.setId("acv457gjq");
        product.setName("product");
        product.setDescription("Description");
        product.setPrice(1000);

        when(productService.getProductById("acv457gjq")).thenReturn(product);
        mockMvc.perform(get("/products/{id}", "acv457gjq"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("acv457gjq"));
    }

    @Test
    void search_products_with_pagination_and_sorting() throws Exception {
        ProductDocument product = new ProductDocument();
        product.setId("1");
        product.setName("MacBook Air M1");
        product.setDescription("Apple laptop");
        product.setPrice(99999);

        Pageable pageable = PageRequest.of(0, 2, Sort.by("price").descending());
        Page<ProductDocument> page =
                new PageImpl<>(List.of(product), pageable, 1);


        when(productService.searchProduct("mac", pageable)).thenReturn(page);
        mockMvc.perform(
                        get("/products/search")
                                .param("query", "mac")
                                .param("page", "0")
                                .param("size", "2")
                                .param("sort", "price,desc")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("MacBook Air M1"))
                .andExpect(jsonPath("$.totalElements").value(1));
    }

    @Test
    void search_missingNameParam_returns400() throws Exception {
        mockMvc.perform(get("/products/search"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void test_shouldReturnString() throws Exception{
        mockMvc.perform(get("/products/test"))
                .andExpect(status().isOk())
                .andExpect(content().string("OK"));
    }

    /**
     Controller tests validate:
     HTTP contract - HTTP status
     Serialization - JSON array size
     Mapping- JSON fields
     */


}
