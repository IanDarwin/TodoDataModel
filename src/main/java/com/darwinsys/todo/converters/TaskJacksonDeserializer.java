package com.darwinsys.todo.converters;

import java.time.LocalDate;

import com.darwinsys.todo.model.Task;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class TaskJacksonDeserializer extends StdDeserializer<Task> { 
 
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
        String description = node.get("description").asText();
        LocalDate creation = TaskSerializer.DF.parse(node.get("creationDate").asText());
 
        Task t = new Task(name, description);
		t.setCreationDate(creation);
		t.setDescription(description);
		return t;
    }
}
