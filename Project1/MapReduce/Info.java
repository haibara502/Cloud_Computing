import java.io.*
import org.apache.hadoop.io.*;

public class Info implements WritableComparable<Info> {
	private long id;
	private double rank;
	private ArrayList<int> posBegin;
	private ArrayList<int> posEnd;
	
	private static int num1 = 13777;
	private static int num2 = 957777;
	
	public Info() {
		set(new long(), new double(), new ArrayList<int>, new ArrayList<int>);
	}
	
	public Info(long _id, double _rank, int _posBegin, int _posEnd) {
		ArrayList<int> tempBegin = new ArrayList<int>;
		ArrayList<int> tempEnd = new ArrayList<int>;
		tempBegin.add(_posBegin);
		tempEnd.add(_posEnd);
		set(_id, _rank, tempBegin, tempEnd);
	}
	
	public void set(long _id, double _rank, ArrayList<int> _posBegin, ArrayList<int> _posEnd) {
		this.id = _id;
		this.rank = _rank;
		this.posBegin = _posBegin;
		this.posEnd = _posEnd;
	}
	
	public double getRank() {
		return rank;
	}
	
	public int getPosBegin(int pos) {
		return posBegin.get(pos);
	}
	
	public int getPosEnd(int pos) {
		return posEnd.get(pos);
	}
	
	public int getTotalPos() {
		return posBegin.size();
	}
	
	@Override
	public void write(DataOutput out) throws IOException {
		id.write(out);
		rank.write(out);
		posBegin.write(out);
		posEnd.write(out);
	}
	
	@Override
	public void readFields(DataInput in) throws IOException {
		id.readFields(in);
		rank.readFields(in);
		posBegin.readFields(in);
		posEnd.readFields(in);
	}
	
	@Override
	public int hashCode() {
		String thisTemp = this.toString();
		int hash = 0;
		for (int i = 0; i < thisTemp.length(); ++i)
			hash = (hash * num1 + thisTemp[i]) % num2;
		return hash;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Info) {
			Info info = (Info) o;
			return (id.equals(info.id) && (rank.equals(info.rank)) && (posBegin.equals(info.posBegin)) && (posEnd.equals(info.posEnd)));
	}
	
	@Override
	public String toString() {
		String position = "";
		int total = this.getTotalPos();
		for (int i = 0; i < total; ++i) {
			if (i)
				position += "%";
			position += posBegin.get(i).toString() + '|' + posEnd.get(i).toString();
		}
		return id + ':' + rank.toString() + ':' + position;
	}
	
	@Override
	public int compareTo(Info info) {
		int cmp = id.compareTo(info.id);
		if (cmp != 0)
			return cmp;
		cmp = rank.compareTo(info.rank);
		if (cmp != 0)
			return cmp;
		cmp = posBegin.compareTo(info.posBegin);
		if (cmp != 0)
			return cmp;
		return posEnd.compareTo(info.posEnd);
	}
}
