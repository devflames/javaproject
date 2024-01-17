package dto.Entity.LineAPI.Webhooks.Events;

import com.fasterxml.jackson.annotation.*;
import dto.Entity.LineAPI.Webhooks.*;

@JsonTypeName("follow")
public class FollowEvent extends Event {

	private String replyToken;

	public String getReplyToken() { return replyToken; }
	public void setReplyToken(String replyToken) { this.replyToken = replyToken; }
}
