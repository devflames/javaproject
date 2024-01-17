package dto.Entity;

import java.sql.Timestamp;

public class LineTag {

	// メンバー宣言
	private int tag_id;
	private int line_account_id;
	private int parent_folder_id;
	private String tag_name;
	private int sort_no;
	private Timestamp create_date;
	private Timestamp update_date;


	// tag_id
	public int getTag_id() { return tag_id; }
	public void setTag_id(int tag_id) { this.tag_id = tag_id; }

	// line_account_id
	public int getLine_account_id() { return line_account_id; }
	public void setLine_account_id(int line_account_id) { this.line_account_id = line_account_id; }

	// parent_folder_id
	public int getParent_folder_id() { return parent_folder_id; }
	public void setParent_folder_id(int parent_folder_id) { this.parent_folder_id = parent_folder_id; }

	// tag_name
	public String getTag_name() { return tag_name; }
	public void setTag_name(String tag_name) { this.tag_name = tag_name; }

	// sort_no
	public int getSort_no() { return sort_no; }
	public void setSort_no(int sort_no) { this.sort_no = sort_no; }

	// create_date
	public Timestamp getCreate_date() { return create_date; }
	public void setCreate_date(Timestamp create_date) { this.create_date = create_date; }

	// update_date
	public Timestamp getUpdate_date() { return update_date; }
	public void setUpdate_date(Timestamp update_date) { this.update_date = update_date; }



}
