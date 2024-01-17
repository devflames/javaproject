package page;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import common.util;
import common.builder.QueryBuilder;
import common.builder.QueryBuilderLineAutoBiz;

import dto.Entity.LineBot;
import dto.Entity.LineChatHistory;
import dto.Entity.LineFriend;
import dto.Entity.LineAPI.Webhooks.Events.MessageEvent;
import dto.Entity.LineAPI.Webhooks.Messages.AudioMessage;
import dto.Entity.LineAPI.Webhooks.Messages.FileMessage;
import dto.Entity.LineAPI.Webhooks.Messages.ImageMessage;
import dto.Entity.LineAPI.Webhooks.Messages.LocationMessage;
import dto.Entity.LineAPI.Webhooks.Messages.StickerMessage;
import dto.Entity.LineAPI.Webhooks.Messages.TextMessage;
import dto.Entity.LineAPI.Webhooks.Messages.VideoMessage;
import dto.Entity.StepMail.Scenario;

public class LIN0022Page extends BasePage {

	public List<LineChatHistory> CHAT_LIST;
	public int CHAT_MAX_COUNT;
	public int FRIEND_ID;
	public String HTML = "";
	public String MESSAGE;
	public String MESSAGE_JSON;
	public String LINE_ID;
	public int CONTINUE;
	public String TYPE;	// スマホ　SP
	public List<Scenario> SCENARIO;
	public int TEXT_MAX_COUNT;

