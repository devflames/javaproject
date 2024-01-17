package dto.Entity.StepMail;

public class Scenario {

	// メンバー宣言
	private int sno;
	private int pid;
	private String scenario;
	private String fname;

	// sno
	public int getSno() { return sno; }
	public void setSno(int sno) { this.sno = sno; }

	// pid
	public int getPid() { return pid; }
	public void setPid(int pid) { this.pid = pid; }

	// scenario
	public String getScenario() { return scenario;}
	public void setScenario(String scenario) { this.scenario = scenario; }

	// fnameを取得します。
	public String getFname() { return fname;}
	public void setFname(String fname) { this.fname = fname; }

}
