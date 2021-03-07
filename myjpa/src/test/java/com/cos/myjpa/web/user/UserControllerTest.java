package com.cos.myjpa.web.user;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;

import com.cos.myjpa.domain.user.User;
import com.cos.myjpa.domain.user.UserRepository;
import com.cos.myjpa.web.user.dto.UserJoinReqDto;
import com.cos.myjpa.web.user.dto.UserLoginReqDto;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class UserControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private EntityManager entityManager;
	
	@BeforeEach //각각의 단위 테스트 전에 초기화할 함수를 적어준다. 
	public void init() {
		//entityManager.createNativeQuery("ALTER TABLE user ALTER COLUMN id RESTART WITH 1").executeUpdate();
		//Transictional를 안 걸어줘서 필요없다.
	}
	
	
	@Test
	public void 회원가입_테스트() throws Exception {
		log.info("회원가입_테스트() 시작 ==========================================================");
		// given(테스트를 하기 위한 준비)
		UserJoinReqDto userJoinReqDto = new UserJoinReqDto();
		userJoinReqDto.setEmail("ssar@nate.com");
		userJoinReqDto.setPassword("1234");
		userJoinReqDto.setUsername("ssar");
		
		String content = new ObjectMapper().writeValueAsString(userJoinReqDto);
		log.info(content);

		// when(테스트 실행)
		ResultActions resultAction = mockMvc.perform(post("/join").contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(content).accept(MediaType.APPLICATION_JSON_UTF8));

		// then(검증)
		resultAction.andExpect(jsonPath("$.msg")
				.value("성공"))
				.andDo(MockMvcResultHandlers.print());

	}
	
	@Test
	public void 로그인_테스트() throws Exception{ //@Lombok의 toString 무한반복로직 해결!
		log.info("로그인 테스트 시작 ==================================================");
		UserLoginReqDto userLoginReqDto = new UserLoginReqDto();
		userLoginReqDto.setUsername("ssar");
		userLoginReqDto.setPassword("1234");
		
		String content = new ObjectMapper().writeValueAsString(userLoginReqDto);
		log.info(content);
		
		ResultActions resultAction = mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(content).accept(MediaType.APPLICATION_JSON_UTF8));
		
		resultAction.andExpect(jsonPath("$.msg").value("성공")).andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void 한건찾기_테스트()throws Exception{
		Long id = 1L;
		ResultActions resultAction = mockMvc.perform(get("/user/{id}", id).accept(MediaType.APPLICATION_JSON_UTF8));
		resultAction.andExpect(jsonPath("$.msg").value("성공")).andDo(MockMvcResultHandlers.print());
	}
	
	
	@Test
	public void 전체찾기_테스트()throws Exception{
		ResultActions resultAction = mockMvc.perform(get("/user").accept(MediaType.APPLICATION_JSON_UTF8));
		resultAction.andExpect(jsonPath("$.msg").value("성공")).andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void 프로파일_테스트()throws Exception{
		Long id = 1L;
		ResultActions resultAction = mockMvc.perform(get("/user/{id}/post", id).accept(MediaType.APPLICATION_JSON_UTF8));
		resultAction.andExpect(jsonPath("$.msg").value("성공")).andDo(MockMvcResultHandlers.print());
	}
	
	
	

}
