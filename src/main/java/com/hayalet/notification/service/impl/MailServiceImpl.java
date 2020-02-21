package com.hayalet.notification.service.impl;

import com.hayalet.notification.api.request.SendMailRequest;
import com.hayalet.notification.domain.Mail;
import com.hayalet.notification.domain.MailAttachment;
import com.hayalet.notification.domain.MailTemplate;
import com.hayalet.notification.dto.MailAttachmentDTO;
import com.hayalet.notification.dto.MailParamDTO;
import com.hayalet.notification.repository.MailRepository;
import com.hayalet.notification.service.MailSemaphoreService;
import com.hayalet.notification.service.MailService;
import com.hayalet.notification.service.MailTemplateService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Transactional
public class MailServiceImpl implements MailService {
    @Autowired
    private MailRepository mailRepository;
    @Autowired
    private MailTemplateService mailTemplateService;
    @Autowired
    private MailSemaphoreService mailSemaphoreService;

    @Override
    public Long sendEmail(SendMailRequest sendEmailRequest) {
        MailTemplate mailTemplate = this.mailTemplateService.checkAndGetMailTemplate(sendEmailRequest.getTemplateCode());
        Mail mail = new Mail();
        mail.setMailTemplateCode(sendEmailRequest.getTemplateCode());
        mail.setFrom(mailTemplate.getFrom());
        mail.setTo(sendEmailRequest.getTo());
        if (!StringUtils.isEmpty(mailTemplate.getBcc())) {
            mail.setBcc(mailTemplate.getBcc());
        }

        String subjectTemplate = mailTemplate.getSubjectTemplate();
//        if (org.apache.commons.lang.StringUtils.isNotEmpty(notificationConfigurationService.getEnvironmentCode())) {
//            subjectTemplate = "[" + notificationConfigurationService.getEnvironmentCode() + "]  " + subjectTemplate;
//        }
        if (sendEmailRequest.getSubjectParams() != null){
            for (MailParamDTO mailParamDTO : sendEmailRequest.getSubjectParams()){
                subjectTemplate = StringUtils.replace(subjectTemplate,mailParamDTO.getKey(),mailParamDTO.getValue());
            }
        }
        mail.setSubject(subjectTemplate);

        String bodyTemplate = mailTemplate.getBodyTemplate();
        if (sendEmailRequest.getBodyParams() != null) {
            for (MailParamDTO mailParamDTO : sendEmailRequest.getBodyParams()) {
                bodyTemplate = StringUtils.replace(bodyTemplate, mailParamDTO.getKey(), mailParamDTO.getValue());
            }
        }
        mail.setBody(bodyTemplate);

        if (sendEmailRequest.getMailAttachments() != null) {
            for (MailAttachmentDTO mailAttachmentDTO : sendEmailRequest.getMailAttachments()) {
                MailAttachment mailAttachment = new MailAttachment();
                mailAttachment.setFileName(mailAttachmentDTO.getFileName());
                mailAttachment.setFile(mailAttachmentDTO.getFile());
                mailAttachment.setHash(DigestUtils.sha256Hex(mailAttachmentDTO.getFile()));
                mailAttachment.setMail(mail);
                mail.getMailAttachments().add(mailAttachment);
            }
        }

        mail = this.mailRepository.save(mail);
        this.mailSemaphoreService.save(mail.getId(), mailTemplate.getMaxRetryCount());
        return mail.getId();
    }
}
