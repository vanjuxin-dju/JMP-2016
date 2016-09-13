package com.epam.example.ecinema.repository.base;

import java.util.List;

import org.hibernate.criterion.Criterion;

public interface BaseRepository<T> {
	List<T> findAll();
	
	List<T> findByCriteria(Criterion criterion);
	
	T findById(Long id);
	
	Long create(T object);
	
	T update(T object);
	
	void delete(T persistentObject);
	
	void deleteById(Long id);
}
