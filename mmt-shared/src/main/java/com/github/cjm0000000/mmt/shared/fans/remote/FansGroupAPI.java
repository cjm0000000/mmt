package com.github.cjm0000000.mmt.shared.fans.remote;

import java.util.List;

import com.github.cjm0000000.mmt.core.access.JSONResponse;
import com.github.cjm0000000.mmt.core.message.process.InitiativeProcessor;
import com.github.cjm0000000.mmt.shared.fans.FansGroup;

/**
 * Fans group APIs
 * @author lemon
 * @version 2.0
 *
 */
public interface FansGroupAPI extends InitiativeProcessor {
	/**
	 * create fans group to remote server
	 * @param group
	 * @return
	 */
	JSONResponse createGroup(FansGroup group);
	
	/**
	 * get fans group from remote server
	 * @return
	 */
	List<FansGroup> getGroups();
	
	/**
	 * get group by fan's open id
	 * @param openId
	 * @return
	 */
	FansGroup getGroupByOpenId(String openId);
	
	/**
	 * modify group name
	 * @param group
	 * @return
	 */
	JSONResponse modifyGroupName(FansGroup group);
	
	/**
	 * change fan's group
	 * @param openId
	 * @param toGroupId
	 * @return
	 */
	JSONResponse changeFansGroup(String openId, String toGroupId);
}
