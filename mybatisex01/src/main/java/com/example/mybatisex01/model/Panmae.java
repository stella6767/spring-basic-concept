package com.example.mybatisex01.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Panmae {
	private int id;
	private int productId; // 상품명
	private String username; // 상품코드
}
