package common.builder;

public class QueryBuilderLineAutoBiz extends QueryBuilder {

	public String getSelectString() {
		String buffer = "";

		buffer += "SELECT";
		buffer += " DISTINCT T1.friend_id,";
		buffer += " T1.line_account_id,";
		buffer += " T1.email,";
		buffer += " T1.line_id,";
		buffer += " T1.nonce_code,";
		buffer += " T1.profile_image,";
		buffer += " T1.display_name,";
		buffer += " T1.status_message,";
		buffer += " T1.state,";
		buffer += " T1.support,";
		buffer += " T1.block_date,";
		buffer += " T1.create_date,";
		buffer += " T1.update_date ";
		buffer += " FROM";
		buffer += " " + this.SCHEMA + ".line_friend T1";
		buffer += " LEFT JOIN";
		buffer += " " + this.SCHEMA + ".line_attribute T2";
		buffer += " ON T1.friend_id = T2.friend_id";
		buffer += " WHERE";
		buffer += this.getWheres();
		buffer += " T1.line_account_id = '" + this.LINE_ACCOUNT_ID + "'";
		buffer += " ORDER BY T1.create_date";

		return buffer;
	}

	public String getCountQuery() {
		String buffer = "";

		buffer += "SELECT COUNT(*) AS ct FROM (";
		buffer += this.getCountQueryParts();
		buffer += " ) TBL";

		return buffer;
	}

	public String getCountQueryParts() {
		String buffer = "";

		buffer += " SELECT DISTINCT T1.friend_id";
		buffer += " FROM " + this.SCHEMA  + ".line_friend T1";
		buffer += " LEFT JOIN";
		buffer += " " + this.SCHEMA + ".line_attribute T2";
		buffer += " ON T1.friend_id = T2.friend_id";
		buffer += " WHERE";
		buffer += this.getWheres();
		buffer += " T1.line_account_id = '" + this.LINE_ACCOUNT_ID + "'";

		return buffer;
	}
}
