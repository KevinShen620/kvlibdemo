/*
 * 2017年4月21日 
 */
package org.kvlibdemo.study.concurrent;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;

/**
 * @author Kevin
 *
 */
public class CopyOnWriteTest {

	public static void main(String[] args) throws InterruptedException {
		CopyOnWriteArrayList<String> lst = new CopyOnWriteArrayList<>();
		for (char a = 'a'; a <= 'z'; a++) {
			lst.add("" + a);
		}
		Write write = new Write(lst);
		new Thread(() -> {
			int i = 0;
			for (String s : lst) {
				System.out.print(s + " ");
				if (i++ == 13) {
					write.notifyWrite();
				}
			}
		}).start();
		write.startWrite();
	}

	private static class Write {
		CopyOnWriteArrayList<String> lst;
		private volatile boolean canWrite;

		private Object finishLock = new Object();

		
		Write(CopyOnWriteArrayList<String> lst) {
			this.lst = lst;
		}

		public void waitFinish() {

		}

		public void notifyWrite() {
			synchronized (this) {
				this.canWrite = true;
				this.notifyAll();
			}
		}

		public void startWrite() {
			while (!canWrite) {
				synchronized (this) {
					try {
						this.wait();
					} catch (InterruptedException e) {

					}
				}
			}
			for (char c = 'A'; c <= 'Z'; c++) {
				lst.add("" + c);
			}
		}

	}
}
