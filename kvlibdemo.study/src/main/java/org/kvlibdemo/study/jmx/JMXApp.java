/*
 * 2016年6月24日 
 */
package org.kvlibdemo.study.jmx;

import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;
import javax.management.ObjectName;

/**
 * @author Kevin
 *
 */
public class JMXApp {

	public static void main(String[] args) throws Exception {
		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		// 新建MBean ObjectName, 在MBeanServer里标识注册的MBean
		ObjectName name = new ObjectName("org.kvlibdemo.study.jmx:type=Echo");
		// 创建MBean
		Echo mbean = new Echo();

		// 在MBeanServer里注册MBean, 标识为ObjectName(com.tenpay.jmx:type=Echo)
		mbs.registerMBean(mbean, name);

		// 在MBeanServer里调用已注册的EchoMBean的print方法
		mbs.invoke(name, "print", new Object[] { "kevin" },
				new String[] { "java.lang.String" });

		Thread.sleep(Long.MAX_VALUE);
	}
}
