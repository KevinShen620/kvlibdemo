/*
 * 2016年4月8日 
 */
package kevsn.thrift;

import java.net.InetSocketAddress;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TBinaryProtocol.Factory;

import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.server.TThreadPoolServer.Args;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;

import kevsn.thrift.service.Hello;
import kevsn.thrift.service.Hello.Processor;
import kevsn.thrift.service.impl.HelloImpl;

/**
 * @author Kevin
 *
 */
public class ThriftServer {

	public static void main(String[] args) throws TTransportException {
		String tHost = "localhost";
		int tPort = 7911;
		InetSocketAddress address = new InetSocketAddress(tHost, tPort);
		TServerSocket serverTransport = new TServerSocket(address);
		// 设置协议工厂为 TBinaryProtocol.Factory
		Factory proFactory = new TBinaryProtocol.Factory();
		// 关联处理器与 Hello 服务的实现
		Processor<HelloImpl> processor = new Hello.Processor<HelloImpl>(
				new HelloImpl());
		Args ags = new Args(serverTransport);
		ags.processor(processor);
		ags.protocolFactory(proFactory);
		TThreadPoolServer server = new TThreadPoolServer(ags);
		System.out.println("Start server on port 7911...");
		server.serve();
	}
}
