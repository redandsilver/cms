package com.example.cms.user.service;

import com.example.cms.user.domain.SignUpForm;
import com.example.cms.user.domain.model.Customer;
import com.example.cms.user.domain.repository.CustomerRepository;
import com.example.cms.user.exception.CustomException;
import com.example.cms.user.exception.ErrorCode;
import com.example.cms.user.type.UserStatus;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SignUpCustomerService {
    private final CustomerRepository customerRepository;
    public Customer signUp(SignUpForm form){
        return customerRepository.save(Customer.from(form));
    }

    public boolean isEmailExist(String email){
        return customerRepository.findByEmail(
                email.toLowerCase(Locale.ROOT)).isPresent();
    }
    @Transactional
    public void verifyEmail(String email, String code){
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_EXIST_USER));

        if(customer.isVerify()){
            throw new CustomException(ErrorCode.ALREADY_VERIFIED);
        }
        else if(!customer.getVerificationCode().equals(code)){
            throw new CustomException(ErrorCode.WRONG_VERIFICATION);
        }
        else if(customer.getVerifyExpiredAt().isBefore(LocalDateTime.now())){
            throw new CustomException(ErrorCode.EXPIRE_CODE);
        }
        customer.setVerify(UserStatus.VERIFIED);
    }
    @Transactional
    public LocalDateTime changeCustomerValidateEmail(Long customerId, String verificationCode){
        Optional <Customer> customerOptional= customerRepository.findById(customerId);

        if(customerOptional.isEmpty()){
            throw new CustomException(ErrorCode.NOT_EXIST_USER);
        }else{
           Customer customer =  customerOptional.get();
           customer.setVerificationCode(verificationCode);
           customer.setVerifyExpiredAt(LocalDateTime.now().plusDays(1));
           return customer.getVerifyExpiredAt();
        }
    }
}
