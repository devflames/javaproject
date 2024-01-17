

package dto.Entity.LineAPI.Webhooks.Events;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dto.Entity.LineAPI.Webhooks.Event;
import dto.Entity.LineAPI.Webhooks.Messages.AudioMessage;
import dto.Entity.LineAPI.Webhooks.Messages.FileMessage;
import dto.Entity.LineAPI.Webhooks.Messages.ImageMessage;
import dto.Entity.LineAPI.Webhooks.Messages.LocationMessage;
import dto.Entity.LineAPI.Webhooks.Messages.StickerMessage;
import dto.Entity.LineAPI.Webhooks.Messages.TextMessage;
import dto.Entity.LineAPI.Webhooks.Messages.VideoMessage;


@JsonTypeName("message")
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeInfo(
		use = JsonTypeInfo.Id.NAME,
		include = JsonTypeInfo.As.PROPERTY,
		property = "type",
		visible = true
	)
@JsonSubTypes({
	@JsonSubTypes.Type(AudioMessage.class),
	@JsonSubTypes.Type(ImageMessage.class),
	@JsonSubTypes.Type(LocationMessage.class),
	@JsonSubTypes.Type(StickerMessage.class),
	@JsonSubTypes.Type(TextMessage.class),
	@JsonSubTypes.Type(VideoMessage.class),
	@JsonSubTypes.Type(FileMessage.class)
})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageEvent extends Event{
	@JsonBackReference

	private String type;
	private String replyToken;
	//public StickerMessage sticker;


	public String getType() { return type; }
	public void setType(String type) { this.type = type; }

	public String getReplyToken() { return replyToken; }
	public void setReplyToken(String replyToken) { this.replyToken = replyToken; }

	public static MessageEvent loadJson(String jsonText) throws JsonParseException, JsonMappingException, IOException {

		//ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(jsonText, MessageEvent.class);
	}

	public static String toStringJson(MessageEvent message) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);
		String jsonstr = mapper.writeValueAsString(message);
		return jsonstr;
	}



}
