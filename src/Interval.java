import java.util.Calendar;
import java.util.Date;


public class Interval {
	Date start;
	Date end;
 
	Interval() {
		start = null;
		end = null;
	}
 
	Interval(Date s, Date e) {
		start = s;
		if(e!=null){
			end = e;			
		}else{
			Calendar c = Calendar.getInstance();
			c.setTime(s);
			// c.add(Calendar.DATE, 1);
			c.set(Calendar.HOUR_OF_DAY, 23);
			c.set(Calendar.MINUTE,59);
			c.set(Calendar.SECOND,59);
			end=c.getTime();
		}	
	}
}
