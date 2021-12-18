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
		assertEquals("toString", "Task[" + GET_THE_LEAD_OUT + " (A) " + today + ']', t.toString());
	}

	@Test
	public void testSubtasks() {
		t.setName(GET_THE_LEAD_OUT);
		t.addSubTask(new Task("Test Subtask"));
		System.out.println("Task is " + t.toString());
		assertEquals(1, t.getSubTasks().size());
	}

	@Test
	public void testComplexToString() {
		t.setName(GET_THE_LEAD_OUT);
		t.setContext(CONTEXT);
		t.setProject(PROJECT);
		t.setPriority(Priority.High);
		assertEquals("toString", "Task[" + GET_THE_LEAD_OUT + " (B) " + today + SPACE + "+" + PROJECT + ' ' + "@" + CONTEXT + ']',
				t.toString());
	}
	
	@Test
	public void testCompletedToString() {
		t.setName(GET_THE_LEAD_OUT);
		t.complete();
		assertEquals("toString", "Task[" + GET_THE_LEAD_OUT + ' ' + 'x' + SPACE + today + SPACE + "(A) " + today + ']', t.toString());
	}
}
