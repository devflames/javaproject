package dto.Entity.LineAPI.Webhooks;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import dto.Entity.LineAPI.Webhooks.Events.AccountLinkEvent;
import dto.Entity.LineAPI.Webhooks.Events.FollowEvent;
import dto.Entity.LineAPI.Webhooks.Events.MessageEvent;
import dto.Entity.LineAPI.Webhooks.Events.UnfollowEvent;

@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeInfo(
		use = JsonTypeInfo.Id.NAME,
		include = JsonTypeInfo.As.PROPERTY,
		property = "type",
		visible = true
)
@JsonSubTypes({
	@JsonSubTypes.Type(FollowEvent.class),
	@JsonSubTypes.Type(UnfollowEvent.class),
	@JsonSubTypes.Type(MessageEvent.class),
	@JsonSubTypes.Type(AccountLinkEvent.class)
})

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public abstract class Event {


	private String type;
	private String mode;
	private Source source;
	@JsonIgnore
	private long timestamp;
	private MessageEvent message;

	public String getType() { return type; }
	public void setType(String type) { this.type = type; }

	public String getMode() { return mode;}
	public void setMode(String mode) { this.mode = mode; }

	public Source getSource() { return source; }
	public void setSource(Source source) { this.source = source; }

	public MessageEvent getMessage() { return message; }
	public void setMessage(MessageEvent message) {  this.message = message; }

	public long getTimestamp() { return timestamp; }
	public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
}
