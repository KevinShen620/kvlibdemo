/*
 * 2017年5月12日 
 */
package kevsn.http;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 * @author Kevin
 *
 */
public class CommonHttp {

	public static void deleteMethod()
			throws ClientProtocolException, IOException {
		try (CloseableHttpClient client = HttpClients.createDefault()) {
			HttpDelete delete = new HttpDelete(
					"http://127.0.0.1:8081/tjsirs/web/js/zk/");
			try (CloseableHttpResponse rest = client.execute(delete)) {
				printContent(rest);
			}
		}
	}

	public static void uploadFile() throws IOException {
		// String url =
		// "http://127.0.0.1:8080/tjsirs/zkau/web/d491a438/js/zk.wpd";
		String url = "http://127.0.0.1:8080/tjsirs/pages/login";
		// String url = "http://www.baidu.com";
		String file = "/Users/kevin/test.xml";
		// String file = "/Users/kevin/st-20170519.sql";
		// file = "/Users/kevin/st.tar.gz";
		try (CloseableHttpClient client = HttpClients.createDefault()) {
			HttpPost post = new HttpPost(url);
			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			FileBody body = new FileBody(new File(file));
			builder.addPart("bin", body);
			HttpEntity entity = builder.build();
			post.setEntity(entity);
			try (CloseableHttpResponse rest = client.execute(post)) {
				printContent(rest);
			}
		}
	}

	private static void putMethod() throws IOException {
		try (CloseableHttpClient client = HttpClients.createDefault()) {
			HttpPut put = new HttpPut("http://127.0.0.1:8080/tjsirs/web/js");
			StringEntity entity = new StringEntity("{count=1}",
					StandardCharsets.UTF_8);
			put.setEntity(entity);
			try (CloseableHttpResponse rest = client.execute(put)) {
				printContent(rest);
			}
		}
	}

	public static void postMethod() throws IOException {
		try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
			HttpGet post = new HttpGet("http://127.0.0.1:8081/tjsirs/");
			BasicHttpEntity entity = new BasicHttpEntity();
			String c = "u:\'cLzS31\'}]']);";
			byte[] bytes = c.getBytes();
			ByteArrayInputStream stream = new ByteArrayInputStream(bytes);
			entity.setContent(stream);
			entity.setContentEncoding("utf8");
			entity.setContentLength(bytes.length);
			try (CloseableHttpResponse res = httpclient.execute(post)) {
				printContent(res);
			}
		}
	}

	public static void getMethod() throws ClientProtocolException, IOException {
		try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
			HttpGet get = new HttpGet("http://127.0.0.1:8081/tjsirs/");
			try (CloseableHttpResponse res = httpclient.execute(get)) {
				printContent(res);
			}
		}
	}

	private static void printContent(CloseableHttpResponse res)
			throws UnsupportedOperationException, IOException {
		System.out.println(res.getStatusLine());
		Header[] headers = res.getAllHeaders();
		for (Header header : headers) {
			System.out.println(header.getName() + ":" + header.getValue());
		}
		HttpEntity entity = res.getEntity();
		Header encoding = entity.getContentEncoding();
		if (encoding == null) {
			System.out.println("null encoding");
		} else {
			System.out.println(encoding.getValue());
		}
		long length = entity.getContentLength();
		System.out.println("length:" + length);
		Header contentType = entity.getContentType();
		if (contentType != null) {
			System.out.println("contentType:" + contentType.getValue());
		}
		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(entity.getContent()))) {
			String line;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}
		}
	}

	public static void main(String[] args)
			throws ClientProtocolException, IOException {
		// getMethod();
		// deleteMethod();
		uploadFile();

		// putMethod();
		// deleteMethod();
		// postMethod();
	}
}
