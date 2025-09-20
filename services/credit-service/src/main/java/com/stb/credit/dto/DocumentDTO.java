package com.stb.credit.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class DocumentDTO {
    private Long id;
    private String url;
    private Long customerId;
    private Long loanRequestId;
    private String name;
    private String contentType;
    private List<String> listName;
    // For upload
    private MultipartFile file;
    private String fileName;

    // For download (when retrieved from Cloudinary)
    private String fileBytes;

    public DocumentDTO() {}
    
    

    public String getFileName() {
		return fileName;
	}



	public void setFileName(String fileName) {
		this.fileName = fileName;
	}



	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

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

    public String getFileBytes() {
        return fileBytes;
    }
    public void setFileBytes(String fileBytes) {
        this.fileBytes = fileBytes;
    }

	public List<String> getListName() {
		return listName;
	}

	public void setListName(List<String> listName) {
		this.listName = listName;
	}
    
}
