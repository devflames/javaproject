package common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalendarEx {

	private Calendar cal;

	public CalendarEx() {
		this.cal = Calendar.getInstance();
	}

	public CalendarEx(String _dt) throws ParseException {
		this();
		this.setDateTime(_dt);
	}

	public CalendarEx(Date _dt) throws ParseException {
		this();
		Calendar cal = Calendar.getInstance();
		cal.setTime(_dt);
		this.setDateTime(cal);
	}

	public CalendarEx(long _dt) {
		this();
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(_dt);
		this.setDateTime(cal);
	}

	public void setDateTime(String _dt) throws ParseException {
		this.setDateTime(_dt, "yyyy-MM-dd HH:mm");
	}

	public void setDateTime(String _dt, String _format) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(_format);
		sdf.setLenient(false);
		this.cal.setTime( sdf.parse(_dt.replaceAll("\\/", "-")) );
	}

	public void setDateTime(Calendar instance) {
		this.cal = instance;
	}

	public void add(int field, int amount) {
		this.cal.add(field, amount);
	}

	public String getDateTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return sdf.format(this.cal.getTime());
	}
	public String getDateTime(String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(this.cal.getTime());
	}

	public Date getDate() {
		return this.cal.getTime();
	}

	public String getDateStr() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(this.cal.getTime());
	}

	public String getTimeStr() {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		return sdf.format(this.cal.getTime());
	}

	public long getMilliSecond() {
		return this.cal.getTimeInMillis();
	}

	public static String formatDateTime(Date dt, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(dt);
	}

	public static boolean isDatetime(String _dt) {
		return CalendarEx.isDatetime(_dt, "yyyy-MM-dd HH:mm");
	}

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

	public void reset() {
		this.cal.setTime(new Date());
	}
}
