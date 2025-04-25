package com.demo.config;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;

@WebFilter("/*")
public class CorsLoggingFilter implements Filter{

	  @Override
	    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
	            throws IOException, ServletException {
	        String origin = request.getRemoteHost();
	        System.out.println("CORS request from: " + origin);
	        
	        // 繼續執行其他過濾器或目標資源
	        chain.doFilter(request, response);
	    }

	    @Override
	    public void init(FilterConfig filterConfig) throws ServletException {
	        // 初始化過濾器
	    }

	    @Override
	    public void destroy() {
	        // 清理資源
	    }
}
