package com.hus.notification.domain;

import com.hus.notification.domain.base.BaseEntity;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Where(clause = BaseEntity.SOFT_DELETE_CLAUSE)
public class Mail extends BaseEntity {

    private String mailTemplateCode;
    @Column(name = "[from]")
    private String from;
    @Column(name = "[to]")
    private String to;
    private String bcc;
    private String subject;
    @Lob
    private String body;

    @OneToMany(mappedBy = "mail", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MailAttachment> mailAttachments = new ArrayList<>();

    public String getMailTemplateCode() {
        return mailTemplateCode;
    }

    public void setMailTemplateCode(String mailTemplateCode) {
        this.mailTemplateCode = mailTemplateCode;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getBcc() {
        return bcc;
    }

    public void setBcc(String bcc) {
        this.bcc = bcc;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<MailAttachment> getMailAttachments() {
        return mailAttachments;
    }

    public void setMailAttachments(List<MailAttachment> mailAttachments) {
        this.mailAttachments = mailAttachments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mail mail = (Mail) o;
        return Objects.equals(mailTemplateCode, mail.mailTemplateCode) &&
                Objects.equals(from, mail.from) &&
                Objects.equals(to, mail.to) &&
                Objects.equals(bcc, mail.bcc) &&
                Objects.equals(subject, mail.subject) &&
                Objects.equals(body, mail.body) &&
                Objects.equals(mailAttachments, mail.mailAttachments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mailTemplateCode, from, to, bcc, subject, body, mailAttachments);
    }

    @Override
    public String toString() {
        return "Mail{" +
                "mailTemplateCode='" + mailTemplateCode + '\'' +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", bcc='" + bcc + '\'' +
                ", subject='" + subject + '\'' +
                ", body='" + body + '\'' +
                ", mailAttachments=" + mailAttachments +
                '}';
    }
}
