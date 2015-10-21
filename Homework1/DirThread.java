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

public class DirThread extends Thread {
	private Path dirPath, desirePath;
	
	public DirThread(Path currentPath, Path aimPath) {
		dirPath = currentPath;
		desirePath = aimPath;
	}
	
	public void run() {
		Configuration conf = new Configuration();
		try {
			FileSystem fs = FileSystem.getLocal(conf);
			FileStatus[] status = fs.listStatus(dirPath); //List all the files in this dir
			if (status.length > 0) {
				Thread[] thread = new Thread[status.length];
				for (int i = 0; i < status.length; ++i) {
					if (status[i].isDir()) { //Continue to deal with this dir
						thread[i] = new DirThread(new Path(status[i].getPath().toUri()), desirePath);
					}
					else thread[i] = new FileThread(new Path(status[i].getPath().toUri()), desirePath); //Submit this file
					thread[i].start();
				}
				for (int i = 0; i < status.length; ++i) 
					thread[i].join();
			}
		} catch (IOException | InterruptedException exception) {
			System.out.println(exception);
		}
	}
}
