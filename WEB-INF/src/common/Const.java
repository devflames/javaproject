package common;

import java.util.LinkedHashMap;


public class Const {

	// ブラウザ種別
	public static final int BROWSER_UNKNOWN = 0;
	public static final int BROWSER_IE = 1;
	public static final int BROWSER_FIREFOX = 2;
	public static final int BROWSER_OPERA = 3;
	public static final int BROWSER_CHROME = 4;
	public static final int BROWSER_SAFARI = 5;
	public static final int BROWSER_NETSCAPE = 6;

	// メール文字コード
	public static final int MAIL_CHARA_SET_JIS = 1;
	public static final int MAIL_CHARA_SET_UTF8 = 2;

	// 成りすまし対策
	public static final int ENVELOPE_TYPE_ON = 1;
	public static final int ENVELOPE_TYPE_OFF = 0;

	// メールタイプ（テキスト／ＨＴＭＬ）
	public static final int MAIL_TYPE_TEXT = 1;
	public static final int MAIL_TYPE_HTML = 2;

	// ユーザ権限
	public static final String PERMISSION_ADMIN = "777";
	public static final String PERMISSION_LIMIT = "033";

	// ユーザ権限名称
	public static final String PERMISSION_ADMIN_NAME = "管理者";
	public static final String PERMISSION_LIMIT_NAME = "限定ユーザ";

	// 登録状態名称
	public static final String DATA_STATUS_NAME_DEFAULT = "破損";
	public static final String DATA_STATUS_NAME_0 = "仮登録";
	public static final String DATA_STATUS_NAME_1 = "正常";
	public static final String DATA_STATUS_NAME_8 = "配信ｴﾗｰ";
	public static final String DATA_STATUS_NAME_9 = "不達";

	// 文字コード
	public static final String CHAR_CODE_ISO = "1";
	public static final String CHAR_CODE_UTF = "2";

	// 文字コード名称
	public static final String CHAR_CODE_ISO_NAME = "JIS（iso-2022-jp）";
	public static final String CHAR_CODE_UTF_NAME = "UTF-8";

	// 都道府県
	public static final String[] PREF_LIST = {"北海道","青森県","秋田県","岩手県","山形県","宮城県","福島県","茨城県","栃木県","群馬県","埼玉県","東京都","千葉県","神奈川県","山梨県","長野県","静岡県","新潟県","富山県","石川県","福井県","岐阜県","愛知県","奈良県","三重県","滋賀県","京都府","兵庫県","大阪府","和歌山県","岡山県","広島県","鳥取県","島根県","山口県","香川県","徳島県","愛媛県","高知県","福岡県","大分県","佐賀県","長崎県","宮崎県","熊本県","鹿児島県","沖縄県"};

	// 配信結果コード
	public static final String NOMAL_STATUS = "'250'";
	public static final String ABSENCE_STATUS = "'-8', '-9', '511', '550', '551', '553', '554', '559', '615'";

	// 確認ページ上部メッセージ
	public static final String DEFAULT_CONFIRM_TOP = "入力内容をご確認ください。";

	// 確認ページ下部メッセージ
	public static final String DEFAULT_CONFIRM_BOTTOM = "入力内容を変更する場合は「申込み画面に戻る」ボタンを押してください。";

	// 完了ページ上部メッセージ
	public static final String DEFAULT_THANKYOU_TOP = "お申し込みありがとうございます。";

	// 完了ページ下部メッセージ
	public static final String DEFAULT_THANKYOU_BOTTOM = "当日お会いできるのを楽しみにしております。";

	// 区分
	public static final LinkedHashMap<String, String> CLASSIFICATION_MAP = new LinkedHashMap<String,String>();

		static {

			//LinkedHashMap<String,String> CLASSIFICATION_MAP = new LinkedHashMap<String,String>();
			CLASSIFICATION_MAP.put("0","管理者向け申込通知");
			CLASSIFICATION_MAP.put("1","入金催促");
			CLASSIFICATION_MAP.put("2","入金お礼");
			CLASSIFICATION_MAP.put("3","申込確認");
			CLASSIFICATION_MAP.put("4","キャンセル確認");
			CLASSIFICATION_MAP.put("5","個別");
			CLASSIFICATION_MAP.put("6","一斉配信");
			CLASSIFICATION_MAP.put("7","キャンセル待ち受付開始");
			CLASSIFICATION_MAP.put("8","セミナー参加お礼");
			CLASSIFICATION_MAP.put("11","キャンセル待ち申込受付");
			CLASSIFICATION_MAP.put("12","仮申込受付");
			CLASSIFICATION_MAP.put("13","本登録受付開始");

		}

