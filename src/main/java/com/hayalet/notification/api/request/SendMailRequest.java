package com.hayalet.notification.api.request;

import com.hayalet.notification.dto.MailAttachmentDTO;
import com.hayalet.notification.dto.MailParamDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SendMailRequest {
    private String templateCode;
    private String to;
    private List<MailParamDTO> subjectParams = new ArrayList<>();
    private List<MailParamDTO> bodyParams = new ArrayList<>();
    private List<MailAttachmentDTO> mailAttachments = new ArrayList<>();

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public List<MailParamDTO> getSubjectParams() {
        return subjectParams;
    }

    public void setSubjectParams(List<MailParamDTO> subjectParams) {
        this.subjectParams = subjectParams;
    }

    public List<MailParamDTO> getBodyParams() {
        return bodyParams;
    }

    public void setBodyParams(List<MailParamDTO> bodyParams) {
        this.bodyParams = bodyParams;
    }

    public List<MailAttachmentDTO> getMailAttachments() {
        return mailAttachments;
    }

    public void setMailAttachments(List<MailAttachmentDTO> mailAttachments) {
        this.mailAttachments = mailAttachments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SendMailRequest that = (SendMailRequest) o;
        return Objects.equals(templateCode, that.templateCode) &&
                Objects.equals(to, that.to) &&
                Objects.equals(subjectParams, that.subjectParams) &&
                Objects.equals(bodyParams, that.bodyParams) &&
                Objects.equals(mailAttachments, that.mailAttachments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(templateCode, to, subjectParams, bodyParams, mailAttachments);
    }
}
