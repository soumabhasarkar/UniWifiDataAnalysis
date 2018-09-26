import org.apache.hadoop.io.*;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.util.*;

public class UniWifiDataAnalysisMain extends Configured implements Tool{

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		
		int res=ToolRunner.run(new Configuration(), new UniWifiDataAnalysisMain(), args);
		System.exit(res);
	}

	@Override
	public int run(String[] args) throws Exception {
		
		JobConf conf=new JobConf(getConf(),UniWifiDataAnalysisMain.class);
		conf.setJobName("WifiDataAnalysis");
		conf.set("mapreduce.map.java.opts", "-Djava.util.Arrays.useLegacyMergeSort=true");
		conf.set("mapreduce.reduce.java.opts", "-Djava.util.Arrays.useLegacyMergeSort=true");
//		conf.set("studentid", args[2].trim().toString());
		
		//Setting configuration object with the Data Type of output Key and Value
        conf.setOutputKeyClass(Text.class);
        conf.setOutputValueClass(Text.class);

        //Providing the mapper and reducer class names
        conf.setMapperClass(UniWifiDataAnalysisMapper.class);
        conf.setReducerClass(UniWifiDataAnalysisReducer.class);
        
//      conf.setNumReduceTasks(10);
                        
        Path inp = new Path("/user/hduser/UniWifiData");
        Path out = new Path("/user/hduser/UniWifiData-out");
        
        //the hdfs input and output directory 
        FileInputFormat.addInputPath(conf, inp);
        FileOutputFormat.setOutputPath(conf, out);               
        
        
        
        JobClient.runJob(conf);
        return 0;
	}

}
