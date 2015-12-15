import java.io.*;
import java.util.*;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.util.*;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;

public class aJob extends Configured implements Tool {
	public int run(String[] args) throws Exception {
		String[] firstArgs = null;
		firstArgs.add(args[0]);
		firstArgs.add(args[1]);
		Job jobFirst = JobBuilder.firstIndex(this, getConf(), firstArgs);
		if (job == null) {
			return -1;
		}
		
		String[] secondArgs = null;
		secondArgs.add(args[2]);
		secondArgs.add(args[3]);
		Job jobSecond = JobBuilder.secondIndex(this, getConf(), secondArgs);
		if (jobSecond == null) {
			return -1;
		}
		
		JobClient.runJob(jobFirst);
		if (jobFirst.waitForCompletion(true) == false) {
			return 1;
		}
		JobClient.runJob(jobSecond);
		return jobSecond.waitForCompletion(true)? 0 : 1;
		
		
		return job.waitForCompletion(true)? 0 : 1;
	}
	
	public static void main(String[] args) throws Exception {
		int exitCode = ToolRunner.run(new Job(), args);
		System.exit(exitCode);
	}
}
