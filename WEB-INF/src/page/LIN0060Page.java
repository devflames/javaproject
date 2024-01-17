package page;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import common.util;
import dto.Entity.LineFolder;
import dto.Entity.LineTag;

public class LIN0060Page extends BasePage {
	public List<LineFolder> FOLDER_LIST;
	public List<LineTag> TAG_LIST;
	public int FOLDER_ID;
	public int TAG_ID;
	public String FOLDER_NAME;
	public String TAG_NAME;
	public String ERR_MSG;

	public String FOLDER_ID_STR;
	public String TAG_ID_STR;
	public String SORT_NO_STR;

	@SuppressWarnings("unchecked")
	public void onExec()  {

		try {

			HashMap paraMap = this.getBaseQueryParam();
			paraMap.put("LINE_ACCOUNT_ID", this.LINE_ACCOUNT_ID);
			paraMap.put("SCHEMA", this.SCHEMA);
			this.sysProp = util.getProperties("system.properties");

			// フォルダ　新規登録
			if( "FOLDER_ADD".equals(this.getParam("EXEC_TYPE")) ){

				this.FOLDER_NAME = this.getParam("FOLDER_NAME");
				paraMap.put("FOLDER_NAME", this.FOLDER_NAME);

				// 必須入力チェック
				if( common.util.isEmpty(this.FOLDER_NAME) ){
					throw new Exception("フォルダ名を入力して下さい");
				}

				// 文字チェック
				if( this.FOLDER_NAME.matches(".*(\"|').*") ){
					throw new Exception("「\"」「'」は使用できません");
				}

				// フォルダ名の重複チェック
				int check_cnt = (int) this.sqlMap.queryForObject("getCheckLineFolder",paraMap);
				if( check_cnt > 0 ){
					throw new Exception("フォルダ名は既に使用されています");
				}

				// 最大登録数のチェック
				int folder_cnt = (int) this.sqlMap.queryForObject("getCountLineFolder",paraMap);
				int folder_max_cnt = util.toNum( this.sysProp.getProperty("FOLDER_MAX_COUNT") );

				if( folder_cnt >= folder_max_cnt ){
					throw new Exception("フォルダの最大登録数を超えています");
				}

				// sort_noの最大値を取得
				int max_no = (int) this.sqlMap.queryForObject("getMaxFolderSortNo",paraMap);
				paraMap.put("SORT_NO", max_no+1);
				this.sqlMap.insert("addLineFolder",paraMap);

			// フォルダ　編集
			} else if( "FOLDER_EDIT".equals(this.getParam("EXEC_TYPE")) ){

				this.FOLDER_ID = common.util.toNum(this.getParam("FOLDER_ID"));
				this.FOLDER_NAME = this.getParam("FOLDER_NAME");

				// 必須入力チェック
				if( common.util.isEmpty(this.FOLDER_NAME) ){
					throw new Exception("フォルダ名を入力して下さい");
				}

				// 文字チェック
				if( this.FOLDER_NAME.matches(".*(\"|').*") ){
					throw new Exception("「\"」「'」は使用できません");
				}

				paraMap.put("FOLDER_NAME", this.FOLDER_NAME);
				paraMap.put("FOLDER_ID", this.FOLDER_ID);

				// フォルダ名の重複チェック
				int check_cnt = (int) this.sqlMap.queryForObject("getCheckLineFolder",paraMap);
				if( check_cnt > 0 ){
					throw new Exception("フォルダ名は既に使用されています");
				}

				this.sqlMap.update("updLineFolder",paraMap);

			// フォルダ　削除
			} else if( "FOLDER_DEL".equals(this.getParam("EXEC_TYPE")) ){

				this.FOLDER_ID = common.util.toNum(this.getParam("FOLDER_ID"));
				paraMap.put("FOLDER_ID", this.FOLDER_ID);

				try {

					// トランザクション開始
					this.sqlMap.startTransaction();

					// フォルダの削除
					this.sqlMap.update("delLineFolder",paraMap);

					// タグの削除
					this.sqlMap.update("delLineTagAll",paraMap);

					// トランザクションコミット
					this.sqlMap.commitTransaction();

				} catch(Exception e){

					this.logE(e);

				} finally {
					this.sqlMap.endTransaction();

					// フォルダIDを初期値に戻す
					this.FOLDER_ID = 0;
				}

			// フォルダ　並び順決定
			} else if( "FOLDER_SORT".equals(this.getParam("EXEC_TYPE")) ){

				this.FOLDER_ID_STR = this.getParam("FOLDER_ID_STR");
				this.SORT_NO_STR = this.getParam("SORT_NO_STR");

				String[] id_arr = this.FOLDER_ID_STR.split(",");
				String[] sort_arr = this.SORT_NO_STR.split(",");

				String sql_sort="";
				for( int i=0; i< id_arr.length; i++){
					sql_sort += " WHEN folder_id = " + id_arr[i] + " THEN " + sort_arr[i];

					if( i == id_arr.length-1 ){
						sql_sort += " ELSE 0 END ";
					}
				}

				paraMap.put("SQL_SORT", sql_sort);
				this.sqlMap.update("updLineFolderSort",paraMap);

			// タグ　新規登録
			} else if( "TAG_ADD".equals(this.getParam("EXEC_TYPE")) ){

				this.TAG_NAME = this.getParam("TAG_NAME");
				this.FOLDER_ID = common.util.toNum(this.getParam("FOLDER_ID"));

				paraMap.put("TAG_NAME", this.TAG_NAME);
				paraMap.put("PARENT_FOLDER_ID", this.FOLDER_ID);

				// 必須入力チェック
				if( common.util.isEmpty(this.TAG_NAME) ){
					throw new Exception("タグ名を入力して下さい");
				}

				// 文字チェック
				if( this.TAG_NAME.matches(".*(\"|').*") ){
					throw new Exception("「\"」「'」は使用できません");
				}

				// タグ名の重複チェック
				int check_cnt = (int) this.sqlMap.queryForObject("getCheckLineTag",paraMap);
				if( check_cnt > 0 ){
					throw new Exception("タグ名は既に使用されています");
				}

				// 最大登録数のチェック
				int tag_cnt = (int) this.sqlMap.queryForObject("getCountLineTag",paraMap);
				int tag_max_cnt = util.toNum( this.sysProp.getProperty("TAG_MAX_COUNT") );

				if( tag_cnt >= tag_max_cnt ){
					throw new Exception("タグの最大登録数を超えています");
				}

				// sort_noの最大値を取得
				int max_no = (int) this.sqlMap.queryForObject("getMaxTagSortNo",paraMap);
				paraMap.put("SORT_NO", max_no+1);
				this.sqlMap.insert("addLineTag",paraMap);

			// タグ　編集
			} else if( "TAG_EDIT".equals(this.getParam("EXEC_TYPE")) ){

				this.TAG_ID = common.util.toNum(this.getParam("TAG_ID"));
				this.TAG_NAME = this.getParam("TAG_NAME");

				// 必須入力チェック
				if( common.util.isEmpty(this.TAG_NAME) ){
					throw new Exception("タグ名を入力して下さい");
				}

				// 文字チェック
				if( this.TAG_NAME.matches(".*(\"|').*") ){
					throw new Exception("「\"」「'」は使用できません");
				}

				paraMap.put("TAG_NAME", this.TAG_NAME);
				paraMap.put("TAG_ID", this.TAG_ID);

				// タグ名の重複チェック
				int check_cnt = (int) this.sqlMap.queryForObject("getCheckLineTag",paraMap);
				if( check_cnt > 0 ){
					throw new Exception("タグ名は既に使用されています");
				}

				this.sqlMap.update("updLineTag",paraMap);

			// タグ　削除
			} else if( "TAG_DEL".equals(this.getParam("EXEC_TYPE")) ){

				this.TAG_ID = common.util.toNum(this.getParam("TAG_ID"));

				// タグの削除
				paraMap.put("TAG_ID", this.TAG_ID);
				this.sqlMap.update("delLineTag",paraMap);

			// タグ　並び順決定
			} else if( "TAG_SORT".equals(this.getParam("EXEC_TYPE")) ){

				this.TAG_ID_STR = this.getParam("TAG_ID_STR");
				this.SORT_NO_STR = this.getParam("SORT_NO_STR");

				String[] id_arr = this.TAG_ID_STR.split(",");
				String[] sort_arr = this.SORT_NO_STR.split(",");

				String sql_sort="";
				for( int i=0; i< id_arr.length; i++){
					sql_sort += " WHEN tag_id = " + id_arr[i] + " THEN " + sort_arr[i];

					if( i == id_arr.length-1 ){
						sql_sort += " ELSE 0 END ";
					}
				}

				paraMap.put("SQL_SORT", sql_sort);
				this.sqlMap.update("updLineTagSort",paraMap);
			}

			paraMap.clear();
			paraMap.put("LINE_ACCOUNT_ID", this.LINE_ACCOUNT_ID);
			paraMap.put("SCHEMA", this.SCHEMA);

			// フォルダ一覧を取得
			this.FOLDER_LIST = (List<LineFolder>)this.sqlMap.queryForList("getLineFolderList",paraMap);


			// モバイル判定
			boolean mobile = common.util.isMobile(this.req);

			if( mobile ) {
				this.setPath("/LIN0060SP.htm");
				return;
			}


		} catch( Exception ex){

			this.ERR_MSG = "ERROR : " + ex.getMessage();

			HashMap paraMap = this.getBaseQueryParam();
			paraMap.clear();
			paraMap.put("LINE_ACCOUNT_ID", this.LINE_ACCOUNT_ID);
			paraMap.put("SCHEMA", this.SCHEMA);

			// フォルダ一覧を取得
			try {
				this.FOLDER_LIST = (List<LineFolder>)this.sqlMap.queryForList("getLineFolderList",paraMap);
			} catch (SQLException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			this.logE(ex);

			// モバイル判定
			boolean mobile = common.util.isMobile(this.req);

			if( mobile ) {
				this.setPath("/LIN0060SP.htm");
				return;
			}
		}
	}
}
