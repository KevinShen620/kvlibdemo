/*
 * 2016年4月20日 
 */
package kevsn.kvlibdemo.web.spring;

import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * @author Kevin
 *
 */
public class UserInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private ServletContext servletContext;

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		HttpSession session = request.getSession(true);
		String userName = (String) session.getAttribute("username");
		if (StringUtils.isEmpty(userName)) {
			session.setAttribute("username", UUID.randomUUID().toString());
		}
		String path = servletContext.getContextPath();
		System.out.println(path);
		System.out.println(request.getRequestURI());
		System.out.println(request.getRequestURL());
		System.out.println(request.getRequestedSessionId());
		System.out.println(request.getContentLength());
		return true;
	}

}
