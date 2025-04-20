package com.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
	
	@Autowired
    private JavaMailSender mailSender;

    public void sendResetPasswordEmail(String toEmail, String token) {
        String resetLink = "http://localhost:5173/reset-password?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("965302@gmail.com"); // 必須設定寄件人
        message.setTo(toEmail);
        message.setSubject("密碼重設連結");
        String body = String.format(
        	    "親愛的用戶您好：\n\n我們收到您重設密碼的請求，請點擊以下連結完成設定：\n\n%s\n\n" +
        	    "若您未提出此請求，請忽略本郵件。\n\n此連結將於30分鐘後失效。\n\n感謝您使用 YourApp！",
        	    resetLink
        	);

        	message.setText(body);

        mailSender.send(message);
    }

}
