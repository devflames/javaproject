package dto.Entity.LineAPI.ServerAPI;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@JsonIgnoreProperties(ignoreUnknown=true)

public class ChanelAccessToken {

	public String access_token;
	public int expires_in;
	public String token_type;

	// access_tokenを取得します。
	public String getAccess_token() { return access_token; }
	public void setAccess_token(String access_token) { this.access_token = access_token; }

	// expires_inを取得します。
	public int getExpires_in() { return expires_in; }
	public void setExpires_in(int expires_in) { this.expires_in = expires_in; }

	// token_type
	public String getToken_type() { return token_type; }
	public void setToken_type(String token_type) { this.token_type = token_type;}

	public static ChanelAccessToken loadJson(String jsonText) throws JsonParseException, JsonMappingException, IOException {

		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(jsonText, ChanelAccessToken.class);
	}

}
