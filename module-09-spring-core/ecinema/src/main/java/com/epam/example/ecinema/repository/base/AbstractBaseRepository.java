package com.epam.example.ecinema.repository.base;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
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
	
	protected void rollbackTransaction(final Transaction tx) {
        try {
            if (tx != null) {
                tx.rollback();
            }
        } catch (Exception ex) {
            //LOGGER.error("Error during transaction roll back:", ex);
        }
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
		Transaction tx = null; 
		Long id = null;
		try {
			tx = session.beginTransaction();
			id = (Long)session.save(object);
			tx.commit();
		} catch (Exception ex) {
			rollbackTransaction(tx);
		} finally {
			
		}
		return id;
	}

	@Override
	public T update(T object) {
		Session session = getSession();
		Transaction tx = null; 
		try {
			tx = session.beginTransaction();
			session.update(object);
			tx.commit();
		} catch (Exception ex) {
			rollbackTransaction(tx);
		} finally {
			
		}
		return object;
	}

	@Override
	public void delete(T persistentObject) {
		Session session = getSession();
		Transaction tx = null; 
		try {
			tx = session.beginTransaction();
			session.delete(persistentObject);
			tx.commit();
		} catch (Exception ex) {
			rollbackTransaction(tx);
		} finally {
			
		}
	}

	@Override
	public void deleteById(Long id) {
		Session session = getSession();
		Transaction tx = null; 
		T object = session.get(this.getGenericEntityClass(), id);
		try {
			tx = session.beginTransaction();
			session.delete(object);
			tx.commit();
		} catch (Exception ex) {
			rollbackTransaction(tx);
		} finally {
			
		}
	}
	

}