	@SuppressWarnings("unchecked")
	public void onExec() {

		try {

			HashMap paraMap = this.getBaseQueryParam();
			this.sysProp = util.getProperties("system.properties");
			this.TEXT_MAX_COUNT = util.toNum( this.sysProp.getProperty("TEXT_MAX_COUNT") );

			this.LIMIT = common.util.toNum(this.getParam("LIMIT"));
			this.TYPE = this.getParam("TYPE");

			// 初期値設定
			if( this.LIMIT == 0 ){
				this.LIMIT = 11;
			}
			this.CONTINUE = this.LIMIT + 10;

			this.FRIEND_ID = common.util.toNum( this.getParam("FRIEND_ID") );

			// chanel access tokenからボット情報を取得
			String response = common.util.httpGet("https://api.line.me/v2/bot/info", this.CHANEL_ACCESS_TOKEN );

			if( common.util.isEmpty(response)){
				throw new Exception("ボット情報を取得できませんでした");
			}

			LineBot bot = LineBot.loadJson(response);
			String picture_url  = bot.getPictureUrl();

			// LINE友だち情報を取得
			paraMap.put("LINE_ACCOUNT_ID", this.LINE_ACCOUNT_ID);
			paraMap.put("FRIEND_ID", this.FRIEND_ID);
			LineFriend friend = (LineFriend) this.sqlMap.queryForObject("getLineFriendProfile",paraMap);

			// 未読チャットを既読に変更
			this.sqlMap.update("updChatHistoryRead",paraMap);

			// チャット履歴最大数を取得
			this.CHAT_MAX_COUNT = (int) this.sqlMap.queryForObject("getChatHistoryMax",paraMap);

			// チャット履歴情報
			paraMap.put("LIMIT", this.LIMIT);
			this.CHAT_LIST = (List<LineChatHistory>) this.sqlMap.queryForList("getChatHistoryList", paraMap);

			// メッセージ送信
			if( "ADD".equals(this.getParam("EXEC_TYPE")) ){

				this.MESSAGE = this.getParam("MESSAGE");
				this.LINE_ID = this.getParam("LINE_ID");

				// テキストメッセージ
				if( "TEXT".equals(this.getParam("POST_TYPE"))){

					TextMessage text = new TextMessage();
					text.setType("text");
					text.setText(this.MESSAGE);
					this.MESSAGE_JSON = text.toStringJson(text);


				// 画像メッセージ
				} else if( "IMAGE".equals(this.getParam("POST_TYPE")) ){

					ImageMessage image = new ImageMessage();
					image.setType("image");
					image.setOriginalContentUrl(this.MESSAGE);
					image.setPreviewImageUrl(this.MESSAGE);
					this.MESSAGE_JSON = image.toStringJson(image);
				}

				QueryBuilder qb;
				qb = new QueryBuilderLineAutoBiz();

				qb.SCHEMA = this.SCHEMA;
				qb.LINE_ACCOUNT_ID = this.LINE_ACCOUNT_ID;
				qb.FRIEND_ID = this.FRIEND_ID;
				qb.STATE = 0;

				// メッセージをキューに登録
				paraMap.put("LINE_ACCOUNT_ID", this.LINE_ACCOUNT_ID);
				paraMap.put("ACCOUNT", this.ACCOUNT);
				paraMap.put("STATUS", 5);
				//paraMap.put("XTRACTION_KEY", "SELECT * FROM " + this.SCHEMA + ".line_friend WHERE friend_id = " + this.FRIEND_ID + " AND state in(0,1) AND del_flg = 0");
				paraMap.put("XTRACTION_KEY", qb.getSelectString());
				//paraMap.put("COUNT_QUERY", "SELECT COUNT(friend_id) AS CNT FROM (SELECT friend_id FROM " + this.SCHEMA + ".line_friend WHERE friend_id =" + this.FRIEND_ID +") CNT");
				paraMap.put("COUNT_QUERY", qb.getCountQuery());
				paraMap.put("MESSAGE1", this.MESSAGE_JSON);
				paraMap.put("SCHEMA", this.SCHEMA);
				paraMap.put("SEND_COUNT", 1);
				paraMap.put("SEND_TYPE", 1);
				this.sqlMap.insert("addLineQue",paraMap);
			}

			ObjectMapper mapper = new ObjectMapper();
			MessageEvent message;

			int i= 1;
			String html = "";
			String class_name = "";
			String before_date = "";
			String image_url = "";
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy年MM月dd日 (E)");
			SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
			Calendar calendar = Calendar.getInstance();

			// 現在の日付
			Date nowdate = new Date();

			ImageMessage image;
			VideoMessage video;
			AudioMessage audio;
			FileMessage file;
			LocationMessage location;
			StickerMessage sticker;

			// 要素を逆にする
			Collections.reverse(this.CHAT_LIST);

			int size = this.CHAT_LIST.size();
			for( LineChatHistory chat : this.CHAT_LIST ) {

					if( (i == 1 && size > 10) && (i == 1 && size < this.CHAT_MAX_COUNT) ){
	                    html += "<div class=\"commentSummary__item align-center\">\n";
	                    html += "<button class=\"btn btn-sm btn-pill btn-primary px-5\" type=\"button\" id=\"CONTINUE\" data-friend=\"" + this.FRIEND_ID + "\" data-line=\"" + this.LINE_ID + "\">↑ 続きをロード</button>\n";
	                    html += "</div>\n";
					}

					if( this.CHAT_LIST.size() > 10 && i == 1 ){
						i++;
						continue;
					}

					String chatStr = chat.getChat();
					int direction = chat.getDirection();

					// 管理者　→　友だち
					if( direction == 1 ){
						class_name = "is-own";
						image_url = picture_url;

					// 友だち　→　管理者
					} else if( direction == 2){
						class_name = "from-friend";
						image_url = friend.getProfile_image();
					}

					// 日付
					String date = sdf1.format(chat.getCreate_date());
					String c_date = sdf2.format(chat.getCreate_date());

					// 有効期限日付 1年後に設定
					calendar.setTime(chat.getCreate_date());
					calendar.add(Calendar.YEAR, 1);


					if( !(before_date.equals(date)) ){
	                      html += "<div class=\"commentSummary__item\">\n";
	                      html += "<time class=\"commentDate\" datetime=\"" + c_date + "\">" + date + "</time>\n";
	                      html += "</div>\n";
					}

					if( direction != 0 ){
						message = MessageEvent.loadJson(chatStr);
						String filename = "";
						String suffix = "";

						switch( message.getType() ){

							// 画像
							case "image":
								image = ImageMessage.loadJson(MessageEvent.toStringJson(message));

								html += "<div class=\"commentSummary__item\">\n";
								html += "<div class=\"commentCard " + class_name + "\">\n";
								html += "<div class=\"commentCard__image trim-image\">\n";
								if(  common.util.isNotEmpty(image_url)  ){
									html += "<img src=\"" + image_url + "\" alt=\"\" class=\"img-thumbnai\">\n";
								} else {
									html += "<img src=\"/-ui/assets/img/icon.png\" alt=\"\">\n";
								}
								html += "</div>\n";
								if( direction == 1 ){
									if( nowdate.compareTo(calendar.getTime()) == 1 ){
										html += "<div>保存期間が終了したため画像を表示できません</div>\n";
									} else {
										html += "<div><img src=\"" + image.getOriginalContentUrl() + "\" style=\"max-width:12em;max-height:12em;border-radius:0.5em;\"></div>\n";
									}
								} else {
									if( nowdate.compareTo(calendar.getTime()) == 1 ){
										html += "<div>保存期間が終了したため画像を表示できません</div>\n";
									} else {
										html += "<div><img src=\"php2java.php?file=LIN0024&if=3&cid=" + chat.getChat_id() +"&id=" + image.getId() + "&type=image" +"\" style=\"max-width:12em;max-height:12em;border-radius:0.5em;\"></div>\n";
									}
								}
								html += "<time class=\"commentCard__date\" datetime=\"" + c_date + "\">" + c_date +"</time>\n";
								html += "</div>\n";
								html += "</div>\n";

							break;

							// 動画
							case "video":

								video = VideoMessage.loadJson(MessageEvent.toStringJson(message));

								html += "<div class=\"commentSummary__item\">\n";
								html += "<div class=\"commentCard " + class_name + "\">\n";
								html += "<div class=\"commentCard__image trim-image\">\n";
								if(  common.util.isNotEmpty(image_url)  ){
									html += "<img src=\"" + image_url + "\" alt=\"\" class=\"img-thumbnai\">\n";
								} else {
									html += "<img src=\"/-ui/assets/img/icon.png\" alt=\"\">\n";
								}
								html += "</div>\n";
								html += "<div class=\"commentCard__message\">\n";
								if( nowdate.compareTo(calendar.getTime()) == 1 ){
									html += "<div>保存期間が終了したため動画を読み込めません</div>\n";
								} else {
									html += "<i class=\"cil-video\"></i> ダウンロードは<a href=\"php2java.php?file=LIN0024&if=3&cid=" + chat.getChat_id() +"&id=" + video.getId() + "&type=video\" download=\"" + chat.getChat_id() + "_" + video.getId() + ".mp4" + "\">こちら</a>";
								}
								html += "</div>\n";
								html += "<time class=\"commentCard__date\" datetime=\"" + c_date + "\">" + c_date +"</time>\n";
								html += "</div>\n";
								html += "</div>\n";

							break;

							// 音声
							case "audio":

								audio = AudioMessage.loadJson(MessageEvent.toStringJson(message));

								html += "<div class=\"commentSummary__item\">\n";
								html += "<div class=\"commentCard " + class_name + "\">\n";
								html += "<div class=\"commentCard__image trim-image\">\n";
								if(  common.util.isNotEmpty(image_url)  ){
									html += "<img src=\"" + image_url + "\" alt=\"\" class=\"img-thumbnai\">\n";
								} else {
									html += "<img src=\"/-ui/assets/img/icon.png\" alt=\"\">\n";
								}
								html += "</div>\n";
								html += "<div class=\"commentCard__message\">\n";
								if( nowdate.compareTo(calendar.getTime()) == 1 ){
									html += "保存期間が終了したため音声を読み込めません\n";
								} else {
									html += "<i class=\"cil-audio\"></i> ダウンロードは<a href=\"php2java.php?file=LIN0024&if=3&cid=" + chat.getChat_id() + "&id=" + audio.getId() + "&type=audio\" download=\"" + chat.getChat_id() + "_" + audio.getId() + ".m4a" + "\">こちら</a>\n";
								}
								html += "</div>\n";
								html += "<time class=\"commentCard__date\" datetime=\"" + c_date + "\">" + c_date +"</time>\n";
								html += "</div>\n";
								html += "</div>\n";

							break;

							// ファイル
							case "file":

								file = FileMessage.loadJson(MessageEvent.toStringJson(message));
								filename = file.getFileName();
								suffix = "." + common.util.getSuffix(filename);

								html += "<div class=\"commentSummary__item\">\n";
								html += "<div class=\"commentCard " + class_name + "\">\n";
								html += "<div class=\"commentCard__image trim-image\">\n";
								if(  common.util.isNotEmpty(image_url)  ){
									html += "<img src=\"" + image_url + "\" alt=\"\" class=\"img-thumbnai\">\n";
								} else {
									html += "<img src=\"/-ui/assets/img/icon.png\" alt=\"\">\n";
								}
								html += "</div>\n";
								html += "<div class=\"commentCard__message\">\n";
								if( nowdate.compareTo(calendar.getTime()) == 1 ){
									html += "保存期間が終了したためファイルを読み込めません\n";
								} else {
									html += "<i class=\"cil-file\"></i> ダウンロードは<a href=\"php2java.php?file=LIN0024&if=3&cid=" + chat.getChat_id() +"&id=" + file.getId() + "&type=file&file_name=" + filename + "\" download=\"" + chat.getChat_id() + "_" + file.getId() + suffix + "\">こちら</a>\n";
								}
								html += "</div>\n";
								html += "<time class=\"commentCard__date\" datetime=\"" + c_date + "\">" + c_date +"</time>\n";
								html += "</div>\n";
								html += "</div>\n";

							break;

							case "location":

								location = LocationMessage.loadJson(MessageEvent.toStringJson(message));

								html += "<div class=\"commentSummary__item\">\n";
								html += "<div class=\"commentCard " + class_name + "\">\n";
								html += "<div class=\"commentCard__image trim-image\">\n";
								if(  common.util.isNotEmpty(image_url)  ){
									html += "<img src=\"" + image_url + "\" alt=\"\" class=\"img-thumbnai\">\n";
								} else {
									html += "<img src=\"/-ui/assets/img/icon.png\" alt=\"\">\n";
								}
								html += "</div>\n";
								html += "<div class=\"commentCard__message\"><i class=\"cil-location-pin\"></i> <a href=\"http://maps.google.co.jp/maps?q=" + location.getLatitude() + "," + location.getLongitude() + "\" target=\"_blank\">"+ location.getAddress() +"</a></div>\n";
								html += "<time class=\"commentCard__date\" datetime=\"" + c_date + "\">" + c_date +"</time>\n";
								html += "</div>\n";
								html += "</div>\n";

							break;

							case "sticker":

								sticker = StickerMessage.loadJson(MessageEvent.toStringJson(message));

								html += "<div class=\"commentSummary__item\">\n";
								html += "<div class=\"commentCard " + class_name + "\">\n";
								html += "<div class=\"commentCard__image trim-image\">\n";
								if(  common.util.isNotEmpty(image_url)  ){
									html += "<img src=\"" + image_url + "\" alt=\"\" class=\"img-thumbnai\">\n";
								} else {
									html += "<img src=\"/-ui/assets/img/icon.png\" alt=\"\">\n";
								}
								html += "</div>\n";
								html += "<div>\n";
								if( nowdate.compareTo(calendar.getTime()) == 1 ){
									html += "保存期間が終了したためスタンプを読み込めません\n";
								} else {
									html += "<img src=\"php2java.php?file=LIN0024&if=3&cid=" + chat.getChat_id() +"&id=" + sticker.getStickerId() + "&type=sticker" +"\" style=\"max-width:12em;max-height:12em;border-radius:0.5em;\">\n";
								}
								html += "</div>\n";
								html += "<time class=\"commentCard__date\" datetime=\"" + c_date + "\">" + c_date +"</time>\n";
								html += "</div>\n";
								html += "</div>\n";

							break;

							case "text":

								TextMessage text;
								text = TextMessage.loadJson(message.toStringJson(message));

								html += "<div class=\"commentSummary__item\">\n";
								html += "<div class=\"commentCard " + class_name + "\">\n";
								html += "<div class=\"commentCard__image trim-image\">\n";
								if( common.util.isNotEmpty(image_url) ){
									html += "<img src=\"" + image_url + "\" alt=\"\" class=\"img-thumbnai\">\n";
								} else {
									html += "<img src=\"/-ui/assets/img/icon.png\" alt=\"\">\n";
								}
								html += "</div>\n";
								html += "<div class=\"commentCard__message\">"+ common.util.nl2br(common.util.ht(text.getText())) +"</div>\n";
								html += "<time class=\"commentCard__date\" datetime=\"" + c_date + "\">" + c_date +"</time>\n";
								html += "</div>\n";
								html += "</div>\n";

							break;
						}
					} else {
	                      html += "<div class=\"commentSummary__item align-center\">\n";
	                      html += "<div class=\"commentStatus\">\n";
	                      html += "<div class=\"commentStatus__message\">" + chat.getChat() +"</div>\n";
	                      html += "<time class=\"commentStatus__date\" datetime=\"" + c_date + "\">" + c_date + "</time>\n";
	                      html += "</div>\n";
	                      html += "</div>\n";
					}
				i++;
				before_date = date;
			}
			this.HTML = html;

			// モバイル判定
			boolean mobile = common.util.isMobile(this.req);

			if( mobile ) {

				// チャット履歴内フレーム
				if( "SP".equals(this.TYPE) ){
					this.setPath("/LIN0022SP2.htm");

				// チャット画面大枠フレーム
				} else {

					// LINE連携用　シナリオ検索
					paraMap.put("SNO", this.LBL_SNO);
					paraMap.put("SCENARIO_NAME", this.LBL_SNAME);
					paraMap.put("TB_SCENARIO", this.TB_SCENARIO);
					paraMap.put("TB_PAGE_FRAME", this.TB_PAGE_FRAME);
					this.SCENARIO = (List<Scenario>) this.sqlMapSM.queryForList("getScenarioList",paraMap);

					this.setPath("/LIN0022SP.htm");
				}
				return;
			}

		} catch( Exception e ){
			this.logE(e);
		}
	}

	public String getContents(String url) throws Exception {
	    //BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(url)));
		BufferedReader in = new BufferedReader(new FileReader(url));

	    String inputLine, output = "";
	    while ((inputLine = in.readLine()) != null) {
	         System.out.println(output += inputLine);
	    }
	    in.close();

		return output;
	}
}
