package dto.Entity.LineAPI.Webhooks.Events;

import com.fasterxml.jackson.annotation.JsonTypeName;

import dto.Entity.LineAPI.Webhooks.Event;

@JsonTypeName("accountLink")
public class AccountLinkEvent extends Event {


	private Link link;
	private String replyToken;
	private String mode;

	// link
	public Link getLink() { return link; }
	public void setLink(Link link) { this.link = link; }

	// replyToken
	public String getReplyToken() { return replyToken; }
	public void setReplyToken(String replyToken) { this.replyToken = replyToken; }

	// mode
	public String getMode() { return mode; }
	public void setMode(String mode) { this.mode = mode; }

	public class Link {

		private String result;
		private String nonce;

		public String getResult() { return result; }
		public void setResult(String result) { this.result = result; }

		public String getNonce() { return nonce; }
		public void setNonce(String nonce) { this.nonce = nonce; }
	}

}
