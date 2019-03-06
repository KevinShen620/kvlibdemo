/*
 * 2016年6月25日 
 */
package kevsn.libdemo.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.barriers.DistributedBarrier;
import org.apache.curator.framework.recipes.barriers.DistributedDoubleBarrier;

/**
 * @author Kevin
 *
 */
public class Barrier {

	public static void main(String[] args) throws InterruptedException {
		CuratorFramework zkClient1 = ZkClient.newNamespaceClient("192.168.1.25:2181");
		CuratorFramework zkClient2 = ZkClient.newNamespaceClient("192.168.1.25:2181");
		new Thread(() -> {
			Barrier b = new Barrier();
			b.handleBarrier(zkClient2);
		}).start();
		Thread.sleep(2 * 1000);
		new Thread(() -> {
			Barrier b = new Barrier();
			b.enter(zkClient1);
		}).start();
		Thread.sleep(10 * 1000);
		System.out.println("finish");
	}

	private void handleBarrier(CuratorFramework zkClient) {
		DistributedBarrier barrier = new DistributedBarrier(zkClient,
				"/barrier");
		try {
			System.out.println("set barrier");
			barrier.setBarrier();
			System.out.println("after set barrier,sleep 5 seconds");
			Thread.sleep(5 * 1000);
			System.out.println("remove barrier");
			barrier.removeBarrier();
			System.out.println("after remove barrier");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void enter(CuratorFramework zkClient) {
		DistributedBarrier barrier = new DistributedBarrier(zkClient,
				"/barrier");
		try {
			System.out.println("wait on barrier");
			barrier.waitOnBarrier();
			System.out.println("after wait on barrier");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
