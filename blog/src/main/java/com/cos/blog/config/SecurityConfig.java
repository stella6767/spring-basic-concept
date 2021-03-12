package com.cos.blog.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.cos.blog.config.oauth.OAuth2DetailsService;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@EnableWebSecurity
@Configuration // ioc 등록
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	
	private final OAuth2DetailsService oAuth2DetailsService;
	
	//Ioc 등록만 하면 AuthenticationManager가 Bcrypt로 자동 검증해줌. 예전에는 알려주는 로직을 추가해야함.
	@Bean
	public BCryptPasswordEncoder encode() {
		return new BCryptPasswordEncoder();
	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();   
		http.authorizeRequests()
		.antMatchers("/user/**","/post/**").access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')") //user와 post만 성문을 닫아둔다.
		.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')") //관리자만 들어올 수 있도록	    
		.anyRequest().permitAll()
		.and()
		.formLogin()//x-www-form-urlencoded, formlogin()은 json 던지면 못 받음
		.loginPage("/loginForm")//리다이렉션
		.loginProcessingUrl("/login")//x-www-form-urlencoded, 시큐리티가 post로 온 /login 이라는 주소가 들어오면 낚아챔
//		.successHandler(new AuthenticationSuccessHandler() {
//			
//			@Override
//			public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
//					Authentication authentication) throws IOException, ServletException {
//				response.sendRedirect("/"); //무조건 로그인 성공하면 이 url로 접근
//				
//			}
//		});
		.defaultSuccessUrl("/") //위의 successHandler와 비슷하지만, 사용자가 갈려고 하는 페이지가 있을 때는 안 먹힘
		.and()
		.oauth2Login()
		.userInfoEndpoint()
		.userService(oAuth2DetailsService);
	}
}
