package dto.Entity;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class InfoLineWebhook {

	// メンバー宣言
	private String endpoint;
	private boolean active;

	// webhook URL
	public String getEndpoint() { return endpoint;}
	public void setEndpoint(String endpoint) { this.endpoint = endpoint; }

	// Webhookの利用状態
	public boolean isActive() { return active; }
	public void setActive(boolean active) { this.active = active; }

	public static InfoLineWebhook loadJson(String jsonText) throws JsonParseException, JsonMappingException, IOException {

		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(jsonText, InfoLineWebhook.class);
	}

}
