package com.cos.myjpa.web.user;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.myjpa.domain.post.Post;
import com.cos.myjpa.domain.user.User;
import com.cos.myjpa.domain.user.UserRepository;
import com.cos.myjpa.service.UserService;
import com.cos.myjpa.web.dto.CommonRespDto;
import com.cos.myjpa.web.post.dto.PostRespDto;
import com.cos.myjpa.web.user.dto.UserJoinReqDto;
import com.cos.myjpa.web.user.dto.UserLoginReqDto;
import com.cos.myjpa.web.user.dto.UserRespDto;

import lombok.RequiredArgsConstructor;

@CrossOrigin
@RequiredArgsConstructor
@RestController
public class UserController {
	
	private final UserRepository userRepository;
	private final HttpSession session; //메서드의 파라미터로 넣어줘도 DI가 된다.
	private final UserService userService;
	
	@GetMapping("/user")
	public CommonRespDto<?> findAll(){
		return new CommonRespDto<>(1,"성공",userService.전체찾기());
	}
	
	@GetMapping("/user/{id}")
	public CommonRespDto<?> findById(@PathVariable Long id){
		return new CommonRespDto<>(1,"성공",userService.한건찾기(id));
	}	
	
	@GetMapping("/user/{id}/post")
	public CommonRespDto<?> profile(@PathVariable Long id){
		return new CommonRespDto<>(1,"성공",userService.프로파일(id));
	}
	
	@PostMapping("/join") //auth(인증)
	public CommonRespDto<?> join(@RequestBody UserJoinReqDto userJoinReqDto){
		return new CommonRespDto<>(1,"성공",userService.회원가입(userJoinReqDto));		
	}
	
	@PostMapping("/login")
	public CommonRespDto<?> Login(@RequestBody UserLoginReqDto userLoginReqDto){
		
		User userEntity = userService.로그인(userLoginReqDto);
		
		if (userEntity == null) {  //이 부분은 AOP 처리를 해줘야 된다. 세션인증은 컨트롤러가 담당, 서비스가 담당하면 나중에..
			return new CommonRespDto<>(-1,"실패",null); //굳이 익센션을 타게 할 필요가 없다.			
		}else {
			userEntity.setPassword(null);
			session.setAttribute("principal", userEntity);
			return new CommonRespDto<>(1,"성공",userEntity);		
		}
	}

	
	
}
