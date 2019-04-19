package org.vaadin.easybinder.data.converters;

import com.vaadin.flow.data.converter.Converter;
import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;

@SuppressWarnings("serial")
public class NullConverterPrimitiveTarget<T> implements Converter<T, T> {

	@Override
	public Result<T> convertToModel(T value, ValueContext context) {
		return value == null ? Result.error("Null not allowed") : Result.ok(value);
	}

	@Override
	public T convertToPresentation(T value, ValueContext context) {
		return value;
	}
}
