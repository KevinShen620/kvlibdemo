/*
 * 2016年4月17日 
 */
package org.kvlibdemo.study.concurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;


/**
 * @author Kevin
 *
 */
public class SyncDemo {

	public static void testLatch() throws InterruptedException {
		CountDownLatch latch = new CountDownLatch(1);
		new Thread(() -> {
			try {
				System.out.println("Sleep 5 seconds");
				Thread.sleep(5 * 1000);
			} catch (InterruptedException e) {
				System.out.println("Interrupted");
			}
			System.out.println("Count down");
			latch.countDown();
		}).start();
		System.out.println("Wait");
		latch.await();
		System.out.println("Finish");
	}

	// 信号量
	public static void testSemaphore() {
		Semaphore s = new Semaphore(5);
		Runnable r = () -> {
			try {
				String tname = Thread.currentThread().getName();
				System.out.println(tname + " Acquire");
				s.acquire();
				System.out.println(tname + " Acquired,begin sleep");
				Thread.sleep(5 * 1000);
				System.out.println(tname + " release");
				s.release();
				System.out.println(tname + " released");
			} catch (InterruptedException e) {
				System.out.println("Interrupt");
			}
		};
		for (int i = 0; i < 10; i++) {
			new Thread(r).start();
		}
	}

	public static void testBarrier() throws InterruptedException {
		CyclicBarrier barrier = new CyclicBarrier(6, new Runnable() {

			@Override
			public void run() {
				System.out.println("Cycle end");
			}
		});
		Runnable r = () -> {
			String tname = Thread.currentThread().getName();
			try {
				System.out.println(tname + " await");
				barrier.await();
				System.out.println(tname = " barrier opend");
			} catch (InterruptedException | BrokenBarrierException e) {
				throw new RuntimeException(e);
			}
		};
		for (int i = 0; i < 5; i++) {
			new Thread(r).start();
		}
		System.out.println("five thread started");
		Thread.sleep(5 * 1000);
		System.out.println("Join last one");
		new Thread(r).start();
	}

	public static void main(String[] args) throws InterruptedException {
		// testLatch();
		// testSemaphore();
		testBarrier();
	}
}
