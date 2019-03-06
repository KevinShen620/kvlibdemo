/*
 * 2016年6月30日 
 */
package kevsn.libdemo.spark;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Kevin
 *
 */
public class SparkDemo2 {

	private static final Logger logger = LoggerFactory
			.getLogger(SparkDemo2.class);

	public static void main(String[] args) {
		logger.info("Run Service in Spark");
		WordCountServices.startService();
	}
}
