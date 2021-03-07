package com.cos.myjpa.web.post;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.cos.myjpa.domain.post.PostRepository;
import com.cos.myjpa.domain.user.User;
import com.cos.myjpa.domain.user.UserRepository;
import com.cos.myjpa.service.UserService;
import com.cos.myjpa.web.dto.CommonRespDto;
import com.cos.myjpa.web.post.dto.PostSaveReqDto;
import com.cos.myjpa.web.post.dto.PostUpdateReqDto;
import com.cos.myjpa.web.user.dto.UserLoginReqDto;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PostControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private UserRepository userRepository;

//	@Autowired
//	private HttpSession session; //쓰면 안 됨!

	protected MockHttpSession session;
	protected MockHttpServletRequest request;

	@Autowired
	private UserService userService;

	@Autowired
	private EntityManager entityManager;

	@BeforeEach // 각각의 단위 테스트 전에 초기화할 함수를 적어준다.
	public void init() {
		entityManager.createNativeQuery("ALTER TABLE post ALTER COLUMN id RESTART WITH 1").executeUpdate();

		UserLoginReqDto userLoginReqDto = new UserLoginReqDto();
		userLoginReqDto.setUsername("ssar");
		userLoginReqDto.setPassword("1234");

		User userEntity = userService.로그인(userLoginReqDto);
		userEntity.setPassword(null);
		session = new MockHttpSession();
		session.setAttribute("principal", userEntity);
		
		//request = new MockHttpServletRequest();
		//request.setSession(session);
		//RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
	}
	
	@AfterEach
	public void afterEach() {
		 session.clearAttributes(); 
		 session = null;
	}

	@Test
	public void 글쓰기_테스트() throws Exception {

		PostSaveReqDto postSaveReqDto = new PostSaveReqDto();
		postSaveReqDto.setTitle("제목1");
		postSaveReqDto.setContent("내용1");

		String content = new ObjectMapper().writeValueAsString(postSaveReqDto);

		ResultActions resultAction = mockMvc.perform(post("/post").contentType(MediaType.APPLICATION_JSON_UTF8)
				.session(session) //가짜 세션을 사용
				.content(content).accept(MediaType.APPLICATION_JSON_UTF8));

		resultAction.andExpect(jsonPath("$.msg").value("성공")).andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void 한건찾기_테스트() throws Exception {
		Long id = 2L;
		ResultActions resultAction = mockMvc.perform(get("/post/{id}", id).accept(MediaType.APPLICATION_JSON_UTF8));
		resultAction.andExpect(jsonPath("$.msg").value("성공")).andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void 전체찾기_테스트() throws Exception {
		ResultActions resultAction = mockMvc.perform(get("/post").accept(MediaType.APPLICATION_JSON_UTF8));
		resultAction.andExpect(jsonPath("$.msg").value("성공")).andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void 수정하기_테스트() throws Exception {
		Long id = 2L;
		PostUpdateReqDto postUpdateReqDto = new PostUpdateReqDto();
		postUpdateReqDto.setTitle("제목 수정");
		postUpdateReqDto.setContent("내용 수정");

		String content = new ObjectMapper().writeValueAsString(postUpdateReqDto);

		ResultActions resultAction = mockMvc.perform(put("/post/{id}", id).contentType(MediaType.APPLICATION_JSON_UTF8)
				.session(session) //가짜 세션을 사용
				.content(content).accept(MediaType.APPLICATION_JSON_UTF8));

		resultAction.andExpect(jsonPath("$.msg").value("성공")).andDo(MockMvcResultHandlers.print());

	}

	@Test
	public void 삭제하기_테스트() throws Exception {
		Long id = 2L;

		ResultActions resultAction = mockMvc.perform(delete("/post/{id}", id)); //가짜 세션을 사용

		resultAction.andExpect(jsonPath("$.msg").value("성공")).andDo(MockMvcResultHandlers.print());
	}

}
