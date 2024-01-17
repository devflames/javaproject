/*
 * Module Name		util
 * Info				共通ユーティリティ
 * Auther			H-Andoh.
 * Date				2009-09-01
*/
package common;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;


public class util {

	// ASPタイプ
	public static final int AUTO_BIZ = 1;
	public static final int POWER_STEPMAIL = 2;
	public static final int POWER_RESPONDER = 3;

	// ブラウザ種別
	public static final int BROWSER_UNKNOWN = 0;
	public static final int BROWSER_IE = 1;
	public static final int BROWSER_FIREFOX = 2;
	public static final int BROWSER_OPERA = 3;
	public static final int BROWSER_CHROME = 4;
	public static final int BROWSER_SAFARI = 5;
	public static final int BROWSER_NETSCAPE = 6;

	// プロパティ情報を取得
	public final static Properties getProperties(String _filePath) {

		Properties retValue = new Properties();

		// 設定情報取得
		try {
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			InputStream in = new BufferedInputStream(loader.getResourceAsStream( _filePath ));
			retValue.load(in);
			in.close();

		} catch (Exception ex) {
			retValue = null;
		}

		return retValue;
	}

	// HTMLエスケープ(HTMLタグ→エスケープ文字)
	public final static String ht (String val) {
		try {
			return val.replaceAll("&", "&amp;").replaceAll("\"", "&quot;").replaceAll("<", "&lt;").replaceAll(">", "&gt;");
		} catch (Exception e) {
			return val;
		}
	}

	// HTMLエスケープ(エスケープ文字→HTMLタグ)
	public final static String th (String val) {
		try {
			return val.replaceAll("&amp;", "&").replaceAll("&quot;", "\"").replaceAll("&lt;", "<").replaceAll("&gt;", ">");
		} catch (Exception e) {
			return val;
		}
	}

	// HTTPリクエスタ
	@SuppressWarnings("unchecked")
	public static int httpGet(String urlString) {

		int retValue;

		try {
			URL url = new URL(urlString);

			// HTTP接続設定
			HttpURLConnection urlconn = (HttpURLConnection)url.openConnection();
			urlconn.setRequestMethod("GET");
			urlconn.setInstanceFollowRedirects(false);
			urlconn.setUseCaches(false);

			// HTTP接続開始
			urlconn.connect();

			// 戻り値取得
			retValue = urlconn.getResponseCode();

			// HTTP切断
			urlconn.disconnect();

		} catch (Exception e) {
			retValue = -1;
		}

		return retValue;
	}

	// HTTPリクエスタ
	public static String httpGet(String urlString, String token) {

		String retValue;

		try {
			URL url = new URL(urlString);

			// HTTP接続設定
			HttpURLConnection urlconn = (HttpURLConnection)url.openConnection();
			urlconn.setRequestMethod("GET");
			urlconn.setInstanceFollowRedirects(false);
			urlconn.setUseCaches(false);
			if( common.util.isNotEmpty(token)){
				urlconn.setRequestProperty("Authorization", "Bearer " + token);
			}

			// HTTP接続開始
			urlconn.connect();

			// 戻り値取得
			retValue = urlconn.getResponseMessage();

			// 接続が確立したとき
			StringBuilder resultBuilder = new StringBuilder();
			String line = "";

			BufferedReader br = new BufferedReader(new InputStreamReader(urlconn.getInputStream()));

			// レスポンスの読み込み
			while ( (line = br.readLine()) != null ) {
				resultBuilder.append(line);
			}
			retValue = resultBuilder.toString();


			// HTTP切断
			urlconn.disconnect();

		} catch (Exception e) {
			retValue = "";
		}

		return retValue;
	}

