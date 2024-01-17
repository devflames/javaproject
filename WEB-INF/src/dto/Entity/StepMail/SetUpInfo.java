package dto.Entity.StepMail;

public class SetUpInfo {

	// メンバー宣言
	private String account_name;
	private String db_name;
	private int service_plan_id;
	private String cron_group;
	private int status;
	private int demo;

	// account_name
	public String getAccount_name() { return account_name; }
	public void setAccount_name(String account_name) { this.account_name = account_name; }

	// db_name
	public String getDb_name() { return db_name; }
	public void setDb_name(String db_name) { this.db_name = db_name; }

	// service_plan_id
	public int getService_plan_id() { return service_plan_id; }
	public void setService_plan_id(int service_plan_id) { this.service_plan_id = service_plan_id; }

	// cron_group
	public String getCron_group() { return cron_group; }
	public void setCron_group(String cron_group) { this.cron_group = cron_group; }

	// status
	public int getStatus() { return status; }
	public void setStatus(int status) { this.status = status; }

	// demo
	public int getDemo() { return demo; }
	public void setDemo(int demo) { this.demo = demo; }
}