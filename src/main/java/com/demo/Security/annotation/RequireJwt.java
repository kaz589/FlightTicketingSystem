package com.demo.Security.annotation;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@PreAuthorize("isAuthenticated()") // ✅ 只有登入用戶能使用
public @interface RequireJwt {
}
