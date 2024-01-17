/*
 * Module Name		BasePage
 * Info				基底ページクラス
 * Auther			H-Andoh.
 * Date				2009-09-01
*/
package page;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibatis.sqlmap.client.SqlMapClient;

import common.CalendarEx;
import common.util;
import dto.SqlConfig;
import dto.Entity.AccountInfo03;
import dto.Entity.LineAccount;
import dto.Entity.StepMail.SetUpInfo;
import net.sf.click.Page;

public abstract class BasePage extends Page {

	// Httpオブジェクト
	protected ServletContext sc;
	protected HttpServletRequest req;
	protected HttpServletResponse res;
	public HashMap<String, Object> sess;

	// Logger
	protected final Log actionLogger;
	protected final Log errorLogger;

	// iBatis DB クライアント
	protected SqlMapClient sqlMap;
	protected SqlMapClient sqlMap03;
	protected SqlMapClient sqlMapSSL24;
	protected SqlMapClient sqlMapSM;

	// メンバー宣言
	public String SESSION_ID;
	public String ACCOUNT;
	public String SCHEMA;
	public int ASP_TYPE;
	public int ASP_PLAN;
	public int USING_SERVICE;
	public int SERVER_ID;
	public String SERVER_IP;
	public String SERVER_DOMAIN;
	public String LINE_SERVER_DOMAIN;
	public String SERVER_PLEFIX;
	public String SYS_SERVER_IP;
	public String SYS_SERVER_IP_03;
	public String SYS_SERVER_IP_SSL24;
	public String SYS_SERVER_DOMAIN;

	// LINEアカウント情報
	public int LINE_ACCOUNT_ID;
	public String BASIC_ID;
	public String CHANEL_ID;
	public String CHANEL_SECRET;
	public String CHANEL_ACCESS_TOKEN;
	public String LIFF_ID;
	public int LIMIT;
	public int SEND_LIMIT;
	public String LOGIN_CHANEL_ID;
	public String LOGIN_CHANEL_SECRET;

	// DBテーブル・フィールド名
	public String REDIRECT_URL;

	public Properties sysProp;
	public String LOCAL_IP;
	public String LOCAL_NAME;
	public CalendarEx TODAY;
	public String WEBHOOK_URL;
	public String WEBHOOK_FILE;

	// AutoBiz用
	public String LBL_SNO;
	public String LBL_SNAME;
	public String TB_SCENARIO;
	public String TB_FORM_FRAME;
	public String TB_PLAN_FRAME;
	public String TB_ADDRESS;
	public String TITLE_SNAME;
	public String TB_PAGE_FRAME;

	public String PAGE_PATH;

	public int DEMO;
	public SetUpInfo SETUP_INFO;
	public String MINIMIZE_CLASS;

	// コンストラクタ
	public BasePage() {

		super();

		// Loggerを作成
		this.actionLogger = LogFactory.getLog("ACTION");
		this.errorLogger = LogFactory.getLog("ERROR");

		// 各種サーブレットオブジェクトの取得
		this.sc = this.getContext().getServletContext();
		this.req = this.getContext().getRequest();
		this.res = this.getContext().getResponse();

		this.TODAY = new CalendarEx();

		this.PAGE_PATH = this.req.getServletPath();

	}

