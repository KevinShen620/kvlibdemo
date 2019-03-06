/*
 * 2017年7月2日 
 */
package kvlibdemo.netty;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author Kevin
 *
 */
public class Test {

	public static void main(String[] args) throws IOException {
		Charset gb18030 = Charset.forName("gb18030");
		Charset gb2312 = Charset.forName("gb2312");
		char[] chars = new char[1024 * 1024];
		try (BufferedReader reader = Files.newBufferedReader(
				Paths.get("/Users/kevin/正德五十年gb18030.txt"), gb18030);
				OutputStream out = Files
						.newOutputStream(Paths.get("/Users/kevin/zdws.txt"))) {
			int read = -1;
			while ((read = reader.read(chars)) > 0) {
				String str = String.valueOf(chars, 0, read);
				byte[] gbkBytes = str.getBytes(gb2312);
				out.write(gbkBytes);
			}
		}
	}
}
