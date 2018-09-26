import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.net.*;
import java.util.regex.*;

import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.util.*;

public class UniWifiDataAnalysisMapper extends MapReduceBase 
implements Mapper<LongWritable,Text,Text,Text>{
	
	/*private static String N;
	public void configure(JobConf job){
		N=job.get("studentid", "notSet");
	}*/
	
	private Text studentID=new Text();
	// private LongWritable totalHours=new LongWritable();
	private Text  ConDisconTime=new Text();

	@Override
	public void map(LongWritable key, Text inputs,
			OutputCollector<Text, Text> output, Reporter reporter)
			throws IOException {
		// TODO Auto-generated method stub
		
		String inputString=inputs.toString();
		String[] parts=inputString.split(",");
		
		
		 /*if(parts[33].equals(""))
		 {*/
//		if(!parts[11].replace("\"", "").equals("Not Authenticated"))  // && parts[34].matches("iOS|Android")||(parts[34].matches("Windows|Windows Mobile 8") && parts[19].equals("Nokia Corporation"))
//		 {
//			DateFormat sdf1 = new SimpleDateFormat("MM/dd/yy HH:mm");
//			DateFormat sdf2 = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
//			DateFormat sdf3 = new SimpleDateFormat("MM/dd/yy");
//			
//			String endDate=parts[9].replace("\"", "");
//			String startDate=parts[8].replace("\"", "");
//			long diff = 0;
//			
//			Date enddate =null;
//			Date stdate =null;
//			
//			if(endDate.contains("AM") || endDate.contains("PM")){
//				try {
//					enddate = (Date)sdf2.parse(endDate);					
//					String stenddate=sdf1.format(enddate);
//					enddate = sdf1.parse(stenddate);
//					
//				} catch (ParseException e1) {
//					
//					e1.printStackTrace();
//				}
//			}else{
//				try 
//				{
//					enddate = (Date)sdf1.parse(endDate);				
//				} catch (ParseException e2) {
//					
//					e2.printStackTrace();
//				}
//			}
//			
//			if(startDate.contains("AM") || startDate.contains("PM")){
//				try {
//					stdate = (Date)sdf2.parse(startDate);
//					String ststdate=sdf1.format(stdate);
//					stdate = sdf1.parse(ststdate);
//				} catch (ParseException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//			}else{
//				try 
//				{			
//					stdate = (Date)sdf1.parse(startDate);
//				} catch (ParseException e2) {
//					
//					e2.printStackTrace();
//				}
//			}
//			
//			try{
//				diff= (enddate.getTime()-stdate.getTime())/60000;
//			}catch(Exception e){
//				diff= 1440;
//			}
//			
//			// diff= enddate.getTime();
//			totalHours.set(diff);
//			String ststdate;
//			try{
//				ststdate=sdf3.format(stdate);
//			}catch(Exception e){
//				ststdate="";
//			}
//			
//			studentID.set(parts[1]+":"+parts[6].replace("\"", "")+":"+parts[0]+":"+ststdate);			
//			output.collect(studentID, totalHours);
//		 }
		
		if(!parts[8].equalsIgnoreCase("Connect Time")){
			studentID.set(parts[1]+":"+parts[6].replace("\"", ""));	
			ConDisconTime.set(parts[8]+","+parts[9]);
			output.collect(studentID, ConDisconTime);
		}
		
	}

}
