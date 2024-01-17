package page;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import common.Pager;
import common.util;

import dto.Entity.Condition;
import dto.Entity.LineAttribute;
import dto.Entity.LineFolder;
import dto.Entity.LineFriend;
import dto.Entity.StepMail.Scenario;

public class LIN0031Page extends BasePage {

	public String TAG_JSON="";
	public List<LineFolder> LINE_FOLDER_LIST;
	public List<LineFriend> LINE_FRIEND_LIST;
	public int ADDRESS;
	public int SEND_TYPE;
	public String SEND_DATE;
	public String DISPLAY_NAME;
	public String CREATE_DATE_START;
	public String CREATE_DATE_END;
	public int MAIL_STATUS;
	public String TAG="";
	public String TAG_VALUE="";
	public String TAG_VALUE_SET ="";
	public Condition condition;
	public String RETURN_JSON;
	public String EXEC_TYPE;

	public String MESSAGE1;
	public String MESSAGE2;
	public String MESSAGE3;
	public String MESSAGE4;
	public String MESSAGE5;
	public String SEND_IMAGE1;
	public String SEND_IMAGE2;
	public String SEND_IMAGE3;
	public String SEND_IMAGE4;
	public String SEND_IMAGE5;
	public int DEFAULT_BOX_COUNT;

	public List<Scenario> SCENARIO;
	public int TEXT_MAX_COUNT;

	public int NOW_PAGE = 1;
	public Pager PAGE_OBJ;
	public int FRIEND_LIST_COUNT;
	public String FRIEND_TABLE = "";

