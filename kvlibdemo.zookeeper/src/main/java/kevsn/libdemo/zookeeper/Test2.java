package kevsn.libdemo.zookeeper;

import java.io.IOException;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.ZooKeeper;

public class Test2 {

	public static void main(String[] args) throws IOException {
		ZooKeeper zk = new ZooKeeper("localhost:2181", 2000, Test2::process);
	}

	private static void process(WatchedEvent event) {
		System.out.println("已经触发了" + event.getType() + "事件！");
	}
}
