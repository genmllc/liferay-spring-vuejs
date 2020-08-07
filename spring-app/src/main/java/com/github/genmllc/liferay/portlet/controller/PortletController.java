package com.github.genmllc.liferay.portlet.controller;

import com.liferay.portletmvc4spring.bind.annotation.RenderMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.portlet.RenderResponse;

/**
 * @author Gaetan MOULLEC
 */
@Controller
@RequestMapping("VIEW")
public class PortletController {

	private static final Logger LOG = LoggerFactory.getLogger(PortletController.class);

	@RenderMapping
	public String prepareView(ModelMap modelMap, RenderResponse renderResponse) {
		modelMap.put("namespace", renderResponse.getNamespace());
		return "init";
	}

	@Autowired
	private MessageSource _messageSource;

}