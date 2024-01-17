package dto.Entity;

import java.sql.Timestamp;

public class LineFriend {

	// メンバー宣言
	private int friend_id;
	private int line_account_id;
	private String email;
	private String line_id;
	private String nonce_code;
	private String profile_image;
	private String display_name;
	private String status_message;
	private int state;
	private int support;
	private Timestamp block_date;
	private Timestamp create_date;
	private Timestamp update_date;
	private int del_flg;

	private int active_block;
	private int negative_block;
	private int not_support;
	private int friend_count;
	private int not_read_count;

	private String connect_email;
	private Timestamp chat_create_date;

	// フレンドID
	public int getFriend_id() { return friend_id; }
	public void setFriend_id(int friend_id) { this.friend_id = friend_id; }

	// LINEアカウントID
	public int getLine_account_id() { return line_account_id; }
	public void setLine_account_id(int line_account_id) { this.line_account_id = line_account_id; }

	// メールアドレス
	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }

	// LINEユーザーID
	public String getLine_id() { return line_id; }
	public void setLine_id(String line_id) { this.line_id = line_id; }

	// ノンスコード
	public String getNonce_code() { return nonce_code; }
	public void setNonce_code(String nonce_code) { this.nonce_code = nonce_code; }

	// プロフィール画像
	public String getProfile_image() { return profile_image; }
	public void setProfile_image(String profile_image) { this.profile_image = profile_image; }

	// 表示名
	public String getDisplay_name() { return display_name; }
	public void setDisplay_name(String display_name) { this.display_name = display_name; }

	// ステータスメッセージ
	public String getStatus_message() { return status_message; }
	public void setStatus_message(String status_message) { this.status_message = status_message; }

	// 状態
	public int getState() { return state; }
	public void setState(int state) { this.state = state; }

	// 対応マーク
	public int getSupport() { return support; }
	public void setSupport(int support) { this.support = support; }

	// ブロック日時
	public Timestamp getBlock_date() { return block_date; }
	public void setBlock_date(Timestamp block_date) { this.block_date = block_date; }

	// 作成日時
	public Timestamp getCreate_date() { return create_date; }
	public void setCreate_date(Timestamp create_date) { this.create_date = create_date; }

	// 最終更新日時
	public Timestamp getUpdate_date() { return update_date; }
	public void setUpdate_date(Timestamp update_date) { this.update_date = update_date; }

	// 削除フラグ
	public int getDel_flg() { return del_flg; }
	public void setDel_flg(int del_flg) { this.del_flg = del_flg; }

	// フィールド値取得
	public String getFiealdValue(String fldname) {
		String retValue;
		try {
			if ( "display_name".equals(fldname) ) { retValue = this.display_name;
			} else if ( "friend_id".equals(fldname) ) { retValue = common.util.toStr( this.friend_id);
			} else {
				retValue = "";
			}
		} catch (Exception e) {
			retValue = "";
		}
		return retValue;
	}

	// active_block 相手からのブロック
	public int getActive_block() { return active_block; }
	public void setActive_block(int active_block) { this.active_block = active_block; }

	// negative_block 自分からのブロック
	public int getNegative_block() { return negative_block; }
	public void setNegative_block(int negative_block) { this.negative_block = negative_block; }

	// not_support 未対応件数
	public int getNot_support() { return not_support; }
	public void setNot_support(int not_support) { this.not_support = not_support; }

	// friend_count
	public int getFriend_count() { return friend_count; }
	public void setFriend_count(int friend_count) { this.friend_count = friend_count; }

	// not_read_count
	public int getNot_read_count() { return not_read_count; }
	public void setNot_read_count(int not_read_count) { this.not_read_count = not_read_count;}

	// connect_email
	public String getConnect_email() { return connect_email;}
	public void setConnect_email(String connect_email) { this.connect_email = connect_email; }

	// chat_create_date
	public Timestamp getChat_create_date() { return chat_create_date; }
	public void setChat_create_date(Timestamp chat_create_date) { this.chat_create_date = chat_create_date; }

}
