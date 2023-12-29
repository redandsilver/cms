package com.example.cms.user.client.service;

import com.example.cms.user.client.domain.SignUpForm;
import com.example.cms.user.client.domain.model.Customer;
import com.example.cms.user.client.domain.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignUpCustomerService {
    private final CustomerRepository customerRepository;
    public Customer signUp(SignUpForm form){
        return customerRepository.save(Customer.from(form));
    }
}
