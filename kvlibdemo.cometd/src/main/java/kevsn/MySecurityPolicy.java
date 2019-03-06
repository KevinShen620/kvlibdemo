/*
 * 2016年7月29日 
 */
package kevsn;

import org.cometd.bayeux.server.BayeuxServer;
import org.cometd.bayeux.server.ServerMessage;
import org.cometd.bayeux.server.ServerSession;
import org.cometd.server.DefaultSecurityPolicy;

/**
 * @author Kevin
 *
 */
public class MySecurityPolicy extends DefaultSecurityPolicy {

	@Override
	public boolean canHandshake(BayeuxServer server, ServerSession session,
			ServerMessage message) {
		if (session.isLocalSession()) {
			return true;
		}
		return super.canHandshake(server, session, message);
	}
}
