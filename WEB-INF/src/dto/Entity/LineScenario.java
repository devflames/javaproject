package dto.Entity;

import java.sql.Timestamp;

public class LineScenario {

	private int line_sid;
	private int line_account_id;
	private String scenario_name;
	private Timestamp create_date;
	private Timestamp update_date;
	private int del_flg;

	// シナリオID
	public int getLine_sid() { return line_sid; }
	public void setLine_sid(int line_sid) { this.line_sid = line_sid; }

	// LINEアカウントID
	public int getLine_account_id() { return line_account_id; }
	public void setLine_account_id(int line_account_id) { this.line_account_id = line_account_id; }

	// シナリオ名
	public String getScenario_name() { return scenario_name; }
	public void setScenario_name(String scenario_name) { this.scenario_name = scenario_name; }

	// 作成日時
	public Timestamp getCreate_date() { return create_date; }
	public void setCreate_date(Timestamp create_date) { this.create_date = create_date; }

	// 最終更新日時
	public Timestamp getUpdate_date() { return update_date; }
	public void setUpdate_date(Timestamp update_date) { this.update_date = update_date; }

	// 削除フラグ
	public int getDel_flg() { return del_flg; }
	public void setDel_flg(int del_flg) { this.del_flg = del_flg; }



}
