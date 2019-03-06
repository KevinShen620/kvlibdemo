/*
 * 2017年4月27日 
 */
package org.kvlibdemo.study.concurrent;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Kevin
 *
 */
public class LockTest {

	public static void main(String[] args) throws InterruptedException {
		Lock lock = new ReentrantLock();
		try {
			lock.lock();
			new Thread(() -> lockAgain(lock)).start();
			while (true) {
				Thread.sleep(1000 * 10);
			}
		} finally {
			lock.unlock();
		}
	}

	private static void lockAgain(Lock lock) {
		// dead lock
		try {
			lock.lock();
		} finally {
			lock.unlock();
		}
	}
}
