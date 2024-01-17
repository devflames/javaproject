package page;

import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import common.Pager;
import common.util;
import common.builder.QueryBuilder;
import common.builder.QueryBuilderLineAutoBiz;
import dto.Entity.Condition;
import dto.Entity.LineAttribute;
import dto.Entity.LineFolder;
import dto.Entity.LineFriend;
import dto.Entity.LineQue;
import dto.Entity.LineTemp;
import dto.Entity.LineAPI.Webhooks.Events.MessageEvent;
import dto.Entity.LineAPI.Webhooks.Messages.ImageMessage;
import dto.Entity.LineAPI.Webhooks.Messages.TextMessage;
import dto.Entity.StepMail.Scenario;

public class LIN0032Page extends BasePage {

	public String TAG_JSON="";
	public List<LineFolder> LINE_FOLDER_LIST;
	public List<LineFriend> LINE_FRIEND_LIST;
	public List<LineFriend> SEND_FRIEND_LIST;
	public int ADDRESS;
	public int SEND_TYPE;
	public String SEND_DATE;
	public String DISPLAY_NAME;
	public String CREATE_DATE_START;
	public String CREATE_DATE_END;
	public int MAIL_STATUS;
	public String FRIEND_ID_LIST;
	public String TAG_ID_LIST;
	public String TAG;
	public String TAG_VALUE="";
	public String TAG_VALUE_SET ="";
	public Condition condition;
	public String RETURN_JSON;
	public String EXEC_TYPE;

	public String MESSAGE1 = "";
	public String MESSAGE2 = "";
	public String MESSAGE3 = "";
	public String MESSAGE4 = "";
	public String MESSAGE5 = "";
	public String SEND_IMAGE1 = "";
	public String SEND_IMAGE2 = "";
	public String SEND_IMAGE3 = "";
	public String SEND_IMAGE4 = "";
	public String SEND_IMAGE5 = "";
	public int DEFAULT_BOX_COUNT;

	public List<Scenario> SCENARIO;
	public String FRIEND_TABLE="";

	public int QUE_ID;
	public int TEMP_ID;
	public Timestamp SEND_DATETIME;

	public Pager PAGE_OBJ;
	public int NOW_PAGE = 1;
	public int FRIEND_LIST_COUNT;
	public int TEXT_MAX_COUNT;

