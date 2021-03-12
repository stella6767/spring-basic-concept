package com.cos.blog.config.oauth;

import java.util.UUID;

import org.apache.catalina.realm.X509UsernameRetriever;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.cos.blog.config.auth.PrincipalDetails;
import com.cos.blog.domain.user.User;
import com.cos.blog.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OAuth2DetailsService extends DefaultOAuth2UserService{

	private final UserRepository userRepository;
	
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		System.out.println("OAuth 로그인 진행중................");
		System.out.println(userRequest.getAccessToken().getTokenValue());
		//이 토큰을 가지고 회원정보 요청(재요청)
		
		//1. AccessToken 으로 회원정보를 받았다는 의미
		OAuth2User oAuth2User = super.loadUser(userRequest); //이 과정을 loadUser()로 제공. 구글, 페이스북, 트위터, 깃허브
		
		//레트로핏 https://www.googleapis.com/drive/v2/files?access_token=userRequest.getAccessToken().getTokenValue()
		System.out.println("==========================");
		System.out.println(oAuth2User.getAttributes()); //여기서 얻은 정보를 바탕으로 info 클래스 작성
		System.out.println("==========================");
		
		return processOAuth2User(userRequest,oAuth2User);
	}
	
	//구글 로그인 프로세스
	private OAuth2User processOAuth2User (OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
		//1번 통합 클래스를 생성
		OAuth2UserInfo oAuth2UserInfo = null;
		System.out.println("머로 로그인 됐지?" + userRequest.getClientRegistration().getClientName());
		if(userRequest.getClientRegistration().getClientName().equals("Google")) {
			oAuth2UserInfo = new GoogleInfo(oAuth2User.getAttributes());
		}else if((userRequest.getClientRegistration().getClientName().equals("Facebook"))){
			oAuth2UserInfo = new FaceBookInfo(oAuth2User.getAttributes());
		}		
			
		//2번 최초: 회원가입 + 로그인 최초X : 로그인 
		User userEntity = userRepository.findByUsername(oAuth2UserInfo.getUsername());
		
		UUID uuid = UUID.randomUUID();
		String encPassword = new BCryptPasswordEncoder().encode(uuid.toString());
		
		if(userEntity == null) {
			System.out.println("최초 사용자입니다. 자동 회원가입을 진행합니다.");
			User user = User.builder()
					.username(oAuth2UserInfo.getUsername())
					.password(encPassword)
					.email(oAuth2UserInfo.getEmail())
					.build();
			
			userEntity = userRepository.save(user);

			return new PrincipalDetails(userEntity, oAuth2UserInfo.getAttributes());
			
		}else { //이미 회원가입이 완료됐다는 뜻(원래는 구글 정보가 변경될 수 있기 떄문에 update 해야되는데 지금은 안하겠음)
			
			System.out.println("회원정보가 있습니다. 바로 로그인합니다.");
			
			userEntity = updateExistingUser(userEntity, oAuth2UserInfo);			
			return new PrincipalDetails(userEntity, oAuth2UserInfo.getAttributes());
		}
		
	}
	
    private User updateExistingUser(User existingUser, OAuth2UserInfo oAuth2UserInfo) {
        existingUser.builder()
            .email(oAuth2UserInfo.getEmail())
            .build();
        
        return userRepository.save(existingUser);
    }

}
