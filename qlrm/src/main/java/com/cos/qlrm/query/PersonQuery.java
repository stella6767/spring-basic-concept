package com.cos.qlrm.query;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PersonQuery {
	
	private final EntityManager em;
	
	public Query personPivot() {
		Query query = em.createNativeQuery("SELECT id, name, job as job1, job as job2  FROM person");	
		return query;
	}
}
