package com.darwinsys.todo.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.darwinsys.todo.model.Date;
import com.darwinsys.todo.model.Task;

public class TaskTest {
	
	private static final String GET_THE_LEAD_OUT = "Get the lead out";
	private static final char SPACE = ' ';
	private static final String PROJECT = "Plumbing";
	private static final String CONTEXT = "Home";
	Task t = new Task();
	String today = new Date().toString();
	
	@Test
	public void testConstructor() {
		assertNotNull("constructed ok", t.getCreationDate());
	}
	
	@Test
	public void testSetCompletedSetsCompletionDate() {
		t.setComplete(true);
		assertNotNull("completion goo", t.getCompletedDate());
	}
	
	@Test
	public void testSetNotCompletedSetsCompletionDateNull() {
		t.setComplete(true);
		assertNotNull("completion true", t.getCompletedDate());
		t.setComplete(false);
		assertNull("completion false", t.getCompletedDate());
	}

	@Test
	public void testSimpleToString() {
		t.setName(GET_THE_LEAD_OUT);
		assertEquals("toString", today + ' ' + GET_THE_LEAD_OUT, t.toString());
	}

	@Test
	public void testComplexToString() {
		t.setName(GET_THE_LEAD_OUT);
		t.setContext(CONTEXT);
		t.setProject(PROJECT);
		assertEquals("toString", today + SPACE + "Get the lead out" + SPACE + "+" + PROJECT + ' ' + "@" + CONTEXT, 
				t.toString());
	}
	
	@Test
	public void testCompleteToString() {
		t.setName(GET_THE_LEAD_OUT);
		t.setComplete(true);
		assertEquals("toString", "x" + SPACE + today + SPACE + today + ' ' + GET_THE_LEAD_OUT, t.toString());
	}
}
