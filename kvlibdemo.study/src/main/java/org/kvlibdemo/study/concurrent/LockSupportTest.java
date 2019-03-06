/*
 * 2017年4月27日 
 */
package org.kvlibdemo.study.concurrent;

import java.util.concurrent.locks.LockSupport;

/**
 * @author Kevin
 *
 */
public class LockSupportTest {

	public static void park2() {
		Thread c = Thread.currentThread();
		LockSupport.unpark(c);
		System.out.println("before park");
		LockSupport.park();
		System.out.println("after park");
	}

	public static void park1() {
		System.out.println("before park");
		final Thread c = Thread.currentThread();
		new Thread(() -> {
			try {
				Thread.sleep(5 * 1000);
			} catch (final Exception e) {
			}
			System.out.println("before unpark");
			LockSupport.unpark(c);
			System.out.println("after unpark");
		}).start();
		LockSupport.park();
		System.out.println("after park");

	}

	public static void main(String[] args) {
		park2();
	}
}
