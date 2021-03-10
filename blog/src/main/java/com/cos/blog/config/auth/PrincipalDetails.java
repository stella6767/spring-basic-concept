package com.cos.blog.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cos.blog.domain.user.User;

import lombok.Data;

@Data
public class PrincipalDetails implements UserDetails{

	private User user;
	
	public PrincipalDetails(User user) {
		this.user = user;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() { //만료될 때 처리 로직
		// TODO Auto-generated method stub
		return true;   //안 쓸거면 그냥 true 
	}

	@Override
	public boolean isAccountNonLocked() { //예를 들어 아이디 3번 시도했는데 실패했으면 락인한다든가.. 그런 로직
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() { //비밀번호 만료
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() { //계정이 현재 유효한지
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		System.out.println("Role 검증 하는 중");
		Collection<GrantedAuthority> collectors = new ArrayList<>();
		collectors.add(()->"ROLE_"+user.getRole().toString());
		
		return collectors;
	}

}