	public static String httpPost(String urlString, String token, String json) {

		String retValue;

		try {
			URL url = new URL(urlString);

			// HTTP接続設定
			HttpURLConnection urlconn = (HttpURLConnection)url.openConnection();
			urlconn.setRequestMethod("POST");
			urlconn.setDoInput(true);
			urlconn.setDoOutput(true);
			urlconn.setChunkedStreamingMode(0);
			urlconn.setInstanceFollowRedirects(false);
			urlconn.setUseCaches(false);
			urlconn.setRequestProperty("Connection", "Keep-Alive");
			urlconn.setRequestProperty("Content-Type", "application/json");
			urlconn.setRequestProperty("Authorization", "Bearer " + token);

			// データを送信する
			PrintStream ps = new PrintStream(new DataOutputStream(urlconn.getOutputStream()));

			ps.print( json );
            ps.close();

			// レスポンスを受信する
			int iResponseCode = urlconn.getResponseCode();

			// 接続が確立したとき
			if ( iResponseCode == HttpURLConnection.HTTP_OK ) {
				StringBuilder resultBuilder = new StringBuilder();
				String line = "";

				BufferedReader br = new BufferedReader(new InputStreamReader(urlconn.getInputStream()));

				// レスポンスの読み込み
				while ( (line = br.readLine()) != null ) {
					resultBuilder.append(line);
				}
				retValue = resultBuilder.toString();
			}

			// 接続が確立できなかったとき
			else {
				retValue = urlconn.getResponseMessage();
			}

			// HTTP切断
			urlconn.disconnect();

		} catch (Exception e) {
			retValue = null;
		}

		return retValue;
	}

	public static String httpPut(String urlString, String token, String json) {

		String retValue;

		try {
			URL url = new URL(urlString);

			// HTTP接続設定
			HttpURLConnection urlconn = (HttpURLConnection)url.openConnection();
			urlconn.setRequestMethod("PUT");
			urlconn.setDoInput(true);
			urlconn.setDoOutput(true);
			urlconn.setChunkedStreamingMode(0);
			urlconn.setInstanceFollowRedirects(false);
			urlconn.setUseCaches(false);
			urlconn.setRequestProperty("Connection", "Keep-Alive");
			urlconn.setRequestProperty("Content-Type", "application/json");
			urlconn.setRequestProperty("Authorization", "Bearer " + token);

			// データを送信する
			PrintStream ps = new PrintStream(new DataOutputStream(urlconn.getOutputStream()));

			ps.print( json );
            ps.close();

			// レスポンスを受信する
			int iResponseCode = urlconn.getResponseCode();

			// 接続が確立したとき
			if ( iResponseCode == HttpURLConnection.HTTP_OK ) {
				StringBuilder resultBuilder = new StringBuilder();
				String line = "";

				BufferedReader br = new BufferedReader(new InputStreamReader(urlconn.getInputStream()));

				// レスポンスの読み込み
				while ( (line = br.readLine()) != null ) {
					resultBuilder.append(line);
				}
				retValue = resultBuilder.toString();
			}

			// 接続が確立できなかったとき
			else {
				retValue = urlconn.getResponseMessage();
			}

			// HTTP切断
			urlconn.disconnect();

		} catch (Exception e) {
			retValue = null;
		}

		return retValue;
	}

	public static String httpPostParam(String urlString,  HashMap<String, String> param) {

		String retValue;

		try {
			URL url = new URL(urlString);

			// HTTP接続設定
			HttpURLConnection urlconn = (HttpURLConnection)url.openConnection();
			urlconn.setRequestMethod("POST");
			urlconn.setDoInput(true);
			urlconn.setDoOutput(true);
			urlconn.setChunkedStreamingMode(0);
			urlconn.setInstanceFollowRedirects(false);
			urlconn.setUseCaches(false);
			urlconn.setRequestProperty("Connection", "Keep-Alive");
			urlconn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

			// 引数をセットする
			ArrayList<String> paramArray = new ArrayList<String>();
			for ( String key : param.keySet() ) {
				paramArray.add(key + "=" + param.get(key) );
			}

			// データを送信する
			PrintStream ps = new PrintStream(new DataOutputStream(urlconn.getOutputStream()));
            ps.print( util.Join(paramArray.toArray(), "&"));
            ps.close();

			// レスポンスを受信する
			int iResponseCode = urlconn.getResponseCode();

			// 接続が確立したとき
			if ( iResponseCode == HttpURLConnection.HTTP_OK ) {
				StringBuilder resultBuilder = new StringBuilder();
				String line = "";

				BufferedReader br = new BufferedReader(new InputStreamReader(urlconn.getInputStream()));

				// レスポンスの読み込み
				while ( (line = br.readLine()) != null ) {
					resultBuilder.append(line);
				}
				retValue = resultBuilder.toString();
			}

			// 接続が確立できなかったとき
			else {
				retValue = urlconn.getResponseMessage();
			}

			// HTTP切断
			urlconn.disconnect();

		} catch (Exception e) {
			retValue = null;
		}

		return retValue;
	}

