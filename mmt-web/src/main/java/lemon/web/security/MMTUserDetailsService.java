package lemon.web.security;

import java.util.HashSet;
import java.util.Set;

import lemon.shared.entity.Status;
import lemon.shared.toolkit.secure.SecureUtil;
import lemon.web.base.MMTAction;
import lemon.web.system.bean.Role;
import lemon.web.system.bean.User;
import lemon.web.system.bean.UserConfig;
import lemon.web.system.mapper.RoleMapper;
import lemon.web.system.mapper.UserConfigMapper;
import lemon.web.system.mapper.UserMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 用户细节管理
 * 
 * @author lemon
 * @version 1.0
 *
 */
@Service("mmtUserDetailsService")
public class MMTUserDetailsService implements UserDetailsService {
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private UserConfigMapper userConfigMapper;
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		if(username == null)
			throw new AuthenticationServiceException("用户名不能为空。");
		User user = userMapper.getUserById(userMapper.getUserIdByName(username));
		if(user == null)
			throw new AuthenticationServiceException("用户名不存在。");
		//设置权限
		Role role = roleMapper.getRole(user.getRole_id());
		Set<GrantedAuthority> authSet = new HashSet<GrantedAuthority>();
		authSet.add(new SimpleGrantedAuthority(role.getRole_name()));
		//匹配参数
		boolean enables = true;  
        boolean accountNonExpired = true;  
        boolean credentialsNonExpired = true;  
        boolean accountNonLocked = user.getIslock().equals(Status.UNAVAILABLE);
        //需要解密用户名
        UserConfig cfg = userConfigMapper.getItem(user.getUser_id(), MMTAction.ENCRYPY_KEY);
        if(cfg == null)
        	throw new AuthenticationServiceException("用户密钥不存在。");
        String pass = SecureUtil.aesDecrypt(user.getPassword(), cfg.getValue());
		return new org.springframework.security.core.userdetails.User(
				user.getUsername(), pass, enables,
				accountNonExpired, credentialsNonExpired, accountNonLocked,
				authSet);
	}

}
