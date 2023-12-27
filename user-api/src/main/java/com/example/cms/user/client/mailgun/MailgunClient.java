package com.example.cms.user.client.mailgun;

import com.example.cms.user.client.mailgun.SendMailForm;
import feign.Response;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "mailgun", url="https://api.mailgun.net/v3/")
@Qualifier("mailgun")
public interface MailgunClient {
    @PostMapping("sandbox2da0342e9bb544628b934f041ee6f529.mailgun.org/messages")
    Response sendEmail(@SpringQueryMap SendMailForm form);
}
