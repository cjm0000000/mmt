package lemon.shared.fans;

import lemon.shared.MmtException;
import lemon.shared.config.Status;
import lemon.shared.fans.log.FansLog;
import lemon.shared.fans.persistence.FansRepository;
import lemon.shared.service.ServiceType;
import lemon.shared.toolkit.idcenter.IdWorkerManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * manager for fans
 * @author lemon
 * @version 1.0
 *
 */
@Service
public class FansManager {
	@Autowired
	private FansRepository fansMapper;
	
	/**
	 * save fans information
	 * @param fans
	 */
	public void saveFans(Fans fans){
		if(null == fans) throw new MmtException("Can't save, fans is null.");
		Fans existsFans = fansMapper.getFans(fans.getCust_id(), fans.getService_type(), fans.getUser_id());
		if(null == existsFans){//add fans
			fans.setId(IdWorkerManager.getIdWorker(Fans.class).getId());
			fansMapper.saveFans(fans);
			existsFans = fans;
		}else{//update fans
			existsFans.setNick_name(fans.getNick_name());
			existsFans.setStatus(fans.getStatus());
			fansMapper.updateFans(existsFans);
		}
		// save subscribe log
		saveSubscribeLog(existsFans);
	}
	
	/**
	 * disable fans, because he(she) unsubscribe service
	 * @param cust_id
	 * @param service_type
	 * @param user_id
	 */
	public void disableFans(int cust_id, ServiceType service_type, String user_id){
		Fans existsFans = fansMapper.getFans(cust_id, service_type, user_id);
		if(null == existsFans) return;
		//disable fans
		existsFans.setStatus(Status.UNAVAILABLE);
		fansMapper.updateFans(existsFans);
		// save unsubscribe log
		saveUnSubscribeLog(existsFans);
	}
	
	/**
	 * save Subscribe log 
	 * @param log
	 */
	public void saveSubscribeLog(Fans fans){
		saveFansLog(fans, Actions.SUBSCRIBE);
	}
	
	/**
	 * save UnSubscribe log 
	 * @param log
	 */
	public void saveUnSubscribeLog(Fans fans){
		saveFansLog(fans, Actions.UNSUBSCRIBE);
	}
	
	/**
	 * real save fans log
	 * @param fans
	 * @param action
	 * @return
	 */
	private int saveFansLog(Fans fans, Actions action){
		FansLog log = new FansLog();
		log.setAction(action);
		log.setCust_id(fans.getCust_id());
		log.setService_type(fans.getService_type());
		log.setUser_id(fans.getUser_id());
		log.setId(IdWorkerManager.getIdWorker(FansLog.class).getId());
		return fansMapper.saveFansLog(log);
	}

}