	public static byte[] httpGetBinary(String urlString, HashMap<String, String> param) {

		byte[] retValue = null;

		try {

			// 引数をセットする
			ArrayList<String> paramArray = new ArrayList<String>();
			for ( String key : param.keySet() ) {
				paramArray.add(key + "=" + param.get(key));
			}

			URL url;
			if ( urlString.indexOf("?") == -1 ) {
				url = new URL(urlString + "?" + util.Join(paramArray.toArray(), "&"));
			} else {
				url = new URL(urlString + "&" + util.Join(paramArray.toArray(), "&"));
			}

			// HTTP接続設定
			HttpURLConnection urlconn = (HttpURLConnection)url.openConnection();
			urlconn.setRequestMethod("GET");
			urlconn.setInstanceFollowRedirects(false);
			urlconn.setUseCaches(false);

			// HTTP接続開始
			urlconn.connect();

			// レスポンスを受信する
			int iResponseCode = urlconn.getResponseCode();

			// 接続が確立したとき
			if ( iResponseCode == HttpURLConnection.HTTP_OK ) {

			    byte[] buff = new byte[1];
				InputStream is = urlconn.getInputStream();
			    ByteArrayOutputStream bos = new ByteArrayOutputStream();

				// レスポンスの読み込み
			    while ( is.read(buff) > 0 ) bos.write(buff);

				bos.close();
			    is.close();

			    // バイト配列変換
			    retValue = bos.toByteArray();
			}

			// HTTP切断
			urlconn.disconnect();

		} catch (Exception e) {
			retValue = null;
		}

		return retValue;
	}

	public static String httpPost(String urlString, HashMap<String, String> param) {

		String retValue;

		try {
			URL url = new URL(urlString);

			// HTTP接続設定
			HttpURLConnection urlconn = (HttpURLConnection)url.openConnection();
			urlconn.setRequestMethod("POST");
			urlconn.setDoInput(true);
			urlconn.setDoOutput(true);
			urlconn.setChunkedStreamingMode(0);
			urlconn.setInstanceFollowRedirects(false);
			urlconn.setUseCaches(false);
			urlconn.setRequestProperty("Connection", "Keep-Alive");

			// 引数をセットする
			ArrayList<String> paramArray = new ArrayList<String>();
			for ( String key : param.keySet() ) {
				paramArray.add(key + "=" + param.get(key));
			}

			// データを送信する
			PrintStream ps = new PrintStream(new DataOutputStream(urlconn.getOutputStream()));
            ps.print( util.Join(paramArray.toArray(), "&") );
            ps.close();

			// レスポンスを受信する
			int iResponseCode = urlconn.getResponseCode();

			// 接続が確立したとき
			if ( iResponseCode == HttpURLConnection.HTTP_OK ) {
				StringBuilder resultBuilder = new StringBuilder();
				String line = "";

				BufferedReader br = new BufferedReader(new InputStreamReader(urlconn.getInputStream()));

				// レスポンスの読み込み
				while ( (line = br.readLine()) != null ) {
					resultBuilder.append(line);
				}
				retValue = resultBuilder.toString();
			}

			// 接続が確立できなかったとき
			else {
				retValue = urlconn.getResponseMessage();
			}

			// HTTP切断
			urlconn.disconnect();

		} catch (Exception e) {
			retValue = null;
		}

		return retValue;
	}

	// Null値判定
	public static boolean isNull (String str) {
		boolean bl = false;
		if ( str == null || str.length() < 1 ) {
			bl = true;
		}
		return bl;
	}

	// メールアドレス形式チェック
	public static boolean isEmailFormatCheck(String email) {
		boolean bl = false;
		try {
			new InternetAddress(email, true);
			bl = true;
		} catch (AddressException e) {
		}
		return bl;
	}

	// Null値の空文字変換
	public static String ReplaceAll(String str, String regex, String replacement) {
		String ret = Nvl(str);
		try {

			while ( ret.indexOf(regex) > 0 ) {
				ret = ret.replaceAll(regex, replacement);
			}

		} catch (Exception ex) {}

		return ret;
	}

