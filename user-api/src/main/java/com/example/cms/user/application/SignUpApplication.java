package com.example.cms.user.application;

import com.example.cms.user.client.mailgun.SendMailForm;
import com.example.cms.user.domain.SignUpForm;
import com.example.cms.user.client.MailgunClient;
import com.example.cms.user.domain.model.Customer;
import com.example.cms.user.domain.model.Seller;
import com.example.cms.user.exception.CustomException;
import com.example.cms.user.exception.ErrorCode;
import com.example.cms.user.service.customer.CustomerService;
import com.example.cms.user.service.customer.SignUpCustomerService;
import com.example.cms.user.service.seller.SignUpSellerService;
import com.example.cms.user.type.UserType;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SignUpApplication {
    private final MailgunClient mailgunClient;
    private final SignUpCustomerService signUpCustomerService;
    private final SignUpSellerService signUpSellerService;

    public String signUp(SignUpForm form, UserType userType){
        String code = getRandomCode();
        // 이미 존재하는 이메일인지
        if(userType.equals(UserType.CUSTOMER)){
            if(signUpCustomerService.isEmailExist(form.getEmail())){
                throw new CustomException(ErrorCode.ALREADY_EXIST_USER);
            }
            Customer customer = signUpCustomerService.signUp(form);
            signUpCustomerService.changeCustomerValidateEmail(customer.getId(),code);

        }else{
            if(signUpSellerService.isEmailExist(form.getEmail())){
                throw new CustomException(ErrorCode.ALREADY_EXIST_USER);
            }
            Seller seller = signUpSellerService.signUp(form);
            signUpSellerService.changeSellerValidateEmail(seller.getId(),code);
        }
        sendMail(form,userType,code);

        return "sign up success!";

    }
    private void sendMail(SignUpForm form, UserType userType, String code){
        String usertype = userType.toString().toLowerCase(Locale.ROOT);
        LocalDateTime now = LocalDateTime.now();
        SendMailForm sendMailForm = SendMailForm.builder()
                .from("tester@cms.com")
                .to(form.getEmail())
                .subject("Verification Email")
                .text(getVerificationEmailBody(
                        form.getEmail(),form.getName(),usertype,code))
                .build();
        mailgunClient.sendEmail(sendMailForm);
    }

    private String getRandomCode(){
        return RandomStringUtils.random(10,true,true);
    }
    private String getVerificationEmailBody(String email, String name, String type, String code){
        StringBuilder sb = new StringBuilder();
        return sb.append("Hello ").append(name)
                .append("Please Click Link for verification\n\n")
                .append("https://localhost:8081/signup/")
                .append(type)
                .append("/verify?email=")
                .append(email)
                .append("&code=")
                .append(code).toString();
    }

    public void verify(String email, String code, UserType userType) {
        if(userType.equals(UserType.CUSTOMER)){
            signUpCustomerService.verifyEmail(email,code);
        }else{
            signUpSellerService.verifyEmail(email, code);
        }

    }
}
