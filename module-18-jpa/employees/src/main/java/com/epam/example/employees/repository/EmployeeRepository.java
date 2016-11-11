package com.epam.example.employees.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.epam.example.employees.App;
import com.epam.example.employees.domain.Employee;
import com.epam.example.employees.domain.Project;
import com.epam.example.employees.domain.Unit;
import com.epam.example.employees.repository.base.GenericDao;

public class EmployeeRepository implements GenericDao<Employee, Long> {
	private EntityManager em = Persistence.createEntityManagerFactory(App.PERSISTENCE_UNIT_NAME).createEntityManager();

	@Override
	public Long create(Employee object) {
		em.persist(object);
		return object.getId();
	}

	@Override
	public void deleteById(Long id) {
		Employee e = em.find(Employee.class, id);
		if (e != null) {
			em.remove(e);
		}
	}

	@Override
	public void delete(Employee persistentObject) {
		em.remove(persistentObject);
	}

	@Override
	public Employee update(Employee object) {
		em.refresh(object);
		return object;
	}

	@Override
	public Employee findById(Long id) {
		return em.find(Employee.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Employee> findAll() {
		Query query = em.createQuery("SELECT emp FROM Employee emp");
		return (List<Employee>) query.getResultList();
	}
	
	public void assignProject(Employee employee, Project project) {
		Query query = em.createNativeQuery("INSERT INTO EmployeeProject (idEmployee, idProject) VALUES (?1, ?2)");
		query.setParameter(1, employee.getId());
		query.setParameter(2, project.getId());
		query.executeUpdate();
	}
	
	public void addToUnit(Employee employee, Unit unit) {
		Query query = em.createNativeQuery("UPDATE Employee SET idUnit=?1 WHERE idEmployee=?2");
		query.setParameter(1, unit.getId());
		query.setParameter(2, employee.getId());
		query.executeUpdate();
	}


}
