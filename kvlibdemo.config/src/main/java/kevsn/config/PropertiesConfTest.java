/*
 * 2016年3月18日 
 */
package kevsn.config;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;

import com.vamsc.util.file.ClassPathIOUtil;

/**
 * @author Kevin
 *
 */
public class PropertiesConfTest {

	public static void main(String[] args) throws ConfigurationException {
		testProperties();
	}

	private static void testProperties() throws ConfigurationException {
		Enumeration<URL> urls = readClassPathResourcesAsURL("app.properties");
		Configurations conf = new Configurations();
		while (urls.hasMoreElements()) {
			PropertiesConfiguration pro = conf.properties(urls.nextElement());
			System.out.println(pro.getString("string"));
			List<String> lst = pro.getList(String.class, "string");
			System.out.println(lst);
		}
	}

	public static Enumeration<URL> readClassPathResourcesAsURL(String path) {
		Enumeration<URL> resources = null;
		try {
			ClassLoader classLoader = ClassPathIOUtil.class.getClassLoader();
			if (classLoader != null) {
				resources = classLoader.getResources(path);
			} else {
				resources = ClassLoader.getSystemResources(path);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return resources;

	}

}
