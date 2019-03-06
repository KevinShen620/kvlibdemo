/*
 * 2016年6月25日 
 */
package kevsn.libdemo.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;

/**
 * @author Kevin
 *
 */
public class LeaderSelect extends LeaderSelectorListenerAdapter {

	private String name;

	public static void main(String[] args) throws Exception {
		CuratorFramework client1 = ZkClient.newClient("192.168.1.25:2181");
		CuratorFramework client2 = ZkClient.newClient("192.168.1.26:2181");
		try {
			new Thread(() -> {
				LeaderSelect s = new LeaderSelect();
				s.name = "Client1";
				s.begin(client1);
			}).start();
			new Thread(() -> {
				LeaderSelect s = new LeaderSelect();
				s.name = "Client2";
				s.begin(client2);
			}).start();
			Thread.sleep(10 * 1000);
			client2.close();
			Thread.sleep(10 * 1000);
		} finally {
			client1.close();
			client2.close();
		}
	}

	public void begin(CuratorFramework client) {
		LeaderSelector s = new LeaderSelector(client, "/leader", this);
		s.start();
		s.autoRequeue();
		System.out.println("OK");
	}

	@Override
	public void takeLeadership(CuratorFramework client) throws Exception {
		System.out.println(name + " is Leader");
		while (true) {
			Thread.sleep(30 * 1000);
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
