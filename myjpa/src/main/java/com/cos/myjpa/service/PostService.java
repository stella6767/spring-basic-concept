package com.cos.myjpa.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.myjpa.domain.post.Post;
import com.cos.myjpa.domain.post.PostRepository;
import com.cos.myjpa.domain.user.User;
import com.cos.myjpa.web.post.dto.PostRespDto;
import com.cos.myjpa.web.post.dto.PostSaveReqDto;
import com.cos.myjpa.web.post.dto.PostUpdateReqDto;

import lombok.RequiredArgsConstructor;

// JPA 영속성컨텍스트는 변경감지를 하는데, 언제하느냐? 서비스 종료시에 함!!


@RequiredArgsConstructor
@Service
public class PostService {

	private final PostRepository postRepository;

	@Transactional
	public Post 글쓰기(PostSaveReqDto postSaveReqDto, User principal) {
		
		Post post = postSaveReqDto.toEntity();
		post.setUser(principal);	
		Post postEntity = postRepository.save(post); // 실패 =>Exception을 탄다.
		
		return postEntity;
	}

	
	@Transactional(readOnly = true) //변경감지 안함, 고립성! 
	public PostRespDto 한건찾기(Long id) {
		// 옵셔널 get(),orElseGet(),orElseThrow()
		Post postEntity = postRepository.findById(id).orElseThrow(() -> {
			return new IllegalArgumentException("id를 찾을 수 없습니다."); // 화살표 함수 안에는 sts툴은 자동완성 안됨
		});

		PostRespDto postRespDto = new PostRespDto(postEntity);
		return postRespDto;
	}

	@Transactional(readOnly = true) //readonly는 select 할 때 다 걸어주자. 쓸데없이 변경감지 안 하도록
	public List<Post> 전체찾기() {
		List<Post> postEntitys = postRepository.findAll();
		return postEntitys;
	}

	@Transactional
	public Post 수정하기(Long id,PostUpdateReqDto postUpdateReqDto) {//save() 필요없음. 알아서 변경감지해서 수정해줌.
		// 영속화
		Post postEntity = postRepository.findById(id).orElseThrow(() -> {
			return new IllegalArgumentException("id를 찾을 수 없습니다.");
		});		// 기존의 id는 그대로 둔채.. 그래야 위치를 찾을 수 있음

		postEntity.setTitle(postUpdateReqDto.getTitle());
		postEntity.setContent(postUpdateReqDto.getContent()); 

		return postEntity;
	}//트랜잭션(서비스) 종료시 영속성 컨텍스트에 영속화 되어있는 모든 객체의 변경을 감지하여 변경된 아이들을 flush한다.(commit) = 더티체킹

	public void 삭제하기(Long id) {
		postRepository.deleteById(id); //삭제는 리턴이 필요없다. 삭제하다가 오류나서 GlobalExceptionHandler로 처리하면 됨
	}//try-catch로 묶어서 내가 생성한 익셉션 클래스에서 처리해도 된다.

}
