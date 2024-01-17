package page;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import common.RmhException;
import common.util;
import common.builder.QueryBuilder;
import common.builder.QueryBuilderLineAutoBiz;
import dto.Entity.LineBot;
import dto.Entity.LineFolder;
import dto.Entity.LineFriend;
import dto.Entity.LineQue;
import dto.Entity.LineTempJSON;
import dto.Entity.LineAPI.Message.Consumption;
import dto.Entity.LineAPI.Webhooks.Messages.ImageMessage;
import dto.Entity.LineAPI.Webhooks.Messages.TextMessage;
import dto.Entity.StepMail.Scenario;

public class LIN0032KPage extends BasePage {

	public int ADDRESS;
	public String DISPLAY_NAME;
	public String CREATE_DATE_START;
	public String CREATE_DATE_END;
	public int MAIL_STATUS;
	public int SEND_TYPE;
	public String SEND_DATE;
	public String TAG;
	public String DATE;
	public String FRIEND_ID_LIST;
	public Timestamp SEND_DATETIME;

	public List<LineFolder> LINE_FOLDER_LIST = new ArrayList<LineFolder>();;
	public List<LineFolder> LINE_FOLDER_LIST2 = new ArrayList<LineFolder>();
	public List<LineFriend> LINE_FRIEND_LIST;
	public String TAG_JSON="";
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

	public String MESSAGE_JSON1;
	public String MESSAGE_JSON2;
	public String MESSAGE_JSON3;
	public String MESSAGE_JSON4;
	public String MESSAGE_JSON5;

	public String IMAGE_JSON1;
	public String IMAGE_JSON2;
	public String IMAGE_JSON3;
	public String IMAGE_JSON4;
	public String IMAGE_JSON5;

	public String ERR_MSG;
	public List<String> TAG_LIST = new ArrayList<String>();

	public int QUE_ID;
	public int TEMP_ID;
	public String PICTURE_URL;
	public String REDIRECT_URL;

	public util UTIL = new util();
	public List<Scenario> SCENARIO;
	public String FRIEND_TABLE="";
	public int TEXT_MAX_COUNT;
	public int FREE_MESSAGE;
	public int QUE_CNT;
	public int WARNING;	// 0:警告なし　1:警告あり
	public String EXEC_TYPE;
	public String RETURN_JSON;
	public LineTempJSON temp;


