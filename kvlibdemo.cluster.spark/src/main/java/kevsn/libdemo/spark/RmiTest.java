/*
 * 2016年7月2日 
 */
package kevsn.libdemo.spark;

import java.rmi.RemoteException;
import java.util.Map;

/**
 * @author Kevin
 *
 */
public class RmiTest {

	public static void main(String[] args) throws RemoteException {
		WordCountService service = WordCountServices.getService();
		Map<String, Integer> counts = service
				.count("file:///usr/local/spark/README.md");
		counts.forEach((word, count) -> System.out.println(word + ":" + count));
	}
}
