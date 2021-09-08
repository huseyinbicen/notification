package com.hus.notification.repository.scheduler;

import com.hus.notification.domain.scheduler.MailSemaphore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;
import java.util.List;

public interface MailSemaphoreRepository extends JpaRepository<MailSemaphore, Long> {

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE mail_semaphore  SET uuid = ?1, last_update_time = {fn NOW()}, retry_count = (retry_count + 1) WHERE uuid IS NULL", nativeQuery = true)
    int markForScheduling(String uuid);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE mail_semaphore  SET uuid = NULL, last_update_time = {fn NOW()} WHERE uuid IS NOT NULL AND retry_count <= max_retry_count AND ({fn NOW()} > {fn TIMESTAMPADD(SQL_TSI_MINUTE, 5, last_update_time)})", nativeQuery = true)
    int unMarkForScheduling();

    @Query(value = "SELECT mail_id FROM mail_semaphore  WHERE uuid = ?1 AND retry_count <= max_retry_count AND ({fn NOW()} < {fn TIMESTAMPADD(SQL_TSI_MINUTE, 4, last_update_time)}) ORDER BY id ASC", nativeQuery = true)
    List<BigInteger> findAllByUuid(String uuid);

    void deleteByMailId(Long mailId);

    MailSemaphore findOneByMailId(Long mailId);
}
