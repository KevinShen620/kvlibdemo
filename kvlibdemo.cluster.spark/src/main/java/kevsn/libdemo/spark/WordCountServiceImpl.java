/*
 * 2016年7月1日 
 */
package kevsn.libdemo.spark;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

import org.apache.spark.SparkConf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Kevin
 *
 */
public class WordCountServiceImpl extends UnicastRemoteObject
		implements WordCountService {

	private static final Logger logger = LoggerFactory
			.getLogger(WordCountServiceImpl.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = -8401051272229848141L;

	public WordCountServiceImpl() throws RemoteException {
		super();
	}

	@Override
	public Map<String, Integer> count(String file) throws RemoteException {
		logger.info("收到文件{}", file);
		Map<String, Integer> map = new HashMap<String, Integer>();
		SparkWordCount.count(file, r -> {
			String word = r._1;
			Integer count = r._2;
			map.put(word, count);
		});
		return map;
	}
}
