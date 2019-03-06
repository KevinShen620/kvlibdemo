/*
 * 2016年7月30日 
 */
package kevsn;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import org.cometd.bayeux.server.BayeuxServer;
import org.cometd.bayeux.server.ServerSession;
import org.springframework.stereotype.Component;

/**
 * @author Kevin
 *
 */
@Component
public class MessageGenerate {

	@Inject
	private BayeuxServer bayeux;
	private ScheduledExecutorService pool;

	public void init() {
		pool = Executors.newScheduledThreadPool(5);
		pool.schedule(this::send, 30, TimeUnit.SECONDS);
	}

	private void send() {
		List<ServerSession> sessions = bayeux.getSessions();
		for (ServerSession session : sessions) {

		}
		pool.schedule(this::send, 30, TimeUnit.SECONDS);
	}
}
