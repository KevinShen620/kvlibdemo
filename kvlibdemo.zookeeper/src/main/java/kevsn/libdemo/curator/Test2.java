/*
 * 2016年6月25日 
 */
package kevsn.libdemo.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListener;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;
import org.apache.curator.framework.state.ConnectionState;

/**
 * @author Kevin
 *
 */
public class Test2 {

	public static void main(String[] args) {
		CuratorFramework client = ZkClient.newClient();
		LeaderSelector select = new LeaderSelector(client, "/leader",
				new LeaderSelectorListenerAdapter() {

					@Override
					public void takeLeadership(CuratorFramework client)
							throws Exception {

					}
				});
		select.start();
	}
	
	
}
