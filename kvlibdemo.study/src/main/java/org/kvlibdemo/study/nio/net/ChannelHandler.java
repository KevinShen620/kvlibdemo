/*
 * 2017年7月13日 
 */
package org.kvlibdemo.study.nio.net;

import java.nio.ByteBuffer;

/**
 * @author Kevin
 *
 */
public interface ChannelHandler {

	void messageReceived(ByteBuffer buffer);
}
