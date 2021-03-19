package com.cos.blog.web.reply.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.cos.blog.domain.reply.Reply;
import com.cos.blog.domain.user.User;

import lombok.Data;

@Data
public class ReplySaveReqDto {
	
	private User user;
	private int postId;
	
	@NotBlank(message = "댓글의 내용이 없습니다.")
	@NotNull(message = "댓글 키값이 없습니다.")
	@Size(max = 20,message = "댓글 길이를 초과하였습니다.")
	private String content;
	
	public Reply toEntity() {
		return Reply.builder()
				.user(user)
				.content(content)
				.build();				
	}
}
