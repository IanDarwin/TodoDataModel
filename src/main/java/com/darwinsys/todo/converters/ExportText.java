package com.darwinsys.todo.converters;

import com.darwinsys.todo.model.Export;
import com.darwinsys.todo.model.Task;

public class ExportText extends Export {

	@Override
	public String export(Task t) {
		return t.toString();
	}
}
