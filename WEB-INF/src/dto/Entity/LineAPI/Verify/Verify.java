package dto.Entity.LineAPI.Verify;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Verify {

	public String scope;
	public String client_id;
	public long expires_in;

	//scope
	public String getScope() { return scope;}
	public void setScope(String scope) { this.scope = scope; }

	// client_id
	public String getClient_id() { return client_id; }
	public void setClient_id(String client_id) { this.client_id = client_id; }

	// expires_in
	public long getExpires_in() { return expires_in; }
	public void setExpires_in(long expires_in) { this.expires_in = expires_in; }

	public static Verify loadJson(String jsonText) throws JsonParseException, JsonMappingException, IOException {

		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(jsonText, Verify.class);
	}
}
