package dto.Entity.StepMail;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;

public class Address {

	// メンバー宣言
	private int sno;
	private String scenario;
	private int uid;
	private String email;
	private String name1;
	private String name2;
	private String fld1;
	private String fld2;
	private String fld3;
	private String fld4;
	private String fld5;
	private String fld6;
	private String fld7;
	private String fld8;
	private String fld9;
	private String fld10;
	private String fld11;
	private String fld12;
	private String fld13;
	private String fld14;
	private String fld15;
	private String fld16;
	private String fld17;
	private String fld18;
	private String fld19;
	private String fld20;
	private String fld21;
	private String fld22;
	private String fld23;
	private String fld24;
	private String fld25;
	private String fld26;
	private String fld27;
	private String fld28;
	private String fld29;
	private String fld30;
	private String fld31;
	private String fld32;
	private String fld33;
	private String fld34;
	private String fld35;
	private String fld36;
	private String fld37;
	private String fld38;
	private String fld39;
	private String fld40;
	private String fld41;
	private String fld42;
	private String fld43;
	private String fld44;
	private String fld45;
	private String fld46;
	private String fld47;
	private String fld48;
	private String fld49;
	private String fld50;
	private String long1;
	private String long2;
	private String long3;
	private String long4;
	private String stopflg;
	private int cstep;
	private int or_open;
	private int or_ts;
	private String candate;
	private String regdate;

	public Address() {
		this.email = "";
		this.name1 = "";
		this.name2 = "";
		this.long1 = "";
		this.long2 = "";
		this.long3 = "";
		this.long4 = "";
		this.fld1 = "";
		this.fld2 = "";
		this.fld3 = "";
		this.fld4 = "";
		this.fld5 = "";
		this.fld6 = "";
		this.fld7 = "";
		this.fld8 = "";
		this.fld9 = "";
		this.fld10 = "";
		this.fld11 = "";
		this.fld12 = "";
		this.fld13 = "";
		this.fld14 = "";
		this.fld15 = "";
		this.fld16 = "";
		this.fld17 = "";
		this.fld18 = "";
		this.fld19 = "";
		this.fld20 = "";
		this.fld21 = "";
		this.fld22 = "";
		this.fld23 = "";
		this.fld24 = "";
		this.fld25 = "";
		this.fld26 = "";
		this.fld27 = "";
		this.fld28 = "";
		this.fld29 = "";
		this.fld30 = "";
		this.fld31 = "";
		this.fld32 = "";
		this.fld33 = "";
		this.fld34 = "";
		this.fld35 = "";
		this.fld36 = "";
		this.fld37 = "";
		this.fld38 = "";
		this.fld39 = "";
		this.fld40 = "";
		this.fld41 = "";
		this.fld42 = "";
		this.fld43 = "";
		this.fld44 = "";
		this.fld45 = "";
		this.fld46 = "";
		this.fld47 = "";
		this.fld48 = "";
		this.fld49 = "";
		this.fld50 = "";
		this.stopflg = "";
		this.cstep = 0;
		this.or_open = 0;
		this.or_ts = 0;
		this.regdate = "";
		this.candate = "";
	}

	// メールアドレス(ドメイン)
	public String getDomain() {
		String retValue = email;
		try {
			String[] buf = email.split("@");
			retValue = buf[buf.length -1];
		} catch (Exception ex) {
		}
		return retValue;
	}

