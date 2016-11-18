package com.darwinsys.todo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Code to import plain-text ToDo files into our Todo model.
 */
public class Import {
	
	public static final Pattern REGEX = Pattern.compile(
			"(x)?(\\s\\d{4}\\-\\d{2}\\-\\d{2})?" + // Completion flag, completion date+
			"\\s?" +
			"(\\([A-Z]\\))?\\s*(\\d{4}-\\d{2}-\\d{2}\\s+)?" +		// PRIORITY, CreationDate
			"(.*(\\+\\w+)|(@\\w+)*.*)",			// name, optional +Project, @Context in either order anywhere	
			Pattern.COMMENTS);
	
	final static int GROUP_COMPLETED = 1;
	final static int GROUP_COMPL_DATE = 2;
	final static int GROUP_PRIO = 3;
	final static int GROUP_CREATION_DATE = 4;
	final static int GROUP_REST = 5;

	public static List<Task> importTasks(List<String> input) {
		List<Task> list = new ArrayList<Task>();
		for (String s : input) {
			list.add(importTask(s));
		}
		return list;
	}
	
	public static Task importTask(String str) {
		Matcher m = REGEX.matcher(str);
		Task t = new Task();
		if (m.matches()) {

			String completed = m.group(GROUP_COMPLETED);
			if (completed != null && completed.startsWith("x")) {
				t.complete();
			}
			String prio = m.group(GROUP_PRIO);
			if (prio != null && prio.startsWith("(")) {
				String prioString = prio.substring(1);
				prioString = prioString.substring(0, prioString.length() - 1);
				if (prioString.length() == 1) {
					switch(prioString.charAt(0)) {
						case 'a': case 'A':
							t.setPriority(Priority.Top);
							break;
						default:
							throw new IllegalStateException("not coded yet");
					}
				} else {
					t.setPriority(Priority.valueOf(prioString));
				}
			}
			t.setName(m.group(GROUP_REST));
			return t;
		} else {
			throw new IllegalArgumentException("Task failed to parse: " + str);
		}
	}
}
