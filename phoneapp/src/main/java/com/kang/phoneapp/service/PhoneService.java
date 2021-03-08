package com.kang.phoneapp.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kang.phoneapp.domain.Phone;
import com.kang.phoneapp.domain.PhoneRepostiory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PhoneService {

	private final PhoneRepostiory phoneRepostiory; 
	
	
	public List<Phone> 전체보기() {
		return phoneRepostiory.findAll();
	}

	public Phone 상세보기(Long id) {
		return phoneRepostiory.findById(id).get();
	}

	@Transactional
	public void 삭제하기(Long id) {
		phoneRepostiory.deleteById(id);
	}

	@Transactional
	public Phone 수정하기(Long id, Phone phone) {
		//영속화
		Phone phoneEntity = phoneRepostiory.findById(id).get();
		
		//영속화 된 객체를 수정
		phoneEntity.setName(phone.getName());
		phoneEntity.setTel(phone.getTel());		
		
		return phoneEntity;
	} //서비스 종료시에 영속성 컨텍스트가 변경을 감지해서 flush()로 DB에 반영함

	@Transactional
	public Phone 저장하기(Phone phone) {
		return phoneRepostiory.save(phone);
	}


}
