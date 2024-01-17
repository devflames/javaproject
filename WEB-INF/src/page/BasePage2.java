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
import net.sf.click.Page;

public abstract class BasePage2 extends Page {

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

	// コンストラクタ
	public BasePage2() {

		super();

		// Loggerを作成
		this.actionLogger = LogFactory.getLog("ACTION");
		this.errorLogger = LogFactory.getLog("ERROR");

		// 各種サーブレットオブジェクトの取得
		this.sc = this.getContext().getServletContext();
		this.req = this.getContext().getRequest();
		this.res = this.getContext().getResponse();

		this.TODAY = new CalendarEx();

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
			//this.sqlMap = SqlConfig.getSqlMapInstance(this.SYS_SERVER_IP, "55336","dto/map/sqlMapConfig.xml");
			this.sqlMap = SqlConfig.getSqlMapInstance(this.SYS_SERVER_IP, "3306","dto/map/sqlMapConfig.xml");
			LineAccount account = (LineAccount) this.sqlMap.queryForObject("getAccountInfoByAccountName", this.ACCOUNT);

			// ASP_TYPE情報の取得
			this.sqlMap03 = SqlConfig.getSqlMapInstance(this.SYS_SERVER_IP_03,"3306", "dto/map/sqlMapConfig.xml");
			AccountInfo03 info03 = (AccountInfo03)this.sqlMap03.queryForObject("getAccountInfo03", this.ACCOUNT);

			this.sqlMapSSL24 = SqlConfig.getSqlMapInstance(this.SYS_SERVER_IP_03,"3306", "dto/map/sqlMapConfigSSL24.xml");
			this.ASP_PLAN = common.util.toNum( this.sqlMapSSL24.queryForObject("getPlanId", this.ACCOUNT) );

			this.SCHEMA = info03.getSchema();
			this.ASP_TYPE = info03.getAsp_type();

			this.SERVER_ID = info03.getServer_id();
			{
				String[] ip = this.sysProp.getProperty("SERVER_IP").split(",");
				String[] domain = this.sysProp.getProperty("SERVER_DOMAIN").split(",");
				String[] plefix = this.sysProp.getProperty("SERVER_PLEFIX").split(",");
				{
					// 独自ドメインサーバ用処理
					ip[6] = info03.getServer_ip();
					domain[6] = info03.getServer_domain();
				}

				this.SERVER_IP = ip[this.SERVER_ID];
				this.SERVER_DOMAIN = domain[this.SERVER_ID];
				this.SERVER_PLEFIX = plefix[this.SERVER_ID];
			}


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
