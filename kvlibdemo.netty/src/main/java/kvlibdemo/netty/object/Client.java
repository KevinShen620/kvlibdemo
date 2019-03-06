/*
 * 2016年7月8日 
 */
package kvlibdemo.netty.object;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author Kevin
 *
 */
public class Client {

	private static final Logger logger = LoggerFactory.getLogger(Client.class);

	public static void main(String[] args) throws InterruptedException {
		// InternalLoggerFactory.setDefaultFactory(Slf4JLoggerFactory.INSTANCE);
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		Bootstrap b = new Bootstrap();
		b.group(workerGroup);
		b.channel(NioSocketChannel.class);

		b.handler(new ChannelInitializer<SocketChannel>() {

			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				// ch.pipeline().addLast(new LoggingHandler(LogLevel.ERROR));
				ch.pipeline().addLast(new ObjectEncoder());
				ch.pipeline().addLast(new ObjectDecoder());
				ch.pipeline().addLast(new ObjectPrintHandler(true));
			}
		});
		ChannelFuture f = b.connect("localhost", 9001);
		f.addListener(new ChannelFutureListener() {

			@Override
			public void operationComplete(ChannelFuture future)
					throws Exception {
				System.out.println("operationComplete");
				if (future.isSuccess()) {
					System.out.println("Connected");
					Channel channel = future.channel();
					for (int i = 0; i < 5; i++) {
						TestObject obj = new TestObject();
						obj.setStr(UUID.randomUUID().toString());
						obj.setI(i);
						channel.writeAndFlush(obj).addListener(f -> {
							System.out.println("Message Sent " + obj);
						});
						Thread.sleep(1000 * 2);
					}
				}
				System.out.println("Sleep 5 seconds");
				Thread.sleep(5 * 1000);
			}
		});
		System.out.println("waiting");
		Channel channel = f.await().channel();
		System.out.println("channel active:" + channel.isActive());
		System.out.println(channel);
	}
}