	// Null値の空文字変換
	public static String Nvl(String str) {
		String ret = str;
		if ( str == null || str.length() < 1 ) {
			ret = "";
		}
		return ret;
	}

	// 数値変換
	public static int toNum (String str) {
		int ret = 0;
		try {
			ret = Integer.parseInt(str);
		} catch (Exception e) {}
		return ret;
	}
	public static int toNum (String str, int def) {
		int ret = 0;
		try {
			ret = Integer.parseInt(str);
		} catch (Exception e) {
			ret = def;
		}
		return ret;
	}
	public static int toNum (Object obj) {
		int ret = 0;
		try {
			ret = (Integer) obj;
		} catch (Exception e) {}
		return ret;
	}
	public static long toLong (String str) {
		long ret = 0;
		try {
			ret = Long.parseLong(str);
		} catch (Exception e) {}
		return ret;
	}
	public static long toLong (String str, long def) {
		long ret = 0;
		try {
			ret = Long.parseLong(str);
		} catch (Exception e) {
			ret = def;
		}
		return ret;
	}
	public static long toLong (Object obj) {
		long ret = 0;
		try {
			ret = (Long) obj;
		} catch (Exception e) {}
		return ret;
	}
	public static boolean isNum (Object obj) {
		try {
			@SuppressWarnings("unused")
			int val = (Integer)obj;
		} catch (Exception e) {
			try {
				Integer.parseInt(obj.toString());
			} catch (Exception e2) {
				return false;
			}
		}
		return true;
	}

	// 文字変換
	public static String toStr (int avall) {
		return Integer.toString(avall);
	}
	public static String toStr (long avall) {
		return Long.toString(avall);
	}

	public static String Join(Object[] array, String sep) {
		if (array == null) {
			return null;

		} else if ( array.length < 1 ) {
			return "";

		} else if ( array.length < 2 ) {
			return ( array[0] != null ) ? array[0].toString() : "";
		}

		StringBuffer buf = new StringBuffer( ( array[0] != null ) ? array[0].toString() : "" );

		for (int i = 1; i < array.length; i++) {
			buf.append(sep);
			buf.append( ( array[i] != null ) ? array[i].toString() : "" );
		}

		return buf.toString();
	}

	public static String Cut(String str, String sep) {

		String retValue = new String("");
		ArrayList<String> newArray = new ArrayList<String>();

		try {
			String[] array = str.split(sep);

			for ( String buf : array ) {
				if ( ! "".equals(buf) ) newArray.add(buf);
			}

			retValue = Join(newArray.toArray(new String[]{}), sep);
		} catch (Exception ex) {
			retValue = str;
		}

		return retValue;
	}

	public static boolean isEmpty (String val) {
		return ( "".equals(val) || val == null );
	}
	public static boolean isNotEmpty (String val) {
		return ( ! "".equals(val) && val != null );
	}

	// ファイル名から拡張子を取得
	public static String getSuffix(String fileName) {
		if (fileName == null) return null;
		int point = fileName.lastIndexOf(".");
		if (point != -1)  return fileName.substring(point + 1);
		return fileName;
	}

	// ブラウザの判定
	public static int getBrowser(String sUserAgent) {

		if ( isIE(sUserAgent) )			return BROWSER_IE;
		if ( isIE2(sUserAgent) )		return BROWSER_IE;
		if ( isEdge(sUserAgent) )		return BROWSER_IE;
		if ( isFirefox(sUserAgent) )	return BROWSER_FIREFOX;
		if ( isOpera(sUserAgent) )		return BROWSER_OPERA;
		if ( isChrome(sUserAgent) )		return BROWSER_CHROME;
		if ( isSafari(sUserAgent) )		return BROWSER_SAFARI;
		if ( isNetscape(sUserAgent) )	return BROWSER_NETSCAPE;
		return BROWSER_UNKNOWN;
	}

	// IE
	public static boolean isIE(String sUserAgent) {
		Pattern pattern = Pattern.compile(".*((MSIE)+ [0-9]\\.[0-9]).*");
		Matcher matcher = pattern.matcher(sUserAgent);
		boolean bMatch = matcher.matches();
		return bMatch;
	}

