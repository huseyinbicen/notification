package com.hus.notification.repository;

import com.hus.notification.domain.MailAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MailAttachmentRepository extends JpaRepository<MailAttachment,Long> {
}
