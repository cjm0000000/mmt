package lemon.web.security;

import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
	private static final Log logger = LogFactory.getLog(MMTAccessDecisionManager.class);
	@Override
	public void decide(Authentication authentication, Object object,
			Collection<ConfigAttribute> configAttributes)
			throws AccessDeniedException, InsufficientAuthenticationException {
		logger.debug(authentication);
		logger.debug(object);
		if(configAttributes == null) {  
            return;  
        }  
        //所请求的资源拥有的权限(一个资源对多个权限)  
        Iterator<ConfigAttribute> iterator = configAttributes.iterator();  
        while(iterator.hasNext()) {  
            ConfigAttribute configAttribute = iterator.next();  
            //访问所请求资源所需要的权限  
            String needPermission = configAttribute.getAttribute();  
            logger.debug("访问这个资源需要的角色是： " + needPermission);  
            //用户所拥有的权限authentication  
            for(GrantedAuthority ga : authentication.getAuthorities()) {
            	logger.debug(ga.getAuthority());
                if(needPermission.equals(ga.getAuthority())) {  
                    return;  
                }  
            }  
        }  
        //没有权限 
        //TODO 暂时去掉权限认证
        //throw new AccessDeniedException(" 没有权限访问！ ");
		
	}

	@Override
	public boolean supports(ConfigAttribute attribute) {
		// TODO 可以在这调整supports
		return true;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO 可以在这调整supports
		return true;
	}

}
