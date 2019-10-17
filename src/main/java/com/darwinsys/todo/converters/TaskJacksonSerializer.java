package com.darwinsys.todo.converters;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

import com.darwinsys.todo.model.Task;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class TaskJacksonSerializer extends StdSerializer<Task> {
	private static final long serialVersionUID = 1L;
	
	static DateTimeFormatter DF = DateTimeFormatter.ofPattern("yyyy-LL-dd");
     
    public TaskJacksonSerializer() {
        this(null);
    }
   
    public TaskJacksonSerializer(Class<Task> t) {
        super(t);
    }
 
    @Override
    public void serialize(
      Task value, JsonGenerator jgen, SerializerProvider provider) 
      throws IOException, JsonProcessingException {
  
        jgen.writeStartObject();
        jgen.writeNumberField("id", value.getServerId());
        jgen.writeStringField("description", value.getDescription());
		jgen.writeStringField("creationDate", value.getCreationDate() != null ?
			value.getCreationDate().format(DF) : "null");
        jgen.writeEndObject();
    }
}
