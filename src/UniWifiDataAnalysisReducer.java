import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;

public class UniWifiDataAnalysisReducer 
extends MapReduceBase implements Reducer<Text,Text,Text,Text>{

	@Override
	public void reduce(Text key, Iterator<Text> values,
			OutputCollector<Text, Text> output, Reporter reporter)
			throws IOException {
		
		long sum=0;
		
		ArrayList<Interval> Intervals=new ArrayList<Interval>();
		
		while(values.hasNext())
		{
			try{
				String value=values.next().toString();
				String[] dates=value.split(",");
				String endDate=dates[1].replace("\"", "");
				String startDate=dates[0].replace("\"", "");
				
				DateFormat sdf1 = new SimpleDateFormat("MM/dd/yy HH:mm");
				DateFormat sdf2 = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
				
				Date enddate =null;
				Date stdate =null;
				
				if(endDate.contains("AM") || endDate.contains("PM")){
					try {
						enddate = (Date)sdf2.parse(endDate);					
						String stenddate=sdf1.format(enddate);
						enddate = sdf1.parse(stenddate);
						
					} catch (ParseException e1) {
						
						e1.printStackTrace();
					}
				}else{
					try 
					{
						enddate = (Date)sdf1.parse(endDate);				
					} catch (ParseException e2) {
						
						e2.printStackTrace();
					}
				}
				
				if(startDate.contains("AM") || startDate.contains("PM")){
					try {
						stdate = (Date)sdf2.parse(startDate);
						String ststdate=sdf1.format(stdate);
						stdate = sdf1.parse(ststdate);
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}else{
					try 
					{			
						stdate = (Date)sdf1.parse(startDate);
					} catch (ParseException e2) {
						
						e2.printStackTrace();
					}
				}
				
				Interval inval=new Interval(stdate,enddate);
				Intervals.add(inval);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		// System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
		Collections.sort(Intervals, new IntervalComparator());
		ArrayList<Interval> Result=new ArrayList<Interval>();
		try{
			
			
			Interval prev = Intervals.get(0);
			for (int i = 1; i < Intervals.size(); i++) {
				Interval curr = Intervals.get(i);
	 
				if (prev.end.after(curr.start) ) {
					
					Interval merged = new Interval(prev.start, Maxdate.max(prev.end, curr.end));
					prev = merged;
				} else {
					Result.add(prev);
					prev = curr;
				}
			}
	 
			Result.add(prev);
		}catch(Exception e){
			System.out.println(e.getMessage());
		}

		
		for(int	i=0;i<Result.size();i++){
			try{
				sum=sum + (Result.get(i).end.getTime()-Result.get(i).start.getTime())/60000;
				//sum=Result.get(i).start.getTime();
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
			//System.out.println(Result.get(i).start+" "+Intervals.get(i).end);
		}
		
		String stdId=key.toString();
		String[] parts=stdId.split(":");
		parts[0]=String.format("%s",parts[0]);
		parts[1]=String.format("%s",parts[1].replace("Top > ","")); // Top > 
		stdId=parts[0]+","+parts[1]+",";
		String totalMin=String.format("%d", sum); // in minutes
		output.collect(new Text(stdId), new Text(totalMin));
		
	}

}

//class Maxdate{
//	public static Date max(Date c1,Date c2){
//		if(c1.compareTo(c2) >0)
//			return c1;
//		else
//			return c2;
//	}
//}
//
//class IntervalComparator implements Comparator<Interval> {
//	public int compare(Interval i1, Interval i2) {
//		return (int) (i1.start.getTime() - i2.start.getTime());
//	}
//}
//
//class Interval {
//	Date start;
//	Date end;
// 
//	Interval() {
//		start = null;
//		end = null;
//	}
// 
//	Interval(Date s, Date e) {
//		start = s;
//		end = e;
//	}
//}
