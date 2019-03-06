/*
 * 2016年6月25日 
 */
package kevsn.libdemo.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.queue.DistributedQueue;
import org.apache.curator.framework.recipes.queue.QueueBuilder;
import org.apache.curator.framework.recipes.queue.QueueConsumer;
import org.apache.curator.framework.recipes.queue.QueueSerializer;
import org.apache.curator.framework.state.ConnectionState;

/**
 * @author Kevin
 *
 */
public class DQueue {

	private String name;

	public static void main(String[] args) throws InterruptedException {
		CuratorFramework zkClient1 = ZkClient.newNamespaceClient("192.168.1.25:2181");
		CuratorFramework zkClient2 = ZkClient.newNamespaceClient("192.168.1.26:2181");
		new Thread(() -> {
			DQueue dqueue = new DQueue();
			dqueue.name = "Producer";
			dqueue.produce(zkClient1);
		}).start();
		Thread.sleep(5 * 1000);
		new Thread(() -> {
			DQueue dqueue = new DQueue();
			dqueue.name = "Consumer";
			dqueue.consume(zkClient2);
		}).start();
		Thread.sleep(5 * 1000);
	}

	private void consume(CuratorFramework zkClient) {
		QueueBuilder<String> builder = QueueBuilder.builder(zkClient,
				new Consumer(), new DataHandler(), "/queue");
		DistributedQueue<String> queue = builder.buildQueue();
		try {
			queue.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void produce(CuratorFramework zkClient) {
		QueueBuilder<String> builder = QueueBuilder.builder(zkClient,
				new Consumer(), new DataHandler(), "/queue");
		DistributedQueue<String> queue = builder.buildQueue();
		try {
			queue.start();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		for (int i = 0; i < 10; i++) {
			try {
				String mess = "Mess" + i;
				System.out.println(name + " put message:" + mess);
				queue.put(mess);
				System.out.println(name + " after put message:" + mess);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	class Consumer implements QueueConsumer<String> {

		@Override
		public void stateChanged(CuratorFramework client,
				ConnectionState newState) {
			System.out.println("state changed");
		}

		@Override
		public void consumeMessage(String message) throws Exception {
			System.out.println(name + " Message received:" + message);
		}

	}

	class DataHandler implements QueueSerializer<String> {

		@Override
		public byte[] serialize(String item) {
			return item.getBytes();
		}

		@Override
		public String deserialize(byte[] bytes) {
			return new String(bytes);
		}

	}
}
