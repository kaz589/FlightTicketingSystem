package com.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedOrigins("http://localhost:5173", "http://localhost:3000","https://payment-stage.ecpay.com.tw","http://localhost:8080") // 允许的域名
				.allowedMethods("GET", "POST", "PUT", "DELETE"); // 添加 PUT 和 DELETE;
	}

	
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {

	    // 保留原有的設定
	    registry.addResourceHandler("/images/**")
	            .addResourceLocations("file:uploads/images/");

	    // 新增的設定
	    registry.addResourceHandler("/uploads/images/**")
	            .addResourceLocations("file:./uploads/images/");
	    
	    registry.addResourceHandler("/ProductsImage/**")
        .addResourceLocations("file:uploads/ProductsImage/");
	}
}
