package com.stb.credit.dto;

import org.springframework.web.multipart.MultipartFile;

public class DocumentDTO {
    private Long id;
    private String url;
    private Long customerId;
    private Long loanRequestId;
    private String name;

    // For upload
    private MultipartFile file;

    // For download (when retrieved from Cloudinary)
    private byte[] fileBytes;

    public DocumentDTO() {}

    // Getters and Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    public Long getCustomerId() {
        return customerId;
    }
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getLoanRequestId() {
        return loanRequestId;
    }
    public void setLoanRequestId(Long loanRequestId) {
        this.loanRequestId = loanRequestId;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public MultipartFile getFile() {
        return file;
    }
    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public byte[] getFileBytes() {
        return fileBytes;
    }
    public void setFileBytes(byte[] fileBytes) {
        this.fileBytes = fileBytes;
    }
}
