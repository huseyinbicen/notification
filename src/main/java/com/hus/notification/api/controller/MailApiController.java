package com.hus.notification.api.controller;

import com.hus.notification.api.constants.ApiEndpoints;
import com.hus.notification.api.request.SendMailRequest;
import com.hus.notification.api.response.ResponseOfGet;
import com.hus.notification.dto.create.CreateMailTemplateDTO;
import com.hus.notification.service.MailService;
import com.hus.notification.service.MailTemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = ApiEndpoints.MAIL_API_URL, produces = ApiEndpoints.RESPONSE_CONTENTTYPE, consumes = MediaType.APPLICATION_JSON_VALUE)
@Api(value = ApiEndpoints.MailService.NAME)
public class MailApiController {

    @Autowired
    private MailTemplateService mailTemplateService;

    @Autowired
    private MailService mailService;

    @Value("${mark.fixeddelay}")
    private String deger;

    @GetMapping(value = "/test", consumes = {MediaType.ALL_VALUE})
    @ApiOperation(value = "Returns pos domain agreement info", notes = "Queries by customerId")
    public String getAllEmployees() {
        return deger;
    }

    @PostMapping("/template/create")
    @ApiOperation(value = "Returns status save mail", notes = "Save by mailTemplateDTO")
    public ResponseOfGet<String> createMailTemplate(CreateMailTemplateDTO mailTemplateDTO) {
        return new ResponseOfGet<>(this.mailTemplateService.saveMailTemplate(mailTemplateDTO));
    }

    @PostMapping("/send")
    @ApiOperation(value = "Returns id of record", notes = "Save by sendMailRequest")
    public ResponseOfGet<Long> sendMail(@RequestBody SendMailRequest sendMailRequest) {
        return new ResponseOfGet<>(this.mailService.sendEmail(sendMailRequest));
    }
}
