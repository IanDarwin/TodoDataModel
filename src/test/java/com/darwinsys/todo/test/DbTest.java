package com.darwinsys.todo.test;

import static org.junit.Assert.assertEquals;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.darwinsys.todo.model.Task;

/** Simple test of database access */
public class DbTest {
	private static EntityManagerFactory entityManagerFactory;
	private EntityManager entityManager;

	@BeforeClass
	public static void setupEMF() {
		entityManagerFactory = Persistence.createEntityManagerFactory("todo");
	}

	@Before
	public void setupEM() {
		entityManager = entityManagerFactory.createEntityManager();
	}

	@Test
	public void testSaveLoad() {
		Task task = new Task("Finish testing and release product");
		entityManager.getTransaction().begin();
		entityManager.persist(task);
		entityManager.getTransaction().commit();
		long id = task.getServerId();
		Task t2 = entityManager.find(Task.class, id);
		assertEquals(task, t2);
	}

	@Test
	public void testSaveLoadWithSubtask() {
		Task task = new Task("Finish testing and release product");
		Task subTask = new Task("Add more tests!");
		task.addSubTask(subTask);
		entityManager.getTransaction().begin();
		entityManager.persist(task);
		entityManager.getTransaction().commit();
		long id = task.getServerId();
		Task t2 = entityManager.find(Task.class, id);
		assertEquals(task, t2);
		assertEquals(1, t2.getSubTasks().size());
	}
}
