package lemon.weixin.biz;

import lemon.shared.entity.Status;
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
	
	/**
	 * save fans information
	 * @param fans
	 */
	public void saveFans(WeiXinFans fans){
		if(null == fans) throw new WeiXinException("Can't save, fans is null.");
		WeiXinFans existsFans = wxFansMapper.getFans(fans.getCust_id(), fans.getWxid());
		if(null == existsFans){//add fans
			fans.setStatus(Status.AVAILABLE);
			wxFansMapper.saveFans(fans);
		}else{//update fans
			existsFans.setStatus(Status.AVAILABLE);
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
		existsFans.setStatus(Status.UNAVAILABLE);
		wxFansMapper.updateFans(existsFans);
	}

}
