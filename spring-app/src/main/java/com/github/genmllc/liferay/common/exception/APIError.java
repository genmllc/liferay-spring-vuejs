package com.github.genmllc.liferay.common.exception;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import javax.annotation.Generated;

import org.springframework.http.HttpStatus;

import com.github.genmllc.liferay.common.exception.bean.APISubError;

public class APIError {

	private HttpStatus status;
	//@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime timestamp;
	private String message;
	private String debugMessage;
	private List<APISubError> subErrors;

	@Generated("SparkTools")
	private APIError(Builder builder) {
		this.status = builder.status;
		this.timestamp = builder.timestamp;
		this.message = builder.message;
		this.debugMessage = builder.debugMessage;
		this.subErrors = builder.subErrors;
	}

	private APIError() {
		timestamp = LocalDateTime.now();
	}

	APIError(HttpStatus status) {
		this();
		this.status = status;
	}

	APIError(HttpStatus status, Throwable ex) {
		this();
		this.status = status;
		this.message = "Unexpected error";
		this.debugMessage = ex.getLocalizedMessage();
	}

	APIError(HttpStatus status, String message, Throwable ex) {
		this();
		this.status = status;
		this.message = message;
		this.debugMessage = ex.getLocalizedMessage();
	}

	public HttpStatus getStatus() {
		return status;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public String getMessage() {
		return message;
	}

	public String getDebugMessage() {
		return debugMessage;
	}

	public List<APISubError> getSubErrors() {
		return subErrors;
	}

	/**
	 * Creates builder to build {@link APIError}.
	 * 
	 * @return created builder
	 */
	@Generated("SparkTools")
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Builder to build {@link APIError}.
	 */
	@Generated("SparkTools")
	public static final class Builder {
		private HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		private LocalDateTime timestamp = LocalDateTime.now();
		private String message = "Unexpected error";
		private String debugMessage;
		private List<APISubError> subErrors = Collections.emptyList();

		private Builder() {
		}

		public Builder withStatus(HttpStatus status) {
			this.status = status;
			return this;
		}

		public Builder withTimestamp(LocalDateTime timestamp) {
			this.timestamp = timestamp;
			return this;
		}

		public Builder withMessage(String message) {
			this.message = message;
			return this;
		}

		public Builder withDebugMessage(String debugMessage) {
			this.debugMessage = debugMessage;
			return this;
		}

		public Builder withSubErrors(List<APISubError> subErrors) {
			this.subErrors = subErrors;
			return this;
		}

		public APIError build() {
			return new APIError(this);
		}
	}

}