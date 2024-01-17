package page;

import java.util.HashMap;

import common.RmhException;

import dto.Entity.LineAPI.ServerAPI.ChanelAccessToken;
import dto.Entity.LineAPI.ServerAPI.Liff;

public class LIN0011ST4Page extends BasePage {

	public String LOGIN_CHANEL_ID;
	public String LOGIN_CHANEL_SECRET;
	public String MSG;
	public String ERR_MSG;
	public String DISPLAY_NAME;

	public int LIMIT;
	public String LIMIT_NOTIFY;
	public int SEND_LIMIT;
	public int RESET_CHECK_FLG = 0;


	@SuppressWarnings("unchecked")
	public void onExec() {

		try {

			HashMap paraMap = this.getBaseQueryParam();
			Object resetCheckFlg = req.getAttribute("RESET_CHECK_FLG");
			this.RESET_CHECK_FLG = (resetCheckFlg != null) ? (int) resetCheckFlg : 0;


			if( "ADD".equals(this.getParam("EXEC_TYPE")) ){

				this.LOGIN_CHANEL_ID = this.getParam("LOGIN_CHANEL_ID").trim();
				this.LOGIN_CHANEL_SECRET = this.getParam("LOGIN_CHANEL_SECRET").trim();

				// LINEログイン用の短期のチャネルアクセストークンを発行
				paraMap.clear();
				paraMap.put("grant_type", "client_credentials");
				paraMap.put("client_id", this.LOGIN_CHANEL_ID);
				paraMap.put("client_secret", this.LOGIN_CHANEL_SECRET);

				String token_json = common.util.httpPostParam("https://api.line.me/v2/oauth/accessToken", paraMap);

				if( token_json.equals("Bad Request") ){
					throw new RmhException("トークンの発行に失敗しました");
				}

				ChanelAccessToken token = ChanelAccessToken.loadJson(token_json);

				String json = "{"
						+ "\"view\": {"
				    		+ " \"type\": \"full\","
				    		+ "\"url\": \"https://tls24.net/LineWebhook/liff.html\""
				    		+ "},"
				    		+ "\"description\": \"AutoBiz\","
				    		+ "\"features\": {"
				    		+ "\"ble\": true,"
				    		+ "\"qrCode\": true"
				    		+ "},"
				    		+ "\"permanentLinkPattern\": \"concat\","
				    		+ "\"scope\": [\"profile\", \"chat_message.write\"],"
				    		+ "\"botPrompt\": \"aggressive\""
				    		+ "}";

				// LIFFアプリをチャネルに追加する 参考：https://developers.line.biz/ja/reference/liff-server/
				String reJson = common.util.httpPost("https://api.line.me/liff/v1/apps", token.getAccess_token(), json);

				Liff liff = Liff.loadJson(reJson);

				if( reJson.equals("Bad Request") || reJson.equals("Unauthorized") ){
					throw new RmhException("LIFFアプリの登録に失敗しました");
				}

				String json2 = "{\"view\": {\"url\": \"https://tls24.net/LineWebhook/" + liff.getLiffId() + "/Liff.lf\"}}";

				// エンドポイントURLの設定
				String reJson2 = common.util.httpPut("https://api.line.me/liff/v1/apps/"+liff.getLiffId(), token.getAccess_token(), json2);

				if( reJson2.equals("Bad Request") || reJson2.equals("Unauthorized") || reJson2.equals("Method Not Allowed")){
					throw new RmhException("LIFFアプリのエンドポイントURL登録に失敗しました");
				}

				// LINEアカウント情報に登録
				paraMap.put("LOGIN_CHANEL_ID", this.LOGIN_CHANEL_ID);
				paraMap.put("LOGIN_CHANEL_SECRET", this.LOGIN_CHANEL_SECRET);
				paraMap.put("LINE_ACCOUNT_ID", this.LINE_ACCOUNT_ID);
				paraMap.put("LIFF_ID", liff.getLiffId());
				this.sqlMap.update("updLineAccount",paraMap);

			} else if( "EDIT".equals(this.getParam("EXEC_TYPE")) ){

				this.LIMIT = common.util.toNum(this.getParam("LIMIT"));

				if ( this.LIMIT == 0 ||  "".equals(this.LIMIT) ){
					//throw new RmhException("無料メッセージ上限数は0以上の値を入力して下さい");
					this.ERR_MSG = "ERROR :　無料メッセージ上限数は0以上の値を入力して下さい";
					this.setPath("LIN0011ST4.htm");
					return;
				}

				this.LIMIT_NOTIFY = this.getParam("LIMIT_NOTIFY");
				this.SEND_LIMIT = common.util.toNum( this.getParam("SEND_LIMIT"));

				paraMap.put("LINE_ACCOUNT_ID", this.LINE_ACCOUNT_ID);
				paraMap.put("LIMIT", this.LIMIT);
				paraMap.put("LIMIT_NOTIFY", this.LIMIT_NOTIFY);
				paraMap.put("SEND_LIMIT", this.SEND_LIMIT);

				this.sqlMap.update("updLineAccount",paraMap);

				this.MSG = "設定が完了しました。";
				this.REDIRECT_URL = "!LIN0012!";
				this.setPath("REDIRECT.htm");
				return;
			}

		} catch( RmhException ex){
			this.ERR_MSG = "ERROR : " + ex.getMessage();
			this.setPath("LIN0011ST3.htm");
			return;
		} catch( Exception e){
			this.logE(e);
		}
	}
}
