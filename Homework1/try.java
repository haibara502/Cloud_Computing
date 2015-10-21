import org.apache.hadoop.*

public class MainClass {
	public static void main(string[] args) {
		String uri = args[0];
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(URI.create(uri), conf);
		while (1) {
			FileStatus[] status = fs.listStatus(new Path(args[0]));
			if (status.length == 0) {
				Thread.sleep(4000);
				continue;
			}
			Thread[] thread = new Thread[status.length];
			for (int i = 0; i < status.length; ++i) {
				if (status[i].isDir()) {
					thread[i] = new DirThread(status[i].getpath().Uri().getpath());
				else thread[i] = new FileThread(status[i].getPath().toUri().getPath());
				thread[i].start();
			}
			for (int i = 0; i < status.length; ++i) 
				thread[i].join();
		}
	}
}
