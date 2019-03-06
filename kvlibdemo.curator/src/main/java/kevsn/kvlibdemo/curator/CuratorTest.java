/*
 * 2016年4月9日 
 */
package kevsn.kvlibdemo.curator;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.CuratorTempFramework;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException.NodeExistsException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.data.Stat;

import com.sun.xml.internal.ws.api.model.CheckedException;

/**
 * @author Kevin
 *
 */
public class CuratorTest {

	private static void testGetChildren() throws Exception {
		CuratorFramework client = getClient();
		client.getChildren().forPath("/solrCloud").forEach(System.out::println);
		client.close();
	}

	private static void testGetChildren2() throws Exception {
		CuratorFramework client = getClient();
		client.getChildren().usingWatcher(new CuratorWatcher() {

			@Override
			public void process(WatchedEvent event) throws Exception {
				System.out.println(event);
				String path = event.getPath();
				System.out.println("found path " + path);
				List<String> lst = client.getChildren().forPath(path);
				for (String nodePath : lst) {
					String npath = path + "/" + nodePath;
					String str = new String(client.getData().forPath(npath));
					System.out.println(path + "/" + nodePath + ":" + str);
				}
			}
		}).forPath("/curator");
		Thread.sleep(300 * 1000);
	}

	private static void checkExists(CuratorFramework client) throws Exception {
		Stat rst = client.checkExists().usingWatcher(new CuratorWatcher() {

			@Override
			public void process(WatchedEvent event) throws Exception {
				EventType type = event.getType();
				switch (type) {
				case NodeCreated:
					System.out.println(event.getPath() + " created");
					break;
				case NodeDeleted:
					System.out.println(event.getPath() + " deleted");
					break;
				default:
					break;
				}
			}
		}).forPath("/curator/nodex");
		if (rst == null) {
			System.out.println("/curator/nodex is not exists");
		} else {
			System.out.println("found node /curator/nodex");
		}
		Thread.sleep(300 * 1000);
	}

	private static void testCreateWithLock() {
		try {
			Thread t1 = new Thread(CuratorTest::_testCreateWithLock, "T1");
			Thread t2 = new Thread(CuratorTest::_testCreateWithLock, "T2");
			t1.start();
			t2.start();
			_testCreateWithLock();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void _testCreateWithLock() {
		try {
			__testCreateWithLock();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void __testCreateWithLock() throws Exception {
		String newNode = "nodey";
		String newNodePath = "/curator/" + newNode;
		String tname = Thread.currentThread().getName();
		CuratorFramework client = getClient();
		InterProcessMutex lock = new InterProcessMutex(client,
				newNodePath + "_lock");
		try {
			System.out.println(tname + " before lock");
			System.out.println(tname + " after lock");
			Stat nodeyStat = client.checkExists().forPath(newNodePath);
			if (nodeyStat == null) {
				System.out.println(tname + " node doesn't exists,create one");
				client.create().forPath(newNodePath);
				System.out.println(tname + " node created");
			} else {
				System.out.println(tname + " node has been created");
			}
		} finally {
			lock.release();
			System.out.println(tname + " exists lock");
		}
	}

	public static void main(String[] args) throws Exception {

		testCreate();
		// testGetChildren();
		// testGetChildren2();
		// checkExists(getClient());

	}

	private static void testCreate() throws Exception, InterruptedException {
		CuratorFramework client = getClient();
		try {
			client.create().forPath("/curator2");
		} catch (NodeExistsException e) {
			System.out.println("/curator has been created");
		}
		client.close();
	}

	private static CuratorFramework getClient() {
		String zkHost = "localhost:2181";
		RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
		CuratorFramework client = CuratorFrameworkFactory.newClient(zkHost,
				retryPolicy);
		client.start();
		return client;
	}

	private static String getJson() throws IOException {
		InputStream stream = CuratorTest.class.getClassLoader()
				.getResourceAsStream("application.json");
		InputStreamReader reader = new InputStreamReader(stream,
				StandardCharsets.UTF_8);
		StringBuilder builder = new StringBuilder(2000);
		char[] chars = new char[1000];
		int readed;
		while ((readed = reader.read(chars)) != -1) {
			builder.append(chars, 0, readed);
		}
		chars = null;
		return builder.toString();

	}
}