	// フィールド値取得
	public String getFiealdValue(String fldname) {
		String retValue;
		try {
			if ( "email".equals(fldname) ) { retValue = this.email;
			} else if ( "name1".equals(fldname) ) { retValue = this.name1;
			} else if ( "name2".equals(fldname) ) { retValue = this.name2;
			} else if ( "long1".equals(fldname) ) { retValue = this.long1;
			} else if ( "long2".equals(fldname) ) { retValue = this.long2;
			} else if ( "long3".equals(fldname) ) { retValue = this.long3;
			} else if ( "long4".equals(fldname) ) { retValue = this.long4;
			} else if ( "fld1".equals(fldname) ) { retValue = this.fld1;
			} else if ( "fld2".equals(fldname) ) { retValue = this.fld2;
			} else if ( "fld3".equals(fldname) ) { retValue = this.fld3;
			} else if ( "fld4".equals(fldname) ) { retValue = this.fld4;
			} else if ( "fld5".equals(fldname) ) { retValue = this.fld5;
			} else if ( "fld6".equals(fldname) ) { retValue = this.fld6;
			} else if ( "fld7".equals(fldname) ) { retValue = this.fld7;
			} else if ( "fld8".equals(fldname) ) { retValue = this.fld8;
			} else if ( "fld9".equals(fldname) ) { retValue = this.fld9;
			} else if ( "fld10".equals(fldname) ) { retValue = this.fld10;
			} else if ( "fld11".equals(fldname) ) { retValue = this.fld11;
			} else if ( "fld12".equals(fldname) ) { retValue = this.fld12;
			} else if ( "fld13".equals(fldname) ) { retValue = this.fld13;
			} else if ( "fld14".equals(fldname) ) { retValue = this.fld14;
			} else if ( "fld15".equals(fldname) ) { retValue = this.fld15;
			} else if ( "fld16".equals(fldname) ) { retValue = this.fld16;
			} else if ( "fld17".equals(fldname) ) { retValue = this.fld17;
			} else if ( "fld18".equals(fldname) ) { retValue = this.fld18;
			} else if ( "fld19".equals(fldname) ) { retValue = this.fld19;
			} else if ( "fld20".equals(fldname) ) { retValue = this.fld20;
			} else if ( "fld21".equals(fldname) ) { retValue = this.fld21;
			} else if ( "fld22".equals(fldname) ) { retValue = this.fld22;
			} else if ( "fld23".equals(fldname) ) { retValue = this.fld23;
			} else if ( "fld24".equals(fldname) ) { retValue = this.fld24;
			} else if ( "fld25".equals(fldname) ) { retValue = this.fld25;
			} else if ( "fld26".equals(fldname) ) { retValue = this.fld26;
			} else if ( "fld27".equals(fldname) ) { retValue = this.fld27;
			} else if ( "fld28".equals(fldname) ) { retValue = this.fld28;
			} else if ( "fld29".equals(fldname) ) { retValue = this.fld29;
			} else if ( "fld30".equals(fldname) ) { retValue = this.fld30;
			} else if ( "fld31".equals(fldname) ) { retValue = this.fld31;
			} else if ( "fld32".equals(fldname) ) { retValue = this.fld32;
			} else if ( "fld33".equals(fldname) ) { retValue = this.fld33;
			} else if ( "fld34".equals(fldname) ) { retValue = this.fld34;
			} else if ( "fld35".equals(fldname) ) { retValue = this.fld35;
			} else if ( "fld36".equals(fldname) ) { retValue = this.fld36;
			} else if ( "fld37".equals(fldname) ) { retValue = this.fld37;
			} else if ( "fld38".equals(fldname) ) { retValue = this.fld38;
			} else if ( "fld39".equals(fldname) ) { retValue = this.fld39;
			} else if ( "fld40".equals(fldname) ) { retValue = this.fld40;
			} else if ( "fld41".equals(fldname) ) { retValue = this.fld41;
			} else if ( "fld42".equals(fldname) ) { retValue = this.fld42;
			} else if ( "fld43".equals(fldname) ) { retValue = this.fld43;
			} else if ( "fld44".equals(fldname) ) { retValue = this.fld44;
			} else if ( "fld45".equals(fldname) ) { retValue = this.fld45;
			} else if ( "fld46".equals(fldname) ) { retValue = this.fld46;
			} else if ( "fld47".equals(fldname) ) { retValue = this.fld47;
			} else if ( "fld48".equals(fldname) ) { retValue = this.fld48;
			} else if ( "fld49".equals(fldname) ) { retValue = this.fld49;
			} else if ( "fld50".equals(fldname) ) { retValue = this.fld50;
			} else {
				retValue = "";
			}
		} catch (Exception e) {
			retValue = "";
		}
		return retValue;
	}

