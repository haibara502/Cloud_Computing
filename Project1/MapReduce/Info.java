import java.io.*;
import org.apache.hadoop.io.*;
import java.util.ArrayList;

public class Info implements WritableComparable<Info> {
	private LongWritable id;
	private DoubleWritable rank;
	private ArrayList<IntWritable> posBegin;
	private ArrayList<IntWritable> posEnd;
	
	private static int num1 = 13777;
	private static int num2 = 957777;
	
	public Info() {
		set(new LongWritable(), new DoubleWritable(), new ArrayList<IntWritable>(), new ArrayList<IntWritable>());
	}
	
	public Info(long _id, double _rank, int _posBegin, int _posEnd) {
		ArrayList<IntWritable> tempBegin = new ArrayList<IntWritable>();
		ArrayList<IntWritable> tempEnd = new ArrayList<IntWritable>();
		tempBegin.add(new IntWritable(_posBegin));
		tempEnd.add(new IntWritable(_posEnd));
		set(new LongWritable(_id), new DoubleWritable(_rank), tempBegin, tempEnd);
	}
	
	public void set(LongWritable _id, DoubleWritable _rank, ArrayList<IntWritable> _posBegin, ArrayList<IntWritable> _posEnd) {
		this.id = _id;
		this.rank = _rank;
		this.posBegin = _posBegin;
		this.posEnd = _posEnd;
	}
	
	public double getRank() {
		return rank.get();
	}
	
	public int getPosBegin(int pos) {
		return posBegin.get(pos).get();
	}
	
	public int getPosEnd(int pos) {
		return posEnd.get(pos).get();
	}
	
	public int getTotalPos() {
		return posBegin.size();
	}
	
	public void reRank(int num) {
		this.rank = new DoubleWritable(this.rank.get() / num);
	}
	
	public void addPos(int value, int _begin, int _end) {
		this.rank = new DoubleWritable(this.rank.get() + value);
		this.posBegin.add(new IntWritable(_begin));
		this.posEnd.add(new IntWritable(_end));
	}
	
	@Override
	public void write(DataOutput out) throws IOException {
		id.write(out);
		rank.write(out);
		
		IntArrayWritable _begin = new IntArrayWritable(posBegin);
		_begin.write(out);
		
		IntArrayWritable _end = new IntArrayWritable(posEnd);
		_end.write(out);
	}
	
	@Override
	public void readFields(DataInput in) throws IOException {
		id.readFields(in);
		rank.readFields(in);
		
		IntArrayWritable _begin = new IntArrayWritable(posBegin);
		_begin.readFields(in);
		
		IntArrayWritable _end = new IntArrayWritable(posEnd);
		_end.readFields(in);
	}
	
	@Override
	public int hashCode() {
		String thisTemp = this.toString();
		int hash = 0;
		for (int i = 0; i < thisTemp.length(); ++i)
			hash = (hash * num1 + thisTemp.charAt(i)) % num2;
		return hash;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Info) {
			Info info = (Info) o;
			IntArrayWritable _begin = new IntArrayWritable(posBegin);
			IntArrayWritable _end = new IntArrayWritable(posEnd);
			IntArrayWritable infoBegin = new IntArrayWritable(info.posBegin);
			IntArrayWritable infoEnd = new IntArrayWritable(info.posEnd);
			return (id.equals(info.id) && (rank.equals(info.rank)) && (_begin.equals(infoBegin)) && (_end.equals(infoEnd)));
		}
	}
	
	@Override
	public String toString() {
		String position = "";
		int total = this.getTotalPos();
		for (int i = 0; i < total; ++i) {
			if (i > 0)
				position += "%";
			position += posBegin.get(i).toString() + '|' + posEnd.get(i).toString();
		}
		return id.toString() + ':' + rank.toString() + ':' + position;
	}
	
	@Override
	public int compareTo(Info info) {
		int cmp = id.compareTo(info.id);
		if (cmp != 0)
			return cmp;
		cmp = rank.compareTo(info.rank);
		if (cmp != 0)
			return cmp;
		IntArrayWritable _begin = new IntArrayWritable(posBegin);
		IntArrayWritable infoBegin = new IntArrayWritable(info.posBegin);
		cmp = _begin.compareTo(infoBegin);
		if (cmp != 0)
			return cmp;
		IntArrayWritable _end = new IntArrayWritable(posEnd);
		IntArrayWritable infoEnd = new IntArrayWritable(info.posEnd);
		return _end.compareTo(infoEnd);
	}
}
