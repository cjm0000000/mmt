package lemon.shared.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import lemon.shared.MmtException;
import lemon.shared.access.AccessToken;
import lemon.shared.access.AccessTokenLog;
import lemon.shared.access.ReturnCode;
import lemon.shared.access.Access;
import lemon.shared.access.persistence.AccessRepository;
import lemon.shared.config.MMTConfig;
import lemon.shared.customer.Action;
import lemon.shared.customer.CustomMenuLog;
import lemon.shared.customer.persistence.CustomMenuRepository;
import lemon.shared.message.log.MsgLog;
import lemon.shared.message.log.MsgLogManager;
import lemon.shared.message.metadata.Message;
import lemon.shared.service.ServiceType;
import lemon.shared.toolkit.http.HttpConnector;
import lemon.shared.toolkit.idcenter.IdWorkerManager;
import lemon.shared.toolkit.secure.SecureUtil;

/**
 * Abstract MMT API
 * @author lemon
 * @version 1.0
 *
 */
public abstract class AbstractMmtAPI implements MmtAPI {
	private static Log logger = LogFactory.getLog(AbstractMmtAPI.class);
	@Autowired
	private MsgLogManager msgLogManager;
	@Autowired
	private AccessRepository accessRepository;
	@Autowired
	private CustomMenuRepository customMenuMapper;
	
	/**
	 * 验证接口配置
	 * @param config
	 * @return
	 */
	public abstract void verifyConfig(MMTConfig config);
	
	/**
	 * 获取通用接口URL
	 * @return
	 */
	public abstract String getCommonUrl();
	
	/**
	 * 获取创建菜单接口URL
	 * @return
	 */
	public abstract String getCreateMenuUrl();
	
	/**
	 * 获取删除菜单接口URL
	 * @return
	 */
	public abstract String getDeleteMenuUrl();
	
	/**
	 * 获取发送客服消息的URL
	 * @return
	 */
	public abstract String getCustomMsgUrl();
	
	/**
	 * 获取AccessToken请求的参数
	 * @param mmt_token
	 * @return
	 */
	public abstract Map<String, Object> getAccessTokenRequestParams(MMTConfig config);
	
	/**
	 * 发送错误信息
	 * @param errorMsg
	 */
	public abstract void sendError(String errorMsg);
	
	/**
	 * 获取服务类型
	 * @return
	 */
	public abstract ServiceType getServiceType();
	
	/**
	 * 生成消息详情
	 * @param msg
	 * @return
	 */
	public abstract JSONObject generateMsgDetail(Message msg);
	
	@Override
	public final ReturnCode createMenus(MMTConfig config, String menuJson) {
		//验证配置
		verifyConfig(config);
		//发送请求
		Map<String, Object> params = new HashMap<>();
		params.put("access_token", getAcessToken(config));
		String result = HttpConnector.post(getCreateMenuUrl(), menuJson, params);
		//save log
		CustomMenuLog log = generateCustomMenuLog(params.get("access_token")
				.toString(), Action.CREATE, config.getCust_id(), menuJson, result);
		customMenuMapper.saveMenuSyncLog(log);
		//parser result
		JSONObject json = JSONObject.fromObject(result);
		return (ReturnCode) JSONObject.toBean(json, ReturnCode.class);
	}
	
