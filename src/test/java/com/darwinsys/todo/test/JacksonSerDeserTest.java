package com.darwinsys.todo.test;

import com.darwinsys.todo.model.*;
import org.junit.*;
import static org.junit.Assert.*;

/** Test that the serialize/deserialize annotations are working */
public class JacksonSerDeserTest {

	Task start;

	@Setup
	public void init() {
		start = new Task(1, "Get up in the morning");
		start.setCreationDate(LocalDate.of(2010,11,12));
	}

	@Test
	public void testDeSerialize() {
		String json = "{\"id\":1, \"name\" : \"Get up in the morning\"," +
			"creationDate\": \"2010-11-12\"}";
		Task task = new ObjectMapper().readValue(json, Task.class);
		assertEquals("creation date survived", 
			LocalDate.of(2010,11,12), task.getCreationDate());
	}

	@Test
	public void testSerialize() {



}
