package lemon.weixin.biz;

import lemon.weixin.bean.WeiXinFans;
import lemon.weixin.dao.WXFansMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * manage WeiXin fans
 * @author lemon
 * @version 1.0
 *
 */
@Service
public class WeiXinFansManager {
	@Autowired
	private WXFansMapper wxFansMapper;
	private static final String FANS_ENABLE = "1";
	private static final String FANS_DISABLE = "0";
	
	/**
	 * save fans information
	 * @param fans
	 */
	public void saveFans(WeiXinFans fans){
		if(null == fans) throw new WeiXinException("Can't save, fans is null.");
		WeiXinFans existsFans = wxFansMapper.getFans(fans.getCust_id(), fans.getWxid());
		if(null == existsFans){//add fans
			fans.setStatus(FANS_ENABLE);
			wxFansMapper.saveFans(fans);
		}else{//update fans
			existsFans.setStatus(FANS_ENABLE);
			wxFansMapper.updateFans(existsFans);
		}
	}
	
	/**
	 * disable fans, because he(she) unsubscribe service
	 * @param fans
	 */
	public void disableFans(int cust_id, String wxid){
		WeiXinFans existsFans = wxFansMapper.getFans(cust_id, wxid);
		if(null == existsFans) throw new WeiXinException("unsubscribe, but not subscribe.");
		//disable fans
		existsFans.setStatus(FANS_DISABLE);
		wxFansMapper.updateFans(existsFans);
	}

}
