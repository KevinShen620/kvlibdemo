/*
 * 2016年7月28日 
 */
package kevsn;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRegistration.Dynamic;
import javax.servlet.annotation.WebListener;

import org.cometd.server.CometDServlet;
import org.springframework.web.context.ContextLoaderListener;

/**
 * @author Kevin
 *
 */
@WebListener
public class BootListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent evt) {
	}

	@Override
	public void contextInitialized(ServletContextEvent evt) {
		ServletContext context = evt.getServletContext();
		Dynamic cometServlet = context.addServlet("cometd",
				CometDServlet.class);
		cometServlet.addMapping("/cometd/*");
		cometServlet.setAsyncSupported(true);

		// context.addListener(ContextLoaderListener.class);

	}

	
}
