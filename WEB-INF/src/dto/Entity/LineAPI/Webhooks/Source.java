package dto.Entity.LineAPI.Webhooks;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import dto.Entity.LineAPI.Webhooks.Sources.GroupSource;
import dto.Entity.LineAPI.Webhooks.Sources.RoomSource;
import dto.Entity.LineAPI.Webhooks.Sources.UserSource;

@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeInfo(
		use = JsonTypeInfo.Id.NAME,
		include = JsonTypeInfo.As.PROPERTY,
		property = "type",
		visible = true
	)
@JsonSubTypes({
	@JsonSubTypes.Type(UserSource.class),
	@JsonSubTypes.Type(GroupSource.class),
	@JsonSubTypes.Type(RoomSource.class)
})
public abstract class Source {

	private String type;
	private String userId;

	public String getType() { return type; }
	public void setType(String type) { this.type = type;}

	public String getUserId() { return userId;}
	public void setUserId(String userId) { this.userId = userId;}

}
