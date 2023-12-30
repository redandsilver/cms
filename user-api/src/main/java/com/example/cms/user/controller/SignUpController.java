package com.example.cms.user.controller;

import com.example.cms.user.application.SignUpApplication;
import com.example.cms.user.domain.SignUpForm;
import feign.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/signup")
@RequiredArgsConstructor
public class SignUpController {
    private final SignUpApplication signUpApplication;


    @PostMapping
    public ResponseEntity<String> customerSignUp(@RequestBody SignUpForm signUpForm){
        return ResponseEntity.ok(signUpApplication.customerSignUp(signUpForm));
    }
    @PutMapping("/verify/customer")
    public ResponseEntity<String> verifyCustomer(String email, String code){
        signUpApplication.customerVerify(email,code);
        return ResponseEntity.ok("인증이 완료되었습니다.");
    }
}
