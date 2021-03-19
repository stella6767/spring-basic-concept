package com.cos.blog.web.user.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class UserUpdateReqDto {
	
	@NotBlank(message = "username을 입력하지 않았습니다.")
	private String username;
	
	@NotBlank(message = "password를 입력하지 않았습니다.")
	private String password;
	
	@NotBlank(message = "이메일을 입력해주세요.")
	private String email;
	
	//toEntity 안만드는 이유는 더티체킹 할 것이기 때문!
}
