package com.cos.mybatisex01.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.cos.mybatisex01.model.beans.PanmaeBean;
import com.cos.mybatisex01.repository.PanmaeRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class PanmaeController {

	private final PanmaeRepository panmaeRepository;
	
	@GetMapping("/panmae/{id}")
	public PanmaeBean findByIdAndJoin(@PathVariable int id){
		return panmaeRepository.findByIdAndJoin(id); 
	}
}
