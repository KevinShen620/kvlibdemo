/*
 * 2017年7月13日 
 */
package org.kvlibdemo.study.nio.net;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Kevin
 *
 */
public class SocketServer {

	private ServerSocketChannel channel;

	private SocketAddress bindingAddress;

	private Selector selector;

	private Selector channelSelector;

	private volatile boolean stopped;

	private ExecutorService pool = Executors.newCachedThreadPool();

	public SocketAddress getBindingAddress() {
		return bindingAddress;
	}

	public void setBindingAddress(SocketAddress bindingAddress) {
		this.bindingAddress = bindingAddress;
	}

	private void _start() throws IOException {
		channel = ServerSocketChannel.open();
		channel.configureBlocking(false);
		channel.bind(this.bindingAddress);
		selector = Selector.open();
		channel.register(selector, SelectionKey.OP_ACCEPT);
		while (!stopped) {
			int n = selector.select();
			if (n == 0) {
				continue;
			}
			Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
			while (keys.hasNext()) {
				SelectionKey key = keys.next();
				if (key.isAcceptable()) {
					ServerSocketChannel serverChannel = (ServerSocketChannel) key
							.channel();
					SocketChannel clientChannel = serverChannel.accept();
					regClientChannel(clientChannel, selector);
				} else if (key.isReadable()) {
					SocketChannelSession session = (SocketChannelSession) key
							.attachment();
					SocketChannel clientChannel = session.getChannel();
				}
				keys.remove();
			}
		}
	}

	private void handleChannel(SocketChannelSession session) {
		SocketChannel ch = session.getChannel();
		session.getAttribute("handler");
	}

	private void regClientChannel(SocketChannel channel, Selector selector)
			throws IOException {
		channel.configureBlocking(false);
		SocketChannelSession session = new SocketChannelSession(channel);
		channel.register(selector, SelectionKey.OP_READ, session);
	}

}
