package lemon.web.security;

import java.util.HashSet;
import java.util.Set;

import lemon.shared.entity.Status;
import lemon.web.global.MMTException;
import lemon.web.system.bean.Role;
import lemon.web.system.bean.User;
import lemon.web.system.mapper.RoleMapper;
import lemon.web.system.mapper.UserMapper;

import org.springframework.beans.factory.annotation.Autowired;
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
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		if(username == null)
			throw new MMTException("用户名不能为空。", new NullPointerException());
		User user = userMapper.getUserById(userMapper.getUserIdByName(username));
		if(user == null)
			throw new MMTException("用户名不存在。", new NullPointerException());
		//设置权限
		Role role = roleMapper.getRole(user.getRole_id());
		Set<GrantedAuthority> authSet = new HashSet<GrantedAuthority>();
		authSet.add(new SimpleGrantedAuthority(role.getRole_name()));
		//匹配参数
		boolean enables = true;  
        boolean accountNonExpired = true;  
        boolean credentialsNonExpired = true;  
        boolean accountNonLocked = user.getIslock().equals(Status.UNAVAILABLE);
        
		return new org.springframework.security.core.userdetails.User(
				user.getUser_name(), user.getPassword(), enables,
				accountNonExpired, credentialsNonExpired, accountNonLocked,
				authSet);
	}

}
