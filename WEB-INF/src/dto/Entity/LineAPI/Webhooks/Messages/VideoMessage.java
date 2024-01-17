package dto.Entity.LineAPI.Webhooks.Messages;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dto.Entity.LineAPI.Webhooks.Events.MessageEvent;
@JsonTypeInfo(
		use = JsonTypeInfo.Id.NAME,
		include = JsonTypeInfo.As.PROPERTY,
		property = "type",
		visible = true
)
@JsonTypeName("video")
public class VideoMessage extends MessageEvent {

	private String id;
	private String originalContentUrl;
	private String previewImageUrl;

	public String getId() { return id; }
	public void setId(String id) { this.id = id; }

	public String getOriginalContentUrl() { return originalContentUrl; }
	public void setOriginalContentUrl(String originalContentUrl) { this.originalContentUrl = originalContentUrl; }

	public String getPreviewImageUrl() { return previewImageUrl; }
	public void setPreviewImageUrl(String previewImageUrl) { this.previewImageUrl = previewImageUrl; }

	public static VideoMessage loadJson(String jsonText) throws JsonParseException, JsonMappingException, IOException {

		//ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(jsonText, VideoMessage.class);
	}
}