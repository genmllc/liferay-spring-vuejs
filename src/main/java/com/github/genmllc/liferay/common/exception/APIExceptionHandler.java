package com.github.genmllc.liferay.common.exception;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class APIExceptionHandler extends ResponseEntityExceptionHandler {
	
	private static final Logger LOG = LoggerFactory.getLogger(APIExceptionHandler.class);

	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<Object> handleGenericException(HttpServletRequest request, Exception ex) {
		LOG.warn("No handler for this exception, returning default message : ", ex);
		APIError error = APIError.builder()//
				.build();
		return buildResponseEntity(error);
	}


	private ResponseEntity<Object> buildResponseEntity(APIError apiError) {
		return new ResponseEntity<>(apiError, apiError.getStatus());
	}
}