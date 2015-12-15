import java.io.*
import org.apache.hadoop.io.*;

public class Info implements WritableComparable<Info> {
	private long id;
	private double rank;
	private pair<int, int> position;
	
	public Info() {
		set(new String(), new String(), new double(), new pair<new int(), new int()>);
	}
	
	public Info(String _token, String _url, double _rank, pair<int, int> _position) {
		set(_token, _url, _rank, _position);
	}
	
	public void set(String _token, String _url, double _rank, pair<int, int> _position) {
		this.token = _token;
		this.url = _url;
		this.rank = _rank;
		this.position = _position;
	}
	
	public String getToken() {
		return token;
	}
	
	public String getUrl() {
		return url;
	}
	
	public double getRank() {
		return rank;
	}
	
	public pair<int, int> getPosition() {
		return position;
	}
	
	@Override
	public void write(DataOutput out) throws IOException {
		token.write(out);
		url.write(out);
		rank.write(out);
		position.write(out);
	}
	
	@Override
	public void readFields(DataInput in) throws IOException {
		token.readFields(in);
		url.readFields(in);
		rank.readFields(in);
		position.readFields(in);
	}
	
	@Override
	public int hashCode() {
		int hash = 0;
		for (int i = 0; i < token.length(); ++i)
			hash = ((hash * num1) + token[i]) % num2;
		for (int i = 0; i < url.length(); ++i)
			hash = ((hash * num3) + url[i]) % num4;
		String rankTemp = rank.toString();
		for (int i = 0; i < rankTemp.length(); ++i)
			hash = ((hash * num5) + rankTemp[i]) % num6;
		String positionTemp = position.toString();
		for (int i = 0; i < positionTemp.length(); ++i)
			hash = ((hash * num7) + positionTemp[i]) % num8;
		return hash;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Info) {
			Info info = (Info) o;
			return (token.equals(info.token) && url.equals(info.url) && (rank.equals(info.rank)) && (position.equals(info.position)));
	}
	
	@Override
	public String toString() {
		return token + '#' + url + '#' + rank.toString() + '#' + position.toString();
	}
	
	@Override
	public int compareTo(Info info) {
		int cmp = token.compareTo(info.token);
		if (cmp != 0)
			return cmp;
		cmp = url.compareTo(info.url);
		if (cmp != 0)
			return cmp;
		cmp = rank.compareTo(info.rank);
		if (cmp != 0)
			return cmp;
		return position.compareTo(info.position);
	}
}
