package common.builder;

import java.util.HashMap;

import com.ibatis.sqlmap.client.SqlMapClient;

public abstract class QueryBuilder {


	public String SCHEMA;
	public int LINE_ACCOUNT_ID;
	public SqlMapClient sqlMap;

	@SuppressWarnings("unchecked")
	public HashMap paraMap;

	public String TAG_ID_LIST;
	public String FRIEND_ID_LIST;
	public String DISPLAY_NAME;
	public String CREATE_DATE_START;
	public String CREATE_DATE_END;
	public int STATE;
	public int MAIL_STATUS;
	public int SUPPORT;
	public int FRIEND_ID;

	// コンストラクタ
	public QueryBuilder() {
	}

	public abstract String getSelectString();
	public abstract String getCountQuery();
	public abstract String getCountQueryParts();

	public String getWheres() {
		String buffer = "";

		buffer += " T1.del_flg = 0 AND";

		// 友だちIDのカンマ区切りリスト
		if ( common.util.isNotEmpty(this.FRIEND_ID_LIST) ){
			buffer += " T1.friend_id in (" + this.FRIEND_ID_LIST + ") AND";
		}

		// タグIDのカンマ区切りリスト
		if ( common.util.isNotEmpty(this.TAG_ID_LIST) ){
			buffer += " T2.tag_id in (" + this.TAG_ID_LIST + ") AND";
		}

		// フレンドID
		if( this.FRIEND_ID != 0 ){
			buffer += " T1.friend_id = '" + this.FRIEND_ID + "' AND";
		}

		// LINE表示名
		if ( common.util.isNotEmpty(this.DISPLAY_NAME) ){
			buffer += " T1.display_name LIKE ('%" + this.DISPLAY_NAME + "%') AND";
		}

		// 登録日開始
		if ( common.util.isNotEmpty(this.CREATE_DATE_START) ){
			buffer += " T1.create_date >= '" + this.CREATE_DATE_START + "' AND";
		}

		// 登録日終了
		if ( common.util.isNotEmpty(this.CREATE_DATE_END) ){
			buffer += " T1.create_date <= '" + this.CREATE_DATE_END + " 23:59:59" + "' AND";
		}

		// 対応マーク
		if ( ! common.util.isNum(this.SUPPORT)  ){
			buffer += " T1.support = '" + this.SUPPORT + "' AND";
		}

		// メール連携状態
		if ( this.MAIL_STATUS == 2  ){
			buffer += " T1.email = ''  AND";
		} else if( this.MAIL_STATUS == 3 ){
			buffer += " T1.email != ''  AND";
		}


		buffer += " T1.state = '" + this.STATE + "' AND";

		return " " + buffer;
	}

	public void setWheres(String queryStr) {


		String[] strArray = (queryStr.trim() + " ").split(" AND ");
		String buffer = "";

		for (int idx = 0; idx < strArray.length; idx++) {

			buffer = strArray[idx].trim();

			// 友だちIDのカンマ区切りリスト
			if ( buffer.indexOf("T1.friend_id in ") == 0 ){
				this.FRIEND_ID_LIST = buffer.split(" in ")[1].replaceAll("\\(", "").replaceAll("\\)", "");
			}

			// タグIDのカンマ区切りリスト
			if ( buffer.indexOf("T2.tag_id in ") == 0 ){
				this.TAG_ID_LIST = buffer.split(" in ")[1].replaceAll("\\(", "").replaceAll("\\)", "");
			}

			//  LINE表示名
			if ( buffer.indexOf("T1.display_name LIKE ") == 0 ) {
				this.DISPLAY_NAME = buffer.split(" LIKE ")[1].replaceAll("\\(","").replaceAll("\\)","").replaceAll("%","").replaceAll("'", "").trim();
			}

			// 登録日開始
			if ( buffer.indexOf("T1.create_date >= ") == 0 ) {
				this.CREATE_DATE_START = buffer.split(" >= ")[1].replaceAll("'", "").trim();
			}

			// 登録日終了
			if ( buffer.indexOf("T1.create_date <= ") == 0 ) {
				this.CREATE_DATE_END = buffer.split(" <= ")[1].split(" ")[0].replaceAll("'", "").trim();
			}

			// 対応マーク
			if ( buffer.indexOf("T1.support = ") == 0 ) {
				this.SUPPORT = common.util.toNum(buffer.split(" = ")[1].replaceAll("'", "").trim());
			}

			// メール連携
			if ( buffer.indexOf("T1.email = ") == 0 ) {
				this.MAIL_STATUS = 2;
			} else if( buffer.indexOf("T1.email != ") == 0 ){
				this.MAIL_STATUS = 3;
			}

		}
	}

}
