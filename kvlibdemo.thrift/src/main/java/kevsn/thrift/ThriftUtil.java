/*
 * 2016年4月13日 
 */
package kevsn.thrift;

import java.lang.reflect.Constructor;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.function.Supplier;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TBinaryProtocol.Factory;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TServer.AbstractServerArgs;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.server.TThreadPoolServer.Args;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TNonblockingSocket;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;

import kevsn.thrift.service.Hello;
import kevsn.thrift.service.Hello.Processor;
import kevsn.thrift.service.impl.HelloImpl;

/**
 * @author Kevin
 *
 */
public class ThriftUtil {

	public static TNonblockingServer getNonblockingServer(String host, int port,
			TProcessor processer) throws TTransportException {
		InetSocketAddress address = new InetSocketAddress(host, port);
		TNonblockingServerSocket socket = new TNonblockingServerSocket(address);
		// 设置协议工厂为 TBinaryProtocol.Factory
		Factory proFactory = new TBinaryProtocol.Factory();
		// 关联处理器与 Hello 服务的实现
		TNonblockingServer.Args ags = new TNonblockingServer.Args(socket);
		ags.processor(processer);
		ags.protocolFactory(proFactory);
		TNonblockingServer s = new TNonblockingServer(ags);
		return s;
	}

	public static TNonblockingServer getNonblockingServer(int port,
			TProcessor processer) throws TTransportException {
		return getNonblockingServer("0.0.0.0", port, processer);
	}

	public static TThreadPoolServer getThreadPoolServer(int port,
			TProcessor processer) throws TTransportException {
		return getThreadPoolServer("0.0.0.0", port, processer);
	}

	public static TThreadPoolServer getThreadPoolServer(String host, int port,
			TProcessor processer) throws TTransportException {
		InetSocketAddress address = new InetSocketAddress(host, port);
		TServerSocket serverTransport = new TServerSocket(address);
		// 设置协议工厂为 TBinaryProtocol.Factory
		Factory proFactory = new TBinaryProtocol.Factory();
		// 关联处理器与 Hello 服务的实现
		Args ags = new Args(serverTransport);
		ags.processor(processer);
		ags.protocolFactory(proFactory);
		TThreadPoolServer server = new TThreadPoolServer(ags);
		return server;
	}

	public static void main(String[] args) throws TTransportException {
		Hello.Processor<HelloImpl> processer = new Hello.Processor<HelloImpl>(
				new HelloImpl());
		TServer server = getThreadPoolServer(7912, processer);
		server.serve();
		// TNonblockingServer server = getNonblockingServer(7912, processer);
		// server.serve();
		System.out.println("serving");
	}

	
}
