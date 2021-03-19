package com.cos.blog.web;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.blog.config.auth.PrincipalDetails;
import com.cos.blog.domain.post.Post;
import com.cos.blog.domain.post.PostRepository;
import com.cos.blog.service.PostService;
import com.cos.blog.web.dto.CMRespDto;
import com.cos.blog.web.post.dto.PostSaveReqDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class PostController {

	private final PostService postService;
		
	
	@GetMapping("/")
	public String findAll(Model model, @PageableDefault(sort = "id",direction = Sort.Direction.DESC, size = 4) Pageable pageable,
			@AuthenticationPrincipal PrincipalDetails details) {
		
		System.out.println("누구로 로그인 됐을까?");
//		System.out.println(details.isOAuth()); 요것 때문에 로그인 안하면 널 포인트 익셉션 뜸
//		System.out.println(details.getAttributes());
//		System.out.println(details.getUser().getUsername());
		System.out.println("페이지넘버: " + pageable.getPageNumber());
		System.out.println("offest: " +pageable.getOffset()); //시작점
		
		Page<Post> posts = postService.전체찾기(pageable);		
		
		model.addAttribute("posts",posts);
		return "post/list"; 
	}
	
	@GetMapping("/post/saveForm")
	public String saveForm() {
		
		return "post/saveForm";
	}
	
	@GetMapping("/post/{id}/updateForm")
	public String updateForm(@PathVariable int id, Model model) {
		Post postEntity = postService.상세보기(id);
		model.addAttribute("post",postEntity);
		return "post/updateForm";
	}
	
	@GetMapping("/post/{id}")
	public String detail(@PathVariable int id, Model model) {
		Post postEntity = postService.상세보기(id);
		model.addAttribute("post", postEntity);
		
		return "post/detail"; //ViewResolver 
	}
	
	@PostMapping("/post")
	public String save(PostSaveReqDto postSaveReqDto, @AuthenticationPrincipal PrincipalDetails details
			, BindingResult bindingResult) {
		
		Post post = postSaveReqDto.toEntity();
		post.setUser(details.getUser());
		Post postEntity = postService.글쓰기(post);
		
		if(postEntity == null) {
			return "post/saveForm";
		}else {
			return "redirect:/";
		}
		
	}
	
	
	@GetMapping("/post/search")
	public String search(Model model, String keyword,
			@PageableDefault(sort = "id",direction = Sort.Direction.DESC, size = 4) Pageable pageable) {
		
		System.out.println("키워드 "+keyword);
		System.out.println("offset: "+ pageable.getOffset());
		Page<Post> posts = postService.검색하기(keyword,pageable);	
		
		System.out.println(posts);
		System.out.println(posts.getTotalElements());
		System.out.println(posts.isLast());
		System.out.println(posts.isFirst());
			
		model.addAttribute("posts",posts);
		model.addAttribute("keyword",keyword);
		
		return "post/searchlist";
	}
	
	@PutMapping("/post/{id}")
	public @ResponseBody CMRespDto<?> update(@PathVariable int id, @RequestBody PostSaveReqDto postSaveReqDto,  
			@AuthenticationPrincipal PrincipalDetails principalDetails, BindingResult bindingResult){
		
		postService.수정하기(id, postSaveReqDto, principalDetails.getUser().getId());
		return new CMRespDto<>(1, null);
	}
	
	
	
	@DeleteMapping("/post/{id}")
	public @ResponseBody CMRespDto<?> deleteById(@PathVariable int id,  @AuthenticationPrincipal PrincipalDetails principalDetails){
		int result = postService.삭제하기(id, principalDetails.getUser().getId());
		return new CMRespDto<>(result,null);
	}
	
	
	
}
