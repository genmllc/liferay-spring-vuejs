package com.github.genmllc.liferay.portlet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.liferay.portletmvc4spring.multipart.StandardPortletMultipartResolver;

/**
 * Portlet Specific Spring Configuration. You can either declare yout bean here or in {@link src\main\webapp\WEB-INF\spring-context\portlet\RestSpringExample.xml}
 * @author Gaetan Moullec
 *
 */
@Configuration
public class PortletConfig {
	
	@Bean
	protected StandardPortletMultipartResolver portletMultipartResolver() {
		return new StandardPortletMultipartResolver();
	}
}
