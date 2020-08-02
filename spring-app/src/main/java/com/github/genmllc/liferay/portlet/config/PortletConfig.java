package com.github.genmllc.liferay.portlet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.liferay.portletmvc4spring.multipart.StandardPortletMultipartResolver;

/**
 * Portlet Specific Spring Configuration. You can either declare your bean here or in {@link portlet-spring-context.xml}
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
