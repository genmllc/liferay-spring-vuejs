package com.github.genmllc.liferay.common.exception.bean;

import javax.annotation.Generated;

public class APIValidationError extends APISubError {
	private String object;
	private String field;
	private Object rejectedValue;
	private String message;

	@Generated("SparkTools")
	private APIValidationError(Builder builder) {
		this.object = builder.object;
		this.field = builder.field;
		this.rejectedValue = builder.rejectedValue;
		this.message = builder.message;
	}

	public String getObject() {
		return object;
	}

	public String getField() {
		return field;
	}

	public Object getRejectedValue() {
		return rejectedValue;
	}

	public String getMessage() {
		return message;
	}

	/**
	 * Creates builder to build {@link APIValidationError}.
	 * 
	 * @return created builder
	 */
	@Generated("SparkTools")
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Builder to build {@link APIValidationError}.
	 */
	@Generated("SparkTools")
	public static final class Builder {
		private String object;
		private String field;
		private Object rejectedValue;
		private String message;

		private Builder() {
		}

		public Builder withObject(String object) {
			this.object = object;
			return this;
		}

		public Builder withField(String field) {
			this.field = field;
			return this;
		}

		public Builder withRejectedValue(Object rejectedValue) {
			this.rejectedValue = rejectedValue;
			return this;
		}

		public Builder withMessage(String message) {
			this.message = message;
			return this;
		}

		public APIValidationError build() {
			return new APIValidationError(this);
		}
	}

}
