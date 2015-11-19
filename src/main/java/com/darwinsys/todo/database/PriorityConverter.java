package com.darwinsys.todo.database;

import com.darwinsys.todo.model.Priority;

import javax.persistence.*;

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
