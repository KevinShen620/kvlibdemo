/*
 * 2016年7月18日 
 */
package kevsn.libdemo.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;

/**
 * @author Kevin
 *
 */
public class Test4 {

	public static void main(String[] args) throws Exception {
		CuratorFramework zk = ZkClient.newClient("localhost:2181");
		String p = zk.create().creatingParentsIfNeeded()
				.withMode(CreateMode.PERSISTENT_SEQUENTIAL)
				.forPath("/a/");
		System.out.println(p);
	}
}