	// IE10以降-
	public static boolean isIE2(String sUserAgent) {
		Pattern pattern = Pattern.compile(".*((Trident)+/?[0-9]\\.?[0-9]?).*");
		Matcher matcher = pattern.matcher(sUserAgent);
		boolean bMatch = matcher.matches();
		return bMatch;
	}

	// MS Edge
	public static boolean isEdge(String sUserAgent) {
		Pattern pattern = Pattern.compile(".*((Edge)+/?[0-9]\\.?[0-9]?).*");
		Matcher matcher = pattern.matcher(sUserAgent);
		boolean bMatch = matcher.matches();
		return bMatch;
	}

	// Firefox
	public static boolean isFirefox(String sUserAgent) {
		Pattern pattern = Pattern.compile(".*((Firefox/)+[0-9]\\.[0-9]\\.?[0-9]?).*");
		Matcher matcher = pattern.matcher(sUserAgent);
		boolean bMatch = matcher.matches();
		return bMatch;
	}

	// Opera
	public static boolean isOpera(String sUserAgent) {
		Pattern pattern = Pattern.compile(".*((Opera)+/? ?[0-9]\\.[0-9][0-9]?).*");
		Matcher matcher = pattern.matcher(sUserAgent);
		boolean bMatch = matcher.matches();
		return bMatch;
	}

	// Chrome
	public static boolean isChrome(String sUserAgent) {
		Pattern pattern = Pattern.compile(".*((Chrome)+/?[0-9]\\.?[0-9]?).*");
		Matcher matcher = pattern.matcher(sUserAgent);
		boolean bMatch = matcher.matches();
		return bMatch;
	}

	// Safari
	public static boolean isSafari(String sUserAgent) {
		Pattern pattern = Pattern.compile(".*((Version/)+[0-9]\\.?[0-9]?\\.?[0-9]? Safari).*");
		Matcher matcher = pattern.matcher(sUserAgent);
		boolean bMatch = matcher.matches();
		return bMatch;
	}

	// Netscape
	public static boolean isNetscape(String sUserAgent) {
		Pattern pattern = Pattern.compile(".*((Netscape)+[0-9]\\.[0-9][0-9]?).*");
		Matcher matcher = pattern.matcher(sUserAgent);
		boolean bMatch = matcher.matches();
		return bMatch;
	}

	// UserAgentによるサイト振り分け判定
	public static boolean isMobile( HttpServletRequest req) {

		// false = PC , true = MOBILE
		boolean mobile = false;

		String ua = req.getHeader("User-Agent");

		int iphone = ua.indexOf("iPhone");
		int ipad = ua.indexOf("iPad");
		int ipod = ua.indexOf("iPod");
		int win = ua.indexOf("Windows Phone");

		int android = ua.indexOf("Android");

		int android_m = -1;
		int android_t = -1;

		if ( -1 < android ) {
			android_m = ua.indexOf("Mobile");
			android_t = ua.indexOf("Tablet");
		}

		if(-1 < ipad || -1 < iphone || -1 < ipod || -1 < win || -1 < android_m || -1 < android_t ) {
			mobile = true;
		}

		return mobile;
	}

	// 日付チェック
	public static boolean isDatetime(String _dt, String _format) {
		SimpleDateFormat sdf = new SimpleDateFormat(_format);
		try {
			sdf.parse(_dt.replaceAll("\\/", "-"));
			sdf.setLenient(false);
		} catch (ParseException ex) {
			return false;
		}
		return true;
	}

	public static String passwordGenerate(int passNum,int styleNum, boolean useSign) {

		 //パスワード桁数
        int length = passNum;

        //記号使用有無 useSign = true;

        //アルファベット大文字小文字のスタイル(1:normal/2:lowerCase/3:upperCase)
        int style = styleNum;

        //生成処理
        StringBuilder result = new StringBuilder();

        //パスワードに使用する文字を格納
        StringBuilder source = new StringBuilder();

        //数字
        for (int i = 0x30; i < 0x3A; i++) {
            source.append((char) i);
        }
        //記号
        if (useSign) {
            for (int i = 0x21; i < 0x30; i++) {
                source.append((char) i);
            }
        }
        //アルファベット小文字
        switch (style) {
            case 2:
                break;
            default:
                for (int i = 0x41; i < 0x5b; i++) {
                    source.append((char) i);
                }
                break;
        }
        //アルファベット大文字
        switch (style) {
            case 3:
                break;
            default:
                for (int i = 0x61; i < 0x7b; i++) {
                    source.append((char) i);
                }
                break;
        }

        int sourceLength = source.length();
        SecureRandom random = new SecureRandom();
        while (result.length() < length) {
            result.append(source.charAt(Math.abs(random.nextInt()) % sourceLength));
        }

		return new String(result);
	}

