package com.github.genmllc.liferay.common.security;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import com.liferay.portal.kernel.model.User;

/**
 * Basic AuthenticationToken implementation
 * @author Gaetan MOULLEC
 *
 */
public class LiferayAuthenticationToken extends AbstractAuthenticationToken {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6097079286211426699L;
	
	private final User principal;

	/**
	 * Create a authenticationToken and declared the user authenticated
	 * @param liferayDetails
	 * @param authorities
	 */
	public LiferayAuthenticationToken(User liferayDetails, Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		if(liferayDetails == null) {
			throw new NullPointerException();
		}
		setAuthenticated(true);
		this.principal = liferayDetails;
	}

	/**
	 * Return an empty string because no credentials were needed for authentication
	 */
	@Override
	public Object getCredentials() {
		return "";
	}

	/**
	 * Rreturn the Liferay User object @see {@link User}
	 */
	@Override
	public Object getPrincipal() {
		return principal;
	}
}
