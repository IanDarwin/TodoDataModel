package com.darwinsys.todo.converters;

import com.darwinsys.todo.model.Import;
import com.darwinsys.todo.model.Task;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class Txt2ToodleDo {

	public static void main(String[] args) throws Exception {
		final String fileName = "/home/ian/TODO.txt";
		List<Task> tasks =
				Files.lines(Path.of(fileName)).map(Import::importTask).collect(Collectors.toList());
		final List<String> exportedTasks = new ExportText().export(tasks);
		exportedTasks.forEach(System.out::println);
	}
}
