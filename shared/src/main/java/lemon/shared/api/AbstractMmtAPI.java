package lemon.shared.api;

import java.util.Map;

import lemon.shared.request.bean.ReturnCode;
import lemon.shared.request.bean.Token;
import lemon.shared.util.HttpConnector;
import lemon.shared.xstream.XStreamHelper;

/**
 * Abstract MMT API
 * @author lemon
 * @version 1.0
 *
 */
public abstract class AbstractMmtAPI implements MmtAPI {
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
	
	@Override
	public final String getAcessToken(String mmt_token) {
		//请求URL
		String url = getCommonUrl();
		// 请求参数
		Map<String, Object> params = getAccessTokenRequestParams(mmt_token);
		//获取结果
		String result = HttpConnector.get(url, params);
		if(result.startsWith("{\"errcode\"")){
			ReturnCode rCode = (ReturnCode) XStreamHelper.createJSONXStream()
					.fromXML(addRoot(ReturnCode.class, result));
			sendError(rCode.getErrmsg());
		}
		Token token = (Token) XStreamHelper.createJSONXStream().fromXML(addRoot(Token.class, result));
		return token.getAccess_token();
	}

	/**
	 * 为JSON字符串加根节点
	 * @param clazz
	 * @param json
	 * @return
	 */
	private String addRoot(Class<?> clazz, String json){
		StringBuilder sb = new StringBuilder();
		sb.append("{").append("\"");
		sb.append(clazz.getName()).append("\"").append(":");
		sb.append(json).append("}");
		return sb.toString();
	}
}
