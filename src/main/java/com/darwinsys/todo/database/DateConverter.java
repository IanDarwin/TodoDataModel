package com.darwinsys.todo.database;

import com.darwinsys.todo.model.Date;

import javax.persistence.*;

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
