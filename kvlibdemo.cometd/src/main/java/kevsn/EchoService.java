/*
 * 2016年7月29日 
 */
package kevsn;

import org.cometd.annotation.Service;
import org.cometd.annotation.Session;
import org.cometd.bayeux.server.BayeuxServer;
import org.cometd.bayeux.server.ServerMessage;
import org.cometd.bayeux.server.ServerSession;
import org.cometd.server.AbstractService;
import org.springframework.stereotype.Component;

/**
 * @author Kevin
 *
 */
@Service
@Component
public class EchoService {

	private ServerSession serverSession;

	public ServerSession getServerSession() {
		return serverSession;
	}

	@Session
	public void setServerSession(ServerSession serverSession) {
		this.serverSession = serverSession;
	}

}
