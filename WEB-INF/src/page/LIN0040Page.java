package page;

import java.util.HashMap;
import java.util.List;

import common.Pager2;
import common.util;
import dto.Entity.LineTemp;

public class LIN0040Page extends BasePage {

	public List<LineTemp> TEMP_QUE_LIST;

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

			paraMap.put("SCHEMA", this.SCHEMA);

			// 下書　削除処理
			if ( "TEMP_DEL".equals(this.getParam("EXEC_TYPE")) ){

				try {

					paraMap.put("TEMP_ID", this.getParam("ID"));
					this.sqlMap.insert("delLineTemp", paraMap);

				} catch (Exception ex) {

					this.logE("下書情報の削除に失敗しました。");
					this.logE(ex);

				}
			}

			// 下書件数の取得
			int cnt = common.util.toNum(this.sqlMap.queryForObject("getTempQueCount", paraMap));

			// ページ制御
			if ( "RELOAD".equals(this.getParam("PAGING")) ) {
				this.NOW_PAGE =  common.util.toNum( this.getSessionParam("NOW_PAGE") );
			}else {

				// ページ制御の初期化
				HashMap<String, String> hashMaps = (HashMap<String, String>)this.sess.get(this.getClass().getSimpleName());
				hashMaps.remove("NOW_PAGE");
			}

			this.sysProp = util.getProperties("system.properties");
			this.TEMP_LIST_COUNT = util.toNum( this.sysProp.getProperty("TEMP_LIST_COUNT") );

			this.PAGE_OBJ2 = new Pager2(this.NOW_PAGE,this.TEMP_LIST_COUNT,cnt);

			paraMap.put("START", this.PAGE_OBJ2.getStart());
			paraMap.put("LIMIT", this.PAGE_OBJ2.getLimit());

			this.TEMP_QUE_LIST = (List<LineTemp>) this.sqlMap.queryForList("getTempQueList", paraMap);

		} catch( Exception e ){
			this.logE(e);
		}
	}
}
