package com.github.cjm0000000.mmt.shared.fans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.cjm0000000.mmt.core.MmtException;
import com.github.cjm0000000.mmt.core.config.Status;
import com.github.cjm0000000.mmt.core.service.ServiceType;
import com.github.cjm0000000.mmt.shared.fans.persistence.FansRepository;
import com.github.cjm0000000.mmt.shared.toolkit.idcenter.IdWorkerManager;

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
	private Fans initFans;
	
	/**
	 * save fans information
	 * @param fans
	 */
	@Transactional(propagation = Propagation.REQUIRED)
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
	@Transactional(propagation = Propagation.REQUIRED)
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
		saveFansLog(fans, FansLog.Actions.SUBSCRIBE);
	}
	
	/**
	 * save UnSubscribe log 
	 * @param log
	 */
	public void saveUnSubscribeLog(Fans fans){
		saveFansLog(fans, FansLog.Actions.UNSUBSCRIBE);
	}
	
	/**
	 * New a fans object
	 * @param cust_id
	 * @param user_id
	 * @param service_type
	 * @return
	 */
	public Fans newFans(int cust_id, String user_id, ServiceType service_type){
		if(initFans == null){
			initFans = new Fans();
			initFans.setStatus(Status.AVAILABLE);
			initFans.setNick_name("");
		}
		initFans.setCust_id(cust_id);
		initFans.setService_type(service_type);
		initFans.setUser_id(user_id);
		return initFans.clone();
	}
	
	/**
	 * real save fans log
	 * @param fans
	 * @param action
	 * @return
	 */
	private int saveFansLog(Fans fans, FansLog.Actions action){
		FansLog log = new FansLog();
		log.setAction(action);
		log.setCust_id(fans.getCust_id());
		log.setService_type(fans.getService_type());
		log.setUser_id(fans.getUser_id());
		log.setId(IdWorkerManager.getIdWorker(FansLog.class).getId());
		return fansMapper.saveFansLog(log);
	}

}
