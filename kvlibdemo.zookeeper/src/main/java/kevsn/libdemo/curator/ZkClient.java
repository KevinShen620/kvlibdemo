/*
 * 2016年6月25日 
 */
package kevsn.libdemo.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.CuratorFrameworkFactory.Builder;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * @author Kevin
 *
 */
public class ZkClient {

	private ZkClient() {

	}

	public static CuratorFramework newClient(String zkHost) {
		RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
		CuratorFramework client = CuratorFrameworkFactory.newClient(zkHost,
				retryPolicy);
		client.start();
		return client;
	}

	private static CuratorFramework newClient(String zkHost, String namespace) {
		RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
		Builder builder = CuratorFrameworkFactory.builder();
		CuratorFramework client = builder.namespace(namespace)
				.retryPolicy(retryPolicy).connectString(zkHost).build();
		client.start();
		return client;

	}

	public static CuratorFramework newNamespaceClient(String zkHost) {
		String namespace = "curator";
		return newClient(zkHost, namespace);
	}

	public static CuratorFramework newClient() {
		String zkHost = "localhost:2181";
		return newNamespaceClient(zkHost);
	}
}
