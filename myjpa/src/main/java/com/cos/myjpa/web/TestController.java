package com.cos.myjpa.web;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.myjpa.domain.post.Post;
import com.cos.myjpa.domain.post.PostRepository;
import com.cos.myjpa.domain.user.User;
import com.cos.myjpa.web.dto.CommonRespDto;
import com.cos.myjpa.web.post.dto.PostSaveReqDto;
import com.cos.myjpa.web.post.dto.PostUpdateReqDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class TestController {
	
	private final PostRepository postRepository;
	
	@GetMapping("/test")
	public String test() {
		return "test";
	}
	
	
	@PostMapping("/test/post")
	public CommonRespDto<?> save(@RequestBody PostSaveReqDto postSaveReqDto) { //title,content
		
		//원래는 세션값을 넣어야 함.
		//User user = new User(1L,"ssar","1234","ssar@naye.com",LocalDateTime.now());
		
		Post postEntity = postRepository.save(postSaveReqDto.toEntity()); // 실패 =>Exception을 탄다.
		//postEntity.setUser(user);
		
		return new CommonRespDto<>(1,"성공",postEntity);
	}
	
	@GetMapping("/test/post")
	public CommonRespDto<?> findAll(){
		List<Post> postEntity = postRepository.findAll();
		return new CommonRespDto<>(1, "성공", postEntity);
	}
	
	
	@GetMapping("test/post/{id}")
	public CommonRespDto<?> findById(@PathVariable Long id){
		
		Post postEntity = postRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("id를 찾을 수 없습니다."); //화살표 함수 안에는 sts툴은 자동완성 안됨
		});
		
		return new CommonRespDto<>(1,"성공",postEntity);
	}
	
	@PutMapping("/test/post/{id}")
	public CommonRespDto<?> update(@PathVariable Long id, @RequestBody PostUpdateReqDto postUpdateReqDto){
		
		Post postEntity = postRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("id를 찾을 수 없습니다."); 
		});
		
		//기존의 id는 그대로 둔채.. 그래야 위치를 찾을 수 있음
		postEntity.setTitle(postUpdateReqDto.getTitle());
		postEntity.setContent(postUpdateReqDto.getContent());
		Post postUpdateEntity = postRepository.save(postEntity); //더티 체킹을 사용해야 하는데 그러려면 @Service 만들어야 가능함!!
		
		return new CommonRespDto<>(1,"성공",postUpdateEntity);
	}
	
	
	@DeleteMapping("/test/post/{id}")
	public CommonRespDto<?> deleteById(@PathVariable Long id){
		postRepository.deleteById(id); //void 타입. 실패했으먄 illgalaception
		return new CommonRespDto<>(1,"성공",null);
	}
	

}