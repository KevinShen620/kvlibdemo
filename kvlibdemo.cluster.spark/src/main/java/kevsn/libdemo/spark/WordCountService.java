/*
 * 2016年7月1日 
 */
package kevsn.libdemo.spark;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

/**
 * @author Kevin
 *
 */
public interface WordCountService extends Remote {

	Map<String, Integer> count(String file) throws RemoteException;
}
