package kevsn.libdemo.zookeeper;

import java.io.IOException;
import java.util.function.Consumer;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NodeWatcher {

	private static final Logger logger = LoggerFactory
			.getLogger(NodeWatcher.class);

	private String name;

	private String address;

	private ZooKeeper zk;

	private Watcher doNothingWatcher;

	public NodeWatcher(String name, String address) {
		this.name = name;
		this.address = address;
		doNothingWatcher = evt -> {
			logger.info("node {} event triggerd:{}", this.name, evt);
		};
	}

	public void startWatch() {
		try {
			zk = new ZooKeeper(address, 10 * 1000, doNothingWatcher);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void startWatch(Watcher watcher) {
		try {
			zk = new ZooKeeper(address, 10 * 1000, evt -> {
				logger.info("node {} global event listener triggerd:{}",
						this.name, evt);
				watcher.process(evt);
				logger.info("event finished");
			});
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public Watcher newWatcher(Consumer<WatchedEvent> consumer) {
		return evt -> {
			logger.info("node {} event triggerd:{}", this.name, evt);
			consumer.accept(evt);
			logger.info("event finished");
		};
	}

	public Watcher defaultWatcher() {
		return doNothingWatcher;
	}

	public ZooKeeper getZookeeper() {
		return zk;
	}

	public void stop() {
		try {
			zk.close();
		} catch (InterruptedException e) {
			throw new RuntimeException();
		}
	}

	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}

}
