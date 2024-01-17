package page;

import java.util.HashMap;

import common.LineException;

import dto.Entity.LineAccount;

public class LIN0011ST2Page extends BasePage {

	public String BASIC_ID;
	public String CHANEL_ID;
	public String CHANEL_SECRET;
	public String CHANEL_ACCESS_TOKEN;
	public String DISPLAY_NAME;
	public String ERR_MSG;
	public int RESET_CHECK_FLG = 0;

	@SuppressWarnings("unchecked")
	public void onExec() {

		try {

			HashMap paraMap = this.getBaseQueryParam();
			Object resetCheckFlg = req.getAttribute("RESET_CHECK_FLG");
			this.RESET_CHECK_FLG = (resetCheckFlg != null) ? (int) resetCheckFlg : 0;

			this.BASIC_ID = this.getParam("BASIC_ID");
			this.DISPLAY_NAME = this.getParam("DISPLAY_NAME");
			this.CHANEL_ID = this.getParam("CHANEL_ID");
			this.CHANEL_SECRET = this.getParam("CHANEL_SECRET").trim();
			this.CHANEL_ACCESS_TOKEN = this.getParam("CHANEL_ACCESS_TOKEN").trim();

			if( "ADD".equals(this.getParam("EXEC_TYPE")) ){

				paraMap.put("BASIC_ID", this.BASIC_ID);
				paraMap.put("CHANEL_ID", this.CHANEL_ID);
				paraMap.put("CHANEL_SECRET", this.CHANEL_SECRET);
				paraMap.put("CHANEL_ACCESS_TOKEN", this.CHANEL_ACCESS_TOKEN);
				paraMap.put("ACCOUNT_NAME", this.ACCOUNT);

				// 既に公式アカウントが他で登録済みかどうか
				LineAccount ac = (LineAccount) this.sqlMap.queryForObject("getAccountInfoByBasicId",paraMap);

				if( ac == null ){

					this.sqlMap.update("addLineAccount",paraMap);

					String url_basic_id = common.util.trimStart(this.BASIC_ID, "@");
					String json = "{\"endpoint\":\"https://tls24.net/LineWebhook/" + url_basic_id + "/Webhook.ln\"}";

					// WebhookエンドポイントURLを設定する
					String reJson = common.util.httpPut("https://api.line.me/v2/bot/channel/webhook/endpoint", this.CHANEL_ACCESS_TOKEN, json);

					if( reJson.equals("Invalid webhook endpoint URL") ){
						throw new LineException("WEBHOOKのエンドポイントURL登録に失敗しました");
					}

				} else {

					throw new LineException("こちらのLINE公式アカウントは既に使用されています");
				}

			}

		} catch( LineException e ){
			this.ERR_MSG = "ERROR : " + e.getMessage();
			this.setPath("LIN0011ST1.htm");
			return;
		} catch( Exception e){
			this.logE(e);
		}
	}
}
