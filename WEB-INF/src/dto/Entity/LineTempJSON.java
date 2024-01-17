package dto.Entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LineTempJSON {

	// メンバー宣言
	private int temp_id;


	// temp_id
	public int getTemp_id() { return temp_id; }
	public void setTemp_id(int temp_id) { this.temp_id = temp_id; }


	public static String toStringJson(LineTempJSON obj) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);
		String jsonstr = mapper.writeValueAsString(obj);
		return jsonstr;
	}


}
