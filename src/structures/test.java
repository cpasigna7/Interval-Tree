package structures;

import java.util.ArrayList;

public class test {
	public static void main(String[] args)
	{
		ArrayList<Interval> tester = new ArrayList<Interval>();
		Interval a = new Interval(1, 2, "");
		tester.add(a);
		Interval b = new Interval(8, 15, "");
		tester.add(b);
		Interval c = new Interval(4, 7, "");
		tester.add(c);
		Interval d = new Interval(2, 5, "");
		tester.add(d);
		Interval e = new Interval(1, 3, "");
		tester.add(e);
		Interval f = new Interval(4, 5, "");
		tester.add(f);
		/*
		for (Interval iv : tester) {
			System.out.println(iv.rightEndPoint);
		}
		System.out.println();
		
		IntervalTree.sortIntervals(tester, 'l');
		ArrayList<Interval>leftboi = tester;
		IntervalTree.sortIntervals(tester, 'r');
		for (Interval iv : leftboi) {
			System.out.println(iv.leftEndPoint);
		}
		System.out.println();
		for (Interval iv : tester) {
			System.out.println(iv.rightEndPoint);
		}
		System.out.println();
		ArrayList<Integer> result = IntervalTree.getSortedEndPoints(leftboi, tester);
		for (Integer iv : result) {
			System.out.println(iv.intValue());
		}
		*/
		IntervalTree tree = new IntervalTree(tester);
		System.out.println(tree.root.splitValue);
	}
}
