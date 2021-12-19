package com.darwinsys.todo.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.darwinsys.todo.model.Import;
import com.darwinsys.todo.model.Priority;
import com.darwinsys.todo.model.Task;

/**
 * Some tests of import functionality.
 */
@RunWith(Parameterized.class)
public class ImportTest {

	final static Object[][] data = new Object[][]  {
		// Expected Task         String used to create Actual Task
		{new Task("Call Mom 1"), "(A) Call Mom 1"},
		{new Task("Call Mom 2"), "Call Mom 2"},
		{new Task("Call Mom 3", null, "Home"),  "Call Mom 3 @Home"},
		{new Task("Call Mom 4", "Family", "Home"),  "Call Mom 4 @Home +Family"},
	};
	static {
		((Task) data[0][0]).setPriority(Priority.Top);
	}
	
	/** This method provides data to the constructor for use in tests */
    @Parameters
    public static List<Object[]> data() {           
            return Arrays.asList(data);                                     
    }
    
    private Task expected;
    private String input;
    
    public ImportTest(Task expected, String input) {
    		this.expected = expected;
    		this.input = input;
    }   

	@Test // Fine-detailed problems with equals method?
	public void testImportTask() {
		Task actual = Import.importTask(input);
		System.out.println("Actual:" + actual);
		assertEquals(expected, actual);
	}
}
