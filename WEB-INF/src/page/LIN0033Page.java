package page;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import common.Pager2;

import dto.Entity.LineBot;
import dto.Entity.LineQue;
import dto.Entity.LineQueLog;
import dto.Entity.LineAPI.Webhooks.Events.MessageEvent;
import dto.Entity.LineAPI.Webhooks.Messages.AudioMessage;
import dto.Entity.LineAPI.Webhooks.Messages.FileMessage;
import dto.Entity.LineAPI.Webhooks.Messages.ImageMessage;
import dto.Entity.LineAPI.Webhooks.Messages.LocationMessage;
import dto.Entity.LineAPI.Webhooks.Messages.StickerMessage;
import dto.Entity.LineAPI.Webhooks.Messages.TextMessage;
import dto.Entity.LineAPI.Webhooks.Messages.VideoMessage;

public class LIN0033Page extends BasePage {

	public int QUE_ID;
	public Timestamp SEND_DATETIME;
	public Timestamp START_DATETIME;
	public Timestamp END_DATETIME;
	public String MESSAGE1;
	public String MESSAGE2;
	public String MESSAGE3;
	public String MESSAGE4;
	public String MESSAGE5;
	public String STATUS_1;
	public String STATUS_2;

	public String DISPLAY_NAME;
	public String CREATE_DATE_START;
	public String CREATE_DATE_END;
	public int MAIL_STATUS;
	public String FRIEND_ID;
	public String RESEND_LOG_ID;
	public int COUNT_FRIEND_ID;
	public String SEND_DATE;


	public Pager2 PAGE_OBJ2;
	public int NOW_PAGE = 1;
	public int DISP_COUNT =100;


	public List<LineQueLog> QUE_LOG_LIST;
	public int TYPE;
	public String PICTURE_URL;

	@SuppressWarnings("unchecked")
	public void onExec() {

		try {

			HashMap paraMap = this.getBaseQueryParam();
			paraMap.clear();
			paraMap.put("ACCOUNT_NAME", this.ACCOUNT);
			paraMap.put("LINE_ACCOUNT_ID", this.LINE_ACCOUNT_ID);

			// 再送する予約日時を設定
			Calendar now = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
			if( this.SEND_DATE == null ) {
				// 予約日時が設定されていない場合は、現在時刻をセット
				this.SEND_DATE = sdf.format( now.getTime() );
			}

			// 画面タイプ
			this.TYPE = common.util.toNum( this.getParam("TYPE") );

			// chanel access tokenからボット情報を取得
			String response = common.util.httpGet("https://api.line.me/v2/bot/info", this.CHANEL_ACCESS_TOKEN );

			if( common.util.isEmpty(response)){
				throw new Exception("ボット情報を取得できませんでした");
			}

			LineBot bot = LineBot.loadJson(response);
			this.PICTURE_URL  = bot.getPictureUrl();
			if(this.PICTURE_URL == null ) {
				this.PICTURE_URL = "https://placehold.jp/150x150.png";
			}

			// キュー情報の取得
			paraMap.put("QUE_ID", this.getParam("QUE_ID"));
			LineQue que = (LineQue)this.sqlMap.queryForObject( "getLineQue2" , paraMap);
			this.QUE_ID = que.getQue_id();
			this.SEND_DATETIME = que.getSend_datetime();
			this.START_DATETIME = que.getStart_datetime();
			this.END_DATETIME = que.getEnd_datetime();


			// 表示するメッセージを作成
			String html1 = "";
			String html2 = "";
			String html3 = "";
			String html4 = "";
			String html5 = "";
			Timestamp time = que.getEnd_datetime();

			String queMessage1 = que.getMessage1();
			if(queMessage1 != null ) {
				MessageEvent message1 = MessageEvent.loadJson(queMessage1);
				this.MESSAGE1 = addMessage(message1, this.MESSAGE1, html1, time);
			}

			String queMessage2 = que.getMessage2();
			if(queMessage2 != null ) {
				MessageEvent message2 = MessageEvent.loadJson(queMessage2);
				this.MESSAGE2 = addMessage(message2, this.MESSAGE2, html2, time);
			}

			String queMessage3 = que.getMessage3();
			if(queMessage3 != null ) {
				MessageEvent message3 = MessageEvent.loadJson(queMessage3);
				this.MESSAGE3 = addMessage(message3, this.MESSAGE3, html3, time);
			}

			String queMessage4 = que.getMessage4();
			if(queMessage4 != null ) {
				MessageEvent message4 = MessageEvent.loadJson(queMessage4);
				this.MESSAGE4 = addMessage(message4, this.MESSAGE4, html4, time);
			}

			String queMessage5 = que.getMessage5();
			if(queMessage5 != null ) {
				MessageEvent message5 = MessageEvent.loadJson(queMessage5);
				this.MESSAGE5 = addMessage(message5, this.MESSAGE5, html5, time);
			}

			// ログ件数の取得
			paraMap.put("SCHEMA", que.getSchema());

			if ( this.STATUS_1 != null && this.STATUS_2 == null ) {

				paraMap.put("STATUS", 1);

			} else if ( this.STATUS_1 == null && this.STATUS_2 != null ) {

				paraMap.put("STATUS", 2);
			}

			int cnt = common.util.toNum(this.sqlMap.queryForObject("getLineQueLogCount", paraMap));

			this.PAGE_OBJ2 = new Pager2(this.NOW_PAGE, this.DISP_COUNT, cnt);
			paraMap.put("START", this.PAGE_OBJ2.getStart());
			paraMap.put("LIMIT", this.PAGE_OBJ2.getLimit());

			// 再送処理
			if( "RESEND_FORM".equals(this.getParam("EXEC_TYPE")) ){

				this.MAIL_STATUS = common.util.toNum(this.MAIL_STATUS);
				this.CREATE_DATE_START = "";
				this.CREATE_DATE_END = "";

				paraMap.put("ACCOUNT", this.ACCOUNT);
				paraMap.put("STATUS", 5);
				paraMap.put("SEND_TYPE", 2);
				paraMap.put("SEND_DATETIME", this.SEND_DATE);
				paraMap.put("SEND_COUNT", this.COUNT_FRIEND_ID);
				paraMap.put("XTRACTION_KEY", addXtractionKey());
				paraMap.put("COUNT_QUERY", addCountQuery());
				paraMap.put("MESSAGE1", que.getMessage1());
				paraMap.put("MESSAGE2", que.getMessage2());
				paraMap.put("MESSAGE3", que.getMessage3());
				paraMap.put("MESSAGE4", que.getMessage4());
				paraMap.put("MESSAGE5", que.getMessage5());
				paraMap.put("SCHEMA", que.getSchema());
				paraMap.put("RESEND_LOG_ID", this.RESEND_LOG_ID);

				// 選択した友だちにキューを再送
				this.sqlMap.insert("addLineQue", paraMap);

				// 再送フラグ変更
				this.sqlMap.insert("updResendFlg", paraMap);

				// 一覧画面にリダイレクト
				this.REDIRECT_URL = "!LIN0030!";
				this.setPath("REDIRECT.htm");
				return;

			}

			// ログ情報の取得
			this.QUE_LOG_LIST = (List<LineQueLog>) this.sqlMap.queryForList("getLineQueLogList", paraMap);

		} catch( Exception e ){
			this.logE(e);
		}
	}

