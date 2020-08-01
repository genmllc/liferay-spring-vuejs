package com.github.genmllc.liferay.common.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.genmllc.liferay.common.exception.APIError;
import com.github.genmllc.liferay.common.security.LiferayAuthenticationFilter;
import com.liferay.portletmvc4spring.security.SpringSecurityPortletConfigurer;

/**
 * This Security configuration is global and will affect both Portlet and API
 * 
 * @author Gaetan Moullec
 *
 */

@Configuration("springSecurityPortletConfigurer")
public class SecurityConfig extends SpringSecurityPortletConfigurer  {
	
	private static final Logger LOG = LoggerFactory.getLogger(SecurityConfig.class);
	
	@Autowired
	@Qualifier("jsonMapper")
	protected ObjectMapper jsonMapper;

	/**
	 * We rely on Liferay Authentication, so no need for a custom
	 * AuthenticationManager
	 */
	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return authentication -> {
			throw new AuthenticationServiceException("Cannot authenticate " + authentication);
		};
	}
	
	/**
	 * For activating CORS
	 * @return
	 */
	@Bean("mvcHandlerMappingIntrospector")
	protected HandlerMappingIntrospector mvcHandlerMappingIntrospector() {
		return new HandlerMappingIntrospector();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.addFilterBefore(new LiferayAuthenticationFilter(), RememberMeAuthenticationFilter.class);
		http.authorizeRequests()
	        .antMatchers("/services/**").hasRole("User")
	        .antMatchers("/services/admin/**").hasRole("Administrator").and()
			.exceptionHandling()//
				.authenticationEntryPoint(authenticationEntryPoint())//
				.accessDeniedHandler(accessDeniedHandler());
		http.cors().and().csrf().csrfTokenRepository(customCookieCsrfTokenRepository());
	}
	
	private CookieCsrfTokenRepository customCookieCsrfTokenRepository() {
		CookieCsrfTokenRepository repo = CookieCsrfTokenRepository.withHttpOnlyFalse();
		repo.setCookiePath("/");
		return repo;
	}

	private AccessDeniedHandler accessDeniedHandler() {
		return new AccessDeniedHandler() {
			@Override
			public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
					AccessDeniedException e) throws IOException, ServletException {
				LOG.warn("Access denied : " + e.getLocalizedMessage());
				httpServletResponse.getWriter().append(jsonMapper.writeValueAsString(APIError.builder().withStatus(HttpStatus.FORBIDDEN).build()));
				httpServletResponse.setStatus(403);
			}
		};
	}

	private AuthenticationEntryPoint authenticationEntryPoint() {
		return new AuthenticationEntryPoint() {
			@Override
			public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
					AuthenticationException e) throws IOException, ServletException {
				LOG.warn("Not authenticated : " + e.getLocalizedMessage());
				httpServletResponse.getWriter().append(jsonMapper.writeValueAsString(APIError.builder().withStatus(HttpStatus.UNAUTHORIZED).build()));
				httpServletResponse.setStatus(401);
			}
		};
	}

}