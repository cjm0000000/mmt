package lemon.yixin.fans;

import lemon.shared.entity.Status;
import lemon.yixin.YiXinException;
import lemon.yixin.fans.bean.YiXinFans;
import lemon.yixin.fans.mapper.YXFansMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * manage YiXin fans
 * @author lemon
 * @version 1.0
 *
 */
@Service
public class YiXinFansManager {
	@Autowired
	private YXFansMapper yxFansMapper;
	
	/**
	 * save fans information
	 * @param fans
	 */
	public void saveFans(YiXinFans fans){
		if(null == fans) throw new YiXinException("Can't save, fans is null.");
		YiXinFans existsFans = yxFansMapper.getFans(fans.getCust_id(), fans.getYxid());
		if(null == existsFans){//add fans
			fans.setStatus(Status.AVAILABLE);
			yxFansMapper.saveFans(fans);
		}else{//update fans
			existsFans.setStatus(Status.AVAILABLE);
			yxFansMapper.updateFans(existsFans);
		}
	}
	
	/**
	 * disable fans, because he(she) unsubscribe service
	 * @param fans
	 */
	public void disableFans(int cust_id, String wxid){
		YiXinFans existsFans = yxFansMapper.getFans(cust_id, wxid);
		if(null == existsFans) throw new YiXinException("unsubscribe, but not subscribe.");
		//disable fans
		existsFans.setStatus(Status.UNAVAILABLE);
		yxFansMapper.updateFans(existsFans);
	}

}
