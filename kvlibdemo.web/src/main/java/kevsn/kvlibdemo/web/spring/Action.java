/*
 * 2016年4月20日 
 */
package kevsn.kvlibdemo.web.spring;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Kevin
 *
 */
@Controller
public class Action {

	@Autowired
	private ServletContext servletContext;

	@RequestMapping({ "/", "/index", "/index.jsp" })
	public String home() {
		return "index";
	}

	@RequestMapping("/{page}")
	public String page(@PathVariable("page") String page) {
		return page;
	}

	@RequestMapping("/cname")
	@ResponseBody
	public String contextName() {
		return servletContext.getContextPath();
	}
}
