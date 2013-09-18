package lemon.web.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lemon.web.system.bean.Menu;
import lemon.web.system.bean.Role;
import lemon.web.system.mapper.RoleMapper;
import lemon.web.system.mapper.RoleMenuMapper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Service;

/**
 * 加载URI与权限的对应关系
 * @author lemon
 * @version 1.0
 *
 */
@Service("mmtSecurityMetadataSource")
public class MMTSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
	@Autowired
	private RoleMenuMapper roleMenuMapper;
	@Autowired
	private RoleMapper roleMapper;
	private volatile Map<String, Collection<ConfigAttribute>> resourceMap = null;
	private static Log logger = LogFactory.getLog(MMTSecurityMetadataSource.class);
	@Override
	public Collection<ConfigAttribute> getAttributes(Object object)
			throws IllegalArgumentException {
		String requestUrl = ((FilterInvocation) object).getRequestUrl();  
		logger.debug("requestUrl is : " + requestUrl);  
		//FIXME URL需要处理下，匹配context内的绝对URL
        if(resourceMap == null) {  
            loadResourceDefine();  
        }  
        return resourceMap.get(requestUrl); 
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		logger.debug("support Class: " + clazz);
		return true;
	}
	
	/**
	 * 加载所有资源与权限的关系 
	 */
	private void loadResourceDefine() {
		if (resourceMap == null) {
			resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
			List<Role> roleList = roleMapper.getRoleList(0, 0);
			for (Role role : roleList) {
				List<Menu> resources = roleMenuMapper.getAllMenuListByRole(role.getRole_id());
				for (Menu resource : resources) {
					Collection<ConfigAttribute> configAttributes = new ArrayList<ConfigAttribute>();
					// 以权限名封装为Spring的security Object
					ConfigAttribute configAttribute = new SecurityConfig(role.getRole_name());
					configAttributes.add(configAttribute);
					resourceMap.put(resource.getMenuurl(), configAttributes);
				}
			}
		}

//		Set<Entry<String, Collection<ConfigAttribute>>> resourceSet = resourceMap
//				.entrySet();
//		Iterator<Entry<String, Collection<ConfigAttribute>>> iterator = resourceSet
//				.iterator();

	}

}
