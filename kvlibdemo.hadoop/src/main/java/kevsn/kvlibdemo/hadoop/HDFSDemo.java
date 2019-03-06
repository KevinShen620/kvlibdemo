/*
 * 2016年4月15日 
 */
package kevsn.kvlibdemo.hadoop;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

/**
 * @author Kevin
 *
 */
public class HDFSDemo {

	public static void main(String[] args) throws IOException {
		Configuration conf = new Configuration();
		conf.set("fs.defaultFS", "hdfs://localhost:9000");
		FileSystem fs = FileSystem.get(conf);
		fs.copyFromLocalFile(new Path("pom.xml"), new Path("/pom.xml"));
		fs.close();
	}
}
