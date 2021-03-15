package com.cos.blog.config.oauth;

import java.util.Map;

import lombok.Data;

public class KaKaoInfo extends OAuth2UserInfo {

	public KaKaoInfo(Map<String, Object> attributes) {
		super(attributes);
	}

	@Override
	public String getId() {
		return (String)attributes.get("id");
	}

	@Override
	public String getName() {
		Map<String, Object> temp = (Map)attributes.get("properties");
		return (String)temp.get("nickname");
	}

	@Override
	public String getEmail() {
		Map<String, Object> temp = (Map)attributes.get("kakao_account");
		return (String)temp.get("email");
	}

	@Override
	public String getImageUrl() {
		Map<String, Object> temp = (Map)attributes.get("properties");	
		return (String)temp.get("profile_image");
	}

	@Override
	public String getUsername() {
		return "KaKao_"+attributes.get("id").toString();
	}	
}
