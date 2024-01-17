package dto.Entity;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LineBot {

	// メンバー宣言
	private String userId;
	private String basicId;
	private String premiumId;
	private String displayName;
	private String pictureUrl;
	private String chatMode;
	private String markAsReadMode;

	// userId
	public String getUserId() { return userId; }
	public void setUserId(String userId) { this.userId = userId; }

	// basicId
	public String getBasicId() { return basicId; }
	public void setBasicId(String basicId) { this.basicId = basicId; }

	// premiumId
	public String getPremiumId() { return premiumId; }
	public void setPremiumId(String premiumId) { this.premiumId = premiumId; }

	// displayName
	public String getDisplayName() { return displayName; }
	public void setDisplayName(String displayName) { this.displayName = displayName; }

	// pictureUrl
	public String getPictureUrl() { return pictureUrl; }
	public void setPictureUrl(String pictureUrl) { this.pictureUrl = pictureUrl; }

	// chatMode
	public String getChatMode() { return chatMode; }
	public void setChatMode(String chatMode) { this.chatMode = chatMode; }

	// markAsReadMode
	public String getMarkAsReadMode() { return markAsReadMode; }
	public void setMarkAsReadMode(String markAsReadMode) { this.markAsReadMode = markAsReadMode; }

	public static LineBot loadJson(String jsonText) throws JsonParseException, JsonMappingException, IOException {

		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(jsonText, LineBot.class);
	}


}
