package com.stb.notification.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.stb.notification.dto.EmailDTO;
import com.stb.notification.service.EmailService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    private final String fromEmail = "stb.credit.express@gmail.com";

    @Override
    public void sendSimpleEmail(EmailDTO emailDTO) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(emailDTO.getTo());
        message.setSubject(emailDTO.getSubject());
        message.setText(emailDTO.getText());

        mailSender.send(message);
    }

    @Override
    public void sendTemplateEmail(EmailDTO emailDTO) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(fromEmail);
            helper.setTo(emailDTO.getTo());
            helper.setSubject(emailDTO.getSubject());

            Context context = new Context();
            if (emailDTO.getTemplateModel() != null) {
                context.setVariables(emailDTO.getTemplateModel());
            }

            String htmlContent = templateEngine.process(emailDTO.getTemplateName(), context);
            helper.setText(htmlContent, true);

            mailSender.send(mimeMessage);

        } catch (MessagingException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to send email", e);
        }
    }
}