	@SuppressWarnings("unchecked")
	public void onExec() {

		try {

			HashMap paraMap = this.getBaseQueryParam();
			this.EXEC_TYPE = this.getParam("EXEC_TYPE");
			this.sysProp = util.getProperties("system.properties");
			this.TEXT_MAX_COUNT = util.toNum( this.sysProp.getProperty("TEXT_MAX_COUNT") );

			Calendar now = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
			this.SEND_DATE = sdf.format( now.getTime() );

			if(this.SEND_TYPE == 0){
				this.SEND_TYPE = 2;
			}

			paraMap.put("LINE_ACCOUNT_ID", this.LINE_ACCOUNT_ID);
			paraMap.put("NOT_NULL_TAG_NAME", 1);

			// フォルダ・タグ情報の取得
			this.LINE_FOLDER_LIST = (List<LineFolder>) this.sqlMap.queryForList("getLineFolderList", paraMap);
			this.TAG_VALUE = this.TAG ;

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

			// 友だち全員
			if( "ALLSET".equals(this.EXEC_TYPE) ){

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

				this.TAG_VALUE_SET = common.util.removeLastConma(this.TAG_VALUE_SET);

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

				this.setPath("LIN0031EX.htm");
				return;

			// 確認画面でのキャンセル戻り時
			} else if( "BACK".equals(this.EXEC_TYPE) ){

				this.SEND_DATE = this.getParam("SEND_DATE");

				if( common.util.isNotEmpty(this.MESSAGE2) || common.util.isNotEmpty(this.SEND_IMAGE2) ){
					this.DEFAULT_BOX_COUNT += 1;
				}
				if( common.util.isNotEmpty(this.MESSAGE3) || common.util.isNotEmpty(this.SEND_IMAGE3) ){
					this.DEFAULT_BOX_COUNT += 1;
				}
				if( common.util.isNotEmpty(this.MESSAGE4) || common.util.isNotEmpty(this.SEND_IMAGE4) ){
					this.DEFAULT_BOX_COUNT += 1;
				}
				if( common.util.isNotEmpty(this.MESSAGE5) || common.util.isNotEmpty(this.SEND_IMAGE5) ){
					this.DEFAULT_BOX_COUNT += 1;
				}

			// 絞り込み該当者表示
			} else if( "DISPLAY_FL".equals(this.EXEC_TYPE)) {

				if( common.util.isNotEmpty(this.TAG) ){
					this.LINE_FOLDER_LIST = LineFolder.loadJson(this.TAG);

					String tag_id = "";
					int iii= 1;
					for(LineFolder folder : this.LINE_FOLDER_LIST){

						if(iii == this.LINE_FOLDER_LIST.size() ){
							tag_id += common.util.toStr( folder.getTag_id() );
							this.TAG_VALUE += folder.getTag_name();
						} else {
							tag_id += folder.getTag_id() + ",";
							this.TAG_VALUE += folder.getTag_name() + ",";
						}
						iii++;
					}

					paraMap.put("TAG_ID", tag_id);

					// 友だちフォルダタグ中間テーブルの検索
					if( common.util.isNotEmpty(tag_id) ){
						LineAttribute attribute = (LineAttribute) this.sqlMap.queryForObject("getLineAttributeFriendId",paraMap);
							if( attribute.getFriend_id_list() == null ){
								paraMap.put("FRIEND_ID_LIST", "NULL");
							} else {
								paraMap.put("FRIEND_ID_LIST", attribute.getFriend_id_list());
							}
					}
				}


				this.DISPLAY_NAME = this.getParam("DISPLAY_NAME");
				this.CREATE_DATE_START = this.getParam("CREATE_DATE_START");
				this.CREATE_DATE_END = this.getParam("CREATE_DATE_END");
				this.MAIL_STATUS = common.util.toNum(this.getParam("MAIL_STATUS"));
				this.TAG = this.getParam("TAG");


				// 日付の初期値対策
				if( "____/__/__".equals(this.CREATE_DATE_START) ){
					this.CREATE_DATE_START = "";
				}
				if( "____/__/__".equals(this.CREATE_DATE_END) ){
					this.CREATE_DATE_END = "";
				}


				paraMap.put("DISPLAY_NAME", this.DISPLAY_NAME);
				paraMap.put("CREATE_DATE_START", this.CREATE_DATE_START);
				if( common.util.isNotEmpty(this.CREATE_DATE_END) ){
					paraMap.put("CREATE_DATE_END", this.CREATE_DATE_END+" 23:59:59");
				} else {
					paraMap.put("CREATE_DATE_END", "");
				}
				paraMap.put("MAIL_STATUS", this.MAIL_STATUS);
				paraMap.put("STATE", 0);
				paraMap.put("NOT_NULL_TAG_NAME", 1);

				// フォルダ・タグ情報の取得
				this.LINE_FOLDER_LIST = (List<LineFolder>) this.sqlMap.queryForList("getLineFolderList", paraMap);

				// フォルダオブジェクトをJSONに変換
				int iiii = 0;
				for( LineFolder folder : this.LINE_FOLDER_LIST){

						iiii++;
						if( iiii == this.LINE_FOLDER_LIST.size() ){
							this.TAG_JSON += LineFolder.toStringJson(folder);
						} else {
							this.TAG_JSON += LineFolder.toStringJson(folder)+",";
						}

				}

				// 友だち件数の取得
				int cnt = common.util.toNum(this.sqlMap.queryForObject("getLineFriendCount", paraMap));

				// ページ制御
				if ( "RELOAD".equals(this.getParam("EXEC_TYPE")) ) {
					this.NOW_PAGE =  common.util.toNum( this.getSessionParam("NOW_PAGE") );
				}else {

					// ページ制御の初期化
					HashMap<String, String> hashMaps = (HashMap<String, String>)this.sess.get(this.getClass().getSimpleName());
					hashMaps.remove("NOW_PAGE");
				}

				this.sysProp = util.getProperties("system.properties");
				this.FRIEND_LIST_COUNT = util.toNum( this.sysProp.getProperty("FRIEND_LIST_COUNT") );

				this.PAGE_OBJ = new Pager(this.FRIEND_LIST_COUNT,cnt);
				this.PAGE_OBJ.setNow_page(this.NOW_PAGE);

				paraMap.put("START", this.PAGE_OBJ.getStart());
				paraMap.put("LIMIT", this.PAGE_OBJ.getLimit());
				paraMap.put("SORT", 1);

				// 友だち情報の取得
				this.LINE_FRIEND_LIST = (List<LineFriend>) this.sqlMap.queryForList("getLineFriendList", paraMap);

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

                for( LineFriend friend : this.LINE_FRIEND_LIST ) {
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
