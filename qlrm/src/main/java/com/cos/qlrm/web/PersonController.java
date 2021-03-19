package com.cos.qlrm.web;

import java.util.List;

import javax.persistence.Query;

import org.qlrm.mapper.JpaResultMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.qlrm.domain.MyDto;
import com.cos.qlrm.domain.PersonRepository;
import com.cos.qlrm.query.PersonQuery;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class PersonController {

	private final PersonRepository personRepository;
	private final PersonQuery personQuery;
	
	
	@GetMapping("/person")
	public List<MyDto> findAll(String name) {
		
		JpaResultMapper jpaResultMapper = new JpaResultMapper();
		List<MyDto> myDtos = jpaResultMapper.list(personQuery.personPivot(), MyDto.class);
		return myDtos;
	}
	
}


