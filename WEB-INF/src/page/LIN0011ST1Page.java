package page;

import java.io.File;
import java.util.HashMap;

import dto.Entity.InfoLineWebhook;
import dto.Entity.LineAccount;

public class LIN0011ST1Page extends BasePage {

	public String CHANEL_ID;
	public String CHANEL_SECRET;
	public String CHANEL_ACCESS_TOKEN;
	public String REDIRECT_URL;
	public int RESET_CHECK_FLG = 0;


	@SuppressWarnings("unchecked")
	public void onExec() {

		try {

			HashMap paraMap = this.getBaseQueryParam();
			if ( "RESET".equals(this.getParam("EXEC_TYPE")) ) {

				LineAccount LINE_ACCOUNT = (LineAccount) this.sqlMap.queryForObject("getAccountInfoByAccountId", paraMap);
				this.CHANEL_ACCESS_TOKEN = (String)LINE_ACCOUNT.getChanel_access_token();

				// Webhookエンドポイントの情報を取得
				String reJson = common.util.httpGet("https://api.line.me/v2/bot/channel/webhook/endpoint", this.CHANEL_ACCESS_TOKEN);

				try {
					InfoLineWebhook info = InfoLineWebhook.loadJson(reJson);
		            boolean active = info.isActive();
		            String basicId = common.util.trimStart(this.BASIC_ID, "@");
		            String webhook = this.WEBHOOK_URL + basicId + this.WEBHOOK_FILE;
		            String fileName = this.getParam("FILE_NAME");

		            if( (active && webhook.equals(info.getEndpoint()) && fileName.equals("LIN0012.htm")) || ( active && !(fileName.equals("LIN0012.htm")) ) ){
						setForward( fileName );
						getContext().setRequestAttribute("RESET_CHECK_FLG", 1);
						return;

					} else {
		            	// トランザクション開始
						this.sqlMap.startTransaction();

						paraMap.put("LINE_ACCOUNT_ID", this.LINE_ACCOUNT_ID);
						this.sqlMap.delete("delLineAccount", paraMap);
						if( fileName.equals("LIN0012.htm") ){
							this.sqlMap.delete("cleanLineFriend", paraMap);
							this.sqlMap.delete("cleanChatHistory", paraMap);
							this.sqlMap.delete("cleanLineFolder", paraMap);
							this.sqlMap.delete("cleanLineTag", paraMap);
							this.sqlMap.delete("cleanLineAttribute", paraMap);
							this.sqlMap.delete("cleanLineEntry_set", paraMap);
							this.sqlMap.delete("cleanLineQue", paraMap);
							this.sqlMap.delete("cleanLineTemp", paraMap);
							this.sqlMapSM.delete("cleanLineConnect", paraMap);
							this.sqlMap.delete("cleanLineQueLog", paraMap);
						}

//						// トランザクション確定
						this.sqlMap.commitTransaction();
//						// トランザクション終了
						this.sqlMap.endTransaction();

						if(fileName.equals("LIN0012.htm")){
							// home/contents/アカウント名/ベーシックID以下を削除
							File directory = new File("/home/contents/"+ LINE_ACCOUNT.getAccount_name() + "/" + basicId + "/");
							deleteDirectory(directory);
						}
		            }

		        } catch (Exception e) {
		            e.printStackTrace();
		        }
			}

			// アカウント設定済みなら編集ページへ
			if( this.LINE_ACCOUNT_ID > 0  ){
				this.REDIRECT_URL = "!LIN0012!";
				this.setPath("REDIRECT.htm");
				return;
			}

			// 初期設定　スキーマ作成

			if( this.LINE_ACCOUNT_ID == 0 ){
				this.sqlMap.insert("createDB", paraMap );
				this.sqlMap.insert("createLineAttributeTable", paraMap);
				this.sqlMap.insert("createLineChatHistoryTable", paraMap);
				this.sqlMap.insert("createLineEntrySetTable", paraMap);
				this.sqlMap.insert("createLineFolderTable", paraMap);
				this.sqlMap.insert("createLineFriendTable", paraMap);
				this.sqlMap.insert("createLineQueLogTable", paraMap);
				this.sqlMap.insert("createLineTempTable", paraMap);
				this.sqlMap.insert("createLineTagTable", paraMap);
			}

			this.CHANEL_ID = this.getParam("CHANEL_ID");
			this.CHANEL_SECRET = this.getParam("CHANEL_SECRET");
			this.CHANEL_ACCESS_TOKEN = this.getParam("CHANEL_ACCESS_TOKEN");

		} catch (Exception e){
			this.logE(e);
		}
	}

	public static void deleteDirectory(File directory) {
		if (directory.exists()) {
			File[] files = directory.listFiles();
			if (files != null) {
				for (File file : files) {
					if (file.isDirectory()) {
						deleteDirectory(file);
					} else {
						file.delete();
					}
				}
			}
			directory.delete();
		}
	}
}
