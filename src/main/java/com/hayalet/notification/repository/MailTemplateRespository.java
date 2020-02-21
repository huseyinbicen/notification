package com.hayalet.notification.repository;

import com.hayalet.notification.domain.MailTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MailTemplateRespository extends JpaRepository<MailTemplate, Long>  {
    MailTemplate findOneByCode(String code);
}
