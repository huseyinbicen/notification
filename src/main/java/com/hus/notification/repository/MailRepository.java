package com.hus.notification.repository;

import com.hus.notification.domain.Mail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MailRepository extends JpaRepository<Mail,Long> {

    Mail findOneById(Long id);
}
