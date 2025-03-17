package com.danieloliveira.restwithspringbootandjava.VO.v1;

import java.io.Serializable;

public class UploadFileResponseVO implements Serializable {
    private static final long serialVersionUID = 1L;

    public String fileName;
    public String fileDownloadUri;
    public String fileType;
    public long size;

    public UploadFileResponseVO(String fileName, String fileDownloadUrl, String fileType, long size) {
        this.fileName = fileName;
        this.fileDownloadUri = fileDownloadUrl;
        this.fileType = fileType;
        this.size = size;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileDownloadUri() {
        return fileDownloadUri;
    }

    public void setFileDownloadUri(String fileDownloadUri) {
        this.fileDownloadUri = fileDownloadUri;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}
