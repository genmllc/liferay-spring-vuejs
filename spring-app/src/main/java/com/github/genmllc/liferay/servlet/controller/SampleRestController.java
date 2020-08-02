
package com.github.genmllc.liferay.servlet.controller;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.genmllc.liferay.servlet.model.SampleModel;
import com.liferay.portal.kernel.model.User;

/**
 * Rest Controller. The base paths must start with the same path declared in {@link web.xml} or they won't be protected by Spring security. (and it won't work anymay)
 * @author Gaetan Moullec
 */
@RestController
@RequestMapping("/api/services")
public class SampleRestController {

	private static final Logger LOG = LoggerFactory.getLogger(SampleRestController.class);

	@GetMapping(path = "/samples", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<SampleModel>> getSamples(@AuthenticationPrincipal User liferayUser) {
		LOG.info("User : " + liferayUser.getScreenName());
		
		List<SampleModel> samples = Arrays.asList(new SampleModel(1, "Sample 1"), new SampleModel(2, "Sample 2"));
		return new ResponseEntity<>(samples, HttpStatus.OK);
	}
	
	@GetMapping(path = "/admin/samples", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<SampleModel>> getAdminSamples(@AuthenticationPrincipal User liferayUser) {
		LOG.info("Admin : " + liferayUser.getScreenName());
		
		List<SampleModel> samples = Arrays.asList(new SampleModel(1, "Sample 1"), new SampleModel(2, "Sample 2"));
		return new ResponseEntity<>(samples, HttpStatus.OK);
	}

	@PostMapping(path = "/sample", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SampleModel> createSample(@RequestBody SampleModel sampleModel) {
		// Simulate creating Sample in database...
		return new ResponseEntity<SampleModel>(sampleModel, HttpStatus.OK);
	}

}
