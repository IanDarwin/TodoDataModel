package com.darwinsys.todo.test;

import javax.persistence.*;

import com.darwinsys.todo.model.Task;

import org.junit.*;
import static org.junit.Assert.*;

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
		long id = task.getId();
		Task t2 = entityManager.find(Task.class, id);
		assertEquals(task, t2);
	}
}
