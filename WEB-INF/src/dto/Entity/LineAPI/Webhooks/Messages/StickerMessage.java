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
@JsonTypeName("sticker")
public class StickerMessage extends MessageEvent {

	private String id;
	private String packageId;
	private String stickerId;

	public String getId() { return id; }
	public void setId(String id) { this.id = id; }

	public String getPackageId() { return packageId; }
	public void setPackageId(String packageId) { this.packageId = packageId; }

	public String getStickerId() { return stickerId; }
	public void setStickerId(String stickerId) { this.stickerId = stickerId; }

	public static StickerMessage loadJson(String jsonText) throws JsonParseException, JsonMappingException, IOException {

		//ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(jsonText, StickerMessage.class);
	}
}