package com.darwinsys.todo.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Code to import plain-text ToDo files into our Todo model.
 */
public class Import {

	// XXX This isn't picking up the @Context or +Project
	public static final Pattern REGEX = Pattern.compile(
			"(x\\s*)?(\\([A-Z]\\)\\s*)?" + // Completion flag, Priority,
			"(\\d{4}-\\d{2}-\\d{2}\\s*){0,2}" +		// PRIORITY, 0-2 dates
			"(.*)",			// name, optional +Project, @Context in either order anywhere
			Pattern.COMMENTS);
	
	final static int GROUP_COMPLETED = 1;
	final static int GROUP_PRIO = 2;
	final static int GROUP_1OR2_DATES = 3;
	final static int GROUP_REST = 4;

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
			if (prio != null)
				if (prio.startsWith("(")) {
					switch (prio.charAt(1)) {
						case 'a':
						case 'A':
							t.setPriority(Priority.Top);
							break;
						default:
							throw new IllegalStateException("not coded yet");
					}
				} else { // Keyword given?
					t.setPriority(Priority.valueOf(prio));
				}
			String dates = m.group(GROUP_1OR2_DATES);
			// XXX Must handle 2 dates
			if (dates != null) {
				LocalDate localDate = LocalDate.parse(dates);
				t.setCompletedDate(localDate);
			}
			String rest = m.group(GROUP_REST);
			StringBuilder nameSB = new StringBuilder(),
					projectSB = new StringBuilder(),
					contextSB = new StringBuilder();
			for (int i = 0; i < rest.length(); i++) {
				char ch = rest.charAt(i);
				if (ch == '+') {
					do {
						projectSB.append(rest.charAt(++i));
					} while (i + 1 < rest.length() && !Character.isWhitespace(rest.charAt(i + 1)));
				} else if (ch == '@') {
					do {
						contextSB.append(rest.charAt(++i));
					} while (i + 1 < rest.length() && !Character.isWhitespace(rest.charAt(i + 1)));
				} else {
					nameSB.append(ch);
				}
			}
			t.setName(nameSB.toString().trim());
			if (projectSB.length() > 0) {
				t.setProject(new Project(projectSB.toString()));
			}
			if (contextSB.length() > 0) {
				t.setContext(new Context(contextSB.toString()));
			}
			return t;
		} else {
			throw new IllegalArgumentException("Task failed to parse: " + str);
		}
	}
}
