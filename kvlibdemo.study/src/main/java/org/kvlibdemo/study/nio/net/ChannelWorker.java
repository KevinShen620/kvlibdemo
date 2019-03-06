/*
 * 2017年7月21日 
 */
package org.kvlibdemo.study.nio.net;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author Kevin
 *
 */
public class ChannelWorker implements Runnable {

	private volatile boolean running;

	private ExecutorService pool;

	private volatile FutureTask<Void> currentTask;

	private BlockingQueue<Runnable> tasks = new LinkedBlockingQueue<>();

	public ChannelWorker(ExecutorService pool) {
		this.pool = pool;
	}

	@Override
	public void run() {

	}

}
