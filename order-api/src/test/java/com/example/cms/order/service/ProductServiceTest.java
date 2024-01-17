package com.example.cms.order.service;

import com.example.cms.order.domain.model.Product;
import com.example.cms.order.domain.model.ProductItem;
import com.example.cms.order.domain.product.AddProductForm;
import com.example.cms.order.domain.product.AddProductItemForm;
import com.example.cms.order.repository.ProductItemRepository;
import com.example.cms.order.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ProductServiceTest {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductItemRepository productItemRepository;

    @Test
    void ADD_PRODUCT_TEST(){
        Long sellerId = 1L;
        AddProductForm form = makeProductForm("nike airforce", "shoose",3);

        Product p = productService.addProduct(sellerId,form);
        Product result = productRepository.findWithProductItemsById(p.getId()).get();
        assertNotNull(result);

        assertEquals("nike airforce",result.getName());
        assertEquals("shoose",result.getDescription());

        assertEquals(3,result.getProductItems().size());
        assertEquals(10000,result.getProductItems().get(0).getPrice());
        assertEquals("nike airforce0",result.getProductItems().get(0).getName()); // 왜 0 1 2 이렇게 붙여지는거지..?
        assertEquals(1,result.getProductItems().get(0).getCount());
    }

    private static AddProductForm makeProductForm(String name, String description, int itemCount){
        List<AddProductItemForm> itemForms = new ArrayList<>();
        for(int i=0; i<itemCount; i++){
            itemForms.add(makeProductItemForm(null,name+i));
        }
        return AddProductForm.builder()
                .name(name)
                .description(description)
                .items(itemForms)
                .build();
    }
    private static  AddProductItemForm makeProductItemForm(Long productId, String name){
        return AddProductItemForm.builder()
                .productId(productId)
                .name(name)
                .price(10000)
                .count(1)
                .build();
    }
}