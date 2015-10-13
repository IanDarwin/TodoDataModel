package com.darwinsys.todo.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.darwinsys.todo.model.Context;
import com.darwinsys.todo.model.Date;
import com.darwinsys.todo.model.Project;
import com.darwinsys.todo.model.Task;

public class TaskTest {
	
	private static final String GET_THE_LEAD_OUT = "Get the lead out";
	private static final char SPACE = ' ';
	private static final Project PROJECT = new Project("Plumbing");
	private static final Context CONTEXT = new Context("Home");
	Task t = new Task();
	String today = new Date().toString();
	
	@Test
	public void testConstructor() {
		assertNotNull("constructed ok", t.getCreationDate());
	}
	
	@Test
	public void testSetCompletedSetsCompletionDate() {
		t.complete();
		assertNotNull("completion goo", t.getCompletedDate());
	}
	
	@Test
	public void testSetNotCompletedSetsCompletionDateNull() {
		t.complete();
		assertNotNull("completion true", t.getCompletedDate());
		t.active();
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
		t.complete();
		assertEquals("toString", "x" + SPACE + today + SPACE + today + ' ' + GET_THE_LEAD_OUT, t.toString());
	}
}
