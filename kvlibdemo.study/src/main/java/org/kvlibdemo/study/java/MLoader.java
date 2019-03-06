/*
 * 2017年4月9日 
 */
package org.kvlibdemo.study.java;

import java.util.regex.Matcher;

/**
 * @author Kevin
 *
 */
public class MLoader extends ClassLoader {

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		getParent();
		return super.findClass(name);
	}

	public static void main(String[] args) {
		ClassLoader loader = MLoader.class.getClassLoader();
		while (loader != null) {
			System.out.println(loader.toString());
			loader = loader.getParent();
		}
	}

}
