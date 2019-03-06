/*
 * 2016年4月17日 
 */ // 
package org.kvlibdemo.study.concurrent;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * @author Kevin
 *
 */
public class FutureTaskDemo {

	public static void main(String[] args)
			throws InterruptedException, ExecutionException {
		FutureTaskDemo demo = new FutureTaskDemo();
		FutureTask<Integer> future = demo.getFuture(20);
		ExecutorService pool = Executors.newCachedThreadPool();
		Future<?> f2 = pool.submit(future);
		System.out.println(f2.get());
		System.out.println(future.get());
		pool.shutdownNow();

	}

	private FutureTask<Integer> getFuture(int end) {
		FutureTask<Integer> task = new FutureTask<>(() -> cal(end));
		new Thread(task).start();
		return task;
	}

	private int cal(int end) {
		int sum = 0;
		for (int i = 1; i <= end; i++) {
			sum += i;
			System.out.println("can to " + i);
			try {
				Thread.sleep(1 * 1000);
			} catch (InterruptedException e) {
				System.err.println("interrupt");
				e.printStackTrace();
				// return sum;
			}
		}
		System.out.println("sum=" + sum);
		return sum;
	}
}
