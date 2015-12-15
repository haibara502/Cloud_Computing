import java.io.*;
import java.util.*;
import java.lang.*;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.util.*;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;

public class firstMapper extends Mapper<LongWritable, Text, Text, IntWritable> {	
	private static Gson gson = new Gson();
	private HashMap<String, Info> token = new HashMap<String, Info>();
	
	private void dealToken(long id, String sentence, int value) {
		String now = "";
		for (int i = 0; i <= sentence.length(); ++i) {
			if ((i == sentence.length()) || ((i != sentence.length()) && (sentence[i] == ' ')))
				if (now != "") {
					Info preValue = token.get(now);
					if (preValue == null)
						token.put(now, new Info(id, value, i - now.length(), i));
					else
						preValue.addPos(value, i - now.length(), i);
					now = "";
				}
		}
	}
	
	private void emitToken(Context context) {
		for (Map.Entry<String, Info> iterator : token.entrySet()) {
			context.write(new Text(iterator.getKey()), iterator.getValue());
		}
	}
	
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		token.clear();
		bbsSJTU sjtu = gson.fromJson(value.toString(), bbsSJTU.class);
		long id = key;
		String title = sjtu.getTitle();
		String context = sjtu.getContext();
		
		dealToken(id, title, 5);
		dealToken(id, context, 1);
		
		emitToken(context);
	}
	
	public void run(Context context) throws IOException, InterruptedException{
		setup(context);
		while (context.nextKeyValue()) {
			map(context.getCurrentKey(), context.getCurrentValue(), context);
		}
		cleanup(context);
	}
}
