/*
 * 2016年7月30日 
 */
package kevsn;

import java.util.ArrayList;
import java.util.List;

import org.cometd.bayeux.server.ServerSession;
import org.springframework.stereotype.Component;

/**
 * @author Kevin
 *
 */
@Component
public class MessageSessions {

	private List<ServerSession> sessions = new ArrayList<>();

	public void addSession(ServerSession session) {
		this.sessions.add(session);
	}

	public void broadCastMessage() {

	}
}
