/*
 * 2016年7月2日 
 */
package kevsn.libdemo.spark;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Kevin
 *
 */
public class WordCountServices {

	private static final Logger logger = LoggerFactory
			.getLogger(WordCountServices.class);

	public static WordCountService getService() {
		try {
			return (WordCountService) Naming
					.lookup("rmi://192.168.1.11:3000/word");
		} catch (MalformedURLException | RemoteException
				| NotBoundException e) {
			logger.error("error looking service ", e);
			throw new RuntimeException(e);
		}
	}

	public static void startService() {
		try {
			WordCountServiceImpl s = new WordCountServiceImpl();
			LocateRegistry.createRegistry(3000);
			Naming.rebind("rmi://192.168.1.11:3000/word", s);
		} catch (Exception e) {
			logger.error("error pub rmi service ", e);
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) {
		startService();
	}
}