	// MESSAGEを作成
	private String addMessage(MessageEvent messageEvent, String MESSAGE, String html, Timestamp time) throws JsonParseException, JsonMappingException, JsonProcessingException, IOException  {

		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

		// 日付
		String date = sdf.format(time);

		switch( messageEvent.getType() ){

		case "text":

			TextMessage text;
			text = TextMessage.loadJson(messageEvent.toStringJson(messageEvent));
			String textMessage = text.getText();

			html += "<div class=\"commentSummary__item\">\n";
			html += "<div class=\"commentCard " + "\">\n";
			html += "<div class=\"commentCard__image trim-image\">\n";
			html += "<img src=\"" + this.PICTURE_URL + "\" alt=\"\">\n";
			html += "</div>\n";
			html += "<div class=\"commentCard__message\">"+ common.util.nl2br(common.util.ht(textMessage)) +"</div>\n";
			html += "<time class=\"commentCard__date\" datetime=\"" + date + "\">" + date +"</time></div></div>\n";

			MESSAGE = html;
		break;

		// スタンプ
		case "sticker":

			StickerMessage sticker;
			sticker = StickerMessage.loadJson(messageEvent.toStringJson(messageEvent));

			html += "<div class=\"commentSummary__item\">\n";
			html += "<div class=\"commentCard " + "\">\n";
			html += "<div class=\"commentCard__image trim-image\">\n";
			html += "<img src=\"" + this.PICTURE_URL + "\" alt=\"\">\n";
			html += "</div>\n";

			html += "<div class=\"commentCard__message\">【スタンプ画像を差し込む】</div>\n";
			html += "<time class=\"commentCard__date\" datetime=\"" + date + "\">" + date +"</time></div></div>\n";

			MESSAGE = html;

		break;

		// 画像
		case "image":

			ImageMessage image;
			image = ImageMessage.loadJson(messageEvent.toStringJson(messageEvent));

			html += "<div class=\"commentSummary__item\">\n";
			html += "<div class=\"commentCard \">\n";
			html += "<div class=\"commentCard__image trim-image\">\n";
			html += "<img src=\"" + this.PICTURE_URL + "\" alt=\"\">\n";
			html += "</div>\n";
			html += "<div><img src=\"" + image.getOriginalContentUrl() + "\" style=\"max-width:12em;max-height:12em;border-radius:0.5em;\"></div>\n";
			html += "<time class=\"commentCard__date\" datetime=\"" + date + "\">" + date +"</time></div></div>\n";

			MESSAGE = html;
		break;

		// 動画
		case "video":

			VideoMessage video;
			video = VideoMessage.loadJson(messageEvent.toStringJson(messageEvent));

			html += "<div class=\"commentSummary__item\">\n";
			html += "<div class=\"commentCard " + "\">\n";
			html += "<div class=\"commentCard__image trim-image\">\n";
			html += "<img src=\"" + this.PICTURE_URL + "\" alt=\"\">\n";
			html += "</div>\n";

			html += "<time class=\"commentCard__date\" datetime=\"" + date + "\">" + date +"</time></div></div>\n";

			MESSAGE = html;

		break;

		// 音声
		case "audio":

			AudioMessage audio;
			audio = AudioMessage.loadJson(messageEvent.toStringJson(messageEvent));

			html += "<div class=\"commentSummary__item\">\n";
			html += "<div class=\"commentCard " + "\">\n";
			html += "<div class=\"commentCard__image trim-image\">\n";
			html += "<img src=\"" + this.PICTURE_URL + "\" alt=\"\">\n";
			html += "</div>\n";

			html += "<time class=\"commentCard__date\" datetime=\"" + date + "\">" + date +"</time></div></div>\n";

			MESSAGE = html;


		break;

		// ファイル
		case "file":

			FileMessage file;
			file = FileMessage.loadJson(messageEvent.toStringJson(messageEvent));

			html += "<div class=\"commentSummary__item\">\n";
			html += "<div class=\"commentCard " + "\">\n";
			html += "<div class=\"commentCard__image trim-image\">\n";
			html += "<img src=\"" + this.PICTURE_URL + "\" alt=\"\">\n";
			html += "</div>\n";
			html += "<div class=\"commentCard__message\"><i class=\"cil-file\"></i> ダウンロードは<a href=\"javascript:void(0)\" onclick=\"downloadFile('" + file.getFileName() +"');\">こちら</a>" +"</div>\n";
			html += "<time class=\"commentCard__date\" datetime=\"" + date + "\">" + date +"</time></div></div>\n";

			MESSAGE = html;

		break;

		// 位置情報
		case "location":

			LocationMessage location;
			location = LocationMessage.loadJson(messageEvent.toStringJson(messageEvent));

			html += "<div class=\"commentSummary__item\">\n";
			html += "<div class=\"commentCard " + "\">\n";
			html += "<div class=\"commentCard__image trim-image\">\n";
			html += "<img src=\"" + this.PICTURE_URL + "\" alt=\"\">\n";
			html += "</div>\n";
			html += "<div class=\"commentCard__message\"><i class=\"cil-location-pin\"></i> <a href=\"http://maps.google.co.jp/maps?q=" + location.getLatitude() + "," + location.getLongitude() + "\" target=\"_blank\">"+ location.getAddress() +"</a></div>\n";
			html += "<time class=\"commentCard__date\" datetime=\"" + date + "\">" + date +"</time></div></div>\n";

			MESSAGE = html;

		break;
	}

		return MESSAGE;
	}

	// XTRACTION_KEYを作成
	public String addXtractionKey() {
		String buffer = "";

		buffer += "SELECT";
		buffer += " * ";
		buffer += "FROM ";
		buffer += "" + this.SCHEMA + ".line_friend T1 ";
		buffer += "WHERE ";
		buffer += "T1.del_flg = 0 AND ";
		buffer += "T1.friend_id in (" + this.FRIEND_ID + ") AND ";
		buffer += "T1.state = 0";

		return buffer;
	}

	// COUNT_QUERYを作成
	public String addCountQuery() {
		String buffer = "";

		buffer += "SELECT COUNT(friend_id) AS ct FROM (";
		buffer += "SELECT friend_id FROM ";
		buffer += "" + this.SCHEMA + ".line_friend ";
		buffer += "WHERE ";
		buffer += "friend_id in (" + this.FRIEND_ID + ") AND ";
		buffer += "state = 0 AND del_flg = 0 ) TBL";

		return buffer;
	}

}
