package com.epam.example.deadlockexample;

public class App {
	
	
	public static void main(String[] args) {
		MyThread thread1 = new MyThread("Thread1");
		MyThread thread2 = new MyThread("Thread2");
		
		thread1.setAnotherThread(thread2);
		thread2.setAnotherThread(thread1);
		
		thread1.start();
		thread2.start();
	}
}
