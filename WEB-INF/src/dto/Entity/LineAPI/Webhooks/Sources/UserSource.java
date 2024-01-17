package dto.Entity.LineAPI.Webhooks.Sources;

import com.fasterxml.jackson.annotation.JsonTypeName;

import dto.Entity.LineAPI.Webhooks.Source;

@JsonTypeName("user")
public class UserSource extends Source {

	private String type;
	private String userId;

	public String getType() { return type; }
	public void setType(String type) { this.type = type; }

	public String getUserId() { return userId; }
	public void setUserId(String userId) { this.userId = userId; }
}