	// HTML -> テキスト
	public final static String htmlToText (String html) {
		try {

			String header = "" ;
			int bodyStart = 0;
			if (html.indexOf("<!-- /HEADER -->") > 0 ) {
				header = html.substring( 0, html.indexOf("<!-- /HEADER -->")+16);
				header = header.replaceAll("&nbsp;", " ");
				header = header.replaceAll("\\<.*?\\>", "");
				header = header.replaceAll("\t", "");
				header = th(header);
				header = header.trim();
				header = header + "\r\n\r\n";
				bodyStart = html.indexOf("<!-- /HEADER -->")+16;
			}

			String footer = "";
			int bodyEnd = html.length();
			if (html.indexOf("<!-- FOOTER -->") > 0 ) {
				footer = html.substring( html.indexOf("<!-- FOOTER -->"), html.length());
				footer = footer.replaceAll("&nbsp;", " ");
				footer = footer.replaceAll("\\<.*?\\>", "");
				footer = footer.replaceAll("\t", "");
				footer = th(footer);
				footer = footer.trim();
				footer = "\r\n\r\n" + footer;
				bodyEnd = html.indexOf("<!-- FOOTER -->");
			}
			String body = html.substring( bodyStart, bodyEnd);

			body = body.replaceAll("&nbsp;", " ");
			body = body.replaceAll("\\<.*?\\>", "");
			body = body.replaceAll("\t", "");
			body = body.replaceAll("\r\n\r\n\r\n", "");
			body = th(body);
			body = body.trim();

			html = body  + footer;
//			html = html.replaceAll("&nbsp;", " ");
//			html = html.replaceAll("\\<.*?\\>", "");

			return html;
		} catch (Exception e) {
			return html;
		}
	}

	public final static String readAll(String path) {

		try {

		    BufferedReader br = new BufferedReader( new InputStreamReader(new FileInputStream(path), Charset.forName("UTF-8")));
		    String str;
		    StringBuffer sb = new StringBuffer();

	        while (( str = br.readLine()) != null ){
	        	sb.append(str + System.getProperty("line.separator"));
	        }

	        br.close();

		    return sb.toString();
		} catch( Exception e) {
			return e.toString();
		}
	}

	// 先頭文字削除
	public static String trimStart(String target, String prefix) {
           if (target.startsWith(prefix)) {
                 return (target.substring(prefix.length()));
           }
           return target;
    }

	// 最後の文字を削除
	public static String removeLast(String target){
        if (target == null || target.length() == 0) {
            return target;
        }
        return target.substring(0, target.length() - 1);
	}

	// 最後のコンマ(,)を削除
	public static String removeLastConma(String target){

		target = target.replaceAll(",$", "");
		return target;
	}

	/**
	 * 改行コードを<br />タグに変換した情報を返却する。<br>
	 * @param s 入力文字列
	 * @return 変換後の文字列を返却します。
	 */
	public static String nl2br(String s) {
	    return nl2br(s, true);
	}


	/**
	 * 改行コードを<br />、または、<br>タグに変換した情報を返却する。<br>
	 * @param s 入力文字列
	 * @param is_xhtml XHTML準拠の改行タグの使用する場合はtrueを指定します。
	 * @return 変換後の文字列を返却します。
	 */
	public static String nl2br(String s, boolean is_xhtml) {
	    if (s == null || "".equals(s)) {
	        return "";
	    }
	    String tag = is_xhtml ? "<br />" : "<br>";
	    return s.replaceAll("\\r\\n|\\n\\r|\\n|\r", tag);
	}

	// 数値と桁数のチェック
	public static boolean validNum(String numberStr, int digit  ){

		String regex_num = "^[0-9]{"+ digit + "}$";

		 Pattern p = Pattern.compile(regex_num);
		 Matcher m = p.matcher(numberStr);

		 boolean result = m.matches();

		 return result;

	}



}