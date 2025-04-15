package com.demo.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.demo.filter.JwtAuthenticationFilter;
import com.demo.service.MyUserDetailsService;
import com.demo.utils.JwtTokenProvider;


@EnableWebSecurity
@Configuration
@EnableMethodSecurity(prePostEnabled = true) //開啟方法級別的Security支援
public class SecurityConfig {
	
	@Autowired
	private final JwtTokenProvider jwtTokenProvider;
	
	 public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
	        this.jwtTokenProvider = jwtTokenProvider;
	    }
	
	
	 @Bean
	 public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	     return http
	         .cors(Customizer.withDefaults())
	         .csrf(csrf -> csrf.disable())
	         .authorizeHttpRequests(auth -> auth
	        		 .anyRequest().permitAll() // 這裡放行所有請求
//	             .requestMatchers(
//	                 "/api/auth/**",          // ✅ 放行登入
//	                 "/api/auth/register",       // ✅ 放行註冊（如果你有）
//	                 "/swagger-ui/**",           // ✅ 放行 Swagger（如需）
//	                 "/v3/api-docs/**"           // ✅ 放行 Swagger JSON（如需）
//	             ).permitAll()
//	             .anyRequest().authenticated() // 其餘都需認證
	         )
	         .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
	         .build();
	 }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:5173"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true); // 如果你前端有帶 cookie 或 auth header 的話

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }
    
    @Bean
    public AuthenticationManager authManager(HttpSecurity http, AuthenticationConfiguration authConfig) throws Exception {
    	return new ProviderManager(List.of(daoAuthenticationProvider()));
    }
    
    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider 
                                  = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(myUserDetailsService);
        authenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        return authenticationProvider;
    }
    
    

}
