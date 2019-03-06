/*
 * 2016年7月1日 
 */
package kevsn.libdemo.spark;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import scala.Tuple2;

/**
 * @author Kevin
 *
 */
public class SparkWordCount {

	
	
	public static void count(String file,
			Consumer<Tuple2<String, Integer>> consumer) {
		SparkConf conf = new SparkConf().setAppName("Simple Application");
		conf.setMaster("local[4]");
		try (JavaSparkContext sc = new JavaSparkContext(conf)) {
			JavaRDD<String> rdd = sc.textFile(file);
			JavaPairRDD<String, Integer> rst = rdd
					.flatMap(line -> Arrays.asList(line.split(" ")))
					.mapToPair(str -> new Tuple2<>(str, 1))
					.reduceByKey((v1, v2) -> v1 + v2);
			List<Tuple2<String, Integer>> collected = rst.collect();
			collected.forEach(consumer);
		}
	}
}