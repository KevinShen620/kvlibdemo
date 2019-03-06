/*
 * 2016年8月12日 
 */
package kevsn.libdemo.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.zookeeper.WatchedEvent;

/**
 * @author Kevin
 *
 */
public class DataChangeWatch {

	public static void main(String[] args) throws Exception {
		Object lock = new Object();
		CuratorFramework zkclient1 = ZkClient.newClient("localhost:2181");
		// CuratorFramework zkclient2 = ZkClient.newClient("localhost:2181");
		byte[] data = zkclient1.getData().usingWatcher(new CuratorWatcher() {

			@Override
			public void process(WatchedEvent event) throws Exception {
				System.out.println(event.getPath());
				System.out.println(event.getType());
				synchronized (lock) {
					lock.notifyAll();
				}
			}
		}).forPath("/abc");
		System.out.println(new String(data));
		synchronized (lock) {
			lock.wait();
		}
	}
}
