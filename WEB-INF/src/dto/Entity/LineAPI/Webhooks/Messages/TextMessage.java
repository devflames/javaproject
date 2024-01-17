package dto.Entity.LineAPI.Webhooks.Messages;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dto.Entity.LineAPI.Webhooks.Events.MessageEvent;
@JsonTypeInfo(
		use = JsonTypeInfo.Id.NAME,
		include = JsonTypeInfo.As.PROPERTY,
		property = "type",
		visible = true
)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("text")
public class TextMessage extends MessageEvent {

	private String id;
	private String text;

	public String getId() { return id; }
	public void setId(String id) { this.id = id; }

	public String getText() { return text; }
	public void setText(String text) { this.text = text; }

	public static TextMessage loadJson(String jsonText) throws JsonParseException, JsonMappingException, IOException {

		//ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(jsonText, TextMessage.class);
	}

	public static String toStringJson(TextMessage message) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);
		String jsonstr = mapper.writeValueAsString(message);
		return jsonstr;
	}
}