	@SuppressWarnings("unchecked")
	public void onInit() {

		try {

			// サーバ設定情報取得
			this.sysProp = util.getProperties("system.properties");
			this.SYS_SERVER_IP = this.sysProp.getProperty("SYS_SERVER_IP");
			this.SYS_SERVER_IP_03 = this.sysProp.getProperty("SYS_SERVER_IP_03");
			this.SYS_SERVER_IP_SSL24 = this.sysProp.getProperty("SYS_SERVER_IP_SSL24");
			this.SYS_SERVER_DOMAIN = this.sysProp.getProperty("SYS_SERVER_DOMAIN");
			this.LINE_SERVER_DOMAIN = this.sysProp.getProperty("LINE_SERVER_DOMAIN");
			this.LOCAL_NAME = this.sysProp.getProperty("MEDIA_POST");
			this.WEBHOOK_URL = this.sysProp.getProperty("WEBHOOK_URL");
			this.WEBHOOK_FILE = this.sysProp.getProperty("WEBHOOK_FILE");


			// セッションの取得
			this.SESSION_ID = this.getParam("sess");
			if( this.SESSION_ID == "" ){
				this.SESSION_ID = java.util.UUID.randomUUID().toString();
			}

			this.sess = (HashMap)this.req.getSession().getAttribute(this.SESSION_ID);
			if ( this.sess == null ) {
				this.req.getSession().invalidate();
				this.sess = (HashMap)this.sc.getAttribute(this.SESSION_ID);
				if ( this.sess == null ) {
					this.sess = new HashMap<String, Object>();
					//return;
				}
				//if ( "0j56hnir79sntbcjch4cirv9i5".equals(this.SESSION_ID) ) {
				//	this.setRedirect("/timeout.html");
				//	return;
				//}
			}

			// ユーザアカウントの取得
			this.ACCOUNT = (String)this.getParam("acc");

			// LINEサーバー
			this.sqlMap = SqlConfig.getSqlMapInstance(this.SYS_SERVER_IP, "55336","dto/map/sqlMapConfig.xml");
			//this.sqlMap = SqlConfig.getSqlMapInstance(this.SYS_SERVER_IP, "3306","dto/map/sqlMapConfig.xml");  /* 本番環境はこちら */

			LineAccount account = (LineAccount) this.sqlMap.queryForObject("getAccountInfoByAccountName", this.ACCOUNT);

			// リダイレクト対象ページ
			int redirect_flg = 0;
			if( !("/LIN0011ST1.htm".equals(this.req.getServletPath())) &&
					!("/LIN0011ST1EX.htm".equals(this.req.getServletPath())) &&
					!("/LIN0011ST2.htm".equals(this.req.getServletPath())) ){
				redirect_flg = 1;
			}

			if ( (this.ACCOUNT == null || account == null) && redirect_flg == 1 ) {
				this.REDIRECT_URL = "!LIN0011ST1!";
				this.setPath("REDIRECT.htm");
				return;
			}

			if(  account != null){
				this.LINE_ACCOUNT_ID = account.getLine_account_id();
				this.BASIC_ID = account.getBasic_id();
				this.CHANEL_ID = account.getChanel_id();
				this.CHANEL_SECRET = account.getChanel_secret();
				this.CHANEL_ACCESS_TOKEN = account.getChanel_access_token();
				this.LIFF_ID = account.getLiff_id();
				this.LIMIT = account.getLimit();
				this.SEND_LIMIT = account.getSend_limit();
				this.LOGIN_CHANEL_ID = account.getLogin_chanel_id();
				this.LOGIN_CHANEL_SECRET = account.getLogin_chanel_secret();

				//this.sess.put("ACCOUNT", this.ACCOUNT);
				//this.sess.put("LINE_ACCOUNT_ID", this.LINE_ACCOUNT_ID);
			}


			// セッション格納
			if ( this.sc.getAttribute(this.SESSION_ID) == null ) {
				this.req.getSession().setAttribute(this.SESSION_ID, this.sess);
				this.sc.setAttribute(this.SESSION_ID, this.sess);
			} else {
				this.req.getSession().invalidate();
				this.sc.setAttribute(this.SESSION_ID, this.sess);
			}

			// ASP_TYPE情報の取得
			this.sqlMap03 = SqlConfig.getSqlMapInstance(this.SYS_SERVER_IP_03,"3306", "dto/map/sqlMapConfig.xml");
			AccountInfo03 info03 = (AccountInfo03)this.sqlMap03.queryForObject("getAccountInfo03", this.ACCOUNT);

			this.sqlMapSSL24 = SqlConfig.getSqlMapInstance(this.SYS_SERVER_IP_03,"3306", "dto/map/sqlMapConfigSSL24.xml");
			this.ASP_PLAN = common.util.toNum( this.sqlMapSSL24.queryForObject("getPlanId", this.ACCOUNT) );

			this.SCHEMA = info03.getSchema();
			this.ASP_TYPE = info03.getAsp_type();

			// サイドバーミニマライザーの値
			this.MINIMIZE_CLASS = this.getParam("min");

			switch ( this.ASP_TYPE ) {
			case common.util.AUTO_BIZ:
				this.LBL_SNO = "pid";
				this.LBL_SNAME = "scenario";
				this.TB_SCENARIO = "scenario";
				this.TB_FORM_FRAME = "form_frame";
				this.TB_PLAN_FRAME = "plan_frame";
				this.TB_PAGE_FRAME = "page_frame";
				this.TB_ADDRESS = "plan_mng";
				this.TITLE_SNAME = "シナリオ";
				break;

			case common.util.POWER_STEPMAIL:
				this.LBL_SNO = "sno";
				this.LBL_SNAME = "scenario";
				this.TB_SCENARIO = "st_scenario";
				this.TB_FORM_FRAME = "st_form_frame";
				this.TB_PLAN_FRAME = "st_plan_frame";
				this.TB_PAGE_FRAME = "st_page_frame";
				this.TB_ADDRESS = "st_plan_mng";
				this.TITLE_SNAME = "シナリオ";
				break;

			case common.util.POWER_RESPONDER:
				this.LBL_SNO = "tno";
				this.LBL_SNAME = "thread";
				this.TB_SCENARIO = "ar_thread";
				this.TB_FORM_FRAME = "ar_form_frame";
				this.TB_PLAN_FRAME = "";
				this.TB_PAGE_FRAME = "ar_page_frame";
				this.TB_ADDRESS = "ar_entry_th";
				this.TITLE_SNAME = "スレッド";
				break;
			}

			this.SERVER_ID = info03.getServer_id();
			{
				String[] ip = this.sysProp.getProperty("SERVER_IP").split(",");
				String[] domain = this.sysProp.getProperty("SERVER_DOMAIN").split(",");
				String[] plefix = this.sysProp.getProperty("SERVER_PLEFIX").split(",");
				{
					// 独自ドメインサーバ用処理
					//ip[6] = info03.getServer_ip();
					ip[6] = "192.168.10.1";
					domain[6] = info03.getServer_domain();
				}

				this.SERVER_IP = ip[this.SERVER_ID];
				this.SERVER_DOMAIN = domain[this.SERVER_ID];
				this.SERVER_PLEFIX = plefix[this.SERVER_ID];
			}

			// AutoBiz用SQLMAP
			this.sqlMapSM = SqlConfig.getSqlMapInstance(this.SERVER_IP,"3306", "dto/map/sqlMapConfig.xml");

			// 76サーバーの場合にはデモアカウント判定
			if( this.SERVER_PLEFIX.equals("76") ){
				this.SETUP_INFO = (SetUpInfo) this.sqlMapSM.queryForObject("getSetUpInfoDemo",this.ACCOUNT);
				this.DEMO = this.SETUP_INFO.getDemo();
			}

			// ASP_PLAN=1(ライトプラン) ASP_PLAN=5(スーパーライトプラン)　ASP_PLAN=3(レスポンダー) デモアカウントは使用不可
			if( this.ASP_PLAN == 1 || this.ASP_PLAN == 5 || this.ASP_PLAN == 3 || this.DEMO == 1 ){
				this.REDIRECT_URL = "!LIN9999!";
				this.setPath("REDIRECT.htm");
				return;
			}

			// IP制限

			// リモートIP
			String ip_address = this.req.getRemoteAddr();
			String reql_ip = this.req.getHeader("X-Real-IP"); // 本番環境用

// 本環境ではコメントアウトを外す
/*
			// ローカルホストIP(自分自身)
			InetAddress addr = InetAddress.getLocalHost();
			this.LOCAL_IP = addr.getHostAddress();

			String media_post_path = this.req.getServletPath();

			boolean isError = true;
			if ( reql_ip.equals( this.SERVER_IP) ){
				isError = false;
			}
			if ( addr.getHostAddress().equals("192.168.10.33") ){
				isError = false;
			}
			if ( addr.getHostAddress().equals("192.168.11.104") ){
				isError = false;
			}
			if( media_post_path.equals("/NMH0062.htm") && "TRUE".equals(this.getParam("FROM_AJAX")) ){
				isError = false;
			}
			if ( isError ) {
				this.logE(reql_ip + ":" + this.SERVER_IP);
				this.REDIRECT_URL = "!LIN9999!";
				this.setPath("REDIRECT.htm");
				return;
			}
*/

			// Referer チェック
			/*
			String referer = this.req.getHeader("REFERER");
			if ( referer != null && referer.length() > 0 ) {
				boolean isError = true;
				if ( referer.indexOf("http://" + this.SYS_SERVER_DOMAIN + "/") >= 0 ) isError = false;
				if ( referer.indexOf("https://" + this.SYS_SERVER_DOMAIN + "/") >= 0 ) isError = false;
				if ( referer.indexOf("http://" + this.SERVER_DOMAIN + "/") >= 0 ) isError = false;
				if ( referer.indexOf("https://" + this.SERVER_DOMAIN + "/") >= 0 ) isError = false;
				if ( referer.indexOf("http://" + this.SYS_SERVER_IP + "/") >= 0 ) isError = false;
				if ( referer.indexOf("https://" + this.SYS_SERVER_IP + "/") >= 0 ) isError = false;
				if ( referer.indexOf("http://" + this.SERVER_IP + "/") >= 0 ) isError = false;
				if ( referer.indexOf("https://" + this.SERVER_IP + "/") >= 0 ) isError = false;
				if ( referer.indexOf("http://" + this.BRD_SERVER_DOMAIN + "/") >= 0 ) isError = false;
				if ( referer.indexOf("https://" + this.BRD_SERVER_DOMAIN + "/") >= 0 ) isError = false;
				if ( referer.indexOf("https://192.168.11.220/") >= 0 ) isError = false;
				if ( referer.indexOf("https://localhost:8080/") >= 0 ) isError = false;
				if ( referer.indexOf("http://192.168.11.104:8080/") >= 0 ) isError = false;
				if ( isError ) {
					this.logE("Referer チェックに失敗しました。");
					this.logE(referer);
					super.setForward("/error.html");
					return;
				}
			} else {
				this.logE("Referer の無いアクセスです。");
				this.logE(referer);
				super.setForward("/error.html");
				return;
			}
			*/


			// 利用停止中の場合の遷移
			if ( this.USING_SERVICE == 1 ) this.setForward("/stop.html");

			// 画面別セッションの取得と格納
			{
				HashMap<String, String> hashMaps;
				try {
					hashMaps = (HashMap<String, String>)this.sess.get(this.getClass().getSimpleName());
					if ( hashMaps == null ) hashMaps = new HashMap<String, String>();

					Enumeration<String> enu = this.req.getParameterNames();
					while ( enu.hasMoreElements() ) {
						String key = enu.nextElement();
						hashMaps.put(key, this.getParam(key) );
					}

				} catch (Exception e) {
					hashMaps = new HashMap<String, String>();
				}

				this.sess.put(this.getClass().getSimpleName(), hashMaps);
			}

			// 現在日時の取得
			Calendar now = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM");

			// 同月内での送信予約件数取得
			/*
			{
				HashMap paraMap = new HashMap();
				paraMap.put("ACCOUNT", this.ACCOUNT);
				paraMap.put("SCHEMA", this.SCHEMA);
				paraMap.put("SENDYM", sdf.format(now.getTime()));

				if ( now.get(Calendar.DATE) > 15 ) {
					now.add(Calendar.MONTH, 1);
				}

				paraMap.put("SENDYMD_ED", sdf.format(now.getTime()) + "/15");
				now.add(Calendar.MONTH, -1);
				paraMap.put("SENDYMD_ST", sdf.format(now.getTime()) + "/16");
				paraMap.put("CUTOFF_DATE", this.CUTOFF_DATE);

				this.sqlMap03.insert("createMailQueTable", paraMap);
				this.sqlMap03.insert("createMailQueView", paraMap);

				this.SEND_COUNT_FIX = common.util.toNum(this.sqlMap03.queryForObject("getSendMailCountFix", paraMap));
				this.SEND_COUNT_RSV = common.util.toNum(this.sqlMap03.queryForObject("getSendMailCountRsv", paraMap));
				this.SEND_COUNT = this.LIMIT_MONTH - this.SEND_COUNT_FIX - this.SEND_COUNT_RSV;
				if ( this.SEND_COUNT <= 0 ) this.SEND_COUNT = 0;
			}
			*/

			// LINE設定情報の確認
			/*
			if(
					(common.util.isEmpty( this.CHANEL_ID ) || common.util.isEmpty( this.CHANEL_SECRET) || common.util.isEmpty( this.CHANEL_ACCESS_TOKEN)) &&
					!("/LIN0011ST1.htm".equals(this.req.getServletPath()))
			){

				this.REDIRECT_URL = "!LIN0011ST1!";
				this.setPath("REDIRECT.htm");
				return;

			} else
			*/
			if(

					( common.util.isNotEmpty(this.CHANEL_ID) && common.util.isNotEmpty(this.CHANEL_SECRET) && common.util.isNotEmpty( this.CHANEL_ACCESS_TOKEN) ) &&
					( common.util.isEmpty( this.LOGIN_CHANEL_ID ) || common.util.isEmpty( this.LOGIN_CHANEL_SECRET) ) &&
					!("/LIN0011ST3.htm".equals(this.req.getServletPath()))

					){

				// php2java.phpにリダイレクト先を渡す
				this.REDIRECT_URL = "!LIN0011ST3!";
				this.setPath("REDIRECT.htm");

			} else if (

					common.util.isNotEmpty(this.CHANEL_ID) && common.util.isNotEmpty(this.CHANEL_SECRET) && common.util.isNotEmpty( this.CHANEL_ACCESS_TOKEN) &&
					common.util.isNotEmpty( this.LOGIN_CHANEL_ID ) && common.util.isNotEmpty( this.LOGIN_CHANEL_SECRET) &&
					this.LIMIT == 0 &&
					!("/LIN0011ST4.htm".equals(this.req.getServletPath()))

					){

				// php2java.phpにリダイレクト先を渡す
				this.REDIRECT_URL = "!LIN0011ST4!";
				this.setPath("REDIRECT.htm");

			}

		} catch (Exception e) {
			this.logE(e);
			this.setRedirect("/error.html");
		}
	}

