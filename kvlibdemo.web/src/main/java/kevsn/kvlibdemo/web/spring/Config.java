/*
 * 2016年4月20日 
 */
package kevsn.kvlibdemo.web.spring;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Kevin
 *
 */
@Component
public class Config {

	@Value("${kv.name}")
	private String name;

	@Value("${kv.birthyear}")
	private int year;

	@PostConstruct
	public void init() {
		System.out.println(name);
		System.out.println(year);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

}