	public int getSno() {return sno;}
	public void setSno(int sno) {this.sno = sno;}

	public String getScenario() {return scenario;}
	public String getShortScenario(int size) {return (this.scenario.length() > size) ? this.scenario.substring(0, size) + "..." : this.scenario;}
	public void setScenario(String scenario) {this.scenario = scenario;}

	public int getUid() {return uid;}
	public void setUid(int uid) {this.uid = uid;}

	public String getEmail() {return email;}
	public void setEmail(String email) {this.email = email;}

	public String getName1() {return name1;}
	public void setName1(String name1) {this.name1 = name1;}

	public String getName2() {return name2;}
	public void setName2(String name2) {this.name2 = name2;}

	public String getFld1() {return fld1;}
	public void setFld1(String fld1) {this.fld1 = fld1;}
	public String getFld2() {return fld2;}
	public void setFld2(String fld2) {this.fld2 = fld2;}

	public String getFld3() {return fld3;}
	public void setFld3(String fld3) {this.fld3 = fld3;}

	public String getFld4() {return fld4;}
	public void setFld4(String fld4) {this.fld4 = fld4;}

	public String getFld5() {return fld5;}
	public void setFld5(String fld5) {this.fld5 = fld5;}

	public String getFld6() {return fld6;}
	public void setFld6(String fld6) {this.fld6 = fld6;}
	public String getFld7() {return fld7;}
	public void setFld7(String fld7) {this.fld7 = fld7;}

	public String getFld8() {return fld8;}
	public void setFld8(String fld8) {this.fld8 = fld8;}

	public String getFld9() {return fld9;}
	public void setFld9(String fld9) {this.fld9 = fld9;}

	public String getFld10() {return fld10;}
	public void setFld10(String fld10) {this.fld10 = fld10;}

	public String getFld11() {return fld11;}
	public void setFld11(String fld11) {this.fld11 = fld11;}

	public String getFld12() {return fld12;}
	public void setFld12(String fld12) {this.fld12 = fld12;}

	public String getFld13() {return fld13;}
	public void setFld13(String fld13) {this.fld13 = fld13;}

	public String getFld14() {return fld14;}
	public void setFld14(String fld14) {this.fld14 = fld14;}

	public String getFld15() {return fld15;}
	public void setFld15(String fld15) {this.fld15 = fld15;}

	public String getFld16() {return fld16;}
	public void setFld16(String fld16) {this.fld16 = fld16;}

	public String getFld17() {return fld17;}
	public void setFld17(String fld17) {this.fld17 = fld17;}

	public String getFld18() {return fld18;}
	public void setFld18(String fld18) {this.fld18 = fld18;}

	public String getFld19() {return fld19;}
	public void setFld19(String fld19) {this.fld19 = fld19;}

	public String getFld20() {return fld20;}
	public void setFld20(String fld20) {this.fld20 = fld20;}

	public String getFld21() {return fld21;}
	public void setFld21(String fld21) {this.fld21 = fld21;}

	public String getFld22() {return fld22;}
	public void setFld22(String fld22) {this.fld22 = fld22;}

	public String getFld23() {return fld23;}
	public void setFld23(String fld23) {this.fld23 = fld23;}

	public String getFld24() {return fld24;}
	public void setFld24(String fld24) {this.fld24 = fld24;}

	public String getFld25() {return fld25;}
	public void setFld25(String fld25) {this.fld25 = fld25;}

	public String getFld26() {return fld26;}
	public void setFld26(String fld26) {this.fld26 = fld26;}

	public String getFld27() {return fld27;}
	public void setFld27(String fld27) {this.fld27 = fld27;}

	public String getFld28() {return fld28;}
	public void setFld28(String fld28) {this.fld28 = fld28;}

	public String getFld29() {return fld29;}
	public void setFld29(String fld29) {this.fld29 = fld29;}

