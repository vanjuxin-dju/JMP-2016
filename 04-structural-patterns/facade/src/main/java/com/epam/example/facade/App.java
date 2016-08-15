package com.epam.example.facade;

import java.sql.SQLException;
import java.util.List;

public class App {

	public static void main(String... args) throws SQLException {
		DBConnection conn = DBConnection.getInstance();
		List<Person> people = conn.getPeople();
		people.forEach((person) -> System.out.println(person));
	}

}
