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
                .from("Excited User <mailgun@sandbox89c53acbe15f405796c2ef57f00d15e9.mailgun.org>")
                .to("jrnecki81@gmail.com")
                .subject("test email from cms")
                .text("my test")
                .build();

        return mailgunClient.sendEmail(form);
    }
}
