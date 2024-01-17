package dto.Entity.LineAPI.Profile;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@JsonIgnoreProperties(ignoreUnknown=true)

public class Profile {

	public String userId;
	public String displayName;
	public String pictureUrl;
	public String statusMessage;

	// userId
	public String getUserId() { return userId; }
	public void setUserId(String userId) { this.userId = userId; }

	// displayName
	public String getDisplayName() { return displayName; }
	public void setDisplayName(String displayName) { this.displayName = displayName; }

	// pictureUrl
	public String getPictureUrl() { return pictureUrl; }
	public void setPictureUrl(String pictureUrl) { this.pictureUrl = pictureUrl; }

	// statusMessae
	public String getStatusMessage() { return statusMessage; }
	public void setStatusMessage(String statusMessage) { this.statusMessage = statusMessage; }

	public static Profile loadJson(String jsonText) throws JsonParseException, JsonMappingException, IOException {

		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(jsonText, Profile.class);
	}
}
