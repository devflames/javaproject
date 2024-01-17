package page;

import java.util.HashMap;
import java.util.List;

import common.Pager2;
import common.util;
import dto.Entity.LineAttribute;
import dto.Entity.LineFolder;
import dto.Entity.LineFriend;

public class LIN0020Page extends BasePage {

	public String DISPLAY_NAME;
	public String CREATE_DATE_START;
	public String CREATE_DATE_END;
	public int MAIL_STATUS;
	public int STATE;
	public List<LineFriend> LINE_FRIEND_LIST;
	public List<LineFolder> LINE_FOLDER_LIST;
	public String TAG;
	public String TAG_VALUE="";
	public int SORT = 1;
	public Pager2 PAGE_OBJ2;
	public int NOW_PAGE = 1;
	public int FRIEND_LIST_COUNT;
	public String TAG_JSON="";


	@SuppressWarnings("unchecked")
	public void onExec() {

		try {

			HashMap paraMap = this.getBaseQueryParam();
			paraMap.put("LINE_ACCOUNT_ID", this.LINE_ACCOUNT_ID);

			// TAG_VALUEがすでにある場合は初期化
			if(this.TAG_VALUE != "") {
				this.TAG_VALUE = "";
			}

			if( common.util.isNotEmpty(this.TAG) ){
				this.LINE_FOLDER_LIST = LineFolder.loadJson(this.TAG);

				String tag_id = "";
				int i= 1;
				for(LineFolder folder : this.LINE_FOLDER_LIST){

					if(i == this.LINE_FOLDER_LIST.size() ){
						tag_id += common.util.toStr( folder.getTag_id() );
						this.TAG_VALUE += folder.getTag_name();
					} else {
						tag_id += folder.getTag_id() + ",";
						this.TAG_VALUE += folder.getTag_name() + ",";
					}
					i++;
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


			if( "RELOAD".equals(this.getParam("EXEC_TYPE")) ){

				this.DISPLAY_NAME = this.getParam("DISPLAY_NAME");
				this.CREATE_DATE_START = this.getParam("CREATE_DATE_START");
				this.CREATE_DATE_END = this.getParam("CREATE_DATE_END");
				this.MAIL_STATUS = common.util.toNum(this.getParam("MAIL_STATUS"));
				this.STATE = common.util.toNum(this.getParam("STATE"));
				this.TAG = this.getParam("TAG");
				this.SORT = common.util.toNum( this.getParam("SORT") );

			}

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
			paraMap.put("STATE", this.STATE);
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

			this.PAGE_OBJ2 = new Pager2(this.NOW_PAGE,this.FRIEND_LIST_COUNT,cnt);

			paraMap.put("START", this.PAGE_OBJ2.getStart());
			paraMap.put("LIMIT", this.PAGE_OBJ2.getLimit());
			paraMap.put("SORT", this.SORT);

			// 友だち情報の取得
			this.LINE_FRIEND_LIST = (List<LineFriend>) this.sqlMap.queryForList("getLineFriendList", paraMap);

		} catch (Exception e) {

			this.logE(e);
		}

	}
}
