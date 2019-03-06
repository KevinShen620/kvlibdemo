
package kevsn;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.cometd.bayeux.Channel;
import org.cometd.bayeux.server.BayeuxServer;
import org.cometd.bayeux.server.ServerMessage;
import org.cometd.bayeux.server.ServerSession;
import org.springframework.stereotype.Component;
import org.cometd.annotation.Listener;
import org.cometd.annotation.Service;
import org.cometd.annotation.Session;

//@Named
@Component
@Singleton
@Service("helloService")
public class HelloService {

	private BayeuxServer bayeux;

	// @Session
	private ServerSession serverSession;

	private volatile boolean inited;

	private ScheduledExecutorService pool;

	@Session
	public void setServerSession(ServerSession session) {
		this.serverSession = session;

	}

	@PostConstruct
	public void init() {
		if (inited) {
			return;
		}
		pool = Executors.newScheduledThreadPool(1);
		pool.schedule(this::send, 30, TimeUnit.SECONDS);
	}

	@PreDestroy
	public void stop() {
		pool.shutdown();
	}

	private void send() {
		List<ServerSession> sessions = bayeux.getSessions();
		for (ServerSession ssession : sessions) {
			if (ssession.isLocalSession() || ssession == this.serverSession) {
				continue;
			}
			Map<String, Object> output = new HashMap<>();
			String uuid = UUID.randomUUID().toString();
			output.put("greeting", "Hello, " + uuid);
			ssession.deliver(this.serverSession, "/hello", output);
		}
		pool.schedule(this::send, 30, TimeUnit.SECONDS);
	}

	@Listener("/service/hello")
	public void processHello(ServerSession remote, ServerMessage message) {
		Map<String, Object> input = message.getDataAsMap();
		String name = (String) input.get("name");
		Map<String, Object> output = new HashMap<>();
		output.put("greeting", "Hello, " + name);
		remote.deliver(serverSession, "/hello", output);
	}

	@Inject
	public void setBayeux(BayeuxServer bayeux) {
		this.bayeux = bayeux;
	}

	@Listener(value = Channel.META_HANDSHAKE)
	public void handShake(ServerSession remote, ServerMessage message) {
		System.out.println("handshake");
	}

	@Listener(Channel.META_CONNECT)
	public void connect(ServerSession remote, ServerMessage message) {
		System.out.println("Connected");
	}

	@Listener(Channel.META_DISCONNECT)
	public void disConnect(ServerSession remote, ServerMessage message) {
		System.out.println("disconnect");
	}

	@Listener(Channel.META_SUBSCRIBE)
	public void subscrib(ServerSession remote, ServerMessage message) {
		System.out.println("Subscribed");
	}

}
