package com.example.cms.user.application;

import com.example.cms.user.domain.SignInForm;
import com.example.cms.user.domain.model.Customer;
import com.example.cms.user.domain.model.Seller;
import com.example.cms.user.exception.CustomException;
import com.example.cms.user.exception.ErrorCode;
import com.example.cms.user.service.customer.CustomerService;
import com.example.cms.user.service.seller.SellerService;
import com.example.domain.common.UserType;
import com.example.domain.config.JwtAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignInApplication {
    private final CustomerService customerService;
    private final SellerService sellerService;
    private final JwtAuthenticationProvider provider;
    public String customerLoginToken(SignInForm signInForm){
       // 1. check valid user
        Customer customer = customerService.findValidCustomer(
                signInForm.getEmail(), signInForm.getPassword())
                .orElseThrow(()->new CustomException(ErrorCode.LOGIN_CHECK_FAIL));
        // 2. generate token
        // 3. return token
        return provider.createToken(customer.getEmail(),customer.getId(), UserType.CUSTOMER);
    }

    public String sellerLoginToken(SignInForm signInForm){
        // 1. check valid user
       Seller seller = sellerService.findValidSeller(
                        signInForm.getEmail(), signInForm.getPassword())
                .orElseThrow(()->new CustomException(ErrorCode.LOGIN_CHECK_FAIL));
        // 2. generate token
        // 3. return token
        return provider.createToken(seller.getEmail(),seller.getId(), UserType.SELLER);
    }

}
