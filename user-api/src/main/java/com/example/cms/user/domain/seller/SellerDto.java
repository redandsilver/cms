package com.example.cms.user.domain.seller;

import com.example.cms.user.domain.model.Customer;
import com.example.cms.user.domain.model.Seller;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class SellerDto {
    private Long id;
    private String email;
    public static SellerDto from(Seller seller){
        return new SellerDto(seller.getId(),seller.getEmail());
    }
}