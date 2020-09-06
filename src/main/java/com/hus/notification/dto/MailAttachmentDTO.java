package com.hus.notification.dto;

import java.util.Arrays;
import java.util.Objects;

public class MailAttachmentDTO {
    private byte[] file;
    private String fileName;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MailAttachmentDTO that = (MailAttachmentDTO) o;
        return Arrays.equals(file, that.file) &&
                Objects.equals(fileName, that.fileName);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(fileName);
        result = 31 * result + Arrays.hashCode(file);
        return result;
    }
}