	public void onGet() { this.onExec(); }
	public void onPost() { this.onExec(); }
	protected abstract void onExec();

	public void onRender() {

		this.res.setHeader("P3P", "CP=\"CAO PSA OUR\"");

		// 操作ログ
		this.logA("--------------------------------------( " + this.path + " )");
		this.logA("REFERER TO " + this.req.getHeader("REFERER"));
	}

	// リクエスト引数取得
	protected String getParam (String str) {
		String retValue = "";
		String[] array = this.req.getParameterValues(str);
		if ( array == null  ) {
			retValue = "";

		} else if ( array != null && array.length > 1 ) {
			String buf = new String();
			for (String s: array) {
				if ( buf.length() > 0 ) {
					buf += ",";
				}
				buf += s;
			}
			retValue = buf.toString();

		} else {
			retValue = array[0];
		}
		return util.Nvl(retValue);
	}
	protected String getParam (String str, String defstr) {
		String retValue = "";
		retValue = this.getParam(str);
		if ( "".equals(retValue) ) retValue = defstr;
		return retValue;
	}

	// 画面別セッションからリクエスト引数を取得
	@SuppressWarnings("unchecked")
	protected String getSessionParam (String key) {
		HashMap<String, String> hashMaps;

		try {
			hashMaps = (HashMap<String, String>)this.sess.get(this.getClass().getSimpleName());
			if ( hashMaps == null ) hashMaps = new HashMap<String, String>();

		} catch (Exception e) {
			hashMaps = new HashMap<String, String>();
		}

		return common.util.Nvl(hashMaps.get(key));
	}

