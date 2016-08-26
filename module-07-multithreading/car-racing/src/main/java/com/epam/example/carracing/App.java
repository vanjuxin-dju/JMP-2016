package com.epam.example.carracing;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

public class App {
	static Logger LOG = Logger.getLogger(App.class);
	public static void main(String[] args) {
		Runnable bmw = new Car("BMW", 100);
		Runnable bentley = new Car("Bentley", 120);
		Runnable mersedes = new Car("Mersedes", 110);
		
		ExecutorService threadService = Executors.newFixedThreadPool(3);
		Future<?> th1 = threadService.submit(bmw);
		Future<?> th2 = threadService.submit(bentley);
		Future<?> th3 = threadService.submit(mersedes);
		
		ScheduledExecutorService disqualifier = Executors.newSingleThreadScheduledExecutor();
		disqualifier.schedule(() -> {
			try {
				int threadNumber = (int)Math.floor(Math.random() * 3 + 1);
				switch (threadNumber) {
				case 1:
					th1.cancel(true);
					LOG.info("BMW disqualified");
					break;
				case 2:
					th2.cancel(true);
					LOG.info("Bentley disqualified");
					break;
				case 3:
					th3.cancel(true);
					LOG.info("Mersedes disqualified");
					break;
				default:
					break;
				}
			} catch (Exception e) {
			}
		}, 5, TimeUnit.SECONDS);
				
		while (true) {
			if (th1.isDone() && !th1.isCancelled()) {
				LOG.info("BMW is winner");
				break;
			} else if (th2.isDone() && !th2.isCancelled()) {
				LOG.info("Bentley is winner");
				break;
			} else if (th3.isDone() && !th3.isCancelled()) {
				LOG.info("Mersedes is winner");
				break;
			}
		}
		
	}

}
