/*
 * 2016年7月4日 
 */
package kevsn.libdemo.spark;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.spark.launcher.SparkLauncher;

/**
 * @author Kevin
 *
 */
public class RunSpark {

	public static void main(String[] args)
			throws IOException, InterruptedException {
		System.out.println("Run spark");
		String jar = "/Users/kevin/develop/workspacemars/kvlibdemo.cluster.spark/target/kvlibdemo.cluster.spark-0.0.1-SNAPSHOT.jar";
		SparkLauncher l = new SparkLauncher();
		l.setAppName("WordCount");
		l.setSparkHome("/usr/local/spark");
		l.setAppResource(jar);
		// l.setMainClass(SparkDemo2.class.getName());
		// l.setDeployMode("yarn:client");
		l.setDeployMode("cluster");
		l.setMaster("yarn");
		System.out.println("process runned");
		Process p = l.launch();
		InputStream stdInput = p.getInputStream();
		InputStream errInput = p.getErrorStream();
		System.out.println("wait for process finish");
		int code = p.waitFor();
		print(stdInput);
		print(errInput);
		System.out.println(code);
	}

	private static void print(InputStream input) throws IOException {
		try (BufferedReader buffer = new BufferedReader(
				new InputStreamReader(input))) {
			String line = buffer.readLine();
			System.out.println(line);
		}
	}
}
