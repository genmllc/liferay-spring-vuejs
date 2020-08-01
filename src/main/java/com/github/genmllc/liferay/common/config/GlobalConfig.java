package com.github.genmllc.liferay.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.liferay.portletmvc4spring.PortletJstlView;

/**
 * This configuration class is common to both API and Portlet part They both can
 * inject the beans declared bellow.
 * 
 * @author Gaetan Moullec
 */
@Configuration
@ComponentScan(value = "com.github.genmllc.liferay.common")
public class GlobalConfig {

	@Bean("jsonMapper")
	public ObjectMapper jsonMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		return mapper;
	}

	@Bean
	public ResourceBundleMessageSource messageSource() {
		ResourceBundleMessageSource source = new ResourceBundleMessageSource();
		source.addBasenames("content.Language");
		source.setDefaultEncoding("UTF-8");
		return source;
	}

	@Bean("jspResolver")
	public InternalResourceViewResolver viewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setContentType("text/html;charset=UTF-8");
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jspx");
		resolver.setViewClass(PortletJstlView.class);

		return resolver;
	}
}
