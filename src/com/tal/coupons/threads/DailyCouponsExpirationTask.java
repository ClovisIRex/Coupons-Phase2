package com.tal.coupons.threads;

/**
 * This runnable task is supposed to run once every 24 hours and removes old coupons
 * from the db using the janitor thread.
 * @author Sol Invictus
 *
 */
public class DailyCouponsExpirationTask implements Runnable{	


	public void run() {

		while (true) {
			try {
				Thread workThread = new Thread(new WorkThread());
				workThread.start();

				Thread.sleep(1000 * 60 * 60 * 24);


			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
}
