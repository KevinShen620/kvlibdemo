package kevsn.libdemo.zookeeper;

import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

public class ZookeeperTest {

	public static void main(String[] args)
			throws IOException, InterruptedException, KeeperException {
		watch("localhost:2181");
	}

	private static void watch(String address)
			throws IOException, KeeperException, InterruptedException {
		ZooKeeper zk = new ZooKeeper(address, 10000, ZookeeperTest::watchEvent);
		zk.create("/test1", "test1".getBytes(), Ids.OPEN_ACL_UNSAFE,
				CreateMode.PERSISTENT);
		zk.create("/test1/c1", "c1".getBytes(), Ids.OPEN_ACL_UNSAFE,
				CreateMode.EPHEMERAL);
		zk.create("/test1/c2", "c2".getBytes(), Ids.OPEN_ACL_UNSAFE,
				CreateMode.EPHEMERAL);
		zk.create("/test1/c3", "c2".getBytes(), Ids.OPEN_ACL_UNSAFE,
				CreateMode.EPHEMERAL);

		Thread.sleep(10 * 1000);
	}

	private static void println(String info) {
		System.out.println(Thread.currentThread().getName() + ":" + info);
	}

	private static void watchEvent(WatchedEvent wevt) {
		println("event trigged,event info:" + wevt);
	}

}
