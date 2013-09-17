package lemon.web.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Service;

/**
 * MMT 安全过滤器
 * @author lemon
 * @version 1.0
 *
 */
public class MMTSecurityInterceptor extends AbstractSecurityInterceptor
		implements Filter {
	@Autowired
	private FilterInvocationSecurityMetadataSource fisMetadataSource;
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		//super.beforeInvocation(fi);源码    
        //1.获取请求资源的权限    
            //执行Collection<ConfigAttribute> attributes = SecurityMetadataSource.getAttributes(object);    
        //2.是否拥有权限    
            //this.accessDecisionManager.decide(authenticated, object, attributes);    
        System.out.println("------------MyFilterSecurityInterceptor.doFilter()-----------开始拦截器了....");  
        FilterInvocation fi = new FilterInvocation(request, response, chain);  
        InterceptorStatusToken token = super.beforeInvocation(fi);
        try {  
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());  
        } finally{  
            super.afterInvocation(token,null);  
        }  
        System.out.println("------------MyFilterSecurityInterceptor.doFilter()-----------拦截器该方法结束了...."); 
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Class<?> getSecureObjectClass() {
		return FilterInvocation.class;
	}

	@Override
	public SecurityMetadataSource obtainSecurityMetadataSource() {
		return fisMetadataSource;
	}

}
