package dto.Entity;

import java.sql.Timestamp;

public class LineAttribute {

	// メンバー宣言
	private int attribute_id;
	private int line_account_id;
	private int friend_id;
	private int folder_id;
	private int tag_id;
	private Timestamp create_date;
	private Timestamp update_date;

	private String friend_id_list;


	// attribute_id
	public int getAttribute_id() { return attribute_id; }
	public void setAttribute_id(int attribute_id) { this.attribute_id = attribute_id; }

	// line_account_id
	public int getLine_account_id() { return line_account_id; }
	public void setLine_account_id(int line_account_id) { this.line_account_id = line_account_id; }

	// friend_id
	public int getFriend_id() { return friend_id; }
	public void setFriend_id(int friend_id) { this.friend_id = friend_id; }

	// folder_id
	public int getFolder_id() { return folder_id; }
	public void setFolder_id(int folder_id) { this.folder_id = folder_id; }

	// tag_id
	public int getTag_id() { return tag_id; }
	public void setTag_id(int tag_id) { this.tag_id = tag_id; }

	// create_date
	public Timestamp getCreate_date() { return create_date; }
	public void setCreate_date(Timestamp create_date) { this.create_date = create_date; }

	// update_date
	public Timestamp getUpdate_date() { return update_date; }
	public void setUpdate_date(Timestamp update_date) { this.update_date = update_date; }

	// friend_id_list カンマ区切り
	public String getFriend_id_list() { return friend_id_list; }
	public void setFriend_id_list(String friend_id_list) { this.friend_id_list = friend_id_list; }


}
