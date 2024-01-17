package dto.Entity.LineAPI.ServerAPI;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@JsonIgnoreProperties(ignoreUnknown=true)

public class Liff {

	public String liffId;


	// liffIdを取得します。
	public String getLiffId() { return liffId; }
	public void setLiffId(String liffId) { this.liffId = liffId; }

	public static Liff loadJson(String jsonText) throws JsonParseException, JsonMappingException, IOException {

		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(jsonText, Liff.class);
	}

}
