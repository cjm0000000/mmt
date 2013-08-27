package lemon.web.util;

import java.util.HashMap;
import java.util.Map;

import lemon.web.system.bean.User;


/**
 * 管理在线用户
 * @author 张连明
 *
 * @date 2012-7-13 下午1:14:03
 */
public class OnlineUserContext {
	private static OnlineUserContext instance;
	/** 当前用户连接数 */
	public int connections;
	/** 在线用户列表 */
	public Map<String, User> userMap;
	
	private OnlineUserContext(){
		userMap = new HashMap<String, User>();
		connections = 0;
	}
	public static OnlineUserContext getInstance(){
		if(instance==null)
			instance = new OnlineUserContext();
		return instance;
	}
	
	public synchronized void put(User user) {
        if (user != null) {
        	userMap.put(user.getUser_id()+"_"+user.getRole_id(), user);
        }
    }
	
	public synchronized void remove(User user){
		if(user != null)
			userMap.remove(user.getUser_id()+"_"+user.getRole_id());
	}
	
	public synchronized User get(String key){
		if (key == null) return null;
		return userMap.get(key);
	}
	
	public synchronized void conAdd(){
		connections++;
	}
	
	public synchronized void conDel(){
		connections--;
	}
	
	public synchronized int getCons(){
		return connections;
	}
	
	public synchronized void reset(){
		connections=0;
		userMap.clear();
	}
}
