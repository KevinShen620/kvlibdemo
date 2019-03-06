package kevsn.libdemo.zookeeper;

import java.io.IOException;
import java.util.List;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class ZookeeperTest2 {

	private static final Logger logger = LoggerFactory.getLogger(ZookeeperTest2.class);

	public static void main(String[] args)
			throws IOException, KeeperException, InterruptedException {
		NodeWatcher watcher25 = new NodeWatcher("25", "192.168.1.25:2181");
		NodeWatcher watcher26 = new NodeWatcher("26", "192.168.1.26:2181");
		NodeWatcher watcher27 = new NodeWatcher("27", "192.168.1.27:2181");
		watcher25.startWatch();
		watcher26.startWatch();
		watcher27.startWatch();
		watch(watcher25, watcher26, watcher27);
		Thread.sleep(Integer.MAX_VALUE);
	}

	private static void watch(NodeWatcher... watchers) {
		try {
			_watch(watchers);
		} catch (KeeperException | InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	private static void _watch(NodeWatcher... watchers)
			throws KeeperException, InterruptedException {
		for (NodeWatcher watcher : watchers) {
			ZooKeeper zk = watcher.getZookeeper();
			zk.exists("/test1", watcher.newWatcher(evt -> {
				getChilren("/test1", watcher);
			}));
		}
	}

	private static void printChildren(List<String> children, String nodeName) {
		StringBuilder builder = new StringBuilder();
		for (String child : children) {
			builder.append(child).append(",");
		}
		if (builder.length() > 0) {
			builder.deleteCharAt(builder.length() - 1);
		}
		logger.info("node {},test1 children {}", nodeName, builder);
	}

	private static void getChilren(String parent, NodeWatcher watcher) {
		ZooKeeper zk = watcher.getZookeeper();
		try {
			List<String> children = zk.getChildren(parent, watcher.newWatcher(evt -> {
				try {
					List<String> chs = zk.getChildren(parent, false);
					printChildren(chs, watcher.getName());
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}));
			printChildren(children, watcher.getName());
		} catch (KeeperException | InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	public static void zookeeper(String address)
			throws IOException, KeeperException, InterruptedException {
		ZooKeeper zk = new ZooKeeper(address, 10000, ZookeeperTest2::globalWatch);
		zk.exists("/test1", true);
		zk.exists("/test2", ZookeeperTest2::existsWatch);
	}

	private static void existsWatch(WatchedEvent wevt) {
		println("exists watch event info " + wevt);
	}

	private static void globalWatch(WatchedEvent wevt) {
		EventType eventType = wevt.getType();
		if (eventType == EventType.NodeCreated) {
			println("node created " + wevt.getPath());
		}
	}

	private static void println(String info) {
		System.out.println(info);
	}
}
