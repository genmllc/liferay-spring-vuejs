package com.github.genmllc.liferay.common.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.util.PortalUtil;

/**
 * Servlet Filter use to authenticate calls from a Front-End App deployed in the portal. In order to be authorized, the user must be currently connected to the portal.
 * Liferay uses the JSESSIONID and the ID Cookies to determine if the user is authenticated or not.
 * (Liferay must keep somewhere under the hood a mapping a SESSIONID corresponding to authenticated session.)
 * @author Gaetan Moullec
 *
 */
public class LiferayAuthenticationFilter extends GenericFilterBean {

	private static final Logger LOG = LoggerFactory.getLogger(LiferayAuthenticationFilter.class);

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		
		SecurityContext securityContext = SecurityContextHolder.getContext();

		if (securityContext.getAuthentication() == null) {
			HttpServletRequest req = (HttpServletRequest) request;
			User user = null;
			try {
				// TODO : impersonification => (Hint) https://liferay.dev/forums/-/message_boards/message/118619239
				// If parameter doAsUserIs is present then
				// check admin permission for the real connected user
				// If ok then retrieve user impersonified
				
				// A user cannot be find if not currently connected to the portal
				user = PortalUtil.getUser(req);
			} catch (PortalException e) {
				LOG.error("An error occured when tring to authenticated a remote call", e);
			}
			
			if(user == null) {
				LOG.warn("Unauthenticated request, access denied. IP :" + getClientIpAddress(req));
			} else {
				List<Role> userRoles = RoleLocalServiceUtil.getUserRoles(user.getUserId());
				List<GrantedAuthority> authorities = new ArrayList<>();
				for (Role userRole : userRoles) {
					authorities.add(new SimpleGrantedAuthority("ROLE_" + userRole.getName()));
				}
				
				// Creating a AuthenticationToken with Authorited is equivalent of declaring that the user is connected and authentified.
				securityContext.setAuthentication(new LiferayAuthenticationToken(user, authorities));
				LOG.debug("Populated SecurityContextHolder with Liferay authentication: '" + securityContext.getAuthentication() + "'");				
			}

		}
		else {
			LOG.debug("SecurityContextHolder not populated with a Liferay authentication, as it already contained: '" + securityContext.getAuthentication() + "'");
		}

		chain.doFilter(request, response);		
	}

	private static final String[] HEADERS_TO_TRY = { "X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP",
			"HTTP_X_FORWARDED_FOR", "HTTP_X_FORWARDED", "HTTP_X_CLUSTER_CLIENT_IP", "HTTP_CLIENT_IP",
			"HTTP_FORWARDED_FOR", "HTTP_FORWARDED", "HTTP_VIA", "REMOTE_ADDR" };

	private String getClientIpAddress(HttpServletRequest request) {
		for (String header : HEADERS_TO_TRY) {
			String ip = request.getHeader(header);
			if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
				return ip;
			}
		}

		return request.getRemoteAddr();
	}
}
