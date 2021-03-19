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
import com.example.mybatisex01.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class ProductController { //간단히 할 생각이므로 서비스 생략
	
	private final ProductRepository productRepository;
	
	@PostMapping("/product")
	public String save(@RequestBody Product product) {
		productRepository.save(product);
		return "ok";
	}
	
	@DeleteMapping("/product/{id}")
	public void delete(@PathVariable int id) {
		productRepository.deleteById(id);
	}
	
	@PutMapping("/product/{id}")
	public String update(@PathVariable int id, @RequestBody Product product) {
		product.setId(id);
		productRepository.update(product);
		return "ok";
	}
	
	@GetMapping("/product")
	public List<Product> findAll(){
		return productRepository.findAll();
	}
	
	@GetMapping("/product/{id}")
	public Product findById(@PathVariable int id){
		return productRepository.findById(id);
	}
	
}
