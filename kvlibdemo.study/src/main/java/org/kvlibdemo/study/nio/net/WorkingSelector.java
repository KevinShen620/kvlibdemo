/*
 * 2017年7月14日 
 */
package org.kvlibdemo.study.nio.net;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Kevin
 *
 */
public class WorkingSelector {

	private Selector selector;

	private volatile boolean stopped;

	public void addChannel(SocketChannel channel) {

	}

	private void _start() throws IOException {
		selector = Selector.open();
		while (stopped) {
			int num = selector.select();
			if (num == 0) {
				continue;
			}
			Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
			while (keys.hasNext()) {
				SelectionKey key = keys.next();
				key.channel();
				keys.remove();
			}
		}
	}
}
