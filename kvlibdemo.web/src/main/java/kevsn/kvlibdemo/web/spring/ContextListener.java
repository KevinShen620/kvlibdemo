/*
 * 2016年4月21日 	
 */
package kevsn.kvlibdemo.web.spring;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * @author Kevin
 *
 */
@WebListener
public class ContextListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("started");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("destoryed");
	}
}
