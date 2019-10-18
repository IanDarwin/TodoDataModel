package com.darwinsys.todo.converters;

import java.io.IOException;
import java.time.LocalDate;

import com.darwinsys.todo.model.Priority;
import com.darwinsys.todo.model.Status;
import com.darwinsys.todo.model.Task;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.IntNode;

public class TaskJacksonDeserializer extends StdDeserializer<Task> {
	private static final long serialVersionUID = 1L;

	public TaskJacksonDeserializer() {
		this(null);
	}

	public TaskJacksonDeserializer(Class<?> vc) {
		super(vc);
	}

	@Override
	public Task deserialize(JsonParser jp, DeserializationContext ctxt)
	  throws IOException, JsonProcessingException {
		JsonNode node = jp.getCodec().readTree(jp);
		String name = node.get("name").asText();
		Task t = new Task(name);
		int id = (Integer) ((IntNode) node.get("id")).numberValue();
		t.setServerId(id);
		final JsonNode descrNode = node.get("description");
		String description = descrNode != null ? descrNode.asText() : null;
		LocalDate creation =
				LocalDate.from(TaskJacksonSerializer.DF.parse(node.get("creationDate").asText()));
		t.setCreationDate(creation);
		final JsonNode dueDateNode = node.get("dueDate");
		if (dueDateNode != null) {
			t.setDueDate(LocalDate.from(TaskJacksonSerializer.DF.parse(dueDateNode.asText())));
		}
		final JsonNode completedDateNode = node.get("completedDate");
		if (completedDateNode != null) {
			t.setCompletedDate(LocalDate.from(TaskJacksonSerializer.DF.parse(completedDateNode.asText())));
		}
		t.setDescription(description);

		final JsonNode prio = node.get("priority");
		if (prio != null) {
			t.setPriority(Priority.valueOf(prio.asText()));
		}

		final JsonNode statNode = node.get("status");
		if (statNode != null) {
			t.setStatus(Status.valueOf(statNode.asText()));
		}

		return t;
	}
}
