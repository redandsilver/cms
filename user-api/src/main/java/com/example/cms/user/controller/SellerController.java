package com.example.cms.user.controller;

import com.example.cms.user.domain.customer.CustomerDto;
import com.example.cms.user.domain.model.Customer;
import com.example.cms.user.domain.model.Seller;
import com.example.cms.user.domain.seller.SellerDto;
import com.example.cms.user.exception.CustomException;
import com.example.cms.user.exception.ErrorCode;
import com.example.cms.user.service.customer.CustomerService;
import com.example.cms.user.service.seller.SellerService;
import com.example.domain.common.UserVo;
import com.example.domain.config.JwtAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/seller")
@RequiredArgsConstructor
public class SellerController {
    private final JwtAuthenticationProvider provider;
    private final SellerService sellerService;
    @GetMapping("/getInfo")
    public ResponseEntity<SellerDto> getInfo(@RequestHeader(name = "X-AUTH-TOKEN")String token){
        UserVo vo = provider.getUserVo(token);
        Seller seller = sellerService.findByIdAndEmail(vo.getId(),vo.getEmail()).orElseThrow(
                ()->new CustomException(ErrorCode.NOT_EXIST_USER)
        );

        return ResponseEntity.ok(SellerDto.from(seller));
    }
}
