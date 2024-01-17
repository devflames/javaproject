package page;

import java.util.HashMap;
import java.util.List;

import common.util;
import dto.Entity.LineFriend;
import dto.Entity.StepMail.Scenario;

public class LIN0021Page extends BasePage {

	public List<LineFriend> LINE_FRIEND_LIST;
	public int FRIEND_LIMIT;
	public int FRIEND_COUNT;
	public int FRIEND_ID;
	public String LINE_ID;
	public int STATE;
	public int SUPPORT;
	public String TYPE;
	public int CONTINUE;
	public String DISPLAY_NAME;
	public int PAGING_FLG;			// 友だちリストのページング判定に使用　ページング経由なら友だちリストのスクロールを下に

	public List<Scenario> SCENARIO;
	public int TEXT_MAX_COUNT;

	@SuppressWarnings("unchecked")
	public void onExec() {

		try {

			HashMap paraMap = this.getBaseQueryParam();
			this.sysProp = util.getProperties("system.properties");
			this.TEXT_MAX_COUNT = util.toNum( this.sysProp.getProperty("TEXT_MAX_COUNT") );

			this.FRIEND_ID = common.util.toNum(this.getParam("fid"));
			this.FRIEND_LIMIT = common.util.toNum(this.getParam("friend_limit"));
			this.STATE = common.util.toNum(this.getParam("state"));
			this.SUPPORT = common.util.toNum(this.getParam("support"));
			this.TYPE = this.getParam("type");
			this.DISPLAY_NAME = this.getParam("display_name");
			this.PAGING_FLG = common.util.toNum( this.getParam("paging_flg") );

			paraMap.put("LINE_ACCOUNT_ID", this.LINE_ACCOUNT_ID);

			// 状態変更
			if( "1".equals( this.TYPE ) ){
				paraMap.put("STATE", this.STATE);
				paraMap.put("FRIEND_ID", this.FRIEND_ID);
				this.sqlMap.update("updLineFriendState",paraMap);

			// サポート変更
			} else if( "2".equals( this.TYPE) ){
				paraMap.put("SUPPORT", this.SUPPORT);
				paraMap.put("FRIEND_ID", this.FRIEND_ID);
				this.sqlMap.update("updLineFriendSupport",paraMap);
			}

			if( this.FRIEND_LIMIT == 0 ){
				this.FRIEND_LIMIT = 100;
			}

			/*
			// 初期値設定
			if( this.LIMIT == 0 ){
				this.LIMIT = 100;
			}
			this.CONTINUE = this.LIMIT + 10;


			// 友だち件数の取得
			paraMap.put("STATE", 99);
			paraMap.put("DISPLAY_NAME", this.DISPLAY_NAME);
			this.FRIEND_COUNT = (int) this.sqlMap.queryForObject("getLineFriendCount",paraMap);

			// 友だち一覧の取得
			paraMap.put("START", 0);
			paraMap.put("LIMIT", this.LIMIT);
			paraMap.put("SORT",2);
			if( this.FRIEND_ID > 0 ){
				paraMap.put("FIXED_ID", this.FRIEND_ID);
			}
			this.LINE_FRIEND_LIST = (List<LineFriend>) this.sqlMap.queryForList("getLineFriendListForTalk", paraMap);

			// 初期表示するLINE_IDの取得
			if( this.LINE_FRIEND_LIST.size() > 0 ){
				this.LINE_ID = this.LINE_FRIEND_LIST.get(0).getLine_id();
				this.FRIEND_ID = this.LINE_FRIEND_LIST.get(0).getFriend_id();
			}
			*/

			// LINE連携用　シナリオ検索
			paraMap.put("SNO", this.LBL_SNO);
			paraMap.put("SCENARIO_NAME", this.LBL_SNAME);
			paraMap.put("TB_SCENARIO", this.TB_SCENARIO);
			paraMap.put("TB_PAGE_FRAME", this.TB_PAGE_FRAME);
			this.SCENARIO = (List<Scenario>) this.sqlMapSM.queryForList("getScenarioList",paraMap);


			// モバイル判定
			boolean mobile = common.util.isMobile(this.req);

			if( mobile ) {
				this.setPath("/LIN0021SP.htm");
				return;
			}

		} catch( Exception e ){
			this.logE(e);
		}
	}
}
