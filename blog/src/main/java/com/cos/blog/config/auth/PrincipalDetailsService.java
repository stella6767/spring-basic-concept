package com.cos.blog.config.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.cos.blog.domain.user.User;
import com.cos.blog.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService{
	
	
	private final UserRepository userRepository;
	
	@Override //Authentication Maneger 가 보내줌
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		System.out.println("/login 이 호출되면 자동 실행되어 username 이 DB에 있는지 확인한다.");
		User principal = userRepository.findByUsername(username);
		
		if(principal == null) {
			return null;
		}else {
			return new PrincipalDetails(principal);
		}
	}

}
