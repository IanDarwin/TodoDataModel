package com.darwinsys.todo.test;

import static org.junit.Assert.assertEquals;

import java.io.StringWriter;
import java.io.Writer;
import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import com.darwinsys.todo.model.Task;
import com.fasterxml.jackson.databind.ObjectMapper;

/** Test that the serialize/deserialize annotations are working */
public class JacksonSerDeserTest {

	Task start;

	@Before
	public void init() {
		start = new Task("Get up in the morning");
		start.setServerId(1);
		start.setCreationDate(LocalDate.of(2010,11,12));
	}

	@Test
	public void testDeSerialize() throws Exception {
		String json = "{\"id\":1, \"name\" : \"Get up in the morning\"," +
			"\"creationDate\": \"2010-11-12\"}";
		Task task = new ObjectMapper().readValue(json, Task.class);
		assertEquals("creation date survived", 
			LocalDate.of(2010,11,12), task.getCreationDate());
	}

	@Test
	public void testSerialize() throws Exception {
		Writer w = new StringWriter();
		new ObjectMapper().writeValue(w, start);
		String json = w.toString();
		System.out.println(json);
	}

}
