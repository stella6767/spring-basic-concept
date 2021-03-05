package com.cos.myjpa.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.myjpa.domain.user.User;
import com.cos.myjpa.domain.user.UserRepository;
import com.cos.myjpa.web.dto.CommonRespDto;
import com.cos.myjpa.web.user.dto.UserJoinReqDto;
import com.cos.myjpa.web.user.dto.UserLoginReqDto;
import com.cos.myjpa.web.user.dto.UserRespDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
	
	private final UserRepository userRepository;
	
	public List<UserRespDto> 전체찾기() {
		List<User> usersEntity = userRepository.findAll();
		
		List<UserRespDto> userRespDtos = usersEntity.stream().map((u)->{
			return new UserRespDto(u);
		}).collect(Collectors.toList());
		
		return userRespDtos;
	}
	
	public UserRespDto 한건찾기(Long id) {
		User userEntity = userRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("id를 찾을 수 없습니다."); //화살표 함수 안에는 sts툴은 자동완성 안됨
		});
		
		UserRespDto userRespDto = new UserRespDto(userEntity);
		return userRespDto;
	}
	
	public User 프로파일(Long id) {
		User userEntity = userRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("id를 찾을 수 없습니다."); //화살표 함수 안에는 sts툴은 자동완성 안됨
		}); //이런 식으로 따로 dto를 만들지 않고 @Jsonignoreproperties 로 간단히 구현가능.
		return userEntity;		
	}
	
	@Transactional //데이터 실패하면 rollback 기능//(rollbackFor = Exception.class) - 이런 식으로 롤백시 익셉션 처리가능
	public User 회원가입(UserJoinReqDto userJoinReqDto) {
		User userEntity = userRepository.save(userJoinReqDto.toEntity());
		return userEntity;
	}
	
	public User 로그인(UserLoginReqDto userLoginReqDto) {
		User userEntity = userRepository.findByUsernameAndPassword(userLoginReqDto.getUsername(), userLoginReqDto.getPassword());
		return userEntity;
	}
	

	
	
}
