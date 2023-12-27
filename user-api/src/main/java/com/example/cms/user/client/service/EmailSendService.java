package com.example.cms.user.client.service;

import com.example.cms.user.client.mailgun.MailgunClient;
import com.example.cms.user.client.mailgun.SendMailForm;
import feign.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailSendService {
    private final MailgunClient mailgunClient;

    public Response sendEmail(){
        new SendMailForm();
        SendMailForm form = SendMailForm.builder()
                .from("zerobase-test@Email.com")
                .to("jellyjelly111@naver.com")
                .subject("test email from cms")
                .text("my test")
                .build();

        return mailgunClient.sendEmail(form);
    }
}
