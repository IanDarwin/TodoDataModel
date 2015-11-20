package com.darwinsys.todo.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.darwinsys.todo.converters.ExportText;
import com.darwinsys.todo.model.Date;
import com.darwinsys.todo.model.Export;
import com.darwinsys.todo.model.Priority;
import com.darwinsys.todo.model.Task;

public class ExportTest {

	private final static String TASK_NAME = "Get laundry done";

	Export exporter = new ExportText();
	Task t = new Task();

	@Before
	public void init() {
		t.setName(TASK_NAME);
		t.setPriority(Priority.High);
		t.setCreationDate(new Date(2013,10,06));
	}

	@Test @Ignore // don't care about this ATM
	public void testExportTasks() {
		String expect = 
		"\"Get laundry done\",,,,,\"2013-10-06\",,,,,\"2\",,,,";
		String actual = exporter.export(t);
		assertEquals("export text", expect, actual);
	}
}
