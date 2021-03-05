package com.cos.myjpa.web.user;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.myjpa.domain.user.User;
import com.cos.myjpa.domain.user.UserRepository;
import com.cos.myjpa.web.dto.CommonRespDto;
import com.cos.myjpa.web.user.dto.UserJoinReqDto;
import com.cos.myjpa.web.user.dto.UserLoginReqDto;

import lombok.RequiredArgsConstructor;

@CrossOrigin
@RequiredArgsConstructor
@RestController
public class UserController {
	
	private final UserRepository userRepository;
	private final HttpSession session; //메서드의 파라미터로 넣어줘도 DI가 된다.
	
	@PostMapping("/join") //auth(인증)
	public CommonRespDto<?> join(@RequestBody UserJoinReqDto userJoinReqDto){
		User userEntity = userRepository.save(userJoinReqDto.toEntity());
		return new CommonRespDto<>(1,"성공",userEntity);		
	}
	
	@PostMapping("/login")
	public CommonRespDto<?> Login(@RequestBody UserLoginReqDto userLoginReqDto){
		User userEntity = userRepository.findByUsernameAndPassword(userLoginReqDto.getUsername(), userLoginReqDto.getPassword());
		
		if (userEntity == null) {
			return new CommonRespDto<>(-1,"실패",null); //굳이 익센션을 타게 할 필요가 없다.			
		}else {
			userEntity.setPassword(null);
			session.setAttribute("principal", userEntity);
			return new CommonRespDto<>(1,"성공",userEntity);		
		}
	}
	
	@GetMapping("/test/user/{id}") //유저정보
	public CommonRespDto<?> userInfo(@PathVariable Long id){
		
		User principal = (User)session.getAttribute("principal");
		if (principal == null) {
			return new CommonRespDto<>(-1,"실패",null);
		}else {
			User userEntity = userRepository.findById(id).get();
			return new CommonRespDto<>(1,"성공",userEntity);
		}
	}
	
	
}
