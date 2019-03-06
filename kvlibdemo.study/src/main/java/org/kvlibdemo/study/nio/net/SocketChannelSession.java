/*
 * 2017年7月13日 
 */
package org.kvlibdemo.study.nio.net;

import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Kevin
 *
 */
public class SocketChannelSession implements Session {

	private SocketChannel channel;

	private String sessionId;

	private Map<String, Object> attributes=new HashMap<>();
	
	public SocketChannelSession(SocketChannel channel) {
		this.channel = channel;
	}

	@Override
	public void writeMessage() {

	}

	public SocketChannel getChannel() {
		return this.channel;
	}

	
	@Override
	public String getSessionId() {
		return this.sessionId;
	}
	
	public void setSessionId(String sessionId){
		this.sessionId=sessionId;
	}
	
	public Object getAttribute(String key){
		return this.attributes.get(key);
	}
	
	public void setAttribute(String key,Object value){
		this.attributes.put(key, value);
	}
}
