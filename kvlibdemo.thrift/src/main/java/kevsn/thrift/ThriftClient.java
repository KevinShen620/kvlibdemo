/*
 * 2016年4月8日 
 */
package kevsn.thrift;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import kevsn.thrift.service.Hello;

/**
 * @author Kevin
 *
 */
public class ThriftClient {

	public static void main(String[] args) throws TException {

		// 设置调用的服务地址为本地，端口为 7911
		TTransport transport = new TSocket("localhost", 7912);
		transport.open();
		// 设置传输协议为 TBinaryProtocol
		TProtocol protocol = new TBinaryProtocol(transport);
		Hello.Client client = new Hello.Client(protocol);
		// 调用服务的 helloVoid 方法
		client.helloVoid();
		client.helloBoolean(true);
		client.helloInt(3);
		System.out.println(client.helloString("Kevin"));
		
		transport.close();
	}
}