	@SuppressWarnings("unchecked")
	public void onExec() {

		try {

			HashMap paraMap = this.getBaseQueryParam();
			this.EXEC_TYPE = this.getParam("EXEC_TYPE");
			this.sysProp = util.getProperties("system.properties");
			this.TEXT_MAX_COUNT = util.toNum( this.sysProp.getProperty("TEXT_MAX_COUNT") );

			paraMap.put("LINE_ACCOUNT_ID", this.LINE_ACCOUNT_ID);
			paraMap.put("NOT_NULL_TAG_NAME", 1);

			// フォルダ・タグ情報の取得
			this.LINE_FOLDER_LIST = (List<LineFolder>) this.sqlMap.queryForList("getLineFolderList", paraMap);

			// フォルダオブジェクトをJSONに変換
			int i = 0;
			for( LineFolder folder : this.LINE_FOLDER_LIST){

					i++;
					if( i == this.LINE_FOLDER_LIST.size() ){
						this.TAG_JSON += LineFolder.toStringJson(folder);
					} else {
						this.TAG_JSON += LineFolder.toStringJson(folder)+",";
					}

			}

			// LINE連携用　シナリオ検索
			paraMap.put("SNO", this.LBL_SNO);
			paraMap.put("SCENARIO_NAME", this.LBL_SNAME);
			paraMap.put("TB_SCENARIO", this.TB_SCENARIO);
			paraMap.put("TB_PAGE_FRAME", this.TB_PAGE_FRAME);
			this.SCENARIO = (List<Scenario>) this.sqlMapSM.queryForList("getScenarioList",paraMap);

			// Que情報の取得　下書情報の取得
			if( "".equals(this.EXEC_TYPE) || "DRAFT".equals(this.EXEC_TYPE) ){

				String queMessage1 = "";
				String queMessage2 = "";
				String queMessage3 = "";
				String queMessage4 = "";
				String queMessage5 = "";
				String xtraction_key = "";

				if( "".equals(this.EXEC_TYPE) ){

					// キュー情報の取得
					paraMap.put("QUE_ID", this.getParam("QUE_ID"));
					LineQue que = (LineQue)this.sqlMap.queryForObject( "getLineQue2" , paraMap);
					this.QUE_ID = que.getQue_id();
					this.SEND_DATETIME = que.getSend_datetime();

					queMessage1 = que.getMessage1();
					queMessage2 = que.getMessage2();
					queMessage3 = que.getMessage3();
					queMessage4 = que.getMessage4();
					queMessage5 = que.getMessage5();

					xtraction_key = que.getXtraction_key();

				} else if( "DRAFT".equals(this.EXEC_TYPE)){

					// 下書情報の取得
					paraMap.put("TEMP_ID", this.getParam("TEMP_ID"));
					LineTemp temp = (LineTemp) this.sqlMap.queryForObject("getLineTemp",paraMap);
					this.TEMP_ID = temp.getTemp_id();
					this.SEND_DATETIME = temp.getSend_datetime();

					queMessage1 = temp.getMessage1();
					queMessage2 = temp.getMessage2();
					queMessage3 = temp.getMessage3();
					queMessage4 = temp.getMessage4();
					queMessage5 = temp.getMessage5();

					xtraction_key = temp.getXtraction_key();
				}

				// 配信日時を取得
				Calendar now = Calendar.getInstance();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
				this.SEND_DATE = sdf.format( this.SEND_DATETIME );
				this.SEND_TYPE = 2;

				// Jsonから
				if(queMessage1 != null ) {
					MessageEvent message1 = MessageEvent.loadJson(queMessage1);
					switch( message1.getType() ){

						case "text":

							TextMessage text;
							text = TextMessage.loadJson(message1.toStringJson(message1));
							this.MESSAGE1 = text.getText();

						break;

						// 画像
						case "image":

							ImageMessage image;
							image = ImageMessage.loadJson(message1.toStringJson(message1));

							this.SEND_IMAGE1 = image.getOriginalContentUrl();
						break;

					}
				}

				if(queMessage2 != null ) {
					MessageEvent message2 = MessageEvent.loadJson(queMessage2);
					switch( message2.getType() ){

						case "text":

							TextMessage text;
							text = TextMessage.loadJson(message2.toStringJson(message2));
							this.MESSAGE2 = text.getText();

						break;

						// 画像
						case "image":

							ImageMessage image;
							image = ImageMessage.loadJson(message2.toStringJson(message2));

							this.SEND_IMAGE2 = image.getOriginalContentUrl();
						break;

					}
				}

				if(queMessage3 != null ) {
					MessageEvent message3 = MessageEvent.loadJson(queMessage3);
					switch( message3.getType() ){

						case "text":

							TextMessage text;
							text = TextMessage.loadJson(message3.toStringJson(message3));
							this.MESSAGE3 = text.getText();

						break;


						// 画像
						case "image":

							ImageMessage image;
							image = ImageMessage.loadJson(message3.toStringJson(message3));

							this.SEND_IMAGE3 = image.getOriginalContentUrl();
						break;

					}
				}

				if(queMessage4 != null ) {
					MessageEvent message4 = MessageEvent.loadJson(queMessage4);
					switch( message4.getType() ){

						case "text":

							TextMessage text;
							text = TextMessage.loadJson(message4.toStringJson(message4));
							this.MESSAGE4 = text.getText();

						break;

						// 画像
						case "image":

							ImageMessage image;
							image = ImageMessage.loadJson(message4.toStringJson(message4));

							this.SEND_IMAGE4 = image.getOriginalContentUrl();
						break;
					}
				}

				if(queMessage5 != null ) {
					MessageEvent message5 = MessageEvent.loadJson(queMessage5);
					switch( message5.getType() ){

						case "text":

							TextMessage text;
							text = TextMessage.loadJson(message5.toStringJson(message5));
							this.MESSAGE5 = text.getText();

						break;

						// 画像
						case "image":

							ImageMessage image;
							image = ImageMessage.loadJson(message5.toStringJson(message5));

							this.SEND_IMAGE5 = image.getOriginalContentUrl();
						break;

					}
				}

				// Xtraction_keyを分解して条件にセット
				QueryBuilder qb;
				qb = new QueryBuilderLineAutoBiz();

				qb.setWheres(xtraction_key);

				this.FRIEND_ID_LIST = qb.FRIEND_ID_LIST;
				this.DISPLAY_NAME = qb.DISPLAY_NAME;
				this.CREATE_DATE_START = qb.CREATE_DATE_START;
				this.CREATE_DATE_END = qb.CREATE_DATE_END;
				this.MAIL_STATUS = qb.MAIL_STATUS;

				if( this.FRIEND_ID_LIST != null) {

					// FRIEND_ID_LISTがある場合は再送
					this.ADDRESS = 3;

					paraMap.put("FRIEND_ID_LIST", this.FRIEND_ID_LIST);
					paraMap.put("SORT", 1);

					// 対象リストの作成
					this.SEND_FRIEND_LIST = this.sqlMap.queryForList("getLineFriendList", paraMap);

				} else {
					// tag_idからタグの情報を取得
					this.TAG_ID_LIST = qb.TAG_ID_LIST;

					if(this.TAG_ID_LIST != null ) {
						paraMap.put("TAG_ID_LIST", this.TAG_ID_LIST);
						List<LineFolder> lineTagList = (List<LineFolder>) this.sqlMap.queryForList("getLineFolderList2", paraMap);

						// フォルダオブジェクトをJSONに変換
						int index = 0;
						String tagList = "";
						for( LineFolder folder : lineTagList){

							index++;
							if( index == lineTagList.size() ){
								tagList += LineFolder.toStringJson(folder);
							} else {
								tagList += LineFolder.toStringJson(folder)+",";
							}

						}

						this.TAG_VALUE = "[" + tagList + "]";
						this.TAG = this.TAG_VALUE;

					}

					if(this.DISPLAY_NAME == null && this.CREATE_DATE_START == null && this.CREATE_DATE_END == null  && this.MAIL_STATUS <= 1 && this.TAG_ID_LIST == null ) {

						this.ADDRESS = 1;

					} else {

						this.ADDRESS = 2;

					}
				}


			// 友だち全員
			} else if( "ALLSET".equals(this.EXEC_TYPE) ){

				paraMap.put("STATE", 0);
				int cnt = common.util.toNum(this.sqlMap.queryForObject("getLineFriendCount", paraMap));
				condition = new Condition();
				condition.setCount(cnt);
				this.RETURN_JSON = condition.toStringJson(condition);
				this.setPath("LIN0031EX.htm");
				return;

			// 条件設定
			} else if( "SET".equals(this.EXEC_TYPE) ){

				// 初期化
				this.LINE_FOLDER_LIST = new ArrayList<LineFolder>();

				this.DISPLAY_NAME = this.getParam("DISPLAY_NAME");
				this.CREATE_DATE_START = this.getParam("CREATE_DATE_START");
				if( "____/__/__".equals(this.CREATE_DATE_START) ){
					this.CREATE_DATE_START = "";
				}

				this.CREATE_DATE_END = this.getParam("CREATE_DATE_END");
				if( "____/__/__".equals(this.CREATE_DATE_END) ){
					this.CREATE_DATE_END = "";
				}

				this.MAIL_STATUS = common.util.toNum(this.getParam("MAIL_STATUS"));
				this.TAG = this.getParam("TAG");

				if( common.util.isNotEmpty(this.TAG)){
					this.LINE_FOLDER_LIST = LineFolder.loadJson(this.TAG);
				}

				String tag_id = "";
				int ii= 1;
				for(LineFolder folder : this.LINE_FOLDER_LIST){

					if(ii == this.LINE_FOLDER_LIST.size() ){
						tag_id += common.util.toStr( folder.getTag_id() );
						this.TAG_VALUE_SET += folder.getTag_name();
					} else {
						tag_id += folder.getTag_id() + ",";
						this.TAG_VALUE_SET += folder.getTag_name() + ",";
					}
					ii++;
				}

				// 友だちフォルダタグ中間テーブルの検索
				paraMap.put("TAG_ID", tag_id);
				if( common.util.isNotEmpty(tag_id) ){
					LineAttribute attribute = (LineAttribute) this.sqlMap.queryForObject("getLineAttributeFriendId",paraMap);
					if( attribute.getFriend_id_list() == null ){
						paraMap.put("FRIEND_ID_LIST", "NULL");
					} else {
						paraMap.put("FRIEND_ID_LIST", attribute.getFriend_id_list());
					}
				}

				// 抽出条件と配信対象を特定

				// 友だち情報の取得
				paraMap.put("SORT", 1);
				paraMap.put("DISPLAY_NAME", this.DISPLAY_NAME);
				paraMap.put("CREATE_DATE_START", this.CREATE_DATE_START);
				if( common.util.isNotEmpty(this.CREATE_DATE_END) ){

					paraMap.put("CREATE_DATE_END", this.CREATE_DATE_END+" 23:59:59");

				} else {
					paraMap.put("CREATE_DATE_END", "");
				}

				paraMap.put("MAIL_STATUS", this.MAIL_STATUS);
				paraMap.put("STATE", 0);

				this.LINE_FRIEND_LIST = (List<LineFriend>) this.sqlMap.queryForList("getLineFriendList", paraMap);

				this.TAG_VALUE = common.util.removeLastConma(this.TAG_VALUE);

				condition = new Condition();
				condition.setDisplay_name(this.DISPLAY_NAME);
				condition.setCreate_date_start(this.CREATE_DATE_START);
				condition.setCreate_date_end(this.CREATE_DATE_END);
				condition.setMail_status(this.MAIL_STATUS);
				condition.setTag(this.TAG_VALUE);
				condition.setTag_name(this.TAG_VALUE_SET);
				condition.setCount(this.LINE_FRIEND_LIST.size());

				this.RETURN_JSON = condition.toStringJson(condition);

				this.setPath("LIN0031EX.htm");
				return;

			// 条件リセット
			} else if( "RESET".equals(this.EXEC_TYPE) ){

				// 全ての友だち件数を取得
				paraMap.put("SORT", 1);
				this.LINE_FRIEND_LIST = (List<LineFriend>) this.sqlMap.queryForList("getLineFriendList", paraMap);

				condition = new Condition();
				condition.setDisplay_name("");
				condition.setCreate_date_start("");
				condition.setCreate_date_end("");
				condition.setMail_status(1);
				condition.setTag("");
				condition.setCount(this.LINE_FRIEND_LIST.size());

				this.RETURN_JSON = condition.toStringJson(condition);

				this.setPath("LIN0032EX.htm");
				return;

			// 確認画面でのキャンセル戻り時
			} else if( "BACK".equals(this.EXEC_TYPE) ){
				this.TAG_VALUE = this.TAG;

			} else if( "DISPLAY_AP".equals(this.EXEC_TYPE) ){

				// キュー情報の取得
				paraMap.put("QUE_ID", this.getParam("QUE_ID"));
				LineQue que = (LineQue)this.sqlMap.queryForObject( "getLineQue2" , paraMap);

				// Xtraction_keyを分解して条件にセット
				QueryBuilder qb;
				qb = new QueryBuilderLineAutoBiz();

				qb.setWheres(que.getXtraction_key());

				this.FRIEND_ID_LIST = qb.FRIEND_ID_LIST;
				paraMap.put("FRIEND_ID_LIST", this.FRIEND_ID_LIST);

				// 友だち件数の取得
				int cnt = common.util.toNum(this.sqlMap.queryForObject("getLineFriendCount", paraMap));

				// ページ制御の初期化
				HashMap<String, String> hashMaps = (HashMap<String, String>)this.sess.get(this.getClass().getSimpleName());
				hashMaps.remove("NOW_PAGE");

				this.sysProp = util.getProperties("system.properties");
				this.FRIEND_LIST_COUNT = util.toNum( this.sysProp.getProperty("FRIEND_LIST_COUNT") );

				this.PAGE_OBJ = new Pager(this.FRIEND_LIST_COUNT,cnt);
				this.PAGE_OBJ.setNow_page(this.NOW_PAGE);

				paraMap.put("START", this.PAGE_OBJ.getStart());
				paraMap.put("LIMIT", this.PAGE_OBJ.getLimit());
				paraMap.put("SORT", 1);

				// 対象リストの作成
				this.SEND_FRIEND_LIST = this.sqlMap.queryForList("getLineFriendList", paraMap);

				// 日付フォーマット
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

				if( this.SEND_FRIEND_LIST.size() == 0 ) {

					this.FRIEND_TABLE = "<p>配信対象がありません</p>";

				} else {

					this.FRIEND_TABLE += "<div style=\"text-align:left;padding:2px;margin-top:5px;border-bottom:solid 1px #cccccc;\">\n";
					this.FRIEND_TABLE += "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n";
					this.FRIEND_TABLE +=	"<tr>\n";
					this.FRIEND_TABLE += "<td nowrap align=\"left\">\n";
					this.FRIEND_TABLE +=	"Page：\n";
					this.FRIEND_TABLE +=	"<select style=\"width:100px;\" onChange=\"displayFriend(this.value)\">\n";

					for( int idx=1; idx <= PAGE_OBJ.getPage_max(); idx++ ) {

						if( PAGE_OBJ.getNow_page() == idx ) {
							this.FRIEND_TABLE +=	"<option value=\"" + idx + "\" selected>" + idx + "</option>\n";
						} else {
							this.FRIEND_TABLE +=	"<option value=\"" + idx + "\">" + idx + "</option>\n";
						}
					}
					this.FRIEND_TABLE += "</select>\n";
					this.FRIEND_TABLE +=	"</td>\n";
					this.FRIEND_TABLE += "<th nowrap align=\"right\">" + PAGE_OBJ.getList_count() + "件中" + PAGE_OBJ.getPage_st() + "～" + PAGE_OBJ.getPage_ed() + "件目</th>\n";
					this.FRIEND_TABLE += "</tr>\n";
					this.FRIEND_TABLE += "</table>\n";
					this.FRIEND_TABLE += "</div>\n";

	                this.FRIEND_TABLE += "<div class=\"table-scroll-sm\">";
	                this.FRIEND_TABLE += "<table class=\"table table-hover table-bordered mb-0\">";
	                this.FRIEND_TABLE += "<thead class=\"thead-light\">";
	                this.FRIEND_TABLE += "<tr>";
	                this.FRIEND_TABLE += "<th>名前</th>";
	                this.FRIEND_TABLE += "<th>メール連携状態</th>";
	                this.FRIEND_TABLE += "<th>登録日</th>";
	                this.FRIEND_TABLE += "</tr>";
	                this.FRIEND_TABLE += "</thead>";
	                this.FRIEND_TABLE += "<tbody>";

	                for( LineFriend friend : this.SEND_FRIEND_LIST ) {
		                this.FRIEND_TABLE += "<tr>";
		                this.FRIEND_TABLE += "<td>";
		                this.FRIEND_TABLE += "<div class=\"d-flex align-items-center\">";
		                this.FRIEND_TABLE += "<div class=\"c-avatar\">";
		                this.FRIEND_TABLE += "<img class=\"c-avatar-img\" src=\"" + friend.getProfile_image() + "\" alt=\"" + friend.getEmail() + "\">";
		                this.FRIEND_TABLE += "</div>";
		                this.FRIEND_TABLE += "<div class=\"ml-2\">" + friend.getDisplay_name() + "</div>";
		                this.FRIEND_TABLE += "</div>";
		                this.FRIEND_TABLE += "</td>";
		                if( common.util.isNotEmpty(friend.getEmail()) ){
		                	this.FRIEND_TABLE += "<td>○</td>";
		                } else {
		                	this.FRIEND_TABLE += "<td>×</td>";
		                }
		                this.FRIEND_TABLE += "<td>";
		                this.FRIEND_TABLE += sdf.format(friend.getCreate_date());
		                this.FRIEND_TABLE += "</td>";
		                this.FRIEND_TABLE += "</tr>";
	                }

	                this.FRIEND_TABLE += "</tbody>";
	                this.FRIEND_TABLE += "</table>";
	                this.FRIEND_TABLE += "</div>";

				}

				// ContentTypeを設定
				this.res.setContentType("text/plain; charset=UTF-8");

			    // 出力用PrintWriterを取得
			    PrintWriter out = this.res.getWriter();

				//出力
			    out.println(this.FRIEND_TABLE);

				setPath(null);
				return;

			}

		} catch(Exception e){
			this.logE(e);
		}
	}

}
