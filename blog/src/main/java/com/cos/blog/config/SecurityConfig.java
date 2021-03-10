package com.cos.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration // ioc 등록
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	//Ioc 등록만 하면 AuthenticationManager가 Bcrypt로 자동 검증해줌. 예전에는 알려주는 로직을 추가해야함.
	@Bean
	public BCryptPasswordEncoder encode() {
		return new BCryptPasswordEncoder();
	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();   
		http.authorizeRequests()
		.antMatchers("/user","/post").access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')") //user와 post만 성문을 닫아둔다.
		.antMatchers("/admin").access("hasRole('ROLE_ADMIN')") //관리자만 들어올 수 있도록
		.anyRequest().permitAll()
		.and()
		.formLogin()//x-www-form-urlencoded, formlogin()은 json 던지면 못 받음
		.loginPage("/loginForm")//리다이렉션
		.loginProcessingUrl("/login"); //x-www-form-urlencoded, 시큐리티가 post 로 /login 이라는 주소가 들어오면 낚아챔
	}
}
