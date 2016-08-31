package com.epam.example.deadlockexample;

public class App {
	
	
	public static void main(String[] args) {
		MyObject obj1 = new MyObject("ABC");
		MyObject obj2 = new MyObject("DEF");
		MyThread thread1 = new MyThread("Thread1");
		MyThread thread2 = new MyThread("Thread2");
		
		thread1.setObject1(obj1);
		thread1.setObject2(obj2);
		
		thread2.setObject1(obj2);
		thread2.setObject2(obj1);
		
		thread1.start();
		thread2.start();
	}
}
