package lemon.weixin.biz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thoughtworks.xstream.XStream;

import lemon.shared.api.MmtAPI;
import lemon.shared.weixin.bean.SiteAccessLog;
import lemon.shared.weixin.dao.WXLogManager;
import lemon.weixin.bean.message.BasicMessage;
import lemon.weixin.util.WXHelper;

/**
 * The Weixin API for message
 * 
 * @author lemon
 * 
 */
@Service
public class WXGZAPI implements MmtAPI {
	private static Log logger = LogFactory.getLog(WXGZAPI.class);
	@Autowired
	private WXLogManager wxLogManager;
	private XStream xStream = WXHelper.createXstream();

	@Override
	public boolean verifySignature(Map<String, Object> params) {
		SiteAccessLog log = (SiteAccessLog) params.get("SiteAccess");
		if(null == log)
			return false;
		// save log
		saveAccessLog(log);
		// nonce,token,timestamp dictionary sort
		List<String> list = new ArrayList<>();
		list.add(log.getTimestamp());
		list.add(log.getEchostr());
		list.add(log.getNonce());
		Collections.sort(list);
		StringBuilder sb = new StringBuilder();
		for (String str : list) {
			sb.append(str);
		}
		// sh1 for signature
		String sh1str = WXHelper.sha1(log.getSignature());
		//compare
		return sb.toString().equalsIgnoreCase(sh1str);
	}

	@Override
	public String getReplayMsg(String msg) {
		xStream.processAnnotations(BasicMessage.class);
		Object obj = xStream.fromXML(msg);
		logger.info(obj);
		BasicMessage bm = (BasicMessage) obj;
		logger.debug(bm.getMsgType());
		return "hello, welcome to weixin.";
	}

	private void saveAccessLog(SiteAccessLog log) {
		wxLogManager.saveSiteAccessLog(log);
	}
	
	private String getMsgType(String msg){
		
		
		return null;
	}

}
