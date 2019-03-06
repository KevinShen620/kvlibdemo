/*
 * 2016年7月30日 
 */
package kevsn;

import org.cometd.annotation.Configure;
import org.cometd.annotation.Service;
import org.cometd.bayeux.server.Authorizer;
import org.cometd.bayeux.server.ConfigurableServerChannel;

/**
 * @author Kevin
 *
 */
@Service("userMessages")
public class UserMessageService {

	@Configure("/service/")
	public void configure(ConfigurableServerChannel channel) {
		
	}
}
