/*
 * 2016年6月24日 
 */
package org.kvlibdemo.study.jmx;

/**
 * @author Kevin
 *
 */
public class Echo implements EchoMBean {

	@Override
	public void print(String message) {
		System.out.println(message);
	}

}
