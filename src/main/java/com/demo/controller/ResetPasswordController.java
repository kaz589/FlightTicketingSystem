package com.demo.controller;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.model.Member;
import com.demo.model.PasswordResetToken;
import com.demo.repository.MemberRepository;
import com.demo.repository.PasswordResetTokenRepository;
import com.demo.service.EmailService;

@RestController
@RequestMapping("/api")
public class ResetPasswordController {
	
	
	@Autowired
    private PasswordResetTokenRepository tokenRepo;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private EmailService emailService;
    
    
    
    
//取得token並發送驗證信
    @PostMapping("/request-reset")
    public ResponseEntity<?> requestReset(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        System.out.println("aaa");

        Optional<Member> member = memberRepository.findByEmail(email);
        if (member.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body("找不到該用戶");
        }

        // 生成 token
        String token = UUID.randomUUID().toString();

        // 儲存 token（可更新舊的或刪除後新增）
        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setToken(token);
        resetToken.setMember(member.get());
        resetToken.setExpiryDate(LocalDateTime.now().plusMinutes(30));
        tokenRepo.save(resetToken);

        // 發送 email
        emailService.sendResetPasswordEmail(email, token);

        return ResponseEntity.ok("重設密碼信件已寄出");
    }
    
    
 
 // 驗證 token 是否有效
    @GetMapping("/reset-password")
    public ResponseEntity<?> validateResetToken(@RequestParam("token") String token) {
        PasswordResetToken tokenEntity = tokenRepo.findByToken(token); //透過token找人

        
        //如果找不到或是過期
        if (tokenEntity == null || tokenEntity.getExpiryDate().isBefore(LocalDateTime.now())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Token 無效或已過期");
        }
        //如果找到
        return ResponseEntity.ok("Token 有效");
    }

    // 處理重設密碼的邏輯
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam("token") String token, 
                                            @RequestParam("newPassword") String newPassword) {
    	
    	//透過token找id
        PasswordResetToken tokenEntity = tokenRepo.findByToken(token);
        //如果找不到或是過期
        if (tokenEntity == null || tokenEntity.getExpiryDate().isBefore(LocalDateTime.now())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Token 無效或已過期");
        }
        
        //透過物件找Member
        Member member = tokenEntity.getMember();
        
        member.setPassword(passwordEncoder.encode(newPassword));
        memberRepository.save(member);

        // Optional: 刪除 token，不讓它再次被使用
        tokenRepo.delete(tokenEntity);

        return ResponseEntity.ok("密碼重設成功！");
    }
    
    
    
    
    
    

}
