package dto.Entity;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import dto.Entity.LineAPI.Webhooks.Events.MessageEvent;
import dto.Entity.LineAPI.Webhooks.Messages.TextMessage;

public class LineQue {

	// メンバー宣言
	private int que_id;
	private int line_account_id;
	private String account;
	private int status;
	private Timestamp send_datetime;
	private int send_count;
	private int error_count;
	private Timestamp start_datetime;
	private Timestamp end_datetime;
	private String xtraction_key;
	private String count_query;
	private String message1;
	private String message2;
	private String message3;
	private String message4;
	private String message5;
	private String schema;
	private Timestamp create_date;
	private Timestamp update_date;

	private int chanel_id;
	private String chanel_secret;
	private String chanel_access_token;

	// ID
	public int getQue_id() { return que_id; }
	public void setQue_id(int que_id) { this.que_id = que_id; }

	// LINEアカウントID
	public int getLine_account_id() { return line_account_id; }
	public void setLine_account_id(int line_account_id) { this.line_account_id = line_account_id; }

	// アカウントID
	public String getAccount() { return account; }
	public void setAccount(String account) { this.account = account; }

	// 配信ステータス
	public int getStatus() { return status; }
	public void setStatus(int status) { this.status = status; }

	// 配信予約日時
	public Timestamp getSend_datetime() { return send_datetime; }
	public void setSend_datetime(Timestamp send_datetime) { this.send_datetime = send_datetime; }

	// 宛先件数
	public int getSend_count() { return send_count; }
	public void setSend_count(int send_count) { this.send_count = send_count; }

	// 配信エラー数
	public int getError_count() { return error_count; }
	public void setError_count(int error_count) { this.error_count = error_count; }

	// 配信開始日時
	public Timestamp getStart_datetime() { return start_datetime; }
	public void setStart_datetime(Timestamp start_datetime) { this.start_datetime = start_datetime; }

	// 配信完了日時
	public Timestamp getEnd_datetime() { return end_datetime; }
	public void setEnd_datetime(Timestamp end_datetime) { this.end_datetime = end_datetime; }

	// 抽出条件
	public String getXtraction_key() { return xtraction_key; }
	public void setXtraction_key(String xtraction_key) { this.xtraction_key = xtraction_key; }

	// 宛先取得クエリー
	public String getCount_query() { return count_query; }
	public void setCount_query(String count_query) { this.count_query = count_query; }


	public String getMessage() {
		List<String> lst = new ArrayList<String>();
		lst.add(this.message1);
		lst.add(this.message2);
		lst.add(this.message3);
		lst.add(this.message4);
		lst.add(this.message5);

		StringBuilder builder = new StringBuilder();

		for(String str : lst) {
			if ( str != null ) {
				builder.append(str).append(",");
			}
		}

		int last = builder.toString().lastIndexOf(",");

		return "[" + builder.toString().substring(0, last) + "]";
	}

	// LINEメッセージ1
	public String getMessage1() { return message1; }
	public void setMessage1(String message1) { this.message1 = message1; }

	// LINEメッセージ2
	public String getMessage2() { return message2; }
	public void setMessage2(String message2) { this.message2 = message2; }

	// LINEメッセージ3
	public String getMessage3() { return message3; }
	public void setMessage3(String message3) { this.message3 = message3; }

	// LINEメッセージ4
	public String getMessage4() { return message4; }
	public void setMessage4(String message4) { this.message4 = message4; }

	// LINEメッセージ5
	public String getMessage5() { return message5; }
	public void setMessage5(String message5) { this.message5 = message5; }

	// スキーマ名
	public String getSchema() { return schema; }
	public void setSchema(String schema) { this.schema = schema; }

	// 作成日時
	public Timestamp getCreate_date() { return create_date; }
	public void setCreate_date(Timestamp create_date) { this.create_date = create_date; }

	// 最終更新日時
	public Timestamp getUpdate_date() { return update_date; }
	public void setUpdate_date(Timestamp update_date) { this.update_date = update_date; }

	// チャンネルID
	public int getChanel_id() { return chanel_id; }
	public void setChanel_id(int chanel_id) { this.chanel_id = chanel_id; }

	// チャンネルシークレット
	public String getChanel_secret() { return chanel_secret; }
	public void setChanel_secret(String chanel_secret) { this.chanel_secret = chanel_secret; }

	// チェンネルアクセストークン
	public String getChanel_access_token() { return chanel_access_token; }
	public void setChanel_access_token(String chanel_access_token) { this.chanel_access_token = chanel_access_token; }

	// MessageのHTMLを作成
	public String messageHtml() throws JsonParseException, JsonMappingException, IOException {

		String result = null;
		MessageEvent message = MessageEvent.loadJson(this.message1);

		switch( message.getType() ){

			case "text":

				TextMessage text;
				text = TextMessage.loadJson(message.toStringJson(message));
				String textMessage = common.util.ht(text.getText());

				int len = textMessage.length();

				if( len > 10 ) {
					textMessage = textMessage.substring(0,10) + "...";
				}

				result = textMessage;


			break;

			// スタンプ
			case "sticker":

				result = "<i class=\"cil-video\"></i>【スタンプ】";

			break;

			// 画像
			case "image":

				result = "<i class=\"cil-image\"></i>【画像】";

			break;

			// 動画
			case "video":

				result = "<i class=\"cil-video\"></i>【動画】";

			break;

			// 音声
			case "audio":

				result = "<i class=\"cil-audio\"></i>【音声】";

			break;

			// ファイル
			case "file":

				result = "<i class=\"cil-file\"></i>【ファイル】";

			break;

			// 位置情報
			case "location":

				result = "<i class=\"cil-location-pin\"></i>【位置情報】";

			break;
		}

		return  result;
	}

}
