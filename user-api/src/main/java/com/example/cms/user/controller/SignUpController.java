package com.example.cms.user.controller;

import com.example.cms.user.application.SignUpApplication;
import com.example.cms.user.domain.SignUpForm;
import com.example.cms.user.type.UserType;
import feign.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/signup")
@RequiredArgsConstructor
public class SignUpController {
    private final SignUpApplication signUpApplication;


    @PostMapping("/customer")
    public ResponseEntity<String> customerSignUp(@RequestBody SignUpForm signUpForm){

        return ResponseEntity.ok(signUpApplication.signUp(signUpForm, UserType.CUSTOMER));
    }
    @GetMapping("/customer/verify")
    public ResponseEntity<String> verifyCustomer(String email, String code){
        signUpApplication.verify(email,code,UserType.CUSTOMER);
        return ResponseEntity.ok("인증이 완료되었습니다.");
    }
    @PostMapping("/seller")
    public ResponseEntity<String> sellerSignUp(@RequestBody SignUpForm signUpForm){

        return ResponseEntity.ok(signUpApplication.signUp(signUpForm,UserType.SELLER));
    }
    @GetMapping("/seller/verify")
    public ResponseEntity<String> verifySeller(String email, String code){
        signUpApplication.verify(email,code,UserType.SELLER);
        return ResponseEntity.ok("인증이 완료되었습니다.");
    }
}
