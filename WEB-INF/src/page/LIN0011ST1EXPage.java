package page;

import java.util.HashMap;

import common.LineException;
import dto.Entity.LineAccount;
import dto.Entity.LineBot;

public class LIN0011ST1EXPage extends BasePage {

	public String CHANEL_ID;
	public String CHANEL_SECRET;
	public String CHANEL_ACCESS_TOKEN;
	public String ERR_MSG;

	public String DISPLAY_NAME;
	public String BASIC_ID;
	public String PICTURE_URL;

	@SuppressWarnings("unchecked")
	public void onExec() {

		try {

			HashMap paraMap = this.getBaseQueryParam();

			this.CHANEL_ACCESS_TOKEN = this.getParam("CHANEL_ACCESS_TOKEN");
			this.CHANEL_ID = this.getParam("CHANEL_ID");
			this.CHANEL_SECRET = this.getParam("CHANEL_SECRET");

			// 必須チェック
			if ( "CHECK".equals(this.getParam("EXEC_TYPE")) ) {

				if ( "".equals(this.CHANEL_ID) ){
					throw new LineException("Channel ID を入力して下さい");
				}

				if( common.util.validNum(this.CHANEL_ID, 10) == false ){
					throw new LineException("Channel ID は10桁の数値で指定して下さい");
				}

				if ( "".equals(this.CHANEL_SECRET) ){
					throw new LineException("Channel Secret を入力して下さい");
				}

				if ( "".equals(this.CHANEL_ACCESS_TOKEN) ){
					throw new LineException("Channel access token を入力して下さい");
				}

			}

			// chanel access tokenからボット情報を取得
			String response = common.util.httpGet("https://api.line.me/v2/bot/info", this.CHANEL_ACCESS_TOKEN );

			if( common.util.isEmpty(response)){
				throw new Exception("ボット情報を取得できませんでした");
			}

			LineBot bot = LineBot.loadJson(response);

			this.DISPLAY_NAME = bot.getDisplayName();
			this.BASIC_ID = bot.getBasicId();
			this.PICTURE_URL = bot.getPictureUrl();

			// 既に公式アカウントが他で登録済みかどうか
			paraMap.put("BASIC_ID", this.BASIC_ID);
			LineAccount ac = (LineAccount) this.sqlMap.queryForObject("getAccountInfoByBasicId",paraMap);

			if( ac != null ){
				throw new LineException("こちらのLINE公式アカウントは既に使用されています");
			}

		} catch(LineException ex){
			this.ERR_MSG = "ERROR : " + ex.getMessage();
			this.setPath("LIN0011ST1.htm");
			return;
		} catch( Exception e ){
			this.logE(e);
		}

	}
}
