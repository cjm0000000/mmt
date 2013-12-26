package lemon.shared.toolkit.idcenter;

import java.util.HashMap;
import java.util.Map;

import com.github.cjm0000000.mmt.shared.access.Access;
import com.github.cjm0000000.mmt.shared.access.AccessToken;
import com.github.cjm0000000.mmt.shared.access.AccessTokenLog;
import com.github.cjm0000000.mmt.shared.customer.CustomMenu;
import com.github.cjm0000000.mmt.shared.customer.CustomMenuLog;
import com.github.cjm0000000.mmt.shared.customer.CustomerService;
import com.github.cjm0000000.mmt.shared.fans.Fans;
import com.github.cjm0000000.mmt.shared.fans.FansLog;

import lemon.shared.media.Media;
import lemon.shared.media.MediaSync;
import lemon.shared.media.MediaSyncLog;
import lemon.shared.message.local.LocalMsgBean;
import lemon.shared.message.log.MsgLog;
import lemon.shared.message.metadata.Message;
import lemon.shared.message.metadata.send.Article;

/**
 * ID generator manager
 * @author lemon
 * @version 1.1
 *
 */
public class IdWorkerManager {
	private static final int datacenterId = 1;
	private static final int sequence = 1 << 10;
	/** 存放所有的ID worker */
	private static final Map<Class<?>, IdWorker> idRepos = new HashMap<>();
	static{
		idRepos.put(Access.class, 			generateIdWorker(1));
		idRepos.put(AccessToken.class, 		generateIdWorker(2));
		idRepos.put(AccessTokenLog.class, 	generateIdWorker(3));
		
		idRepos.put(CustomMenu.class, 		generateIdWorker(4));
		idRepos.put(CustomMenuLog.class, 	generateIdWorker(5));
		idRepos.put(CustomerService.class, 	generateIdWorker(6));
		
		idRepos.put(Fans.class, 			generateIdWorker(7));
		idRepos.put(FansLog.class, 			generateIdWorker(8));
		
		idRepos.put(MsgLog.class, 			generateIdWorker(9));
		idRepos.put(Message.class, 			generateIdWorker(10));
		idRepos.put(Article.class, 			generateIdWorker(11));
		idRepos.put(LocalMsgBean.class, 	generateIdWorker(12));
		
		idRepos.put(Media.class, 			generateIdWorker(13));
		idRepos.put(MediaSync.class, 		generateIdWorker(14));
		idRepos.put(MediaSyncLog.class, 	generateIdWorker(15));
	}
	
	/**
	 * 获取ID worker
	 * @param clz
	 * @return
	 */
	public static IdWorker getIdWorker(Class<?> clz){
		return idRepos.get(clz);
	}
	
	/**
	 * 生成一个新的ID worker
	 * @param workerId
	 * @return
	 */
	private static IdWorker generateIdWorker(int workerId){
		return new IdWorker(workerId, datacenterId, sequence);
	}
}
