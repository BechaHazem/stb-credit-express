package com.stb.notification.service;

import com.stb.notification.dto.EmailDTO;

public interface EmailService {
    void sendSimpleEmail(EmailDTO emailDTO);
    void sendTemplateEmail(EmailDTO emailDTO);
}