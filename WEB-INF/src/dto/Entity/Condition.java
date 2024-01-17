package dto.Entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Condition {

	// メンバー宣言
	private String display_name;
	private String create_date_start;
	private String create_date_end;
	private int mail_status;
	private String tag;
	private String tag_name;
	private int count;


	// display_name
	public String getDisplay_name() { return display_name;}
	public void setDisplay_name(String display_name) { this.display_name = display_name;}

	// create_date_start
	public String getCreate_date_start() { return create_date_start; }
	public void setCreate_date_start(String create_date_start) { this.create_date_start = create_date_start; }

	// create_date_end
	public String getCreate_date_end() { return create_date_end; }
	public void setCreate_date_end(String create_date_end) { this.create_date_end = create_date_end; }

	// mail_status
	public int getMail_status() { return mail_status; }
	public void setMail_status(int mail_status) { this.mail_status = mail_status; }

	// tag
	public String getTag() { return tag; }
	public void setTag(String tag) { this.tag = tag; }

	// tag_name
	public String getTag_name() { return tag_name; }
	public void setTag_name(String tag_name) { this.tag_name = tag_name; }

	// count
	public int getCount() { return count; }
	public void setCount(int count) { this.count = count; }

	public static String toStringJson(Condition obj) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);
		String jsonstr = mapper.writeValueAsString(obj);
		return jsonstr;
	}



}
