package com.example.cms.order.repository;

import com.example.cms.order.domain.model.Product;
import com.example.cms.order.domain.model.ProductItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductItemRepository extends JpaRepository<ProductItem,Long> {
}
