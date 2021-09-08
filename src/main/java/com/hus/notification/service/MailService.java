package com.hus.notification.service;

import com.hus.notification.api.request.SendMailRequest;

public interface MailService {

    Long sendEmail(SendMailRequest sendEmailRequest);
}
