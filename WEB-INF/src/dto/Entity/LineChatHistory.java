package dto.Entity;

import java.sql.Timestamp;

public class LineChatHistory {

	// メンバー宣言
	private int chat_id;
	private int line_account_id;
	private int friend_id;
	private String line_id;
	private String chat;
	private int direction;
	private int is_read;
	private Timestamp create_date;
	private Timestamp update_date;


	// chat_id
	public int getChat_id() { return chat_id; }
	public void setChat_id(int chat_id) { this.chat_id = chat_id; }

	// line_account_idを取得します。
	public int getLine_account_id() { return line_account_id; }
	public void setLine_account_id(int line_account_id) { this.line_account_id = line_account_id; }

	// friend_id
	public int getFriend_id() { return friend_id; }
	public void setFriend_id(int friend_id) { this.friend_id = friend_id; }

	// line_idを取得します。
	public String getLine_id() { return line_id; }
	public void setLine_id(String line_id) { this.line_id = line_id; }

	// chat
	public String getChat() { return chat; }
	public void setChat(String chat) { this.chat = chat; }

	// direction
	public int getDirection() { return direction; }
	public void setDirection(int direction) { this.direction = direction; }

	// is_read
	public int getIs_read() { return is_read; }
	public void setIs_read(int is_read) { this.is_read = is_read; }

	// create_date
	public Timestamp getCreate_date() { return create_date; }
	public void setCreate_date(Timestamp create_date) { this.create_date = create_date; }

	// update_date
	public Timestamp getUpdate_date() { return update_date; }
	public void setUpdate_date(Timestamp update_date) { this.update_date = update_date; }


}
