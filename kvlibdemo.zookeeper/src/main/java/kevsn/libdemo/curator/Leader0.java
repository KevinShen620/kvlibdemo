/*
 * 2016年7月18日 
 */
package kevsn.libdemo.curator;

import org.apache.curator.framework.CuratorFramework;

/**
 * @author Kevin
 *
 */
public class Leader0 {

	public static void main(String[] args) throws InterruptedException {
		CuratorFramework client1 = ZkClient.newClient("192.168.1.25:2181");
		LeaderSelect s = new LeaderSelect();
		s.setName("client1");
		s.begin(client1);
		System.out.println("Begin");
		// while (true) {
		// Thread.sleep(10 * 1000);
		// }
	}
}
