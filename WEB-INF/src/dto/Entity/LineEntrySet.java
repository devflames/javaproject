package dto.Entity;

import java.sql.Timestamp;

public class LineEntrySet {

	// メンバー宣言
	private int sid;
	private int line_account_id;
	private int tag_id;
	private int entry_type;
	private Timestamp create_date;
	private Timestamp update_date;

	private String tag_name;
	private int folder_id;


	// sid
	public int getSid() { return sid; }
	public void setSid(int sid) { this.sid = sid; }

	// line_account_id
	public int getLine_account_id() { return line_account_id; }
	public void setLine_account_id(int line_account_id) { this.line_account_id = line_account_id; }

	// tag_id
	public int getTag_id() { return tag_id; }
	public void setTag_id(int tag_id) { this.tag_id = tag_id; }

	// entry_type
	public int getEntry_type() { return entry_type;}
	public void setEntry_type(int entry_type) { this.entry_type = entry_type; }

	// create_date
	public Timestamp getCreate_date() { return create_date; }
	public void setCreate_date(Timestamp create_date) { this.create_date = create_date; }

	// update_date
	public Timestamp getUpdate_date() { return update_date; }
	public void setUpdate_date(Timestamp update_date) { this.update_date = update_date; }

	// tag_name
	public String getTag_name() { return tag_name; }
	public void setTag_name(String tag_name) { this.tag_name = tag_name; }

	// folder_id
	public int getFolder_id() { return folder_id; }
	public void setFolder_id(int folder_id) { this.folder_id = folder_id; }


}
