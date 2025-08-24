package com.stb.credit.dto;

import java.util.Map;

public class EmailDTO {
    private String to;
    private String subject;
    private String text; // for plain text
    private String templateName; // for Thymeleaf templates
    private Map<String, Object> templateModel; // parameters for template

    public EmailDTO() {}

    public EmailDTO(String to, String subject, String text) {
        this.to = to;
        this.subject = subject;
        this.text = text;
    }

    // getters and setters
    public String getTo() { return to; }
    public void setTo(String to) { this.to = to; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public String getTemplateName() { return templateName; }
    public void setTemplateName(String templateName) { this.templateName = templateName; }

    public Map<String, Object> getTemplateModel() { return templateModel; }
    public void setTemplateModel(Map<String, Object> templateModel) { this.templateModel = templateModel; }
}
