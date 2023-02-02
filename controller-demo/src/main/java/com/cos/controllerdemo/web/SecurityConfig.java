package com.cos.controllerdemo.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@EnableWebSecurity //해당 파일로 시큐리티를 활성화        
 @Configuration //ioc
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		//인증이 필요한 페이지 요청이 오면 Form 로그인 page 인 /auth/signin으로 연결해준다.
		//super.configure(http);  super 삭제 - 기존 시큐리티가 가지고 있는 기능이 다 비활성화 됨.
		http.authorizeHttpRequests()
		.antMatchers("/","user/**","/image/**","/subscribe/**","/comment/**").authenticated()
		.anyRequest().permitAll()
		.and()
		.formLogin()
		.loginPage("/auth/signin")
		.defaultSuccessUrl("/");
	}
}