package dto.Entity.LineAPI.Webhooks;

import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@JsonSubTypes({
	@JsonSubTypes.Type(Event.class)
})
public class Webhook {

	public String destination;
	public ArrayList<Event> events;

	public Webhook() {
		this.events = new ArrayList<Event>();
	}

	public static Webhook loadJson(String jsonText) throws JsonParseException, JsonMappingException, IOException {

		//ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		ObjectMapper mapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);
		return mapper.readValue(jsonText, Webhook.class);
	}

}
