package com.cos.blog.web.auth.dto;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.cos.blog.domain.user.User;

import lombok.Data;

//Valid 나중에 처리하자.

@Data
public class AuthJoinReqDto {
	
	@NotNull(message = "유저네임 키값이 없습니다.") //통신에 거는 제약조건
	@NotBlank(message = "유저네임을 입력해주세요.")
	@Size(max = 20,message = "유저네임 길이를 초과하였습니다.")
	private String username;
	
	@NotNull(message = "비밀번호가 없습니다.")
	private String password;
	
	@NotBlank(message = "이메일을 입력해주세요.")
	private String email;
	
	public User toEntity() {
		return User.builder()
				.username(username)
				.password(password)
				.email(email)
				.build();
	}
}
