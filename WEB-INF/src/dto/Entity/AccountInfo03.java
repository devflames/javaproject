package dto.Entity;

public class AccountInfo03 {

	// メンバー宣言
	private String acount;
	private String schema;
	private String name;
	private int asp_type;
	private int server_id;
	private String server_ip;
	private String server_domain;
	private int send_limit;
	private int send_limit_month;
	private int mail_max_count;
	private int using_service;
	private int cutoff_date;
	private String google_user;
	private String google_pass;

	// アカウント
	public String getAcount() {return acount;}
	public void setAcount(String acount) {this.acount = acount;}

	// DB名
	public String getSchema() {return schema;}
	public void setSchema(String schema) {this.schema = schema;}

	public String getName() {return name;}
	public void setName(String name) {this.name = name;}

	// ASP種別（1:AutoBiz/2:PowerStepMail/3:PowerResponder）
	public int getAsp_type() {return asp_type;}
	public void setAsp_type(int asp_type) {this.asp_type = asp_type;}

	// サーバーID
	public int getServer_id() {return server_id;}
	public void setServer_id(int server_id) {this.server_id = server_id;}

	// サーバーIPアドレス　※独自ドメインプランのみ
	public String getServer_ip() {return server_ip;}
	public void setServer_ip(String server_ip) {this.server_ip = server_ip;}

	// サーバードメイン名　※独自ドメインプランのみ
	public String getServer_domain() {return server_domain;}
	public void setServer_domain(String server_domain) {this.server_domain = server_domain;}

	// 送信リミット（送信件数/回）
	public int getSend_limit() {return send_limit;}
	public void setSend_limit(int send_limit) {this.send_limit = send_limit;}

	// 送信リミット（送信件数/月）
	public int getSend_limit_month() {return send_limit_month;}
	public void setSend_limit_month(int send_limit_month) {this.send_limit_month = send_limit_month;}

	// 保管メール件数
	public int getMail_max_count() {return mail_max_count;}
	public void setMail_max_count(int mail_max_count) {this.mail_max_count = mail_max_count;}

	// サービス状態(0:利用中/1:停止中)
	public int getUsing_service() {return using_service;}
	public void setUsing_service(int using_service) {this.using_service = using_service;}

	// 課金締日(15:15日締/99:末日締)
	public int getCutoff_date() { return cutoff_date; }
	public void setCutoff_date(int cutoff_date) { this.cutoff_date = cutoff_date; }

	// GoogleDrive ユーザ
	public String getGoogle_user() {return google_user;}
	public void setGoogle_user(String google_user) {this.google_user = google_user;}

	// GoogleDrive パスワード
	public String getGoogle_pass() {return google_pass;}
	public void setGoogle_pass(String google_pass) {this.google_pass = google_pass;}
}