package dto.Entity.LineAPI.Webhooks.Events;

import com.fasterxml.jackson.annotation.*;

import dto.Entity.LineAPI.Webhooks.*;

@JsonTypeName("postback")
public class PostbackEvent extends Event {

	private String replyToken;
	private Postback postback;

	public String getReplyToken() { return replyToken; }
	public void setReplyToken(String replyToken) { this.replyToken = replyToken; }

	public Postback getPostback() { return postback; }
	public void setPostback(Postback postback) { this.postback = postback; }

	public class Postback {

		private String data;
		private String params;

		public String getData() { return data; }
		public void setData(String data) { this.data = data; }

		public String getParams() { return params; }
		public void setParams(String params) { this.params = params; }
	}
}
