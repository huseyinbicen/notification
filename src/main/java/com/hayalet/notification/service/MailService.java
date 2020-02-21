package com.hayalet.notification.service;

import com.hayalet.notification.api.request.SendMailRequest;

public interface MailService {
    Long sendEmail(SendMailRequest sendEmailRequest);
}
