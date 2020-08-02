package com.github.genmllc.liferay.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.servlet.ServletContext;
import java.util.HashMap;
import java.util.Map;

/**
 * This configuration class is common to both API and Portlet part They both can
 * inject the beans declared bellow.
 * 
 * @author Gaetan Moullec
 */
@Configuration
@EnableWebMvc
@ComponentScan(value = "com.github.genmllc.liferay.common")
@PropertySource(value={"classpath:/application.properties"})
public class GlobalConfig implements WebMvcConfigurer  {

	@Autowired
	public ServletContext servletContext;

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

	@Bean
	public ServletContextTemplateResolver templateResolver() {
		ServletContextTemplateResolver resolver = new ServletContextTemplateResolver(servletContext);
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setTemplateMode(TemplateMode.HTML);
		resolver.setSuffix(".html");
		return resolver;
	}

	@Bean
	public SpringTemplateEngine templateEngine(ServletContextTemplateResolver templateResolver) {
		SpringTemplateEngine engine = new SpringTemplateEngine();
		engine.setTemplateResolver(templateResolver);
		engine.setEnableSpringELCompiler(true);
		return engine;
	}

	@Bean("thymeleafResolver")
	public ThymeleafViewResolver viewResolver(SpringTemplateEngine templateEngine) {
		ThymeleafViewResolver resolver = new ThymeleafViewResolver();
		resolver.setTemplateEngine(templateEngine);
		resolver.setOrder(1);
		return resolver;
	}


	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		configurer.useRegisteredExtensionsOnly(true);
		configurer.favorParameter(true);

		configurer.defaultContentType(MediaType.APPLICATION_JSON);
		Map<String, MediaType> types = new HashMap<>();
		types.put("json", MediaType.APPLICATION_JSON);
		types.put("html", MediaType.TEXT_HTML);
		configurer.mediaTypes(types);
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry
				.addResourceHandler("/resources/**")
				.addResourceLocations("/WEB-INF/views/");
	}
}
