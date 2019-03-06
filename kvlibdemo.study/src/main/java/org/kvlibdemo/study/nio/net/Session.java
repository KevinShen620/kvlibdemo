/*
 * 2017年7月13日 
 */
package org.kvlibdemo.study.nio.net;

/**
 * @author Kevin
 *
 */
public interface Session {

	void writeMessage();
	
	String getSessionId();
}
