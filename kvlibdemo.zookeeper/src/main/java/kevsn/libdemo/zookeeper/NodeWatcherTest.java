/*
 * 2016年3月5日 
 */
package kevsn.libdemo.zookeeper;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.proto.WatcherEvent;

/**
 * @author Kevin
 *
 */
public class NodeWatcherTest {

	public static void main(String[] args)
			throws KeeperException, InterruptedException {
		NodeWatcher node25 = new NodeWatcher("25", "192.168.1.25");
		NodeWatcher node26 = new NodeWatcher("26", "192.168.1.26");
		node25.startWatch();
		node26.startWatch();
		ZooKeeper zk = node25.getZookeeper();
		new Thread(() -> watch(node26)).start();
		zk.create("/node25", "hello".getBytes(), Ids.OPEN_ACL_UNSAFE,
				CreateMode.PERSISTENT);
		Thread.sleep(Integer.MAX_VALUE);
	}

	private static void watch(NodeWatcher node) {
		node.startWatch(evt -> {
			System.out.println("event type:" + evt.getType());
			String path = evt.getPath();
			System.out.println("path:" + path);
			KeeperState state = evt.getState();
			System.out.println(state);
		});
	}
}
