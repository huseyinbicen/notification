package com.hayalet.notification.service.scheduler;

import com.hayalet.notification.domain.Mail;
import com.hayalet.notification.domain.MailAttachment;
import com.hayalet.notification.domain.scheduler.MailSemaphore;
import com.hayalet.notification.repository.MailRepository;
import com.hayalet.notification.repository.scheduler.MailSemaphoreRepository;
import com.hayalet.notification.service.MailSemaphoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@Service
@Transactional
public class MailExecuter {
    private static Logger LOGGER = LoggerFactory.getLogger(MailExecuter.class);

    @Autowired
    private MailRepository mailRepository;

    @Autowired
    private MailSemaphoreRepository mailSemaphoreRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MailSemaphoreService mailSemaphoreService;

    @Async("mailAsyncTaskExecutor")
    public Future<Long> execute(Long mailId) {
        CompletableFuture<Long> completableFuture = new CompletableFuture<>();
        boolean success = false;
        try {

            MailSemaphore mailSemaphore = this.mailSemaphoreRepository.findOneByMailId(mailId);
            if (mailSemaphore == null) {
                completableFuture.complete(1L);
                return completableFuture;
            }

            Mail mail = this.mailRepository.findOneById(mailId);
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(mail.getFrom());
            helper.setTo(InternetAddress.parse(mail.getTo()));
            helper.setSubject(mail.getSubject());
            helper.setText(mail.getBody(), true);
            if (!StringUtils.isEmpty(mail.getBcc())) {
                helper.setBcc(InternetAddress.parse(mail.getBcc()));
            }
            helper.setValidateAddresses(true);
            List<MailAttachment> mailAttachmentList = mail.getMailAttachments();
            if (mailAttachmentList != null) {
                for (MailAttachment mailAttachment : mailAttachmentList) {
                    helper.addAttachment(mailAttachment.getFileName(), new ByteArrayResource(mailAttachment.getFile()));
                }
            }
            mailSender.send(message);
            success = true;
        } catch (Exception e) {
            MailExecuter.LOGGER.error("MailExecuter execute:" + MailSchedulerService.INSTANCE_UUID + ", mailId" + mailId, e);
            mailSemaphoreService.updateWithError(mailId, e.getMessage());
        }

        if (success) {
            mailSemaphoreService.delete(mailId);
        }
        completableFuture.complete(1L);
        return completableFuture;
    }
}
