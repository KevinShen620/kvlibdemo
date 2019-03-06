/*
 * 2016年6月25日 
 */
package kevsn.libdemo.curator;

import org.apache.curator.framework.CuratorFramework;

/**
 * @author Kevin
 *
 */
public class Test1 {

	public static void main(String[] args) throws Exception {
		CuratorFramework client = ZkClient.newNamespaceClient("192.168.1.25");
		String created = client.create().forPath("/test");
		System.out.println(created);
		client.close();
	}}