	public String getFld30() {return fld30;}
	public void setFld30(String fld30) {this.fld30 = fld30;}

	public String getFld31() {return fld31;}
	public void setFld31(String fld31) {this.fld31 = fld31;}

	public String getFld32() {return fld32;}
	public void setFld32(String fld32) {this.fld32 = fld32;}

	public String getFld33() {return fld33;}
	public void setFld33(String fld33) {this.fld33 = fld33;}

	public String getFld34() {return fld34;}
	public void setFld34(String fld34) {this.fld34 = fld34;}

	public String getFld35() {return fld35;}
	public void setFld35(String fld35) {this.fld35 = fld35;}

	public String getFld36() {return fld36;}
	public void setFld36(String fld36) {this.fld36 = fld36;}

	public String getFld37() {return fld37;}
	public void setFld37(String fld37) {this.fld37 = fld37;}

	public String getFld38() {return fld38;}
	public void setFld38(String fld38) {this.fld38 = fld38;}

	public String getFld39() {return fld39;}
	public void setFld39(String fld39) {this.fld39 = fld39;}

	public String getFld40() {return fld40;}
	public void setFld40(String fld40) {this.fld40 = fld40;}

	public String getFld41() {return fld41;}
	public void setFld41(String fld41) {this.fld41 = fld41;}

	public String getFld42() {return fld42;}
	public void setFld42(String fld42) {this.fld42 = fld42;}

	public String getFld43() {return fld43;}
	public void setFld43(String fld43) {this.fld43 = fld43;}

	public String getFld44() {return fld44;}
	public void setFld44(String fld44) {this.fld44 = fld44;}

	public String getFld45() {return fld45;}
	public void setFld45(String fld45) {this.fld45 = fld45;}

	public String getFld46() {return fld46;}
	public void setFld46(String fld46) {this.fld46 = fld46;}

	public String getFld47() {return fld47;}
	public void setFld47(String fld47) {this.fld47 = fld47;}

	public String getFld48() {return fld48;}
	public void setFld48(String fld48) {this.fld48 = fld48;}

	public String getFld49() {return fld49;}
	public void setFld49(String fld49) {this.fld49 = fld49;}

	public String getFld50() {return fld50;}
	public void setFld50(String fld50) {this.fld50 = fld50;}

	public String getLong1() {return long1;}
	public void setLong1(String long1) {this.long1 = long1;}

	public String getLong2() {return long2;}
	public void setLong2(String long2) {this.long2 = long2;}

	public String getLong3() {return long3;}
	public void setLong3(String long3) {this.long3 = long3;}

	public String getLong4() {return long4;}
	public void setLong4(String long4) {this.long4 = long4;}

	public String getStopflg() {return stopflg;}
	public String getStopflgName() {
		String retValue = "";
		if ( this.stopflg == null ) {
			retValue = "";
		}
		else if ( "".equals(this.stopflg) ) {
			retValue = "稼動";
		}
		else if ( "go".equals(this.stopflg) ) {
			retValue = "稼動2";
		}
		else if ( "on".equals(this.stopflg) ) {
			retValue = "待機";
		}
		return retValue;
	}
	public void setStopflg(String stopflg) {this.stopflg = stopflg;}

	public int getCstep() {return cstep;}
	public void setCstep(int cstep) {this.cstep = cstep;}

	public int getOr_open() { return or_open; }
	public void setOr_open(int or_open) { this.or_open = or_open; }

	public int getOr_ts() { return or_ts; }
	public void setOr_ts(int or_ts) { this.or_ts = or_ts; }


	public String getDate(){
		String date = "";
		Timestamp timestamp = new Timestamp(Instant.ofEpochSecond(this.or_ts).toEpochMilli());
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		date = df.format(new Date(timestamp.getTime()));
		return date;
	}

	public String getCandate() {return candate;}
	public void setCandate(String candate) {this.candate = candate;}

	public String getRegdate() {return regdate;}
	public void setRegdate(String regdate) {this.regdate = regdate;}
}
