package com.epam.example.employees.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.epam.example.employees.App;
import com.epam.example.employees.domain.Project;
import com.epam.example.employees.repository.base.GenericDao;

public class ProjectRepository implements GenericDao<Project, Long> {
	private EntityManager em = Persistence.createEntityManagerFactory(App.PERSISTENCE_UNIT_NAME).createEntityManager();

	@Override
	public Long create(Project object) {
		em.persist(object);
		return object.getId();
	}

	@Override
	public void deleteById(Long id) {
		Project e = em.find(Project.class, id);
		if (e != null) {
			em.remove(e);
		}
	}

	@Override
	public void delete(Project persistentObject) {
		em.remove(persistentObject);
	}

	@Override
	public Project update(Project object) {
		em.refresh(object);
		return object;
	}

	@Override
	public Project findById(Long id) {
		return em.find(Project.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Project> findAll() {
		Query query = em.createQuery("SELECT proj FROM Project proj");
		return (List<Project>) query.getResultList();
	}

}
