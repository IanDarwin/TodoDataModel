package com.darwinsys.todo.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import com.darwinsys.todo.model.Context;
import com.darwinsys.todo.model.Priority;
import com.darwinsys.todo.model.Project;
import com.darwinsys.todo.model.Status;
import com.darwinsys.todo.model.Task;

/**
 * Tests for Task itself.
 */
public class TaskTest {
	
	private static final String GET_THE_LEAD_OUT = "Get the lead out";
	private static final char SPACE = ' ';
	private static final Project PROJECT = new Project("Plumbing");
	private static final Context CONTEXT = new Context("Home");
	String today = LocalDate.now().toString();
	Task t;
	
	@Before
	public void setup() {
		t = new Task();
	}
	
	@Test
	public void testConstructor() {
		assertNotNull("constructed ok", t.getCreationDate());
	}
	
	@Test
	public void testSetCompletedSetsCompletionDate() {
		t.complete();
		assertEquals(Status.COMPLETE, t.getStatus());
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
		assertEquals("toString", "(A) " + today + ' ' + GET_THE_LEAD_OUT, t.toString());
	}

	@Test
	public void testComplexToString() {
		t.setName(GET_THE_LEAD_OUT);
		t.setContext(CONTEXT);
		t.setProject(PROJECT);
		t.setPriority(Priority.High);
		assertEquals("toString", "(B) " + today + SPACE + "Get the lead out" + SPACE + "+" + PROJECT + ' ' + "@" + CONTEXT, 
				t.toString());
	}
	
	@Test
	public void testCompleteToString() {
		t.setName(GET_THE_LEAD_OUT);
		t.complete();
		assertEquals("toString", "x" + SPACE + today + SPACE + "(A) " + today + ' ' + GET_THE_LEAD_OUT, t.toString());
	}
}
