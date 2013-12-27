package com.github.cjm0000000.mmt.web.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Service;

import com.github.cjm0000000.mmt.web.global.MMT;
import com.github.cjm0000000.mmt.web.system.bean.Menu;
import com.github.cjm0000000.mmt.web.system.bean.Role;
import com.github.cjm0000000.mmt.web.system.persistence.MenuRepository;
import com.github.cjm0000000.mmt.web.system.persistence.RoleRepository;

/**
 * 加载URI与权限的对应关系
 * @author lemon
 * @version 1.0
 *
 */
@Service("mmtSecurityMetadataSource")
public class MMTSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
	@Autowired
	private RoleRepository roleMapper;
	@Autowired
	private MenuRepository menuMapper;
	private static Map<String, Collection<ConfigAttribute>> resourceMap = null;
	
	@Override
	public Collection<ConfigAttribute> getAttributes(Object object)
			throws IllegalArgumentException {
		String requestUrl = ((FilterInvocation) object).getRequestUrl();
        if(resourceMap == null)
            loadResourceDefine();
        return resourceMap.get(requestUrl);
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		if(resourceMap == null)
			loadResourceDefine();
		Set<ConfigAttribute> allResources = new HashSet<>();
		for (Entry<String, Collection<ConfigAttribute>> entry : resourceMap.entrySet())
			allResources.addAll(entry.getValue());
		return allResources;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}
	
	/**
	 * 加载所有资源与权限的关系 
	 */
	public synchronized void loadResourceDefine() {
		List<Menu> resources = menuMapper.getMenuList();
		if (resourceMap == null) 
			resourceMap = new HashMap<String, Collection<ConfigAttribute>>((int)(resources.size()/0.75F));
		else
			resourceMap.clear();
		for (Menu resource : resources) {
			if("1".equals(resource.getMenulevcod())) continue;
			//获取可以访问这个资源的角色
			List<Role> roleList = roleMapper.getRoleListByAuthority(resource.getMenu_id());
			if(null == roleList || roleList.size() == 0) continue;
			Collection<ConfigAttribute> configAttributes = new ArrayList<ConfigAttribute>();
			for (Role role : roleList) {
				configAttributes.add(new SecurityConfig(role.getRole_name()));
			}
			//授权信息存入内存
			resourceMap.put(MMT.FILTER_ROOT + resource.getMenuurl(), configAttributes);
		}
	}

}
