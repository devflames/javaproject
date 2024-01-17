package dto.Entity;

import java.sql.Timestamp;

public class LineQueLog {

	// メンバー宣言
	private int id;
	private int que_id;
	private int line_account_id;
	private String account;
	private int friend_id;
	private String display_name;
	private int status;
	private String error_message;
	private Timestamp create_date;
	private Timestamp update_date;
	private int resend_flg;

	// ID
	public int getId() { return id; }
	public void setId(int id) { this.id = id; }

	// 配信ID
	public int getQue_id() { return que_id; }
	public void setQue_id(int que_id) { this.que_id = que_id; }

	// LINEアカウントID
	public int getLine_account_id() { return line_account_id; }
	public void setLine_account_id(int line_account_id) { this.line_account_id = line_account_id; }

	// アカウントID
	public String getAccount() { return account; }
	public void setAccount(String account) { this.account = account; }

	// フレンドID
	public int getFriend_id() { return friend_id; }
	public void setFriend_id(int friend_id) { this.friend_id = friend_id; }

	// 友だち名
	public String getDisplay_name() { return display_name; }
	public void setDisplay_name(String display_name) { this.display_name = display_name; }

	// ステータス
	public int getStatus() { return status; }
	public void setStatus(int status) { this.status = status; }

	// エラーメッセージ
	public String getError_message() { return error_message; }
	public void setError_message(String error_message) { this.error_message = error_message; }

	// 作成日時 / エラー発生日時
	public Timestamp getCreate_date() { return create_date; }
	public void setCreate_date(Timestamp create_date) { this.create_date = create_date; }

	// 最終更新日時
	public Timestamp getUpdate_date() { return update_date; }
	public void setUpdate_date(Timestamp update_date) { this.update_date = update_date; }

	// 再送フラグ
	public int getResend_flg() { return resend_flg; }
	public void setResend_flg(int resend_flg) { this.resend_flg = resend_flg; }

}