	@SuppressWarnings("unchecked")
	public void onExec() {

		try {

			HashMap paraMap = this.getBaseQueryParam();
			this.sysProp = util.getProperties("system.properties");
			this.TEXT_MAX_COUNT = util.toNum( this.sysProp.getProperty("TEXT_MAX_COUNT") );

			this.EXEC_TYPE = this.getParam("EXEC_TYPE");

			// 送信先
			this.ADDRESS = common.util.toNum(this.getParam("ADDRESS"));

			// 絞り込み条件
			this.FRIEND_ID_LIST = this.getParam("FRIEND_ID_LIST");
			this.DISPLAY_NAME = this.getParam("DISPLAY_NAME");
			this.CREATE_DATE_START = this.getParam("CREATE_DATE_START");
			if( "____/__/__".equals(this.CREATE_DATE_START) ){
				this.CREATE_DATE_START = "";
			}

			this.CREATE_DATE_END = this.getParam("CREATE_DATE_END");
			if( "____/__/__".equals(this.CREATE_DATE_END) ){
				this.CREATE_DATE_END = "";
			}

			this.MAIL_STATUS = common.util.toNum(this.MAIL_STATUS);
			this.TAG = this.getParam("TAG");
			this.SEND_TYPE = common.util.toNum(this.SEND_TYPE);
			if(this.SEND_TYPE == 1){
				Calendar now = Calendar.getInstance();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
				this.SEND_DATE = sdf.format( now.getTime() );
			} else {
				this.SEND_DATE = this.getParam("SEND_DATE");
			}
			this.DATE = this.getParam("DATE");

			if( this.QUE_ID > 0 ){
				paraMap.put("QUE_ID", this.QUE_ID);
			}

			if( this.TEMP_ID > 0 ){
				paraMap.put("TEMP_ID", this.TEMP_ID);
			}


			// メッセージ1を設定
			this.MESSAGE1 = this.getParam("MESSAGE1");
			this.SEND_IMAGE1 = this.getParam("SEND_IMAGE1");


			// メッセージ2
			// メッセージ1が空でない場合にメッセージ2を設定
			if(common.util.isNotEmpty(this.MESSAGE1) || common.util.isNotEmpty(this.SEND_IMAGE1)) {

				this.MESSAGE2 = this.getParam("MESSAGE2");
				this.SEND_IMAGE2 = this.getParam("SEND_IMAGE2");

			// メッセージ1が空の場合は繰り上げ
			} else {

				if(common.util.isNotEmpty(this.MESSAGE2) && common.util.isEmpty(this.SEND_IMAGE2)) {

					this.MESSAGE1 = this.getParam("MESSAGE2");
					this.MESSAGE2 = "";
					this.SEND_IMAGE2 = this.getParam("SEND_IMAGE2");


				} if(common.util.isEmpty(this.MESSAGE2) && common.util.isNotEmpty(this.SEND_IMAGE2)) {

					this.SEND_IMAGE1 = this.getParam("SEND_IMAGE2");
					this.MESSAGE2 = this.getParam("MESSAGE2");
					this.SEND_IMAGE2 = "";

				}
			}


			// メッセージ3
			// メッセージ1,2が空でない場合にメッセージ3を設定
			if( (common.util.isNotEmpty(this.MESSAGE1) || common.util.isNotEmpty(this.SEND_IMAGE1)) &&
				(common.util.isNotEmpty(this.MESSAGE2) || common.util.isNotEmpty(this.SEND_IMAGE2)) ) {

				this.MESSAGE3 = this.getParam("MESSAGE3");
				this.SEND_IMAGE3 = this.getParam("SEND_IMAGE3");

			// メッセージ1,2が空の場合は繰り上げで設定
			} else if(common.util.isEmpty(this.MESSAGE1) && common.util.isEmpty(this.SEND_IMAGE1) &&
				common.util.isEmpty(this.MESSAGE2) && common.util.isEmpty(this.SEND_IMAGE2)) {

				if(common.util.isNotEmpty(this.MESSAGE3) && common.util.isEmpty(this.SEND_IMAGE3)) {

					this.MESSAGE1 = this.getParam("MESSAGE3");
					this.MESSAGE3 = "";
					this.SEND_IMAGE3 = this.getParam("SEND_IMAGE3");


				} if(common.util.isEmpty(this.MESSAGE3) && common.util.isNotEmpty(this.SEND_IMAGE3)) {

					this.SEND_IMAGE1 = this.getParam("SEND_IMAGE3");
					this.MESSAGE3 = this.getParam("MESSAGE3");
					this.SEND_IMAGE3 = "";

				}

			// メッセージ2が空の場合は繰り上げで設定
			} else if( (common.util.isNotEmpty(this.MESSAGE1) || common.util.isNotEmpty(this.SEND_IMAGE1)) &&
				common.util.isEmpty(this.MESSAGE2) && common.util.isEmpty(this.SEND_IMAGE2)) {

				if(common.util.isNotEmpty(this.MESSAGE3) && common.util.isEmpty(this.SEND_IMAGE3)) {

					this.MESSAGE2 = this.getParam("MESSAGE3");
					this.MESSAGE3 = "";
					this.SEND_IMAGE3 = this.getParam("SEND_IMAGE3");

				} if(common.util.isEmpty(this.MESSAGE3) && common.util.isNotEmpty(this.SEND_IMAGE3)) {

					this.SEND_IMAGE2 = this.getParam("SEND_IMAGE3");
					this.MESSAGE3 = this.getParam("MESSAGE3");
					this.SEND_IMAGE3 = "";

				}
			}


			// メッセージ4
			// メッセージ1,2,3が空でない場合にメッセージ4を設定
			if( (common.util.isNotEmpty(this.MESSAGE1) || common.util.isNotEmpty(this.SEND_IMAGE1)) &&
				(common.util.isNotEmpty(this.MESSAGE2) || common.util.isNotEmpty(this.SEND_IMAGE2)) &&
				(common.util.isNotEmpty(this.MESSAGE3) || common.util.isNotEmpty(this.SEND_IMAGE3)) ) {

				this.MESSAGE4 = this.getParam("MESSAGE4");
				this.SEND_IMAGE4 = this.getParam("SEND_IMAGE4");

			// メッセージ1,2,3が空の場合は繰り上げで設定
			} else if(common.util.isEmpty(this.MESSAGE1) && common.util.isEmpty(this.SEND_IMAGE1) &&
					common.util.isEmpty(this.MESSAGE2) && common.util.isEmpty(this.SEND_IMAGE2) &&
					common.util.isEmpty(this.MESSAGE3) && common.util.isEmpty(this.SEND_IMAGE3)) {

				if(common.util.isNotEmpty(this.MESSAGE4) && common.util.isEmpty(this.SEND_IMAGE4)) {

					this.MESSAGE1 = this.getParam("MESSAGE4");
					this.MESSAGE4 = "";
					this.SEND_IMAGE4 = this.getParam("SEND_IMAGE4");

				} if( common.util.isEmpty(this.MESSAGE4)  && common.util.isNotEmpty(this.SEND_IMAGE4) ) {

					this.SEND_IMAGE1 = this.getParam("SEND_IMAGE4");
					this.MESSAGE4 = this.getParam("MESSAGE4");
					this.SEND_IMAGE4 = "";

				}

			// メッセージ2,3が空の場合は繰り上げで設定
			} else if( (common.util.isNotEmpty(this.MESSAGE1) || common.util.isNotEmpty(this.SEND_IMAGE1)) &&
					common.util.isEmpty(this.MESSAGE2) && common.util.isEmpty(this.SEND_IMAGE2) &&
					common.util.isEmpty(this.MESSAGE3) && common.util.isEmpty(this.SEND_IMAGE3) ) {

				if(common.util.isNotEmpty(this.MESSAGE4) && common.util.isEmpty(this.SEND_IMAGE4)) {

					this.MESSAGE2 = this.getParam("MESSAGE4");
					this.MESSAGE4 = "";
					this.SEND_IMAGE4 = this.getParam("SEND_IMAGE4");

				} if(common.util.isEmpty(this.MESSAGE4) && common.util.isNotEmpty(this.SEND_IMAGE4)) {

					this.SEND_IMAGE2 = this.getParam("SEND_IMAGE4");
					this.MESSAGE4 = this.getParam("MESSAGE4");
					this.SEND_IMAGE4 = "";

				}

			// メッセージ3が空の場合は繰り上げで設定
			} else if( (common.util.isNotEmpty(this.MESSAGE1) || common.util.isNotEmpty(this.SEND_IMAGE1)) &&
					(common.util.isNotEmpty(this.MESSAGE2) || common.util.isNotEmpty(this.SEND_IMAGE2)) &&
					common.util.isEmpty(this.MESSAGE3) && common.util.isEmpty(this.SEND_IMAGE3) ) {

				if(common.util.isNotEmpty(this.MESSAGE4) && common.util.isEmpty(this.SEND_IMAGE4)) {

					this.MESSAGE3 = this.getParam("MESSAGE4");
					this.MESSAGE4 = "";
					this.SEND_IMAGE4 = this.getParam("SEND_IMAGE4");

				} if(common.util.isEmpty(this.MESSAGE4) && common.util.isNotEmpty(this.SEND_IMAGE4)) {

					this.SEND_IMAGE3 = this.getParam("SEND_IMAGE4");
					this.MESSAGE4 = this.getParam("MESSAGE4");
					this.SEND_IMAGE4 = "";

				}
			}


			// メッセージ5
			// メッセージ1,2,3,4が空でない場合にメッセージ5を設定
			if( (common.util.isNotEmpty(this.MESSAGE1) || common.util.isNotEmpty(this.SEND_IMAGE1)) &&
				(common.util.isNotEmpty(this.MESSAGE2) || common.util.isNotEmpty(this.SEND_IMAGE2)) &&
				(common.util.isNotEmpty(this.MESSAGE3) || common.util.isNotEmpty(this.SEND_IMAGE3)) &&
				(common.util.isNotEmpty(this.MESSAGE4) || common.util.isNotEmpty(this.SEND_IMAGE4)) ) {

				this.MESSAGE5 = this.getParam("MESSAGE5");
				this.SEND_IMAGE5 = this.getParam("SEND_IMAGE5");

			// メッセージ1,2,3,4が空の場合は繰り上げで設定
			} else if( common.util.isEmpty(this.MESSAGE1) && common.util.isEmpty(this.SEND_IMAGE1) &&
					common.util.isEmpty(this.MESSAGE2) && common.util.isEmpty(this.SEND_IMAGE2) &&
					common.util.isEmpty(this.MESSAGE3) && common.util.isEmpty(this.SEND_IMAGE3) &&
					common.util.isEmpty(this.MESSAGE4) && common.util.isEmpty(this.SEND_IMAGE4) ) {

				if(common.util.isNotEmpty(this.MESSAGE5) && common.util.isEmpty(this.SEND_IMAGE5)) {

					this.MESSAGE1 = this.getParam("MESSAGE5");
					this.MESSAGE5 = "";
					this.SEND_IMAGE5 = this.getParam("SEND_IMAGE5");

				} if(common.util.isEmpty(this.MESSAGE5)  && common.util.isNotEmpty(this.SEND_IMAGE5)) {

					this.SEND_IMAGE1 = this.getParam("SEND_IMAGE5");
					this.MESSAGE5 = this.getParam("MESSAGE5");
					this.SEND_IMAGE5 = "";

				}

			// メッセージ2,3,4が空の場合は繰り上げで設定
			} else if( (common.util.isNotEmpty(this.MESSAGE1) || common.util.isNotEmpty(this.SEND_IMAGE1)) &&
					(common.util.isEmpty(this.MESSAGE2) && common.util.isEmpty(this.SEND_IMAGE2)) &&
					(common.util.isEmpty(this.MESSAGE3) && common.util.isEmpty(this.SEND_IMAGE3)) &&
					(common.util.isEmpty(this.MESSAGE4) && common.util.isEmpty(this.SEND_IMAGE4)) ) {

				if( common.util.isNotEmpty(this.MESSAGE5) && common.util.isEmpty(this.SEND_IMAGE5) ) {

					this.MESSAGE2 = this.getParam("MESSAGE5");
					this.MESSAGE5 = "";
					this.SEND_IMAGE5 = this.getParam("SEND_IMAGE5");

				} if(common.util.isEmpty(this.MESSAGE5) && common.util.isNotEmpty(this.SEND_IMAGE5)) {

					this.SEND_IMAGE2 = this.getParam("SEND_IMAGE5");
					this.MESSAGE5 = this.getParam("MESSAGE5");
					this.SEND_IMAGE5 = "";

				}

			// メッセージ3,4が空の場合は繰り上げで設定
			} else if( (common.util.isNotEmpty(this.MESSAGE1) || common.util.isNotEmpty(this.SEND_IMAGE1)) &&
					(common.util.isNotEmpty(this.MESSAGE2) || common.util.isNotEmpty(this.SEND_IMAGE2)) &&
					common.util.isEmpty(this.MESSAGE3) && common.util.isEmpty(this.SEND_IMAGE3) &&
					common.util.isEmpty(this.MESSAGE4) && common.util.isEmpty(this.SEND_IMAGE4) ) {

				if(common.util.isNotEmpty(this.MESSAGE5) && common.util.isEmpty(this.SEND_IMAGE5)) {

					this.MESSAGE3 = this.getParam("MESSAGE5");
					this.MESSAGE5 = "";
					this.SEND_IMAGE5 = this.getParam("SEND_IMAGE5");

				} if(common.util.isEmpty(this.MESSAGE5) && common.util.isNotEmpty(this.SEND_IMAGE5)) {

					this.SEND_IMAGE3 = this.getParam("SEND_IMAGE5");
					this.MESSAGE5 = this.getParam("MESSAGE5");
					this.SEND_IMAGE5 = "";

				}

			// メッセージ4が空の場合は繰り上げで設定
			} else if( (common.util.isNotEmpty(this.MESSAGE1) || common.util.isNotEmpty(this.SEND_IMAGE1)) &&
					(common.util.isNotEmpty(this.MESSAGE2) || common.util.isNotEmpty(this.SEND_IMAGE2)) &&
					(common.util.isNotEmpty(this.MESSAGE3) || common.util.isNotEmpty(this.SEND_IMAGE3)) &&
					common.util.isEmpty(this.MESSAGE4) && common.util.isEmpty(this.SEND_IMAGE4) ) {

				if(common.util.isNotEmpty(this.MESSAGE5) && common.util.isEmpty(this.SEND_IMAGE5)) {

					this.MESSAGE4 = this.getParam("MESSAGE5");
					this.MESSAGE5 = "";
					this.SEND_IMAGE5 = this.getParam("SEND_IMAGE5");

				} if(common.util.isEmpty(this.MESSAGE5) && common.util.isNotEmpty(this.SEND_IMAGE5)) {

					this.SEND_IMAGE4 = this.getParam("SEND_IMAGE5");
					this.MESSAGE5 = this.getParam("MESSAGE5");
					this.SEND_IMAGE5 = "";

				}
			}

			// 編集中に配信予約日時が現在時刻より前の時刻になった場合はエラー 下書の場合にはチェックしない
			if( !"DRAFT".equals(this.EXEC_TYPE) && !"DRAFT_ADD".equals(this.EXEC_TYPE)){
				paraMap.put("LINE_ACCOUNT_ID", this.LINE_ACCOUNT_ID);
				paraMap.put("ACCOUNT", this.ACCOUNT);

				if( this.QUE_ID > 0 ){
					LineQue que = (LineQue)this.sqlMap.queryForObject( "getLineQue2" , paraMap);
					this.SEND_DATETIME = que.getSend_datetime();
					Timestamp now = new Timestamp(System.currentTimeMillis());

					if( this.SEND_DATETIME.before(now) ){

						this.ERR_MSG = "ERROR :ID" + this.QUE_ID + "は配信時刻を過ぎたため更新できません。";
						getContext().setRequestAttribute("ERR_MSG", ERR_MSG);
						getContext().setRequestAttribute("TYPE", "2");
						setForward( "LIN0030.htm" );
						return;

					}
				}
			}

			// 当月の予約の場合は無料メッセージ上限数を超えていないかチェック

			// 日付フォーマットを定義
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM");

			// 文字列をCalendarオブジェクトに変換
	        Calendar cal = Calendar.getInstance();
	        cal.setTime(sdf.parse(this.SEND_DATE));

	        // 現在の月を取得
	        Calendar currentMonth = Calendar.getInstance();
	        currentMonth.set(Calendar.DAY_OF_MONTH, 1);

	        // SEND_DATE が当月であるかを判定
	        if (cal.get(Calendar.YEAR) == currentMonth.get(Calendar.YEAR) &&
	            cal.get(Calendar.MONTH) == currentMonth.get(Calendar.MONTH) &&
	            !"DRAFT_ADD".equals(this.EXEC_TYPE)
	            ) {

	        	// 現在日時の取得
				Calendar now2 = Calendar.getInstance();
				paraMap.put("SENDYM", sdf.format(now2.getTime()));
				paraMap.put("LINE_ACCOUNT_ID", this.LINE_ACCOUNT_ID);
				this.QUE_CNT = (int) this.sqlMap.queryForObject("getLineQueCnt2",paraMap);

				// 当月のメッセージ利用状況を取得する
				String checkUsage = common.util.httpGet("https://api.line.me/v2/bot/message/quota/consumption", this.CHANEL_ACCESS_TOKEN );
				Consumption total = Consumption.loadJson(checkUsage);

				// 残りメッセージ送信数の取得
				this.FREE_MESSAGE = this.LIMIT - (this.QUE_CNT + total.getTotalUsage());

				// 今回の配信数を取得
				String check_tag_id = "";
				int check_i= 1;
				for(LineFolder folder : this.LINE_FOLDER_LIST){

					if(check_i == this.LINE_FOLDER_LIST.size() ){
						check_tag_id += common.util.toStr( folder.getTag_id() );
					} else {
						check_tag_id += folder.getTag_id() + ",";
					}
					check_i++;
				}

				QueryBuilder check_qb;
				check_qb = new QueryBuilderLineAutoBiz();
				check_qb.SCHEMA = this.SCHEMA;
				check_qb.LINE_ACCOUNT_ID = this.LINE_ACCOUNT_ID;
				check_qb.TAG_ID_LIST = check_tag_id;
				check_qb.DISPLAY_NAME = this.DISPLAY_NAME;
				check_qb.CREATE_DATE_START = this.CREATE_DATE_START;
				check_qb.CREATE_DATE_END = this.CREATE_DATE_END;
				check_qb.STATE = 0;
				check_qb.MAIL_STATUS = this.MAIL_STATUS;
				int sendCnt = common.util.toNum(this.sqlMap.queryForObject("getLineListCnt", check_qb.getCountQuery()));

				// 無料メッセージ上限数を超えていないかチェック
				int check = this.FREE_MESSAGE - sendCnt;
				if( check < 0 && this.SEND_LIMIT == 1) {
					this.WARNING = 1;
				}

	        }


			// 必須チェック
			if ( "CHECK".equals(this.EXEC_TYPE) ) {

				paraMap.put("LINE_ACCOUNT_ID", this.LINE_ACCOUNT_ID);

				// LINE連携用　シナリオ検索
				paraMap.put("SNO", this.LBL_SNO);
				paraMap.put("SCENARIO_NAME", this.LBL_SNAME);
				paraMap.put("TB_SCENARIO", this.TB_SCENARIO);
				paraMap.put("TB_PAGE_FRAME", this.TB_PAGE_FRAME);
				this.SCENARIO = (List<Scenario>) this.sqlMapSM.queryForList("getScenarioList",paraMap);

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

				if( this.ADDRESS == 0 ){
					throw new RmhException("送信先を設定して下さい");
				}

				if( this.SEND_TYPE == 0 ){
					throw new RmhException("配信日時を指定して下さい");
				}

				if( this.SEND_TYPE == 2 && "".equals(this.SEND_DATE) ){
					throw new RmhException("予約日時を指定して下さい");
				}

				if(
						"".equals(this.MESSAGE1) && "".equals(this.MESSAGE2) && "".equals(this.MESSAGE3) && "".equals(this.MESSAGE4) && "".equals(this.MESSAGE5) &&
						"".equals(this.SEND_IMAGE1) && "".equals(this.SEND_IMAGE2) && "".equals(SEND_IMAGE3) && "".equals(SEND_IMAGE4) && "".equals(SEND_IMAGE5)
				){
					throw new RmhException("配信メッセージは1つ以上設定して下さい");
				}

			// 登録・下書保存
			} else if( "ADD".equals(this.EXEC_TYPE) || "DRAFT_ADD".equals(this.EXEC_TYPE) ){

				// 初期化
				this.LINE_FOLDER_LIST = new ArrayList<LineFolder>();

				this.FRIEND_ID_LIST = this.getParam("FRIEND_ID_LIST");
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
					} else {
						tag_id += folder.getTag_id() + ",";
					}
					ii++;
				}

				if( common.util.isNotEmpty(this.MESSAGE1) ){

					TextMessage text = new TextMessage();
					text.setType("text");
					text.setText(this.MESSAGE1);
					this.MESSAGE_JSON1 = text.toStringJson(text);
				}
				if( common.util.isNotEmpty(this.SEND_IMAGE1) ){

					ImageMessage image = new ImageMessage();
					image.setType("image");
					image.setOriginalContentUrl(this.SEND_IMAGE1);
					image.setPreviewImageUrl(this.SEND_IMAGE1);
					this.MESSAGE_JSON1 = image.toStringJson(image);
				}

				if( common.util.isNotEmpty(this.MESSAGE2) ){

					TextMessage text = new TextMessage();
					text.setType("text");
					text.setText(this.MESSAGE2);
					this.MESSAGE_JSON2 = text.toStringJson(text);
				}
				if( common.util.isNotEmpty(this.SEND_IMAGE2) ){

					ImageMessage image = new ImageMessage();
					image.setType("image");
					image.setOriginalContentUrl(this.SEND_IMAGE2);
					image.setPreviewImageUrl(this.SEND_IMAGE2);
					this.MESSAGE_JSON2 = image.toStringJson(image);
				}

				if( common.util.isNotEmpty(this.MESSAGE3) ){

					TextMessage text = new TextMessage();
					text.setType("text");
					text.setText(this.MESSAGE3);
					this.MESSAGE_JSON3 = text.toStringJson(text);
				}
				if( common.util.isNotEmpty(this.SEND_IMAGE3) ){

					ImageMessage image = new ImageMessage();
					image.setType("image");
					image.setOriginalContentUrl(this.SEND_IMAGE3);
					image.setPreviewImageUrl(this.SEND_IMAGE3);
					this.MESSAGE_JSON3 = image.toStringJson(image);
				}

				if( common.util.isNotEmpty(this.MESSAGE4) ){

					TextMessage text = new TextMessage();
					text.setType("text");
					text.setText(this.MESSAGE4);
					this.MESSAGE_JSON4 = text.toStringJson(text);
				}
				if( common.util.isNotEmpty(this.SEND_IMAGE4) ){

					ImageMessage image = new ImageMessage();
					image.setType("image");
					image.setOriginalContentUrl(this.SEND_IMAGE4);
					image.setPreviewImageUrl(this.SEND_IMAGE4);
					this.MESSAGE_JSON4 = image.toStringJson(image);
				}

				if( common.util.isNotEmpty(this.MESSAGE5) ){

					TextMessage text = new TextMessage();
					text.setType("text");
					text.setText(this.MESSAGE5);
					this.MESSAGE_JSON5 = text.toStringJson(text);
				}
				if( common.util.isNotEmpty(this.SEND_IMAGE5) ){

					ImageMessage image = new ImageMessage();
					image.setType("image");
					image.setOriginalContentUrl(this.SEND_IMAGE5);
					image.setPreviewImageUrl(this.SEND_IMAGE5);
					this.MESSAGE_JSON5 = image.toStringJson(image);
				}

				// キューを更新
				QueryBuilder qb;
				qb = new QueryBuilderLineAutoBiz();

				qb.SCHEMA = this.SCHEMA;
				qb.LINE_ACCOUNT_ID = this.LINE_ACCOUNT_ID;
				qb.TAG_ID_LIST = tag_id;
				qb.FRIEND_ID_LIST = this.FRIEND_ID_LIST;
				qb.DISPLAY_NAME = this.DISPLAY_NAME;
				qb.CREATE_DATE_START = this.CREATE_DATE_START;
				qb.CREATE_DATE_END = this.CREATE_DATE_END;
				qb.STATE = 0;
				qb.MAIL_STATUS = this.MAIL_STATUS;

				int cnt = common.util.toNum(this.sqlMap.queryForObject("getLineListCnt", qb.getCountQuery()));

				paraMap.put("STATUS", 5);
				paraMap.put("SEND_TYPE", 2);
				paraMap.put("SEND_DATETIME", this.SEND_DATE);
				paraMap.put("SEND_COUNT", cnt );
				paraMap.put("XTRACTION_KEY", qb.getSelectString());
				paraMap.put("COUNT_QUERY", qb.getCountQuery());
				paraMap.put("MESSAGE1", this.MESSAGE_JSON1);
				paraMap.put("MESSAGE2", this.MESSAGE_JSON2);
				paraMap.put("MESSAGE3", this.MESSAGE_JSON3);
				paraMap.put("MESSAGE4", this.MESSAGE_JSON4);
				paraMap.put("MESSAGE5", this.MESSAGE_JSON5);
				paraMap.put("SCHEMA", this.SCHEMA);

				// 更新
				if( "ADD".equals(this.EXEC_TYPE) ){

					if( this.TEMP_ID > 0 ){

						try {
							this.sqlMap.startTransaction();

							// 下書の内容をもとにキューに登録
							this.sqlMap.insert("addLineQue",paraMap);

							// 下書を削除
							this.sqlMap.delete("delLineTemp",paraMap);

							// トランザクションコミット
							this.sqlMap.commitTransaction();

						} catch(Exception e){
							this.logE(e);
						} finally {
							this.sqlMap.endTransaction();
						}

					} else {

						// 通常の更新
						this.sqlMap.insert("updLineQue",paraMap);

					}
					this.REDIRECT_URL = "!LIN0030!";
					this.setPath("REDIRECT.htm");
					return;

				// 下書更新
				} else if( "DRAFT_ADD".equals(this.EXEC_TYPE)){

					paraMap.put("TEMP_ID", this.TEMP_ID);
					this.sqlMap.insert("updLineTemp",paraMap);

					temp = new LineTempJSON();
					temp.setTemp_id(this.TEMP_ID);
					this.RETURN_JSON = temp.toStringJson(temp);
					this.setPath("LIN0032EX.htm");
					return;
				}

			}

			// chanel access tokenからボット情報を取得
			String response = common.util.httpGet("https://api.line.me/v2/bot/info", this.CHANEL_ACCESS_TOKEN );

			if( common.util.isEmpty(response)){
				throw new Exception("ボット情報を取得できませんでした");
			}

			LineBot bot = LineBot.loadJson(response);
			this.PICTURE_URL = bot.getPictureUrl();

			if( common.util.isNotEmpty(this.TAG)){
				this.LINE_FOLDER_LIST2 = LineFolder.loadJson(this.TAG);
			}

			for( LineFolder folder : this.LINE_FOLDER_LIST2){
				TAG_LIST.add(folder.getTag_name());
			}


		} catch(RmhException ex){
			this.ERR_MSG = "ERROR : " + ex.getMessage();

			this.setPath("LIN0032.htm");
			return;
		} catch( Exception e ){
			this.logE(e);
		}
	}
}
