package com.github.genmllc.liferay.portlet.controller;
import com.liferay.portletmvc4spring.bind.annotation.RenderMapping;

import javax.portlet.RenderResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Gaetan MOULLEC
 */
@Controller
@RequestMapping("VIEW")
public class PortletController {

	@RenderMapping
	public String prepareView(ModelMap modelMap, RenderResponse renderResponse) {

		modelMap.put("mainFormActionURL", renderResponse.createActionURL());
		modelMap.put("namespace", renderResponse.getNamespace());

		return "init";
	}

	@Autowired
	private MessageSource _messageSource;

}