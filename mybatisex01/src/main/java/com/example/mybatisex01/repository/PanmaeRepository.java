package com.example.mybatisex01.repository;

import org.apache.ibatis.annotations.Mapper;

import com.example.mybatisex01.model.beans.PanmaeBean;

@Mapper
public interface PanmaeRepository {
	public PanmaeBean findByIdAndJoin(int id); 
}
