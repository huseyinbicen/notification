package com.hus.notification.domain;

import com.hus.notification.domain.base.BaseEntity;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Where(clause = BaseEntity.SOFT_DELETE_CLAUSE)
public class MailAttachment extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mail_id")
    private Mail mail;

    @Lob
    @Column(name = "[file]", columnDefinition = "LONGBLOB")
    private byte[] file;

    private String fileName;

    private String hash;

    public Mail getMail() {
        return mail;
    }

    public void setMail(Mail mail) {
        this.mail = mail;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MailAttachment that = (MailAttachment) o;
        return Objects.equals(mail, that.mail) &&
                Arrays.equals(file, that.file) &&
                Objects.equals(fileName, that.fileName) &&
                Objects.equals(hash, that.hash);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(mail, fileName, hash);
        result = 31 * result + Arrays.hashCode(file);
        return result;
    }

    @Override
    public String toString() {
        return "MailAttachment{" +
                "mail=" + mail +
                ", file=" + Arrays.toString(file) +
                ", fileName='" + fileName + '\'' +
                ", hash='" + hash + '\'' +
                '}';
    }
}
