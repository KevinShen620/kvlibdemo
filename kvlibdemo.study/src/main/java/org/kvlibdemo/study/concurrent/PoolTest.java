/*
 * 2017年4月24日 
 */
package org.kvlibdemo.study.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * @author Kevin
 *
 */
public class PoolTest {

	public static void testCallable()
			throws InterruptedException, ExecutionException {
		ExecutorService pool = Executors.newCachedThreadPool();
		Future<Boolean> f = pool.submit(() -> {
			try {
				Thread.sleep(5 * 1000);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			return true;
		});
		System.out.println(f.get());
	}

	public static void testFtask()
			throws InterruptedException, ExecutionException {
		FutureTask<Boolean> task = new FutureTask<>(() -> {
			Thread.sleep(5 * 1000);
			return true;
		});
		ExecutorService pool = Executors.newSingleThreadExecutor();
		Future<?> rst = pool.submit(task);
		Object r1 = rst.get();
		Boolean r2 = task.get();
		System.out.println("r1:" + r1 + ",r2:" + r2);
	}

	public static void testCompService()
			throws InterruptedException, ExecutionException {
		ExecutorService executor = Executors.newCachedThreadPool();
		CompletionService<Integer> service = new ExecutorCompletionService<>(
				executor);
		List<Future<Integer>> list = new ArrayList<>(10);
		for (int i = 0; i < 10; i++) {
			final int j = i;
			Future<Integer> future = service.submit(() -> {
				System.out.println("running task " + j);
				Thread.sleep(2 * 1000);
				return j;
			});
			list.add(future);
		}
		for (int i = 0; i < 10; i++) {
			Future<Integer> f = service.take();
			System.out.println("result:" + f.get());
		}
	}

	public static void main(String[] args)
			throws InterruptedException, ExecutionException {
		// testCallable();
		// testFtask();
		testCompService();
	}
}
