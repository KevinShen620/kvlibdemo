/*
 * 2016年8月4日 
 */
package kevsn.libdemo.curator;

import org.apache.curator.framework.CuratorFramework;

/**
 * @author Kevin
 *
 */
public class Test5 {
	public static void main(String[] args) throws Exception {
		// CuratorFramework zkclient =
		// ZkClient.newNamespaceClient("node0:2181");
		CuratorFramework zkclient = ZkClient.newClient("node0:2181/curator");
		zkclient.create().forPath("/abc");
	}
}
