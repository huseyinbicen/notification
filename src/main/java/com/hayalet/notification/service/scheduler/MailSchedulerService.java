package com.hayalet.notification.service.scheduler;

import com.hayalet.notification.repository.scheduler.MailSemaphoreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Future;

@Service
@Transactional
class MailSchedulerService {
    public static final String INSTANCE_UUID = UUID.randomUUID().toString();
    private static final Logger LOGGER = LoggerFactory.getLogger(MailSchedulerService.class);

    @Autowired
    private MailExecuter mailExecuter;

    @Autowired
    private MailSemaphoreRepository mailSemaphoreRepository;

    @Scheduled(fixedDelayString = "${mark.fixeddelay}")
    @Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRES_NEW)
    public void mark() {
        try {
            this.mailSemaphoreRepository.markForScheduling(MailSchedulerService.INSTANCE_UUID);
        } catch (Exception e) {
            MailSchedulerService.LOGGER.error("markForScheduling Job instance uuid:" + MailSchedulerService.INSTANCE_UUID, e);
        }
    }

    @Scheduled(fixedDelayString = "${unmark.fixeddelay}")
    @Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRES_NEW)
    public void unMark() {
        try {
            this.mailSemaphoreRepository.unMarkForScheduling();
        } catch (Exception e) {
            MailSchedulerService.LOGGER.error("unMarkForScheduling Job instance uuid:" + MailSchedulerService.INSTANCE_UUID, e);
        }
    }

    @Scheduled(fixedDelayString = "${execute.fixeddelay}")
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRES_NEW)
    public void execute() {
        try {
            List<BigInteger> list = this.mailSemaphoreRepository.findAllByUuid(MailSchedulerService.INSTANCE_UUID);
            while (!CollectionUtils.isEmpty(list)) {
                List<Future<Long>> futureList = new ArrayList<>();
                for (BigInteger transacTionId : list) {
                    futureList.add(this.mailExecuter.execute(transacTionId.longValue()));
                }
                try {
                    for (Future<Long> future : futureList) {
                        while (!future.isDone()) {
                            Thread.sleep(10);
                        }
                    }
                } catch (Exception e) {
                    MailSchedulerService.LOGGER.error("execute Job instance uuid:" + MailSchedulerService.INSTANCE_UUID, e);
                }
                list = this.mailSemaphoreRepository.findAllByUuid(MailSchedulerService.INSTANCE_UUID);
            }
        } catch (Exception e) {
            MailSchedulerService.LOGGER.error("execute Job instance uuid:" + MailSchedulerService.INSTANCE_UUID, e);
        }
    }
}
