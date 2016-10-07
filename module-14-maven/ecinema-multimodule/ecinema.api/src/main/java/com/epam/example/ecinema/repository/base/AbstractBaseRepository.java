package com.epam.example.ecinema.repository.base;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public abstract class AbstractBaseRepository<T> implements BaseRepository<T> {
	@Autowired
	private SessionFactory sessionFactory;
	
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@SuppressWarnings("unchecked")
	protected Class<T> getGenericEntityClass() {
		ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
		return (Class<T>) parameterizedType.getActualTypeArguments()[0];
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public List<T> findAll() {
		Criteria cr = getSession().createCriteria(this.getGenericEntityClass());
		return (List<T>) cr.list();
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public List<T> findByCriteria(Criterion criterion) {
		Criteria crit = getSession().createCriteria(this.getGenericEntityClass());
		crit.add(criterion);
		return (List<T>) crit.list();
	}

	@Override
	public T findById(Long id) {
		return (T) getSession().get(this.getGenericEntityClass(), id);
	}

	@Override
	public Long create(T object) {
		Session session = getSession();
		Long id = null;
		id = (Long)session.save(object);
		return id;
	}

	@Override
	public T update(T object) {
		Session session = getSession();
		session.update(object);
		return object;
	}

	@Override
	public void delete(T persistentObject) {
		Session session = getSession();
		session.delete(persistentObject);
	}

	@Override
	public void deleteById(Long id) {
		Session session = getSession();
		T object = session.get(this.getGenericEntityClass(), id);
		session.delete(object);
	}
	

}
