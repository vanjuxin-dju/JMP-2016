package com.epam.example.deadlockexample;

import org.apache.log4j.Logger;

public class MyThread extends Thread {
	private static final Logger LOGGER = Logger.getLogger(MyThread.class);
	
	private Thread anotherThread;
	
	private String name;
	
	public MyThread(String name) {
		this.name = name;
		LOGGER.info("MyThread " + name + " created");
	}
	
	@Override
	public void run() {
		LOGGER.info("MyThread " + name + " start");
		try {
			sleep(1000);
		} catch (Exception e) {
		}
		try {
			LOGGER.info("MyThread " + name + " waiting MyThreadTwo finish");
			anotherThread.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info("MyThread " + name + " finished");
	}

	public void setAnotherThread(Thread anotherThread) {
		this.anotherThread = anotherThread;
	}
	
}
