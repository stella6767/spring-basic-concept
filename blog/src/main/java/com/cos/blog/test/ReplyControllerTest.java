package com.cos.blog.test;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.domain.post.Post;
import com.cos.blog.domain.post.PostRepository;
import com.cos.blog.domain.reply.Reply;
import com.cos.blog.domain.reply.ReplyRepository;
import com.cos.blog.domain.user.User;
import com.cos.blog.domain.user.UserRepository;
import com.cos.blog.web.dto.CMRespDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class ReplyControllerTest {

	private final ReplyRepository replyRepository;
	private final PostRepository postRepository;
	private final UserRepository userRepository;
	
	//게시글 상세보기(user,post,reply들)
	
	@GetMapping("/test/reply2")
	public CMRespDto<?> 저장하기() {
		Post postEntity = postRepository.findById(3).get();
		User userEntity = userRepository.findById(2).get();
		
		Reply reply = new Reply();
		reply.setContent("최초 테스트");
		reply.setPost(postEntity);
		reply.setUser(userEntity);
		
		Reply replyEntity = replyRepository.save(reply);
		
		return new CMRespDto<>(1, replyEntity);
	}
	
	
	@GetMapping("/test/reply")
	public CMRespDto<?> aaa() {
		List<Reply> replys = replyRepository.findAll();
		
		return new CMRespDto<>(1, replys);
	}
	
	
	
}
