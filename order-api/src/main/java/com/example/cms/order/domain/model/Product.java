package com.example.cms.order.domain.model;

import com.example.cms.order.domain.product.AddProductForm;
import com.example.cms.order.domain.product.AddProductItemForm;
import lombok.*;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Audited // 변할때마다 여기도
@AuditOverride(forClass = BaseEntity.class)
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long sellerId;
    private String name;
    private String description;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="product_id")
    private List<ProductItem> productItems;

    public static Product of(Long sellerId, AddProductForm form){
        return Product.builder()
                .sellerId(sellerId)
                .name(form.getName())
                .description(form.getDescription())
                .productItems(form.getItems().stream()
                        .map(piform -> ProductItem.of(sellerId,piform)).collect(Collectors.toList()))
                .build();
    }
}
