package com.github.cjm0000000.mmt.shared.fans.remote;

import com.github.cjm0000000.mmt.core.access.JSONResponse;
import com.github.cjm0000000.mmt.core.message.process.InitiativeProcessor;
import com.github.cjm0000000.mmt.shared.fans.Fans;

/**
 * Fans APIs
 * @author lemon
 * @version 2.0
 *
 */
public interface FansAPI extends InitiativeProcessor {
	/**
	 * get user information from remote server
	 * @param openId
	 * @return
	 */
	Fans getUserInfo(String openId);
	
	/**
	 * get fans list from remote server
	 * @param next_openid
	 * @return
	 */
	JSONResponse getFans(String next_openid);
}