	// 画面別セッションの初期化
	@SuppressWarnings("unchecked")
	protected void initSessionParam () {
		HashMap<String, String> hashMaps= new HashMap<String, String>();

		try {
			Enumeration<String> enu = this.req.getParameterNames();
			while ( enu.hasMoreElements() ) {
				String key = enu.nextElement();
				hashMaps.put(key, this.getParam(key) );
			}

		} catch (Exception e) {
			hashMaps = new HashMap<String, String>();
		}

		this.sess.put(this.getClass().getSimpleName(), hashMaps);
	}

	// Actionログ出力
	protected void logA(String msg) {
		this.actionLogger.info("[" + this.ACCOUNT + "][" + this.req.getRemoteAddr() + "] " + msg);
	}

	// Errorログ出力
	protected void logE(String msg) {
		this.errorLogger.error("[" + this.ACCOUNT + "][" + this.req.getRemoteAddr() + "] " + msg);
	}
	protected void logE(Exception ex) {
		StackTraceElement[] elements = ex.getStackTrace();
		String msg = ex.toString();
        for ( StackTraceElement ste : elements ) {
        	msg += "\n\t" + ste;
        }
		this.errorLogger.error("[" + this.ACCOUNT + "][" + this.req.getRemoteAddr() + "] " + msg);
	}

	@SuppressWarnings("unchecked")
	protected HashMap getBaseQueryParam() {
		HashMap paraMap = new HashMap();
		paraMap.put("ACCOUNT", this.ACCOUNT);
		paraMap.put("LINE_ACCOUNT_ID", this.LINE_ACCOUNT_ID);
		paraMap.put("SCHEMA", this.SCHEMA);
		paraMap.put("ASP_TYPE", this.ASP_TYPE);
		paraMap.put("SERVER_ID", this.SERVER_ID);
		paraMap.put("CHANEL_ACCESS_TOKEN", this.CHANEL_ACCESS_TOKEN);
		return paraMap;
	}
}
