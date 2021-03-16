package com.cos.blog.web.reply.dto;

import com.cos.blog.domain.reply.Reply;
import com.cos.blog.domain.user.User;

import lombok.Data;

@Data
public class ReplySaveReqDto {
	private User user;
	private int postId;
	private String content;
	
	public Reply toEntity() {
		return Reply.builder()
				.user(user)
				.content(content)
				.build();				
	}
}
