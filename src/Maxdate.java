import java.util.Date;


public class Maxdate{
	public static Date max(Date c1,Date c2){
		if(c1.compareTo(c2) >0)
			return c1;
		else
			return c2;
	}
}
