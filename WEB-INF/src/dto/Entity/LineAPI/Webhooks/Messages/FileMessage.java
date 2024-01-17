package dto.Entity.LineAPI.Webhooks.Messages;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dto.Entity.LineAPI.Webhooks.Events.MessageEvent;
@JsonTypeInfo(
		use = JsonTypeInfo.Id.NAME,
		include = JsonTypeInfo.As.PROPERTY,
		property = "type",
		visible = true
)
@JsonTypeName("file")
public class FileMessage extends MessageEvent {

	private String id;
	private String fileName;
	private String fileSize;

	public String getId() { return id; }
	public void setId(String id) { this.id = id; }

	// fileName
	public String getFileName() { return fileName; }
	public void setFileName(String fileName) { this.fileName = fileName; }

	// fileSize
	public String getFileSize() { return fileSize; }
	public void setFileSize(String fileSize) { this.fileSize = fileSize; }

	public static FileMessage loadJson(String jsonText) throws JsonParseException, JsonMappingException, IOException {

		//ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(jsonText, FileMessage.class);
	}

}