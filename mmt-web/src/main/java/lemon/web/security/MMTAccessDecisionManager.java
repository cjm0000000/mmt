package lemon.web.security;

import java.util.Collection;
import java.util.Iterator;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

/**
 * 访问决策管理
 * 
 * @author lemon
 * @version 1.0
 * 
 */
@Service("mmtAccessDecisionManager")
public class MMTAccessDecisionManager implements AccessDecisionManager {
	@Override
	public void decide(Authentication authentication, Object object,
			Collection<ConfigAttribute> configAttributes)
			throws AccessDeniedException, InsufficientAuthenticationException {
		if (configAttributes == null)
			return;
		// 所请求的资源拥有的权限(一个资源对多个权限)
		Iterator<ConfigAttribute> iterator = configAttributes.iterator();
		while (iterator.hasNext()) {
			ConfigAttribute configAttribute = iterator.next();
			// 访问所请求资源所需要的权限
			String needPermission = configAttribute.getAttribute();
			// 用户所拥有的权限authentication
			for (GrantedAuthority ga : authentication.getAuthorities())
				if (needPermission.equals(ga.getAuthority()))
					return;
		}
		// 没有权限
		//FIXME 1. MVC 需要捕获这个异常；2. 显示登录页面的错误提醒
		throw new AccessDeniedException("\u00ef\u00bb\u00bf\u00e6\u008b\u0092\u00e7\u00bb\u009d\u00e8\u00ae\u00bf\u00e9\u0097\u00ae\u00e3\u0080\u0082");
	}

	@Override
	public boolean supports(ConfigAttribute attribute) {
		return true;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}

}
