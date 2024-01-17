package page;

import java.util.HashMap;
import java.util.List;

import dto.Entity.LineFriend;

public class LIN0021EXPage extends BasePage {

	public List<LineFriend> LINE_FRIEND_LIST;
	public int FRIEND_LIMIT;
	public int FRIEND_COUNT;
	public int FRIEND_ID;
	public String LINE_ID;
	public int CONTINUE;
	public String DISPLAY_NAME;
	public int RELOAD_FLG;			// pusherリロード判定に使用　リロードであればチャット&プロフィールのリロードはしない


	@SuppressWarnings("unchecked")
	public void onExec() {

		try {

			HashMap paraMap = this.getBaseQueryParam();

			this.FRIEND_ID = common.util.toNum(this.getParam("FRIEND_ID"));
			this.FRIEND_LIMIT = common.util.toNum(this.getParam("LIMIT"));
			this.DISPLAY_NAME = this.getParam("DISPLAY_NAME");
			this.RELOAD_FLG = common.util.toNum( this.getParam("RELOAD_FLG"));

			paraMap.put("LINE_ACCOUNT_ID", this.LINE_ACCOUNT_ID);

			// 初期値設定

			if( this.FRIEND_LIMIT == 0 ){
				this.FRIEND_LIMIT = 100;
			}
			this.CONTINUE = this.FRIEND_LIMIT + 10;


			// 友だち件数の取得
			paraMap.put("STATE", 99);
			paraMap.put("DISPLAY_NAME", this.DISPLAY_NAME);
			this.FRIEND_COUNT = (int) this.sqlMap.queryForObject("getLineFriendCount",paraMap);

			// 友だち一覧の取得
			paraMap.put("START", 0);
			paraMap.put("LIMIT", this.FRIEND_LIMIT);
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


			// モバイル判定
			boolean mobile = common.util.isMobile(this.req);

			if( mobile ) {
				this.setPath("/LIN0021EXSP.htm");
				return;
			}

		} catch( Exception e ){
			this.logE(e);
		}
	}
}
