package dto.Entity.LineAPI.Message;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Consumption {

	public int totalUsage;


	// totalUsage
	public int getTotalUsage() { return totalUsage; }
	public void setTotalUsage(int totalUsage) { this.totalUsage = totalUsage; }

	public static Consumption loadJson(String jsonText) throws JsonParseException, JsonMappingException, IOException {

		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(jsonText, Consumption.class);
	}
}
