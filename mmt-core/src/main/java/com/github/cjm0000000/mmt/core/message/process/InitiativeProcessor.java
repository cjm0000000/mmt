package com.github.cjm0000000.mmt.core.message.process;

import com.github.cjm0000000.mmt.core.access.AccessToken;
import com.github.cjm0000000.mmt.core.service.ServiceProperty;

/**
 * customer initiative message processor
 * @author lemon
 * @version 2.0
 *
 */
public interface InitiativeProcessor extends ServiceProperty {
	
	/**
	 * get access token
	 * @return
	 */
	AccessToken getAccessToken();
}
