package com.cos.jwtex1.config.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.cos.jwtex1.web.dto.LoginReqDto;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

//토큰 만들어주기
@RequiredArgsConstructor
public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter{
	
	//private final AuthenticationManager authenticationManager; //시큐리티가 이미 ioc에 이 객체를 등록시켜놨음
	
	private final AuthenticationManager authenticationManager;
	
	// 주소: Post 요청으로 /login 요청
	@Override   //기존 로그인 방식을 갈아치우는 과정
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		System.out.println("로그인 요청 옴.");
		
		ObjectMapper om = new ObjectMapper();
		LoginReqDto loginReqDto = null;
		
		try {
			loginReqDto = om.readValue(request.getInputStream(), LoginReqDto.class);
		} catch (Exception e) {
			//throw new 커스텀익셉션
			e.printStackTrace();
		}
		
		//1. UsernamePassword 토큰 만들기
		UsernamePasswordAuthenticationToken authToken = 
				new UsernamePasswordAuthenticationToken(loginReqDto.getUsername(), loginReqDto.getPassword());
		System.out.println("1");
		//2. AuthenticationManager에게 토큰을 전달하면 -> 자동으로 UserDetailsService가 호출=> 응답 Authentication
		Authentication authentication = authenticationManager.authenticate(authToken);
		System.out.println("2");
		return authentication;
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
			//JWT 토큰 만들어서 응답
		System.out.println("로그인 완료되어서 세션 만들어짐. 이제 JWT토큰 만들어서 response.header에 응답할 차리");

	}

}
