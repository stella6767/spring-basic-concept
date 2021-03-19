package com.example.mybatisex01.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.mybatisex01.model.Product;
import com.example.mybatisex01.model.beans.PanmaeBean;
import com.example.mybatisex01.repository.PanmaeRepository;
import com.example.mybatisex01.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class PanmaeController { //간단히 할 생각이므로 서비스 생략
	
	private final PanmaeRepository panmaeRepository;
	
	@GetMapping("/panmae/{id}")
	public PanmaeBean findById(@PathVariable int id){
		System.out.println("뭔데 들어옴?" + id);
		return panmaeRepository.findByIdAndJoin(id);
	}
	
}
