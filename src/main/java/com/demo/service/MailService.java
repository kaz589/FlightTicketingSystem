package com.demo.service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class MailService {
	@Autowired
    private JavaMailSender   mailSender;
	
	   @Autowired
	    private TemplateEngine templateEngine;
	
	 public void sendPlainText(String receivers, String subject, String content) {
	        SimpleMailMessage message = new SimpleMailMessage();
	        message.setFrom("965302@gmail.com"); // 必須設定寄件人
	        message.setTo(receivers);
	        message.setSubject(subject);
	        message.setText(content);

	        mailSender.send(message);
	    }
	 
	 /**
	     * 寄送訂單付款完成通知信
	     */
	    public void sendOrderPaidMail(String to, String customerName,String orderNumber, String amount) throws MessagingException {
	    	Map<String, Object> variables = new HashMap<>();
	        variables.put("customerName", customerName);
	        variables.put("orderNumber", orderNumber);
	        variables.put("amount", amount);
	        variables.put("payTime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
	        Context context = new Context();
	        context.setVariables(variables);

	        String subject = "訂單付款完成通知";
	        String content = templateEngine.process("email/order-paid.html", context);

	        MimeMessage message = mailSender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
	        helper.setFrom("965302@gmail.com");
	        helper.setTo(to);
	        helper.setSubject(subject);
	        helper.setText(content, true);

	        mailSender.send(message);
	    
	}
	    
	    /**
	     * 寄送訂單付款完成通知信
	     */
	    public void sendRedeemMail(String to, String customerName,String orderNumber, String amount) throws MessagingException {
	    	Map<String, Object> variables = new HashMap<>();
	        variables.put("customerName", customerName);
	        variables.put("orderNumber", orderNumber);
	        variables.put("amount", amount);
	        variables.put("payTime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
	        Context context = new Context();
	        context.setVariables(variables);

	        String subject = "訂單付款完成通知";
	        String content = templateEngine.process("email/redeemMail.html", context);

	        MimeMessage message = mailSender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
	        helper.setFrom("965302@gmail.com");
	        helper.setTo(to);
	        helper.setSubject(subject);
	        helper.setText(content, true);

	        mailSender.send(message);
	    
	}
}
