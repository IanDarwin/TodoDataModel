package com.darwinsys.todo.converters;

import java.io.IOException;
import java.time.LocalDate;

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
        int id = (Integer) ((IntNode) node.get("id")).numberValue();
        String name = node.get("name").asText();
        final JsonNode descrNode = node.get("description");
		String description = descrNode != null ? descrNode.asText() : null;
        LocalDate creation = 
        		LocalDate.from(TaskJacksonSerializer.DF.parse(node.get("creationDate").asText()));
 
        Task t = new Task(name);
        t.setServerId(id);
		t.setCreationDate(creation);
		t.setDescription(description);
		return t;
    }
}
