package com.github.genmllc.liferay.servlet.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

/**
 * API Spring Configuration
 * @author Gaetan Moullec
 *
 */
@Configuration
//@EnableWebMvc
@ComponentScan(basePackages = "com.github.genmllc.liferay.servlet")
public class RestConfig  {
	
	@Autowired
	@Qualifier("thymeleafResolver")
	protected ViewResolver viewResolver;
	
	@Autowired
	@Qualifier("jsonMapper")
	protected ObjectMapper jsonMapper;

	@Bean
	public RequestMappingHandlerMapping handlerMapping() {
		RequestMappingHandlerMapping handlerMapping = new RequestMappingHandlerMapping();
		handlerMapping.setAlwaysUseFullPath(true);
		return handlerMapping;
	}
	
	@Bean
	public ViewResolver contentNegotiatingViewResolver(ContentNegotiationManager manager) {
		ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
		resolver.setContentNegotiationManager(manager);

		List<ViewResolver> resolvers = new ArrayList<>();
		resolvers.add(viewResolver);
		resolver.setViewResolvers(resolvers);

		MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
		jsonView.setObjectMapper(jsonMapper);
		resolver.setDefaultViews(Arrays.asList(jsonView));
		return resolver;
	}

}