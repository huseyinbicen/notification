package com.hayalet.notification.api.controller;

import com.hayalet.notification.api.request.SendMailRequest;
import com.hayalet.notification.api.response.ResponseOfGet;
import com.hayalet.notification.dto.create.CreateMailTemplateDTO;
import com.hayalet.notification.service.MailService;
import com.hayalet.notification.service.MailTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EnterpriseApiController {
    @Autowired
    private MailTemplateService mailTemplateService;

    @Autowired
    private MailService mailService;

    @Value("${mark.fixeddelay}")
    private String deger;

    @GetMapping("/onboarding/test")
    public String getAllEmployees() {
        return deger;
    }

    @PostMapping("/onboarding/mail/template/create")
    public  ResponseOfGet<String> createMailTemplate(CreateMailTemplateDTO mailTemplateDTO){
        return new ResponseOfGet<>(this.mailTemplateService.saveMailTemplate(mailTemplateDTO));
    }

    @PostMapping("/onboarding/mail/send")
    public  ResponseOfGet<Long> sendMail(@RequestBody SendMailRequest sendMailRequest){
        return new ResponseOfGet<>(this.mailService.sendEmail(sendMailRequest));
    }

}
