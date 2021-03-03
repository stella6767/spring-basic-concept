package com.cos.myjpa.web.post;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.myjpa.domain.post.Post;
import com.cos.myjpa.web.post.dto.PostSaveReqDto;

/**
 * ORM = Object Relation Mapping (자바 오브젝트 먼저 만들고 DB에 테이블을 자동생성, 자바 오브젝트로 연관관계 맺을 수 있음)
 * JPA는 자바오브젝트를 영구적으로 저장하기 위한 인터페이스(함수)
 *
 */

@RestController
public class PostController {

	
}
