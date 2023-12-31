package com.example.cms.user.application;

import com.example.cms.user.domain.SignUpForm;
import com.example.cms.user.domain.model.Customer;
import com.example.cms.user.client.MailgunClient;
import com.example.cms.user.client.mailgun.SendMailForm;
import com.example.cms.user.service.SignUpCustomerService;
import com.example.cms.user.exception.CustomException;
import com.example.cms.user.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SignUpApplication {
    private final MailgunClient mailgunClient;
    private final SignUpCustomerService signUpCustomerService;

    public void customerVerify(String email, String code){
        signUpCustomerService.verifyEmail(email,code);
    }

    public String customerSignUp(SignUpForm form){
        if(signUpCustomerService.isEmailExist(form.getEmail())){
            // exception: exist email error
            throw  new CustomException(ErrorCode.ALREADY_EXIST_USER);
        }else{
            Customer customer = signUpCustomerService.signUp(form);
            LocalDateTime now = LocalDateTime.now();

            String code = getRandomCode();
            SendMailForm sendMailForm = SendMailForm.builder()
                            .from("tester@cms.com")
                            .to(form.getEmail())
                            .subject("Verification Email")
                            .text(getVerificationEmailBody(
                                    form.getEmail(),form.getName(),code))
                            .build();
            mailgunClient.sendEmail(sendMailForm);
            signUpCustomerService.changeCustomerValidateEmail(customer.getId(), code);
            return "sign up success!";

        }
    }

    private String getRandomCode(){
        return RandomStringUtils.random(10,true,true);
    }
    private String getVerificationEmailBody(String email, String name, String code){
        StringBuilder sb = new StringBuilder();
        return sb.append("Hello ").append(name)
                .append("Please Click Link for verification\n\n")
                .append("https://localhost:8081/signup/verify/customer?email=")
                .append(email)
                .append("&code=")
                .append(code).toString();
    }
}
