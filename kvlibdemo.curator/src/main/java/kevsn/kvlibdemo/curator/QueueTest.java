/*
 * 2016年4月12日 
 */
package kevsn.kvlibdemo.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.queue.BlockingQueueConsumer;
import org.apache.curator.framework.recipes.queue.DistributedQueue;
import org.apache.curator.framework.recipes.queue.QueueBuilder;
import org.apache.curator.framework.recipes.queue.QueueConsumer;
import org.apache.curator.framework.recipes.queue.SimpleDistributedQueue;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * @author Kevin
 *
 */
public class QueueTest {

	public static void main(String[] args) throws Exception {
		RetryPolicy policy = new ExponentialBackoffRetry(3000, 3);

		CuratorFramework zkClient = CuratorFrameworkFactory
				.newClient("localhost:2181", policy);
		zkClient.start();
		SimpleDistributedQueue quque = new SimpleDistributedQueue(zkClient,
				"/queue");
		
	}
}
