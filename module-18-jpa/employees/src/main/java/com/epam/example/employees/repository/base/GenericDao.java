package com.epam.example.employees.repository.base;

import java.io.Serializable;
import java.util.List;

public interface GenericDao<T, PK extends Serializable> {
	PK create(T object);
	
	void deleteById(PK id);
	
	void delete(T persistentObject);
	
	T update(T object);
	
	T findById(PK id);
	
	List<T> findAll();
}
