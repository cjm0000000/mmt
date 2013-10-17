package lemon.shared.api.simple;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import lemon.shared.access.bean.SiteAccess;
import lemon.shared.api.MmtAPI;
import lemon.shared.request.bean.ReturnCode;
import lemon.shared.request.bean.Token;
import lemon.shared.toolkit.http.HttpConnector;
import lemon.shared.toolkit.secure.SecureUtil;

/**
 * Abstract MMT API
 * @author lemon
 * @version 1.0
 *
 */
public abstract class AbstractMmtAPI implements MmtAPI {
	private static Log logger = LogFactory.getLog(AbstractMmtAPI.class);
	/**
	 * 获取通用接口URL
	 * @return
	 */
	public abstract String getCommonUrl();
	
	/**
	 * 获取AccessToken请求的参数
	 * @param mmt_token
	 * @return
	 */
	public abstract Map<String, Object> getAccessTokenRequestParams(String mmt_token);
	
	/**
	 * 发送错误信息
	 * @param errorMsg
	 */
	public abstract void sendError(String errorMsg);
	
	/**
	 * 保存
	 * @param sa
	 */
	protected abstract void saveAccessLog(SiteAccess sa);
	
	@Override
	public final String getAcessToken(String mmt_token) {
		//请求URL
		String url = getCommonUrl();
		// 请求参数
		Map<String, Object> params = getAccessTokenRequestParams(mmt_token);
		//获取结果
		String result = HttpConnector.get(url, params);
		JSONObject jsonObj = JSONObject.fromObject(result);
		if(jsonObj.get("errcode") != null){
			ReturnCode rCode = (ReturnCode) JSONObject.toBean(jsonObj, ReturnCode.class);
			sendError(rCode.getErrmsg());
		}
		Token token = (Token) JSONObject.toBean(jsonObj, Token.class);
		return token.getAccess_token();
	}
	
	@Override
	public final boolean verifySignature(SiteAccess sa) {
		if (null == sa || sa.getSignature() == null)
			return false;
		// save log
		saveAccessLog(sa);
		// nonce,token,timestamp dictionary sort
		List<String> list = new ArrayList<>();
		list.add(sa.getNonce());
		list.add(sa.getToken());
		list.add(sa.getTimestamp());
		Collections.sort(list);
		StringBuilder sb = new StringBuilder();
		for (String str : list) {
			sb.append(str);
		}
		// sha1 for signature
		String sha1str = SecureUtil.sha1(sb.toString());
		logger.debug("After SHA1:" + sha1str);
		// compare
		logger.debug("signature:" + sa.getSignature());
		return sha1str.equalsIgnoreCase(sa.getSignature());
	}

}
