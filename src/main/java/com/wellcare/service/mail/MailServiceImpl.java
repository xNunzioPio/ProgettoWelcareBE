package com.wellcare.service.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {

    private final Logger log = LoggerFactory.getLogger(MailServiceImpl.class);

    @Autowired
    private JavaMailSender emailSender;

    @Override
    public void sendSingleMailHTML(String to, String subject, String text) {

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = null;

        try {
            helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setText(text, true);
            helper.setSubject(subject);

            emailSender.send(message);
        } catch (MessagingException e) {
            log.error("MAIL SENDER ::: ERROR " + e.getMessage());
        }

    }

    @Override
    public void sendMultipleMailHTML(String[] to, String subject, String text) {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = null;

        try {
            helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setText(text, true);
            helper.setSubject(subject);

            emailSender.send(message);
        } catch (MessagingException e) {
            log.error("MAIL SENDER ::: ERROR " + e.getMessage());
        }

    }

}