package project1.nhom2.de14.util;

/**
 *
 * @author WILL
 */
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class CalendarAdapter {
	public static String formatCalendar(GregorianCalendar c){
		if(c==null) return "";
		return String.format("%02d-%02d-%02d",c.get(GregorianCalendar.DATE),c.get(GregorianCalendar.MONTH),c.get(GregorianCalendar.YEAR));
	}
	
	public static String getCurrentDate(){
		Date d = new Date();
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		return dateFormat.format(d);
	}
	
	public static String SQLDateToString(java.sql.Date d){
		if(d==null) return "";
		String r = new String();
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		r = dateFormat.format(d);
		return r;
	}
	
	public static String DateToString(Date d){
		if(d==null) return "";
		String r = new String();
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		r = dateFormat.format(d);
		return r;
	}
	
	public static String DateToStringB(Date d){
		if(d==null) return "";
		String r = new String();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		r = dateFormat.format(d);
		return r;
	}
	
	public static Date StringToDate(String s){
		if(s==null) return new Date();
		String [] spl = new String[3];
		spl = s.split("-",3);
		int date = Integer.parseInt(spl[0]), month = Integer.parseInt(spl[1]), year = Integer.parseInt(spl[2]);
		
		@SuppressWarnings("deprecation")
		Date d = new Date(year-1900,month,date);
		return d;
	}
	
	public static Date StringToDateB(String s){
		if(s==null) return new Date();
		String [] spl = new String[3];
		spl = s.split("-",3);
		int date = Integer.parseInt(spl[0]), month = Integer.parseInt(spl[1]), year = Integer.parseInt(spl[2]);
		
		@SuppressWarnings("deprecation")
		Date d = new Date(year-1900,month-1,date);
		return d;
	}
        
        public static GregorianCalendar SQLDateToCal(java.sql.Date d){
            if(d==null) return null;
            String s = SQLDateToString(d);
            String [] spl = new String[3];
            spl = s.split("-",3);
            return new GregorianCalendar(Integer.parseInt(spl[2]),Integer.parseInt(spl[1]),Integer.parseInt(spl[0]));
        }
}