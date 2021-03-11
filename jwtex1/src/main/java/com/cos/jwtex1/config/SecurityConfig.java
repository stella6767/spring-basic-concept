package com.cos.jwtex1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cos.jwtex1.config.jwt.JwtLoginFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	//Bearer Auth(배리어 인증방식)
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.addFilter(new JwtLoginFilter(authenticationManager()))
		//.addFilter(null)
		.csrf().disable()
		.formLogin().disable()
		.httpBasic().disable()  //기존 http 인증방식 무효화
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //제이세션을 사용하는 statefull 안씀- 이거 안 해줘도 상관은 없지만 쓸데없는 게 달라붙음		
		.and()
		.authorizeRequests()
		.antMatchers("/user/**").access("hasRole('ROLE_USER')")
		.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
		.anyRequest().permitAll();	
		
	}
}
