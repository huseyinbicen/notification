package com.hayalet.notification.service;

import com.hayalet.notification.domain.MailTemplate;
import com.hayalet.notification.dto.create.CreateMailTemplateDTO;

public interface MailTemplateService {
    String saveMailTemplate(CreateMailTemplateDTO createMailTemplateDTO);

    MailTemplate checkAndGetMailTemplate(String mailTemplateCode);

}
