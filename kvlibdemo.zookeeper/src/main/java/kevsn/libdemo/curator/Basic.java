/*
 * 2016年6月25日 
 */
package kevsn.libdemo.curator;

import java.util.List;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException.NoNodeException;
import org.apache.zookeeper.KeeperException.NodeExistsException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.data.Stat;

import ch.qos.logback.core.net.SyslogOutputStream;

/**
 * @author Kevin
 *
 */
public class Basic {

	public static void main(String[] args) throws Exception {
		CuratorFramework zkClient = ZkClient.newClient("192.168.1.25:2181");
		try {
			// testExists(zkClient);
			// testChildren2(zkClient);
			// testCreate(zkClient);
			testData(zkClient);
			Thread.sleep(15 * 1000);
		} finally {
			zkClient.close();
		}
	}

	private static void testData(CuratorFramework zkClient) throws Exception {
		// zkClient.create().forPath("/curator/data", "Hello".getBytes());
		try {
			byte[] data = zkClient.getData().usingWatcher(new CuratorWatcher() {

				@Override
				public void process(WatchedEvent event) throws Exception {
					System.out.println(event);
				}
			}).forPath("/curator/dataabc");
		} catch (NoNodeException e) {
			e.printStackTrace();
		}
		zkClient.setData().forPath("/curator/data", "Kevin".getBytes());
	}

	private static void testCreate(CuratorFramework zkClient) throws Exception {
		for (int i = 0; i < 10; i++) {
			zkClient.create().creatingParentsIfNeeded()
					.withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
					.forPath("/curator/ephe");
		}
	}

	private static void testChildren2(CuratorFramework zkClient)
			throws Exception {
		List<String> children = zkClient.getChildren()
				.usingWatcher(new CuratorWatcher() {

					@Override
					public void process(WatchedEvent event) throws Exception {
						KeeperState state = event.getState();
						System.out
								.println("Event :" + event.getType() + ",path:"
										+ event.getPath() + ",state:" + state);
					}
				}).forPath("/curator");
		System.out.println("children:");
		children.forEach(System.out::println);
	}

	public static void testChildren(CuratorFramework zkClient)
			throws Exception {
		List<String> children = zkClient.getChildren()
				.usingWatcher(new CuratorWatcher() {

					@Override
					public void process(WatchedEvent event) throws Exception {
						EventType type = event.getType();
						System.out.println(type);
					}
				}).forPath("/curator");
		children.forEach(System.out::println);
	}

	public static void testExists(CuratorFramework zkClient) throws Exception {
		Stat state = zkClient.checkExists().usingWatcher(new CuratorWatcher() {

			@Override
			public void process(WatchedEvent event) throws Exception {
				EventType type = event.getType();
				System.out.println(type);
			}
		}).forPath("/leader");
		System.out.println(state);
	}
}