	@Override
	public final ReturnCode deleteMenus(MMTConfig config) {
		if(config == null)
			throw new MmtException("客户接口配置信息不存在。");
		//发送请求
		Map<String, Object> params = new HashMap<>();
		params.put("access_token", getAcessToken(config));
		String result = HttpConnector.post(getDeleteMenuUrl(), params);
		// save log
		CustomMenuLog log = generateCustomMenuLog(params.get("access_token")
				.toString(), Action.DELETE, config.getCust_id(), null, result);
		customMenuMapper.saveMenuSyncLog(log);
		JSONObject json = JSONObject.fromObject(result);
		return (ReturnCode) JSONObject.toBean(json, ReturnCode.class);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public final String getAcessToken(MMTConfig config) {
		//从数据库读取Access Token
		AccessToken token = accessRepository.getAccessToken(config.getCust_id(), getServiceType());
		if (token != null && token.getExpire_time() >= (System.currentTimeMillis() / 1000))
			return token.getAccess_token();
			
		//请求URL
		String url = getCommonUrl();
		// 请求参数
		Map<String, Object> params = getAccessTokenRequestParams(config);
		//获取结果
		String result = HttpConnector.get(url, params);
		//save log
		AccessTokenLog log = new AccessTokenLog();
		log.setCust_id(config.getCust_id());
		log.setAppid(params.get("appid").toString());
		log.setGrant_type(params.get("grant_type").toString());
		log.setSecret(params.get("secret").toString());
		log.setResult(result);
		log.setService_type(getServiceType());
		log.setId(IdWorkerManager.getIdWorker(AccessTokenLog.class).getId());
		accessRepository.saveAccessTokenLog(log);
		//parser result
		JSONObject jsonObj = JSONObject.fromObject(result);
		if(jsonObj.get("errcode") != null){
			ReturnCode rCode = (ReturnCode) JSONObject.toBean(jsonObj, ReturnCode.class);
			sendError("Get Access Token faild: " + rCode.getErrmsg());
		}
		token = (AccessToken) JSONObject.toBean(jsonObj, AccessToken.class);
		//保存
		token.setCust_id(config.getCust_id());
		//扣除20秒，为什么？
		token.setExpire_time((int)(System.currentTimeMillis()/1000) + token.getExpires_in() - 20);
		token.setService_type(getServiceType());
		accessRepository.deleteAccessToken(config.getCust_id(), getServiceType());
		accessRepository.saveAccessToken(token);
		return token.getAccess_token();
	}
	
	@Override
	public final boolean verifySignature(Access sa) {
		if (null == sa || sa.getSignature() == null)
			return false;
		// save log
		sa.setService_type(getServiceType());
		sa.setId(IdWorkerManager.getIdWorker(Access.class).getId());
		accessRepository.saveAccessLog(sa);
		// nonce,token,timestamp dictionary sort
		List<String> list = new ArrayList<>();
		list.add(sa.getNonce());
		list.add(sa.getToken());
		list.add(sa.getTimestamp_api());
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
	
	@Override
	public final ReturnCode sendMsg(MMTConfig config, Message msg) {
		// 发送请求
		Map<String, Object> params = new HashMap<>();
		params.put("access_token", getAcessToken(config));
		String msgJson = processSendMsg(msg);
		String result = HttpConnector.post(getCustomMsgUrl(), msgJson, params);
		//TODO save log
		
		// parser result
		JSONObject json = JSONObject.fromObject(result);
		return (ReturnCode) JSONObject.toBean(json, ReturnCode.class);
	}
	
	/**
	 * save revive message log
	 * @param cust_id
	 * @param msg
	 */
	protected int saveRecvMsgLog(int cust_id, String msg){
		MsgLog log = obtainMsgLog(cust_id, msg);
		return msgLogManager.saveRecvLog(log);
	}
	
	/**
	 * save send message log
	 * @param cust_id
	 * @param msg
	 */
	protected int saveSendMessageLog(int cust_id, String msg){
		MsgLog log = obtainMsgLog(cust_id, msg);
		return msgLogManager.saveSendLog(log);
	}
	
	/**
	 * generate message log
	 * @param cust_id
	 * @param msg
	 * @return
	 */
	private MsgLog obtainMsgLog(int cust_id, String msg){
		MsgLog log = new MsgLog();
		log.setCust_id(cust_id);
		log.setMsg(msg);
		log.setService_type(getServiceType());
		return log;
	}
	
	/**
	 * 拼装成LOG
	 * @param access_token
	 * @param action
	 * @param cust_id
	 * @param result
	 * @return
	 */
	private CustomMenuLog generateCustomMenuLog(String access_token,
			Action action, int cust_id, String msg, String result) {
		CustomMenuLog log = new CustomMenuLog();
		log.setAccess_token(access_token);
		log.setAction(action);
		log.setCust_id(cust_id);
		log.setMsg(msg);
		log.setResult(result);
		log.setService_type(getServiceType());
		log.setId(IdWorkerManager.getIdWorker(CustomMenuLog.class).getId());
		return log;
	}
	
	/**
	 * 处理要发送的消息
	 * @param msg
	 * @return
	 */
	private String processSendMsg(Message msg){
		if(msg == null)
			throw new MmtException("发送消息不能为空。");
		if(msg.getService_type() == null || ServiceType.OTHER.equals(msg.getService_type()))
			throw new MmtException("不合法的接口服务类型。");
		if(msg.getMsgType() == null || "".equals(msg.getMsgType()))
			throw new MmtException("消息类型不正确。");
		if(msg.getToUserName() == null || "".equals(msg.getToUserName()))
			throw new MmtException("消息接收方不能为空。");
		//process message
		JSONObject json = new JSONObject();
		json.put("touser", msg.getToUserName());
		json.put("msgtype", msg.getMsgType());
		json.put(msg.getMsgType(),generateMsgDetail(msg));
		return JSONObject.fromObject(json).toString();
	}

}