	// お知らせRSS
	public static final String RSS_URL = "https://semican.jp/help/category/news/feed";

	//-------------------------------------------------------------------------------------------------------
	// プロパティメソッド
	//-------------------------------------------------------------------------------------------------------
	public static int getBROWSER_UNKNOWN() {return BROWSER_UNKNOWN;}
	public static int getBROWSER_IE() {return BROWSER_IE;}
	public static int getBROWSER_FIREFOX() {return BROWSER_FIREFOX;}
	public static int getBROWSER_OPERA() {return BROWSER_OPERA;}
	public static int getBROWSER_CHROME() {return BROWSER_CHROME;}
	public static int getBROWSER_SAFARI() {return BROWSER_SAFARI;}
	public static int getBROWSER_NETSCAPE() {return BROWSER_NETSCAPE;}

	public static String getPERMISSION_ADMIN() {return PERMISSION_ADMIN;}
	public static String getPERMISSION_LIMIT() {return PERMISSION_LIMIT;}
	public static String getPERMISSION_ADMIN_NAME() {return PERMISSION_ADMIN_NAME;}
	public static String getPERMISSION_LIMIT_NAME() {return PERMISSION_LIMIT_NAME;}

	public static String getDATA_STATUS_NAME_DEFAULT() {return DATA_STATUS_NAME_DEFAULT;}
	public static String getDATA_STATUS_NAME_0() {return DATA_STATUS_NAME_0;}
	public static String getDATA_STATUS_NAME_1() {return DATA_STATUS_NAME_1;}
	public static String getDATA_STATUS_NAME_8() {return DATA_STATUS_NAME_8;}
	public static String getDATA_STATUS_NAME_9() {return DATA_STATUS_NAME_9;}

	public static String getCHAR_CODE_ISO() {return CHAR_CODE_ISO;}
	public static String getCHAR_CODE_UTF() {return CHAR_CODE_UTF;}
	public static String getCHAR_CODE_ISO_NAME() {return CHAR_CODE_ISO_NAME;}
	public static String getCHAR_CODE_UTF_NAME() {return CHAR_CODE_UTF_NAME;}

	public static String[] getPREF_LIST() {return PREF_LIST;}
	public static LinkedHashMap<String, String>  getCLASSIFICATION_MAP(){ return CLASSIFICATION_MAP;}

	public static String getDefaultConfirmTop() { return DEFAULT_CONFIRM_TOP; }
	public static String getDefaultConfirmBottom() { return DEFAULT_CONFIRM_BOTTOM; }
	public static String getDefaultThankyouTop() { return DEFAULT_THANKYOU_TOP; }
	public static String getDefaultThankyouBottom() { return DEFAULT_THANKYOU_BOTTOM; }
	public static String getRSS_URL(){ return RSS_URL;}

	public static String getSystemMailAddress(){ return "system@semican.jp";}
	public static String getSystemMailName(){ return "セミカンシステム";}
	public static String getLimitNotifSubject(){ return "申込者制限案内";}
	public static String getLimitNotifBody(){ return "***************************************************************\n"
			+ "当メールは、semican.jpより自動でお送りさせて頂いております。\n"
			+ "お心当たりのない方は、お手数ではございますが弊社までお知らせ下さい。\n"
			+ "***************************************************************\n\n"
			+ "[[NAME]] 様\n\n"
			+ "拝啓　平素は格別のお引き立て賜り厚くお礼を申し上げます。\n"
			+ "ご利用されているサービスセミカンのプランによって、申込者登録制限数は残り[[REMAINING_REG]]に\n"
			+ "なりましたので、下記にご案内させて頂きます。\n";}
	public static String getAPIFailedNotifSubject(){ return "サービス連携失敗通知";}
	public static String getAPIFailedNotifBody(){ return "セミカンからのお申込み時に他サービスへの連携を行いましたが、登録に失敗しました。";}
}
