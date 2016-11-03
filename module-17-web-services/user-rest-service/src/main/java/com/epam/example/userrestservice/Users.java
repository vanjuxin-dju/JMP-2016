package com.epam.example.userrestservice;

import java.awt.Image;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Users {
	private static CopyOnWriteArrayList<User> users;
	private static AtomicInteger id;
	
	static {
		users = new CopyOnWriteArrayList<>();
		id = new AtomicInteger(1);
		add("Ivan", "Ivanov", "ivan_ivanov", "ivan_ivanov@server.com");
		add("Pyotr", "Petrov", "pyotr_petrov", "pyotr_petrov@server.com");
		add("Sidor", "Sidorov", "sidor_sidorov", "sidor_sidorov@server.com");
	}
	
	// for GET /
	public static CopyOnWriteArrayList<User> getAll() {
		return users;
	}
	
	// for GET /:id
	public static User get(int id) {
		User user = null;
		for (User u: users) {
			if (u.getId() == id) {
				user = u;
				break;
			}
		}
		return user;
	}
	
	// for PUT /:id
	public static void put(int id, String name, String surname, String login, String email) {
		User user = null;
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getId() == id) {
				user = users.get(i);
				user.setName(name);
				user.setSurname(surname);
				user.setLogin(login);
				user.setEmail(email);
				users.set(i, user);
				return;
			}
		}
		throw new RuntimeException("No element with this id!");
	}
	
	public static void addAvatar(int id, Image avatar) {
		User user = null;
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getId() == id) {
				user = users.get(i);
				user.setAvatar(avatar);
				users.set(i, user);
				return;
			}
		}
		throw new RuntimeException("No element with this id!");
	}
	
	// for DELETE /:id
	public static void delete(int id) {
		for (User u: users) {
			if (u.getId() == id) {
				users.remove(u);
				return;
			}
		}
		throw new RuntimeException("No element with this id!");
	}
	
	// for POST /
	public static int add(String name, String surname, String login, String email) {
		if (!isLoginUnique(login)) {
			throw new RuntimeException("Login is not unique!");
		}
		User user = new User();
		user.setId(id.getAndIncrement());
		user.setName(name);
		user.setSurname(surname);
		user.setLogin(login);
		user.setEmail(email);
		users.add(user);
		return user.getId();
	}
	
	private static boolean isLoginUnique(String login) {
		for (User u: users) {
			if (u.getLogin().equals(login))
				return false;
		}
		return true;
	}
}
