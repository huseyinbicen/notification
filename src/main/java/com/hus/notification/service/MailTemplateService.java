package com.hus.notification.service;

import com.hus.notification.domain.MailTemplate;
import com.hus.notification.dto.create.CreateMailTemplateDTO;

public interface MailTemplateService {
    String saveMailTemplate(CreateMailTemplateDTO createMailTemplateDTO);

    MailTemplate checkAndGetMailTemplate(String mailTemplateCode);

}
