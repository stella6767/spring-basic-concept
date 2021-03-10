package com.cos.myjpa.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import com.cos.myjpa.filter.MyAuthentionFilter;



@Configuration//web.xml
public class FilterConfig {
	@Bean
	//@Order(0) // 다른 거
	public FilterRegistrationBean<MyAuthentionFilter> authenticationFilterRegister() {
		FilterRegistrationBean<MyAuthentionFilter> bean = 
				new FilterRegistrationBean<>(new MyAuthentionFilter());
		
		bean.addUrlPatterns("/test");
		bean.setOrder(0); //필터 순서.. 낮은 숫자가 먼저 실행됨
		
		return bean;   //@Bean 이니까 리턴해줘야 IOC 등록
	}
}
