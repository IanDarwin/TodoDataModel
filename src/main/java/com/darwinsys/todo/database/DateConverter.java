package com.darwinsys.todo.database;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.darwinsys.todo.model.Date;

/** JPA Converter for our custom Date class */
@Converter
public class DateConverter implements AttributeConverter<Date, String> {

	@Override
	public String convertToDatabaseColumn(Date d) {
		if (d == null)
			return null;
		return d.toString();
	}

	@Override
	public Date convertToEntityAttribute(String s) {
		if (s == null)
			return null;
		return new Date(s);
	}
}
