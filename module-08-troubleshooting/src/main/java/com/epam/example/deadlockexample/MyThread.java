package com.epam.example.deadlockexample;

import org.apache.log4j.Logger;

public class MyThread extends Thread {
	private static final Logger LOGGER = Logger.getLogger(MyThread.class);
	
	private MyObject object1;
	
	private MyObject object2;
	
	private String name;
	
	public MyThread(String name) {
		this.name = name;
		LOGGER.info("MyThread " + name + " created");
	}
	
	@Override
	public void run() {
		LOGGER.info("MyThread " + name + " start");
		synchronized (object1) {
			LOGGER.info("MyThread " + name + " is holding object " + object1.getObjectName());
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			synchronized (object2) {
				LOGGER.info("MyThread " + name + " is holding objects " + object1.getObjectName() + " and " + object2.getObjectName());
			}
		}
		LOGGER.info("MyThread " + name + " finished");
	}

	public void setObject1(MyObject object1) {
		this.object1 = object1;
	}

	public void setObject2(MyObject object2) {
		this.object2 = object2;
	}
	
}
