package dto.Entity;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;

@JsonPropertyOrder({"value", "id", "category"})
public class LineFolder {

	// メンバー宣言
	private int folder_id;
	private int line_account_id;

	@JsonProperty("category")
	private String folder_name;
	private int sort_no;
	private int folder_sort_no;
	private int tag_sort_no;
	private Timestamp create_date;
	private Timestamp update_date;

	@JsonProperty("id")
	private int tag_id;

	@JsonProperty("value")
	private String tag_name;

	// folder_id
	@JsonIgnore
	public int getFolder_id() { return folder_id; }
	public void setFolder_id(int folder_id) { this.folder_id = folder_id; }

	// line_account_id
	@JsonIgnore
	public int getLine_account_id() { return line_account_id; }
	public void setLine_account_id(int line_account_id) { this.line_account_id = line_account_id; }

	// folder_name
	public String getFolder_name() { return folder_name; }
	public void setFolder_name(String folder_name) { this.folder_name = folder_name; }

	// sort_no
	@JsonIgnore
	public int getSort_no() { return sort_no; }
	public void setSort_no(int sort_no) { this.sort_no = sort_no; }

	// folder_sort_no
	@JsonIgnore
	public int getFolder_sort_no() { return folder_sort_no; }
	public void setFolder_sort_no(int folder_sort_no) { this.folder_sort_no = folder_sort_no; }

	// tag_sort_no
	@JsonIgnore
	public int getTag_sort_no() { return tag_sort_no; }
	public void setTag_sort_no(int tag_sort_no) { this.tag_sort_no = tag_sort_no; }

	// create_date
	@JsonIgnore
	public Timestamp getCreate_date() { return create_date; }
	public void setCreate_date(Timestamp create_date) { this.create_date = create_date; }

	// update_date
	@JsonIgnore
	public Timestamp getUpdate_date() { return update_date; }
	public void setUpdate_date(Timestamp update_date) { this.update_date = update_date; }

	// tag_id
	public int getTag_id() { return tag_id; }
	public void setTag_id(int tag_id) { this.tag_id = tag_id; }

	// tag_name
	public String getTag_name() { return tag_name; }
	public void setTag_name(String tag_name) { this.tag_name = tag_name; }

	public static List<LineFolder> loadJson(String jsonText) throws JsonParseException, JsonMappingException, IOException {

		ObjectMapper mapper = new ObjectMapper();
		TypeFactory typeFactory = mapper.getTypeFactory();
		CollectionType collectionType = typeFactory.constructCollectionType(List.class, LineFolder.class);
		return (List<LineFolder>) mapper.readValue(jsonText, collectionType);
	}

	public static String toStringJson(LineFolder obj) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);
		String jsonstr = mapper.writeValueAsString(obj);
		return jsonstr;
	}

	// タグIDを含むかの判定
	public boolean containTag(ArrayList arr,int target_id){

		if( arr.contains(target_id) ){
			return true;
		} else {
			return false;
		}
	}


}
