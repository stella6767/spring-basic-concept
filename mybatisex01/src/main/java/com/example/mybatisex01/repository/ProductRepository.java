package com.example.mybatisex01.repository;



import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.example.mybatisex01.model.Product;

@Mapper   //인터페이스는 메모리에 띄울 수 없어서, 구현체를 자체적으로 만들어줌(Impl)
public interface ProductRepository {	
	public void save(Product product); 
	public void deleteById(int id);
	public void update(Product product);
	public List<Product> findAll();
	public Product findById(int id);	
}
