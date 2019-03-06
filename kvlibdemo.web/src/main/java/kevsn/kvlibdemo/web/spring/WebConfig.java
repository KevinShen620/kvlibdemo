/*
 * 2016年4月20日 
 */
package kevsn.kvlibdemo.web.spring;

import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 通过程序进行配置，配置也可以放在配置文件的<mvc:annotation-driven>,但只能二选一
 * 
 * @author Kevin
 *
 */
// @Configuration
// @EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		InterceptorRegistration reg = registry
				.addInterceptor(new UserInterceptor());
		reg.addPathPatterns("/**");
	}

}
