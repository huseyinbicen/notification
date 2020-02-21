package com.hayalet.notification.service.impl;

import com.hayalet.notification.domain.scheduler.MailSemaphore;
import com.hayalet.notification.repository.scheduler.MailSemaphoreRepository;
import com.hayalet.notification.service.MailSemaphoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MailSemaphoreServiceImpl implements MailSemaphoreService {
    @Autowired
    private MailSemaphoreRepository mailSemaphoreRepository;
    @Override
    public void save(Long mailId, int maxRetryCount) {
        MailSemaphore mailSemaphore = new MailSemaphore();
        mailSemaphore.setMailId(mailId);
        mailSemaphore.setMaxRetryCount(maxRetryCount);
        mailSemaphore.setRetryCount(0);
        mailSemaphore.setError(null);
        this.mailSemaphoreRepository.save(mailSemaphore);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public void delete(Long mailId) {
        MailSemaphore mailSemaphore = this.mailSemaphoreRepository.findOneByMailId(mailId);
        if (mailSemaphore != null) {
            this.mailSemaphoreRepository.deleteByMailId(mailId);
            this.mailSemaphoreRepository.flush();
        }
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRES_NEW)
    public void updateWithError(Long mailId, String error) {
        MailSemaphore mailSemaphore = this.mailSemaphoreRepository.findOneByMailId(mailId);
        mailSemaphore.setUuid(null);
        mailSemaphore.setError(error);
        this.mailSemaphoreRepository.saveAndFlush(mailSemaphore);
    }
}
