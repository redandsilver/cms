package com.example.cms.order.repository;

import com.example.cms.order.domain.model.Product;

import java.util.List;

public interface ProductRepositoryCustom{
    List<Product> searchByName(String name);
}
