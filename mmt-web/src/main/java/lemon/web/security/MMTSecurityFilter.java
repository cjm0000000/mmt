package lemon.web.security;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

/**
 * MMT 安全过滤器<BR>
 * 这个过滤器只能在XML配置：AbstractSecurityInterceptor有两个属性需要注入
 * @author lemon
 * @version 1.0
 *
 */
public class MMTSecurityFilter extends AbstractSecurityInterceptor
		implements Filter {
	@Resource(name="mmtSecurityMetadataSource")
	private FilterInvocationSecurityMetadataSource securityMetadataSource;
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// 1.获取请求资源的权限
		// 2.是否拥有权限
		System.out.println("------------MyFilterSecurityInterceptor.doFilter()-------....");
		FilterInvocation fi = new FilterInvocation(request, response, chain);
		InterceptorStatusToken token = super.beforeInvocation(fi);
		try {
			fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
		} finally {
			super.afterInvocation(token, null);
		}
		System.out.println("------------MyFilterSecurityInterceptor.doFilter()-------....");
	}

	@Override
	public void destroy() {
		
	}

	@Override
	public Class<?> getSecureObjectClass() {
		return FilterInvocation.class;
	}

	@Override
	public SecurityMetadataSource obtainSecurityMetadataSource() {
		return securityMetadataSource;
	}

}
