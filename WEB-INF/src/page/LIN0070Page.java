package page;

import java.util.HashMap;
import java.util.List;

import dto.Entity.LineEntrySet;
import dto.Entity.LineFolder;

public class LIN0070Page extends BasePage {

	public String NEW_TAG;
	public String RENEW_TAG;

	public String NEW_TAG_VALUE;
	public String RENEW_TAG_VALUE;

	public List<LineFolder> LINE_FOLDER_LIST;
	public String TAG_JSON="";
	public String MESSAGE;


	@SuppressWarnings("unchecked")
	public void onExec() {

		try {

			HashMap paraMap = this.getBaseQueryParam();
			paraMap.put("LINE_ACCOUNT_ID", this.LINE_ACCOUNT_ID);
			paraMap.put("SCHEMA", this.SCHEMA);

			if( "RELOAD".equals(this.getParam("EXEC_TYPE")) ){

				this.NEW_TAG = this.getParam("NEW_TAG");
				this.RENEW_TAG = this.getParam("RENEW_TAG");

				// 	新規友だち
				if( common.util.isNotEmpty(this.NEW_TAG) ){

					this.LINE_FOLDER_LIST = LineFolder.loadJson(this.NEW_TAG);

					String	new_tag_id = "";
					for(LineFolder folder : this.LINE_FOLDER_LIST){

							new_tag_id = common.util.toStr( folder.getTag_id() );
							this.NEW_TAG_VALUE = folder.getTag_name();

					}

					paraMap.put("TAG_ID", new_tag_id);
					paraMap.put("SID", 0);
					paraMap.put("ENTRY_TYPE", 0);

					LineEntrySet new_entry = (LineEntrySet) this.sqlMap.queryForObject("getLineEntrySet",paraMap);

					// 新規
					if( new_entry == null ){
						this.sqlMap.insert("addLineEntrySet",paraMap);

					// 更新
					}else{
						this.sqlMap.insert("updLineEntrySet",paraMap);
					}

				// 設定値なし
				} else {

					paraMap.put("ENTRY_TYPE", 0);
					this.sqlMap.delete("delLineEntrySet",paraMap);

				}

				// 連携前からの友だち
				if( common.util.isNotEmpty(this.RENEW_TAG) ){

					this.LINE_FOLDER_LIST = LineFolder.loadJson(this.RENEW_TAG);

					String renew_tag_id = "";
					for(LineFolder folder : this.LINE_FOLDER_LIST){

							renew_tag_id = common.util.toStr( folder.getTag_id() );
							this.RENEW_TAG_VALUE = folder.getTag_name();

					}

					paraMap.put("TAG_ID", renew_tag_id);
					paraMap.put("SID", 0);
					paraMap.put("ENTRY_TYPE", 1);

					LineEntrySet new_entry = (LineEntrySet) this.sqlMap.queryForObject("getLineEntrySet",paraMap);

					// 新規
					if( new_entry == null ){
						this.sqlMap.insert("addLineEntrySet",paraMap);

					// 更新
					}else{
						this.sqlMap.insert("updLineEntrySet",paraMap);
					}

				// 設定値なし
				} else {

					paraMap.put("ENTRY_TYPE", 1);
					this.sqlMap.delete("delLineEntrySet",paraMap);

				}

				this.MESSAGE = "保存しました";

			}

			// 現在設定されている値
			paraMap.put("ENTRY_TYPE", 0);
			LineEntrySet new_entry = (LineEntrySet) this.sqlMap.queryForObject("getLineEntrySet",paraMap);

			paraMap.put("ENTRY_TYPE", 1);
			LineEntrySet renew_entry = (LineEntrySet) this.sqlMap.queryForObject("getLineEntrySet",paraMap);



			// フォルダ・タグ情報の取得
			paraMap.put("NOT_NULL_TAG_NAME", 1);
			this.LINE_FOLDER_LIST = (List<LineFolder>) this.sqlMap.queryForList("getLineFolderList", paraMap);

			// フォルダオブジェクトをJSONに変換
			int i = 0;
			for( LineFolder folder : this.LINE_FOLDER_LIST){

					i++;
					if( i == this.LINE_FOLDER_LIST.size() ){
						this.TAG_JSON += LineFolder.toStringJson(folder);
					} else {
						this.TAG_JSON += LineFolder.toStringJson(folder)+",";
					}

					if( new_entry != null ){
						if( new_entry.getTag_id() == folder.getTag_id() ){
							this.NEW_TAG_VALUE = folder.getTag_name();
						}
					}

					if( renew_entry != null ){
						if( renew_entry.getTag_id() == folder.getTag_id() ){
							this.RENEW_TAG_VALUE = folder.getTag_name();
						}
					}

			}


		} catch( Exception e){
			this.logE(e);
		}

	}
}
