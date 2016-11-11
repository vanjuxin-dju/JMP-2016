package com.epam.example.employees.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.epam.example.employees.App;
import com.epam.example.employees.domain.Unit;
import com.epam.example.employees.repository.base.GenericDao;

public class UnitRepository implements GenericDao<Unit, Long> {
	private EntityManager em = Persistence.createEntityManagerFactory(App.PERSISTENCE_UNIT_NAME).createEntityManager();
	
	@Override
	public Long create(Unit object) {
		em.persist(object);
		return object.getId();
	}

	@Override
	public void deleteById(Long id) {
		Unit e = em.find(Unit.class, id);
		if (e != null) {
			em.remove(e);
		}
	}

	@Override
	public void delete(Unit persistentObject) {
		em.remove(persistentObject);
	}

	@Override
	public Unit update(Unit object) {
		em.refresh(object);
		return object;
	}

	@Override
	public Unit findById(Long id) {
		return em.find(Unit.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Unit> findAll() {
		Query query = em.createQuery("SELECT un FROM Unit un");
		return (List<Unit>) query.getResultList();
	}

}
