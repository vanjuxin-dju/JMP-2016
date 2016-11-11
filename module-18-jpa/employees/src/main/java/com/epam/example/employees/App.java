package com.epam.example.employees;

import java.util.List;
import java.util.Set;

import com.epam.example.employees.domain.Employee;
import com.epam.example.employees.domain.Project;
import com.epam.example.employees.domain.Unit;
import com.epam.example.employees.repository.EmployeeRepository;
import com.epam.example.employees.repository.ProjectRepository;
import com.epam.example.employees.repository.UnitRepository;

public class App {
	public static final String PERSISTENCE_UNIT_NAME = "employees";

	public static void main(String[] args) {
		EmployeeRepository empRepository = new EmployeeRepository();
		List<Employee> employees = empRepository.findAll();
		employees.forEach((item) -> System.out.println(item.getId() + " "
				+ item.getName() + " " + item.getSurname() + " "
				+ item.getEmail() + " " + item.getStatus() + " "
				+ item.getAddress().getCountry() + " "
				+ item.getAddress().getCity() + " "
				+ item.getAddress().getStreet() + " "
				+ item.getAddress().getBuilding() + " "
				+ item.getAddress().getFlat() + " "
				+ item.getPersonalInfo().getGender() + " "
				+ item.getPersonalInfo().getBirthday()));
		
		ProjectRepository projRepository = new ProjectRepository();
		List<Project> projects = projRepository.findAll();
		projects.forEach((item) -> System.out.println(item.getId() + " "
				+ item.getCodeName() + " " + item.getProgramName()));
		
		System.out.println("Project 1 has the following employees");
		Project project = projRepository.findById(1L);
		Set<Employee> projEmployees = project.getEmployees();
		projEmployees.forEach((item) -> System.out.println(item.getId() + " " + item.getName() + " " + item.getSurname()));
		
		UnitRepository unitRepository = new UnitRepository();
		List<Unit> units = unitRepository.findAll();
		units.forEach((item) -> System.out.println(item.getId() + " " + item.getName()));
		
		System.out.println("Unit 1 has the following employees");
		Unit unit = unitRepository.findById(1L);
		Set<Employee> unitEmployees = unit.getEmployees();
		unitEmployees.forEach((item) -> System.out.println(item.getId() + " " + item.getName() + " " + item.getSurname()));
	}

}
