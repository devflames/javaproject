package page;

import java.util.HashMap;

public class LIN0011ST3Page extends BasePage {

	public String BASIC_ID;
	public String CHANEL_ID;
	public String CHANEL_SECRET;
	public String CHANEL_ACCESS_TOKEN;
	public String DISPLAY_NAME;
	public String ROUTE;		// 画面遷移ルート
	public int RESET_CHECK_FLG = 0;

	@SuppressWarnings("unchecked")
	public void onExec() {

		HashMap paraMap = this.getBaseQueryParam();
		Object resetCheckFlg = req.getAttribute("RESET_CHECK_FLG");
		this.RESET_CHECK_FLG = (resetCheckFlg != null) ? (int) resetCheckFlg : 0;

	}
}
