package com.epam.example.carracing;

import org.apache.log4j.Logger;

public class App {
	static Logger LOG = Logger.getLogger(App.class);
	public static void main(String[] args) {
		Runnable bmw = new Car("BMW", 100);
		Runnable bentley = new Car("Bentley", 120);
		Runnable mersedes = new Car("Mersedes", 110);
		
		Disqualifying t1 = new Disqualifying();
		Disqualifying t2 = new Disqualifying();
		Disqualifying t3 = new Disqualifying();
		
		Thread thread1 = new Thread(bmw);
		Thread thread2 = new Thread(bentley);
		Thread thread3 = new Thread(mersedes);
		
		thread1.start();
		thread2.start();
		thread3.start();
		
		Thread disqualifier = new Thread(()->{
			try {
				Thread.sleep(5000);
				int threadNumber = (int)Math.floor(Math.random() * 3 + 1);
				switch (threadNumber) {
				case 1:
					thread1.interrupt();
					t1.setDisqualified(true);
					LOG.info("BMW disqualified");
					break;
				case 2:
					thread2.interrupt();
					t2.setDisqualified(true);
					LOG.info("Bentley disqualified");
					break;
				case 3:
					thread3.interrupt();
					t3.setDisqualified(true);
					LOG.info("Mersedes disqualified");
					break;
				default:
					break;
				}
			} catch (Exception e) {
			}
		});
		disqualifier.start();
		
		while (true) {
			if (thread1.getState() == Thread.State.TERMINATED && !t1.isDisqualified()) {
				LOG.info("BMW is winner");
				break;
			} else if (thread2.getState() == Thread.State.TERMINATED && !t2.isDisqualified()) {
				LOG.info("Bentley is winner");
				break;
			} else if (thread3.getState() == Thread.State.TERMINATED && !t3.isDisqualified()) {
				LOG.info("Mersedes is winner");
				break;
			}
		}
		
	}
	
	private static class Disqualifying {
		private boolean disqualified;
		
		public Disqualifying() {
			this(false);
		}
		
		public Disqualifying(boolean disqualified) {
			this.disqualified = disqualified;
		}

		public boolean isDisqualified() {
			return disqualified;
		}

		public void setDisqualified(boolean disqualified) {
			this.disqualified = disqualified;
		}
	}

}
