/*
 * 2016年3月5日 
 */
package kevsn.libdemo.zookeeper;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

/**
 * @author Kevin
 *
 */
public class ZookeeperTest3 {

	public static void main(String[] args)
			throws KeeperException, InterruptedException {
		NodeWatcher wathcer25 = new NodeWatcher("node25", "192.168.1.25");
		NodeWatcher watcher26 = new NodeWatcher("node26", "192.168.1.26");
		wathcer25.startWatch();
		Watcher watcher = new ReadDataWatcher(watcher26);
		watcher26.startWatch(watcher);
		ZooKeeper zk26 = watcher26.getZookeeper();
		zk26.exists("/CCC", evt -> {
			printEvt(evt, watcher26);
		});
		ZooKeeper zk25 = wathcer25.getZookeeper();
		zk25.create("/CCC", "Hello".getBytes(), Ids.OPEN_ACL_UNSAFE,
				CreateMode.PERSISTENT);
		Thread.sleep(Integer.MAX_VALUE);
		zk25.close();
		zk26.close();
	}

	private static class ReadDataWatcher implements Watcher {

		private NodeWatcher watcher;

		/**
		 * 
		 */
		public ReadDataWatcher(NodeWatcher watcher) {
			this.watcher = watcher;
		}

		@Override
		public void process(WatchedEvent event) {
			printEvt(event, watcher);
		}
	}

	private static void printEvt(WatchedEvent event, NodeWatcher watcher) {
		System.out.println(
				"event type in " + watcher.getName() + ":" + event.getType());
		System.out.println("event state " + event.getState());
		String path = event.getPath();
		if (StringUtils.isNotEmpty(path)) {
			try {
				byte[] data = watcher.getZookeeper().getData(path, false, null);
				System.out.println("get Data:" + new String(data));
			} catch (KeeperException e) {
				throw new RuntimeException(e);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}
}