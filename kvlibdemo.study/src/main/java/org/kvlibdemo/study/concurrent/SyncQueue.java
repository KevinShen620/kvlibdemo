/*
 * 2017年7月28日 
 */
package org.kvlibdemo.study.concurrent;

import java.util.concurrent.SynchronousQueue;

/**
 * @author Kevin
 *
 */
public class SyncQueue {

	public static void main(String[] args) throws InterruptedException {
		SynchronousQueue<String> queue = new SynchronousQueue<>();
		new Thread(() -> {
			String v = null;
			try {
				Thread.sleep(5 * 1000);
				v = queue.take();
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("taked " + v);
		}).start();
		queue.put("Kevin");
		
		System.out.println("ok");
	}
}
