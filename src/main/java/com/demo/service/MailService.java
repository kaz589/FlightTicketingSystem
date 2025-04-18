package com.demo.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {
	@Autowired
    private JavaMailSender   mailSender;
	
	 public void sendPlainText(String receivers, String subject, String content) {
	        SimpleMailMessage message = new SimpleMailMessage();
	        message.setFrom("965302@gmail.com"); // 必須設定寄件人
	        message.setTo(receivers);
	        message.setSubject(subject);
	        message.setText(content);

	        mailSender.send(message);
	    }
}
