/*
 * 2016年7月29日 
 */
package kevsn;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

/**
 * @author Kevin
 *
 */
@Component
public class Bean2 {

	@PostConstruct
	public void init() {
		System.out.println("bean2 inited");
	}
}
