package com.wellcare.service.mail;

public interface MailService {

    public void sendSingleMailHTML(String to, String subject, String text);
    public void sendMultipleMailHTML(String[] to, String subject, String text);
}