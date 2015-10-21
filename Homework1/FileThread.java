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

public class FileThread extends Thread {
	private Path filePath, desirePath, desireFile;
	
	public FileThread(Path currentFile, Path aimPath) {
		filePath = currentFile;
		desirePath = aimPath;
		String fileP = filePath.toString();
		String desireP = desirePath.toString();
		for (int i = fileP.length() - 1; i >= -1; --i) { //Get the absolute path of the destination of the file
			if (i == -1) {
				desireFile = new Path(desireP + "/" + fileP);
				break;
			}
			if (fileP.charAt(i) == '/') {
				String desireF = desireP; 
				while (i < (fileP.length()))
					desireF += fileP.charAt(i++);
				desireFile = new Path(desireF);
				break;
			}
		}
	}
	
	public void run() {
		try{
			Configuration conf = new Configuration();
			FileSystem fsLocal = FileSystem.getLocal(conf);
			FSDataInputStream input = fsLocal.open(filePath); //Local file to be submitted
			FileSystem fsGlobal = FileSystem.get(desirePath.toUri(), conf);
			FSDataOutputStream output = fsGlobal.create(desireFile); //Desination of the file
			IOUtils.copyBytes(input, output, 4096, true); //Copying
			fsLocal.delete(filePath, false); //Delete the local file
		} catch (IOException exception) {
			System.out.println(exception);
		}
	}
}
