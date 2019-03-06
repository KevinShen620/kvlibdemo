/*
 * 2016年7月18日 
 */
package kevsn.libdemo.curator;

import org.apache.curator.framework.CuratorFramework;

/**
 * @author Kevin
 *
 */
public class Leader1 {

	public static void main(String[] args) {
		CuratorFramework client1 = ZkClient.newClient("192.168.1.25:2181");
		LeaderSelect s = new LeaderSelect();
		s.setName("client2");
		s.begin(client1);
	}
}
