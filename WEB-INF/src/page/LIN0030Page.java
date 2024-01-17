package page;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import common.Pager2;
import dto.Entity.LineQue;
import dto.Entity.LineTemp;
import dto.Entity.LineAPI.Webhooks.Events.MessageEvent;

public class LIN0030Page extends BasePage {

	public List<LineQue> RESERVE_QUE_LIST;
	public List<LineQue> RESULT_QUE_LIST;
	public List<LineTemp> TEMP_QUE_LIST;
	public int TYPE;
	public String RESERVE_HTML = "";
	public String RESULT_HTML = "";
	public String ERR_MSG;
	public int NOW_PAGE = 1;
	public int TEMP_LIST_COUNT;
	public Pager2 PAGE_OBJ2;
	public String EXEC_TYPE;

	@SuppressWarnings("unchecked")
	public void onExec() {

		try {
			HashMap paraMap = this.getBaseQueryParam();
			paraMap.clear();
			paraMap.put("ACCOUNT_NAME", this.ACCOUNT);
			paraMap.put("LINE_ACCOUNT_ID", this.LINE_ACCOUNT_ID);

			// LIN0032 エラー
			String errMsg = (String)req.getAttribute("ERR_MSG");
			this.ERR_MSG = errMsg;
			String type = (String)req.getAttribute("TYPE");

			// 画面タイプ 1:予約中　2:配信結果
			if ( common.util.isNotEmpty(type) ) {
				this.TYPE = common.util.toNum( type );
			} else {
				this.TYPE = common.util.toNum( this.getParam("type") );
			}

			// 削除処理
			if ( "DEL".equals(this.getParam("EXEC_TYPE")) ){

				try {

					paraMap.put("QUE_ID", this.getParam("QUE_ID"));
					this.sqlMap.insert("delLineQue", paraMap);

				} catch (Exception ex) {

					this.logE("一斉配信キュー情報の削除に失敗しました。");
					this.logE(ex);

				}
			}

			// QUE情報の取得
			paraMap.put("SEND_TYPE", 2);
			MessageEvent message = null;
			// 日付フォーマット
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

			// 予約中
			this.RESERVE_QUE_LIST = (List<LineQue>) this.sqlMap.queryForList("getReserveLineQueList", paraMap);

			// 配信結果
			paraMap.put("LIMIT", 50);
			this.RESULT_QUE_LIST = (List<LineQue>) this.sqlMap.queryForList("getResultLineQueList", paraMap);


		} catch( Exception e ){
			this.logE(e);
		}
	}
}
