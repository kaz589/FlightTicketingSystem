package com.demo.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = "*")
public class payController {

	@CrossOrigin
	@PostMapping("/pay")
    public RedirectView  receivePaymentResult(@RequestParam Map<String, String> allParams,HttpServletRequest request) {
        System.out.println("Received payment result: " + allParams);
        System.out.println(request);
        
        // 取得請求的域名
        String domain = request.getHeader("Host");
        System.out.println("Request domain: " + domain);
        // 驗證檢查碼
       // boolean isValid = verifyCheckMacValue(paymentResult.getCheckMacValue());
        boolean isValid =true;
        if (isValid) {
        	return new RedirectView("http://localhost:5173/");
        } else {
        	RedirectView redirectView = new RedirectView();
            redirectView.setUrl("http://localhost:5173/error"); // 可以設置一個錯誤頁面
            return redirectView;
        }
    }

    private boolean verifyCheckMacValue(String checkMacValue) {
        // 檢查碼驗證邏輯
        return true; // 假設驗證通過
    }
}

class PaymentResult {
    private String checkMacValue;
    // 其他屬性和 getter/setter 方法

    public String getCheckMacValue() {
        return checkMacValue;
    }

    public void setCheckMacValue(String checkMacValue) {
        this.checkMacValue = checkMacValue;
    }
	
	
}
