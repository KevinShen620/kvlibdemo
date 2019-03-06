/*
 * 2017年7月31日 
 */
package kvlibdemo.netty.buff;

import java.util.Scanner;

import io.netty.channel.Channel;

/**
 * @author Kevin
 *
 */
public class ClientMain {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		BClient client = new BClient();
		client.start();
		System.out.println("成功连接到服务器");
		Channel channel = client.getChannel();
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			scanner.close();
		}));
		while (true) {
			System.out.println("请输入内容：");
			String line = scanner.nextLine();
			channel.writeAndFlush(line);
		}
	}
}
