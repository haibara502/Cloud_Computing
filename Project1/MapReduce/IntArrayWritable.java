import java.io.IOException;
import java.util.*;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.ArrayWritable;
import org.apache.hadoop.io.IntWritable;

public class IntArrayWritable extends ArrayWritable {
	public IntArrayWritable() {
		super(IntWritable.class);	
	}
	
	public IntArrayWritable(String[] ints) {
		super(IntWritable.class);
		IntWritable[] values = new IntWritable[ints.length];
		for (int i = 0; i < ints.length; ++i) {
			try {
				values[i] = new IntWritable(Integer.parseInt(ints[i].trim()));
			} catch (NumberFormatException e) {
				System.out.println("NumberFormatException");
			}
		}
		set(values);
	}
	
	public IntArrayWritable(ArrayList<IntWritable> ints) {
		super(IntWritable.class);
		IntWritable[] values = new IntWritable[ints.size()];
		for (int i = 0; i < ints.size(); ++i) {
			values[i] = new IntWritable(ints.get(i).get());
		}
		set(values);
	}
	
	public int compareTo(IntArrayWritable aw) {
		return toString().compareTo(aw.toString());
	}
}
