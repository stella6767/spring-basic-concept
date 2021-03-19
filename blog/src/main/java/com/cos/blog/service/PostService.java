package com.cos.blog.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.domain.post.Post;
import com.cos.blog.domain.post.PostRepository;
import com.cos.blog.web.post.dto.PostSaveReqDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PostService {

	private final PostRepository postRepository;
	
	@Transactional(readOnly = true) //1.변경감지 안하도록 하고(쓸데없는 연산제거), 2.고립성 유지 
	public Page<Post> 전체찾기(Pageable pageable){
		return postRepository.findAll(pageable);
	}
	
	
	@Transactional
	public Post 글쓰기(Post post) {
		return postRepository.save(post);
	}
	
	@Transactional
	public int 삭제하기(int id, int userId) {
		Post postEntity = postRepository.findById(id).get();
		if(postEntity.getUser().getId() == userId) {
			postRepository.deleteById(id);
			return 1;
		}else {
			return -1;
		}
		
		
		
	}
	
	
	@Transactional(readOnly = true)
	public Post 상세보기(int id){
		return postRepository.findById(id).get();
	}
	
	
	@Transactional
	public void 수정하기(int id, PostSaveReqDto postSaveReqDto, int userId) {
		Post postEntity = postRepository.findById(id).get(); //영속화
		if(postEntity.getUser().getId() == userId) {
			postEntity.setTitle(postSaveReqDto.getTitle());
			postEntity.setContent(postSaveReqDto.getContent());
		}else {
			
		}

	}//더티체킹
	
	
	@Transactional(readOnly = true) //1.변경감지 안하도록 하고(쓸데없는 연산제거), 2.고립성 유지 
	public Page<Post> 검색하기(String keyword, Pageable pageable){
		
		return postRepository.findByKeyword(keyword,pageable);
	}
	
	
}
