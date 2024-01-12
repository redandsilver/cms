package com.example.cms.user.domain.customer;

import com.example.cms.user.domain.model.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Setter
@Getter
@AllArgsConstructor
public class CustomerDto {
    private Long id;
    private String email;
    @Column(columnDefinition = "int default 0")
    private Integer balance;
    public static CustomerDto from(Customer customer){
        return new CustomerDto(customer.getId(),customer.getEmail(),
                customer.getBalance() == null?0: customer.getBalance());

    }
}
