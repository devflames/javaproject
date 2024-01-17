package page;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dto.Entity.LineAttribute;
import dto.Entity.LineFolder;
import dto.Entity.LineFriend;
import dto.Entity.LineTag;

public class LIN0023Page extends BasePage {


	public int FRIEND_ID;
	public String LINE_ID;
	public LineFriend FRIEND;
	public List<LineTag> TAG_LIST;
	public List<LineTag> MASTER_TAG_LIST;
	public List<LineFolder> FOLDER_LIST;
	public ArrayList<Integer> TAG_NO_ARR = new ArrayList<Integer>();
	public ArrayList<Integer> PARENT_NO_ARR = new ArrayList<Integer>();
	public ArrayList<Integer> TAG_ARR = new ArrayList<Integer>();
	public List<LineAttribute> ATTRIBUTE_LIST = new ArrayList<LineAttribute>();
	public int TAG_ID;

	@SuppressWarnings("unchecked")
	public void onExec() {

		try {

			HashMap paraMap = this.getBaseQueryParam();

			this.FRIEND_ID = common.util.toNum(this.getParam("FRIEND_ID"));
			this.LINE_ID = this.getParam("LINE_ID");

			paraMap.put("SCHEMA", this.SCHEMA);
			paraMap.put("LINE_ACCOUNT_ID", this.LINE_ACCOUNT_ID);
			paraMap.put("FRIEND_ID", this.FRIEND_ID);


			// フォルダ情報の取得
			this.FOLDER_LIST = (List<LineFolder>)this.sqlMap.queryForList("getLineFolderList",paraMap);

			// タグ編集
			if( "EDIT".equals(this.getParam("EXEC_TYPE")) ){

				for ( int idx = 0; idx < this.FOLDER_LIST.size(); idx++ ) {
					int tag_no = common.util.toNum( this.getParam("TAG[" + idx + "]") );
					int parent_no = common.util.toNum( this.getParam("PARENT[" + idx + "]"));
						if( tag_no != 0 ){

							LineAttribute attribute = new LineAttribute();
							attribute.setLine_account_id(this.LINE_ACCOUNT_ID);
							attribute.setFriend_id(this.FRIEND_ID);
							attribute.setFolder_id(parent_no);
							attribute.setTag_id(tag_no);

							ATTRIBUTE_LIST.add(attribute);
						}
				}

				paraMap.put("AttributeMap", ATTRIBUTE_LIST);

					try {

						// トランザクション開始
						this.sqlMap.startTransaction();

						// 一度　line_account_id,friend_idに紐づくデータを全消去
						this.sqlMap.update("deleteLineAttributeAll",paraMap);

						if( this.ATTRIBUTE_LIST.size() != 0 ){

							// 友だちフォルダタグ中間テーブルに一括insert
							this.sqlMap.insert("addBulkLineAttribute",paraMap);
						}

						// トランザクションコミット
						this.sqlMap.commitTransaction();

					} catch(Exception e){

						this.logE(e);

					} finally {

						this.sqlMap.endTransaction();
					}

			// タグ追加
			} else if( "ADD".equals(this.getParam("EXEC_TYPE")) ){

				paraMap.put("PARENT_FOLDER_ID", common.util.toNum( this.getParam("FOLDER_ID")));
				paraMap.put("TAG_NAME", this.getParam("TAG_NAME"));

				// sort_noの最大値を取得
				int max_no = (int) this.sqlMap.queryForObject("getMaxTagSortNo",paraMap);
				paraMap.put("SORT_NO", max_no+1);

				this.TAG_ID = (int) this.sqlMap.insert("addLineTag",paraMap);

				this.setPath("LIN0023EX.htm");
				return;

			}

			// 友だち情報の取得
			this.FRIEND = (LineFriend) this.sqlMap.queryForObject("getLineFriendProfile",paraMap);

			// 友だちのタグ情報の取得
			this.TAG_LIST = (List<LineTag>)this.sqlMap.queryForList("getLineAttributeList",paraMap);

			for( LineTag tag : this.TAG_LIST ){
				this.TAG_ARR.add( tag.getTag_id() );
			}


			// モバイル判定
			boolean mobile = common.util.isMobile(this.req);

			if( mobile ) {
				this.setPath("/LIN0023SP.htm");
				return;
			}

		} catch( Exception e){

			this.logE(e);
		}
	}

}
