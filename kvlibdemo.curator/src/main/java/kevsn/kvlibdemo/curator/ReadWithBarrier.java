/*
 * 2016年4月12日 
 */
package kevsn.kvlibdemo.curator;

import java.nio.charset.StandardCharsets;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.barriers.DistributedBarrier;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * @author Kevin
 *
 */
public class ReadWithBarrier {

	public static void main(String[] args) throws Exception {
		Thread t1 = newThread();
		t1.start();
		// Thread t2 = newThread();
		// t2.start();
	}

	private static Thread newThread() {
		return new Thread(() -> {
			try {
				read();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	private static void read() throws Exception {
		RetryPolicy policy = new ExponentialBackoffRetry(3000, 3);
		CuratorFramework zkClient = CuratorFrameworkFactory
				.newClient("localhost:2181", policy);
		zkClient.start();
		DistributedBarrier barrier = new DistributedBarrier(zkClient,
				"/barrier");
		System.out.println("wait on barrier");
		barrier.setBarrier();
		barrier.waitOnBarrier();
		System.out.println("barrier removed");
		byte[] data = zkClient.getData().forPath("/batest");
		String str = new String(data, StandardCharsets.UTF_8);
		System.out.println(str);
	}
}
