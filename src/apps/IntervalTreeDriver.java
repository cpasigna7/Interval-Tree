package apps;

import structures.*;

import java.io.*;
import java.util.*;

public class IntervalTreeDriver {
	
	static BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
	
	public static void main(String[] args) throws IOException {
		System.out.print("Enter intervals file name => ");
		String infile = keyboard.readLine();
		BufferedReader br = new BufferedReader(new FileReader(infile));
		ArrayList<Interval> intervals = readIntervals(br);
		System.out.println("Read the following intervals:");
		for (Interval interval: intervals) {
			System.out.println(interval);
		}
		/*sortIntervals(intervals, 'l');
		System.out.println(intervals + "this is left");
		sortIntervals(intervals, 'r');
		System.out.println(intervals + "this is right");*/
		// make a copy of intervals to use for right sorting
		ArrayList<Interval> intervalsRight = new ArrayList<Interval>(intervals.size());
		for (Interval iv : intervals) {
			intervalsRight.add(iv);
		}
		
		// rename input intervals for left sorting
		ArrayList<Interval> intervalsLeft = intervals;
		
		// sort intervals on left and right end points
		sortIntervals(intervalsLeft, 'l');
		sortIntervals(intervalsRight,'r');
		
		// get sorted list of end points without duplicates
		ArrayList<Integer> sortedEndPoints = 
							getSortedEndPoints(intervalsLeft, intervalsRight);

		System.out.println(sortedEndPoints+"here it is");
		IntervalTree tree = new IntervalTree(intervals);
		performQueries(tree);
		
	}
		
	
	public static void sortIntervals(ArrayList<Interval> intervals, char lr) {// COMPLETE THIS METHOD
		if(lr == 'l'){
			insertion(intervals,'l');
		}
		if(lr == 'r'){
			insertion(intervals,'r');
		}
	}
	
	
	private static void insertion(ArrayList<Interval>a, char lr){
		if (lr == 'l'){
			for(int j = 0; j<100;j++){
				for (int i = 0; i<a.size()-1; i++){
					if(a.get(i).leftEndPoint>a.get(i+1).leftEndPoint){
						Interval hold = a.get(i);
						a.set(i,a.get(i+1));
						a.set(i+1,hold);
					}
				}
			}
		}
		else {
			for(int j = 0; j<100;j++){
				for (int i = 0; i<a.size()-1; i++){
					if(a.get(i).rightEndPoint>a.get(i+1).rightEndPoint){
						Interval hold = a.get(i);
						a.set(i,a.get(i+1));
						a.set(i+1,hold);
					}
				}
			}
		}
	}
	
	
	public static ArrayList<Integer> getSortedEndPoints(ArrayList<Interval> leftSortedIntervals, ArrayList<Interval> rightSortedIntervals) {// COMPLETE THIS METHOD
		ArrayList<Integer> points = new ArrayList<Integer>();
		for(int i = 0; i<leftSortedIntervals.size(); i++){
			points.add(leftSortedIntervals.get(i).leftEndPoint);
			points.add(rightSortedIntervals.get(i).rightEndPoint);
		}
		for(int j = 0; j<100;j++){//place array into ascending order
			for (int i = 0; i<points.size()-1; i++){
				if(points.get(i)>points.get(i+1)){
					Integer hold = points.get(i);
					points.set(i,points.get(i+1));
					points.set(i+1,hold);
				}
			}
		}
		for(int j = 0; j<100; j++){
			for(int ab = 0; ab<points.size()-1;ab++){
				if(points.get(ab)==points.get(ab+1)) points.remove(ab); //gets rid of duplicates
			}
		}
		return points;
	}
	
	static ArrayList<Interval> readIntervals(BufferedReader br) throws IOException  {
		String line;
		ArrayList<Interval> ret = new ArrayList<Interval>();
		
		while ((line = br.readLine()) != null) {
			StringTokenizer st = new StringTokenizer(line);
			Interval intrvl = new Interval(
					Integer.parseInt(st.nextToken()), 
					Integer.parseInt(st.nextToken()), st.nextToken());
			ret.add(intrvl);
		}
		return ret;
	}
	
	static void performQueries(IntervalTree tree) throws IOException {
		System.out.print("\nEnter an interval (e.g. 3 5) to intersect, quit to stop => ");
		String schedule = keyboard.readLine();
		while (!schedule.equals("quit")) {
			StringTokenizer st = new StringTokenizer(schedule);
			
			Interval intrvl = new Interval(Integer.parseInt(st.nextToken()),
					Integer.parseInt(st.nextToken()), "");
					
			ArrayList<Interval> intersects = tree.findIntersectingIntervals(intrvl);
			for (Interval interval: intersects) {
				System.out.println(interval);
			}
			
			System.out.print("\nEnter an interval (e.g. 3 5) to intersect, quit to stop => ");	    
			schedule = keyboard.readLine();
		}
	}
}
