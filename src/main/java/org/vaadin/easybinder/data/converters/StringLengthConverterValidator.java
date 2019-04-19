package org.vaadin.easybinder.data.converters;

import com.vaadin.flow.data.converter.Converter;
import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;

public class StringLengthConverterValidator implements Converter<String, String> {

	private static final long serialVersionUID = 1L;

	Integer minLength;
	Integer maxLength;
	String errorMessage;

	public StringLengthConverterValidator(String errorMessage, Integer minLength, Integer maxLength) {
		this.errorMessage = errorMessage;
		this.minLength = minLength;
		this.maxLength = maxLength;
	}

	@Override
	public Result<String> convertToModel(String value, ValueContext context) {
		if (value == null) {
			return Result.ok(null);
		}
		if (minLength != null && value.length() < minLength || maxLength != null && value.length() > maxLength) {
			return Result.error(errorMessage);
		}
		return Result.ok(value);
	}

	@Override
	public String convertToPresentation(String value, ValueContext context) {
		return value;
	}

}
