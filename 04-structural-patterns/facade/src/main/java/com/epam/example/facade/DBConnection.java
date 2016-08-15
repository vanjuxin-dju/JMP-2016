package com.epam.example.facade;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBConnection {
	private static final String URL = "jdbc:mysql://localhost:3306/mentoring";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "";
	
	private static DBConnection instance;
	
	public static DBConnection getInstance() throws SQLException {
		if (instance == null) {
			instance = new DBConnection();
		}
		return instance;
	}
	
	private static Connection con;
	
	private DBConnection() throws SQLException {
		con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
	}
	
	public Person getPersonByName(String name) throws SQLException {
		PreparedStatement ps = con.prepareStatement("SELECT * FROM person WHERE name = ?");
		ps.setString(1, name);
		ResultSet rs = ps.executeQuery();
		if (rs != null) {
			rs.next();
			Person person = new Person();
			person.setIdPerson(rs.getInt(1));
			person.setName(rs.getString(2));
			person.setSurname(rs.getString(3));
			person.setBirthday(rs.getDate(4));
			person.setHomeTown(rs.getString(5));
			return person;
		} else {
			return null;
		}
	}
	
	public List<Person> getPeople() throws SQLException {
		List<Person> people = new ArrayList<Person>();
		Statement statement = con.createStatement();
		statement.execute("SELECT * FROM person");
		ResultSet rs = statement.getResultSet();
		if (rs != null) {
			while (rs.next()) {
				Person person = new Person();
				person.setIdPerson(rs.getInt(1));
				person.setName(rs.getString(2));
				person.setSurname(rs.getString(3));
				person.setBirthday(rs.getDate(4));
				person.setHomeTown(rs.getString(5));
				people.add(person);
			}
		}
		statement.close();
		return people;
	}
	
	public void insertPerson(Person person) throws SQLException {
		PreparedStatement ps = con.prepareStatement("INSERT INTO person(name,surname,birthday,homeTown) VALUES (?,?,?,?)");
		ps.setString(1, person.getName());
		ps.setString(2, person.getSurname());
		ps.setDate(3, person.getBirthday());
		ps.setString(4, person.getHomeTown());
		ps.execute();
		ps.close();
	}
}
