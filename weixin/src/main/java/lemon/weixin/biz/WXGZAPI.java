package lemon.weixin.biz;

import java.util.Map;

import lemon.shared.api.MmtAPI;

/**
 * The Weixin API for message
 * @author lemon
 *
 */
public class WXGZAPI implements MmtAPI {

	@Override
	public boolean verifySignature(Map<String, Object> params) {
		return false;
	}

	@Override
	public String getReplayMsg(String msg) {
		return null;
	}

}
