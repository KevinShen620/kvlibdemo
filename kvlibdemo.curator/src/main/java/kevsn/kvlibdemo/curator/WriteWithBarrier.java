/*
 * 2016年4月12日 
 */
package kevsn.kvlibdemo.curator;

import java.nio.charset.StandardCharsets;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.barriers.DistributedBarrier;
import org.apache.curator.framework.recipes.queue.DistributedQueue;
import org.apache.curator.framework.recipes.queue.SimpleDistributedQueue;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

/**
 * @author Kevin
 *
 */
public class WriteWithBarrier {

	public static void main(String[] args) throws Exception {
		write();
	}

	private static Thread newThread() {
		Thread t = new Thread(() -> {
			try {
				write();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		return t;
	}

	private static void write() throws Exception, InterruptedException {
		RetryPolicy policy = new ExponentialBackoffRetry(3000, 3);
		CuratorFramework zkClient = CuratorFrameworkFactory
				.newClient("localhost:2181", policy);
		zkClient.start();
		DistributedBarrier barrier = new DistributedBarrier(zkClient,
				"/barrier");
		barrier.setBarrier();
		byte[] data = "Kevin".getBytes(StandardCharsets.UTF_8);
		zkClient.create().withMode(CreateMode.EPHEMERAL).forPath("/batest",
				data);
		barrier.removeBarrier();
		Thread.sleep(30 * 1000);
		
	}
}
