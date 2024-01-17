package com.example.cms.order.service;

import com.example.cms.order.domain.model.Product;
import com.example.cms.order.domain.model.ProductItem;
import com.example.cms.order.domain.product.AddProductItemForm;
import com.example.cms.order.repository.ProductItemRepository;
import com.example.cms.order.repository.ProductRepository;
import com.example.cms.order.exception.CustomException;
import com.example.cms.order.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class ProductItemService {
    private final ProductRepository productRepository;
    private final ProductItemRepository productItemRepository;
    @Transactional
    public Product addProductItem(Long sellerId, AddProductItemForm form){
        Product product = productRepository.findBySellerIdAndId(sellerId, form.getProductId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_PRODUCT));
        if(product.getProductItems().stream().anyMatch(item->item.getName().equals(form.getName()))){
            throw new CustomException(ErrorCode.SAME_ITEM_NAME);
        }

        ProductItem productItem = ProductItem.of(sellerId,form);
        product.getProductItems().add(productItem);
        return product;
    }
}
