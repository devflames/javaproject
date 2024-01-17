package page;

import java.util.HashMap;

import common.RmhException;

import dto.Entity.LineAccount;

public class LIN0012Page extends BasePage {

	public LineAccount LINE_ACCOUNT;
	public String CHANEL_ACCESS_TOKEN;
	public String LOGIN_ACCESS_TOKEN;
	public String LIMIT_NOTIFY;

	public int TAB_TYPE = 1;
	public String MSG;
	public String WEBHOOK;
	public int RESET_CHECK_FLG = 0;

	@SuppressWarnings("unchecked")
	public void onExec() {

		try {

			HashMap paraMap = this.getBaseQueryParam();
			paraMap.clear();
			paraMap.put("ACCOUNT_NAME", this.ACCOUNT);
			paraMap.put("LINE_ACCOUNT_ID", this.LINE_ACCOUNT_ID);

			Object resetCheckFlg = req.getAttribute("RESET_CHECK_FLG");
			this.RESET_CHECK_FLG = (resetCheckFlg != null) ? (int) resetCheckFlg : 0;



			// アカウント情報を取得
			this.LINE_ACCOUNT = (LineAccount) this.sqlMap.queryForObject("getAccountInfoByAccountName", paraMap);
			this.LIMIT = this.LINE_ACCOUNT.getLimit();
			this.LIMIT_NOTIFY = this.LINE_ACCOUNT.getLimit_notify();
			this.SEND_LIMIT = this.LINE_ACCOUNT.getSend_limit();

			// タブ1の変更
			if( "EDIT".equals(this.getParam("EXEC_TYPE")) && "1".equals(this.getParam("TAB_TYPE")) ){

				this.CHANEL_ACCESS_TOKEN = this.getParam("CHANEL_ACCESS_TOKEN").trim();

				paraMap.put("CHANEL_ACCESS_TOKEN", this.CHANEL_ACCESS_TOKEN);

				this.sqlMap.update("updLineAccount",paraMap);

				this.MSG = "Channel Secretを変更しました";

			// タブ2の変更
			} else if( "EDIT".equals(this.getParam("EXEC_TYPE")) && "2".equals(this.getParam("TAB_TYPE"))  ){

				this.LOGIN_ACCESS_TOKEN = this.getParam("LOGIN_ACCESS_TOKEN").trim();

				paraMap.put("LOGIN_ACCESS_TOKEN", this.LOGIN_ACCESS_TOKEN);

				this.sqlMap.update("updLineAccount",paraMap);

				this.MSG = "LINEログインチャネルのChannel Secretを変更しました";

			// タブ3の変更
			} else if( "EDIT".equals(this.getParam("EXEC_TYPE")) && "3".equals(this.getParam("TAB_TYPE")) ){

				this.LIMIT = common.util.toNum( this.getParam("LIMIT"));

				if ( this.LIMIT == 0 ||  "".equals(this.LIMIT) ){
					throw new RmhException("無料メッセージ上限数は0以上の値を入力して下さい");
				}

				this.LIMIT_NOTIFY = this.getParam("LIMIT_NOTIFY");
				this.SEND_LIMIT = common.util.toNum( this.getParam("SEND_LIMIT"));

				paraMap.put("LIMIT", this.LIMIT);
				paraMap.put("LIMIT_NOTIFY", this.LIMIT_NOTIFY);
				paraMap.put("SEND_LIMIT", this.SEND_LIMIT);

				this.sqlMap.update("updLineAccount",paraMap);

				this.MSG = "その他の設定を変更しました";

			}

			this.WEBHOOK = this.WEBHOOK_URL + common.util.trimStart(this.BASIC_ID, "@") + this.WEBHOOK_FILE;


		} catch( RmhException ex){
			this.MSG = "ERROR : " + ex.getMessage();
			return;

		} catch( Exception e){
			this.MSG = "エラーが発生しました";
			this.logE(e);
		}
	}

}
