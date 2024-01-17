package page;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import dto.Entity.LineFriend;
import dto.Entity.LineQue;
import dto.Entity.LineAPI.Message.Consumption;

public class LIN0000Page extends BasePage {

	public LineFriend LINE_FRIEND;
	public List<LineQue> QUE_LIST;
	public int FREE_MESSAGE;
	public int QUE_CNT;
	public int QUE_CNT_MONTHLY;
	public int WARNING;	// 0:警告なし　1:警告あり
	public int PERCENTAGE;
	public Consumption total;

	@SuppressWarnings("unchecked")
	public void onExec() {

		try {

			if( common.util.isNotEmpty(super.REDIRECT_URL)){
				this.setPath("REDIRECT.htm");
				return;
			}

			HashMap paraMap = this.getBaseQueryParam();

			// ともだち数、個別メッセージ未対応件数の取得
			this.LINE_FRIEND = (LineFriend) this.sqlMap.queryForObject("getLineFriendCountTop", paraMap);

			// 予約中のメッセージ件数
			paraMap.put("LINE_ACCOUNT_ID", this.LINE_ACCOUNT_ID);
			this.QUE_CNT = (int) this.sqlMap.queryForObject("getLineQueCnt",paraMap);

			// 当月のメッセージ利用状況を取得する
			// 現在日時の取得
			Calendar now = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM");
			paraMap.put("SENDYM", sdf.format(now.getTime()));
			String response = common.util.httpGet("https://api.line.me/v2/bot/message/quota/consumption", this.CHANEL_ACCESS_TOKEN );
			Consumption total = Consumption.loadJson(response);

			// 当月の予約数を取得する
			this.QUE_CNT_MONTHLY = (int) this.sqlMap.queryForObject("getLineQueCnt2",paraMap);

			// 残りメッセージ送信数の取得
			//int fixedCount = this.getSendingCount(this.ACCOUNT);
			this.FREE_MESSAGE = this.LIMIT - (this.QUE_CNT_MONTHLY + total.getTotalUsage());

			if( this.LIMIT*0.9 <= this.QUE_CNT + total.getTotalUsage() ){
				this.WARNING = 1;
			}

			this.PERCENTAGE = Math.round((this.QUE_CNT + total.getTotalUsage()) / this.LIMIT * 100);

		} catch( Exception e){
			this.logE(e);
		}
	}

	// 現在までの、当月度配信件数を取得
	private int getSendingCount(String account) {

		int retValue = 0;

		// 現在日時の取得
		Calendar now = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM");

		// 同月内での送信予約件数取得
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("ACCOUNT", account);
		paraMap.put("SENDYM", sdf.format(now.getTime()));

		try {
			retValue = (Integer)this.sqlMap.queryForObject("getSendCountFix", paraMap);
		} catch (SQLException e) {
			this.logE(e);
		}

		return  retValue;
	}
}
