/*
 * 2016年7月12日 
 */
package kevsn.libdemo.curator;

import java.util.List;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.zookeeper.WatchedEvent;

/**
 * @author Kevin
 *
 */
public class Test3 {

	public static void main(String[] args) throws Exception {
		CuratorFramework zkClient = ZkClient.newClient("localhost:2181");
		List<String> children = zkClient.getChildren()
				.usingWatcher(new CuratorWatcher() {

					@Override
					public void process(WatchedEvent event) throws Exception {
						System.out.println(event.getPath());
					}
				}).forPath("/test");
		children.forEach(System.out::println);
		Thread.sleep(30 * 1000);

		zkClient.close();
	}
}
