package com.darwinsys.todo.converters;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.darwinsys.todo.model.Task;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class TaskJacksonSerializer extends StdSerializer<Task> {

	static DateTimeFormatter DF = DateTimeFormatter.ofPattern("yyyy/LL/dd");
     
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
        jgen.writeNumberField("id", value.id);
        jgen.writeStringField("description", value.description);
		jgen.writeStringField("creationDate", value.creationDate != null ?
			value.creationDate.format(DF) : "null");
        jgen.writeEndObject();
    }
}
