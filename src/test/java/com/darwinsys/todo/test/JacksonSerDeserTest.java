package com.darwinsys.todo.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.StringWriter;
import java.io.Writer;
import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import com.darwinsys.todo.model.Task;
import com.fasterxml.jackson.databind.ObjectMapper;

/** Test that the serialize/deserialize annotations are working */
public class JacksonSerDeserTest {

	Task startTask;
	String[] startStrings = {
		"\"id\":1",
		"\"name\":\"Get up in the morning\"",
		"\"description\":\"Always a good idea.\"",
		"\"creationDate\":\"2010-11-12\"",
		"\"dueDate\":\"2034-05-06\"",
		"\"completedDate\":\"2020-01-02\"",
	};
	String startJson;

	@Before
	public void init() {
		startTask = new Task("Get up in the morning");
		startTask.setServerId(1);
		startTask.setDescription("Always a good idea.");
		startTask.setCreationDate(LocalDate.of(2010, 11, 12));
		startTask.setDueDate(LocalDate.of(2034, 05, 06));
		startTask.setCompletedDate(LocalDate.of(2020, 01, 02));
		
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		for (String s : startStrings) {
			sb.append(s).append(",");
		}
		sb.setLength(sb.length() - 1);
		sb.append("}"); // XXX strip last ","
		startJson = sb.toString();
	}

	@Test
	public void testDeSerialize() throws Exception {
		Task task = new ObjectMapper().readValue(startJson, Task.class);
		assertEquals("creation date survived", 
			LocalDate.of(2010,11,12), task.getCreationDate());
	}

	@Test
	public void testSerialize() throws Exception {
		Writer w = new StringWriter();
		new ObjectMapper().writeValue(w, startTask);
		String json = w.toString();
		System.out.println(json);
		for (String s : startStrings) {
			assertTrue("serialize:" + s, json.contains(s));
		}
	}
}
