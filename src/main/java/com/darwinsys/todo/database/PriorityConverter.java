package com.darwinsys.todo.database;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.darwinsys.todo.model.Priority;

/** JPA Converter for our Priority enum */
@Converter
public class PriorityConverter implements AttributeConverter<Priority, Integer> {

	@Override
	public Integer convertToDatabaseColumn(Priority p) {
		return p.ordinal();
	}

	@Override
	public Priority convertToEntityAttribute(Integer i) {
		return Priority.values()[i];
	}
}
