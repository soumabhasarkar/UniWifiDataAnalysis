import java.util.Comparator;


public class IntervalComparator implements Comparator<Interval> {
	
	public int compare(Interval i1, Interval i2) {
		return (int) (i1.start.getTime() - i2.start.getTime());
	}
}