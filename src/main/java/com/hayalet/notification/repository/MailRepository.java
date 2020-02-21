package com.hayalet.notification.repository;

import com.hayalet.notification.domain.Mail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

@Repository
public interface MailRepository extends JpaRepository<Mail,Long> {
    Mail findOneById(Long id);
}
