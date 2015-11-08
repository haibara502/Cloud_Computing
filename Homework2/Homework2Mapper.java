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

public class Homework2Mapper extends Mapper<LongWritable, Text, Text, intWritable> {
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		String line = value.toString(), now = "";
		String fileName = context.getPath().toString();
		for (int i = 0; i <= line.length; ++i) {
			if ((i == line.length) || ((i < line.length) && (!isalnum(line[i])))) {
				context.write((Text)(now + ':' + fileName), (intWritable) 1);
				now = "";
			}
		}
	}
	
	public void run(Context context) throws IOException, InterruptedException{
		setup(context);
		while (context.nextKeyValue()) {
			map(context.getCurrentKey(), context.getCurrentValue(), context);
		}
		cleanup(context);
	}
}
