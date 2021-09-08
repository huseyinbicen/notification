package com.hus.notification.api.controller;

import com.hus.notification.api.request.SendMailRequest;
import com.hus.notification.api.response.ResponseOfGet;
import com.hus.notification.dto.create.CreateMailTemplateDTO;
import com.hus.notification.service.MailService;
import com.hus.notification.service.MailTemplateService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MailApiController {

    @Autowired
    private MailTemplateService mailTemplateService;

    @Autowired
    private MailService mailService;

    @Value("${mark.fixeddelay}")
    private String deger;

    @GetMapping("/mail/test")
    @ApiOperation(value = "Returns pos domain agreement info", notes = "Queries by customerId")
    public String getAllEmployees() {
        return deger;
    }

    @PostMapping("/mail/template/create")
    @ApiOperation(value = "Returns status save mail", notes = "Save by mailTemplateDTO")
    public ResponseOfGet<String> createMailTemplate(CreateMailTemplateDTO mailTemplateDTO) {
        return new ResponseOfGet<>(this.mailTemplateService.saveMailTemplate(mailTemplateDTO));
    }

    @PostMapping("/mail/send")
    @ApiOperation(value = "Returns id of record", notes = "Save by sendMailRequest")
    public ResponseOfGet<Long> sendMail(@RequestBody SendMailRequest sendMailRequest) {
        return new ResponseOfGet<>(this.mailService.sendEmail(sendMailRequest));
    }
}
