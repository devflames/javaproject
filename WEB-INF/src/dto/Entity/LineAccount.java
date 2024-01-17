package dto.Entity;

import java.io.IOException;
import java.sql.Timestamp;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LineAccount {

	// メンバー宣言
	private int line_account_id;
	private String account_name;
	private int admin_use;
	private String basic_id;
	private String chanel_id;
	private String chanel_secret;
	private String chanel_access_token;
	private String liff_id;
	private int limit;
	private String limit_notify;
	private int free_flg;
	private int send_limit;
	private String login_chanel_id;
	private String login_chanel_secret;
	private Timestamp create_date;
	private Timestamp update_date;
	private int del_flg;

	// LINEアカウントID
	public int getLine_account_id() { return line_account_id; }
	public void setLine_account_id(int line_account_id) { this.line_account_id = line_account_id; }

	// アカウント名
	public String getAccount_name() { return account_name; }
	public void setAccount_name(String account_name) { this.account_name = account_name; }

	// admin_use
	public int getAdmin_use() { return admin_use; }
	public void setAdmin_use(int admin_use) { this.admin_use = admin_use; }

	// ベーシックID
	public String getBasic_id() { return basic_id; }
	public void setBasic_id(String basic_id) { this.basic_id = basic_id; }

	// Messaging API チャンネルID
	public String getChanel_id() { return chanel_id; }
	public void setChanel_id(String chanel_id) { this.chanel_id = chanel_id; }

	// Messaging API チャネルシークレット
	public String getChanel_secret() { return chanel_secret; }
	public void setChanel_secret(String chanel_secret) { this.chanel_secret = chanel_secret; }

	// Messaging API チャネルアクセストークン
	public String getChanel_access_token() { return chanel_access_token; }
	public void setChanel_access_token(String chanel_access_token) { this.chanel_access_token = chanel_access_token; }

	// LIFF　ID
	public String getLiff_id() { return liff_id; }
	public void setLiff_id(String liff_id) { this.liff_id = liff_id; }

	// 月間無料メッセージ上限数
	public int getLimit() { return limit; }
	public void setLimit(int limit) { this.limit = limit; }

	// limit_notify
	public String getLimit_notify() { return limit_notify; }
	public void setLimit_notify(String limit_notify) { this.limit_notify = limit_notify;}

	// 有料/無料フラグ
	public int getFree_flg() { return free_flg; }
	public void setFree_flg(int free_flg) { this.free_flg = free_flg; }

	// 送信上限フラグ
	public int getSend_limit() { return send_limit; }
	public void setSend_limit(int send_limit) { this.send_limit = send_limit; }

	// LINE　ログイン　チャンネルID
	public String getLogin_chanel_id() { return login_chanel_id; }
	public void setLogin_chanel_id(String login_chanel_id) { this.login_chanel_id = login_chanel_id; }

	// LINE　ログイン　チャンネルシークレット
	public String getLogin_chanel_secret() { return login_chanel_secret; }
	public void setLogin_chanel_secret(String login_chanel_secret) { this.login_chanel_secret = login_chanel_secret; }

	// 作成日時
	public Timestamp getCreate_date() { return create_date; }
	public void setCreate_date(Timestamp create_date) { this.create_date = create_date; }

	// 最終更新日時
	public Timestamp getUpdate_date() {return update_date;}
	public void setUpdate_date(Timestamp update_date) { this.update_date = update_date; }

	// 削除フラグ
	public int getDel_flg() { return del_flg; }
	public void setDel_flg(int del_flg) { this.del_flg = del_flg; }

	public static LineAccount loadJson(String jsonText) throws JsonParseException, JsonMappingException, IOException {

		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(jsonText, LineAccount.class);
	}

}
