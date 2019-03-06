/*
 * 2016年6月30日 
 */
package kevsn.libdemo.spark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

/**
 * @author Kevin
 *
 */
public class SparkDemo {
	public static void main(String[] args) {
		String logFile = "/usr/local/spark/README.md";
		SparkConf conf = new SparkConf().setAppName("Simple Application");
		try (JavaSparkContext sc = new JavaSparkContext(conf)) {
			JavaRDD<String> logData = sc.textFile(logFile).cache();
			long numAs = logData.filter(s -> s.contains("a")).count();
			long numBs = logData.filter(s -> s.contains("b")).count();
			System.out.println(
					"Lines with a: " + numAs + ", lines with b: " + numBs);
		}
	}
}
