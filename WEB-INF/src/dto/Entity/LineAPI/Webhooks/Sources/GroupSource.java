package dto.Entity.LineAPI.Webhooks.Sources;

import com.fasterxml.jackson.annotation.*;

import dto.Entity.LineAPI.Webhooks.*;

@JsonTypeName("group")
public class GroupSource extends Source {

	private String groupId;

	public String getGroupId() { return groupId; }
	public void setGroupId(String groupId) { this.groupId = groupId; }
}
