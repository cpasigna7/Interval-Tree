package structures;

import java.util.ArrayList;

/**
 * Encapsulates an interval tree.
 * 
 * @author runb-cs112
 */
public class IntervalTree {
	
	/**
	 * The root of the interval tree
	 */
	IntervalTreeNode root;
	
	/**
	 * Constructs entire interval tree from set of input intervals. Constructing the tree
	 * means building the interval tree structure and mapping the intervals to the nodes.
	 * 
	 * @param intervals Array list of intervals for which the tree is constructed
	 */
	public IntervalTree(ArrayList<Interval> intervals) {
		// make a copy of intervals to use for right sorting
		ArrayList<Interval> intervalsRight = new ArrayList<Interval>(intervals.size());
		for (Interval iv : intervals){
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
		
		// build the tree nodes
		root = buildTreeNodes(sortedEndPoints);
		
		// map intervals to the tree nodes
		mapIntervalsToTree(intervalsLeft, intervalsRight);
	}
	
	/**
	 * Returns the root of this interval tree.
	 * 
	 * @return Root of interval tree.
	 */
	public IntervalTreeNode getRoot() {
		return root;
	}
	
	/*
	 * Sorts a set of intervals in place, according to left or right endpoints.  
	 * At the end of the method, the parameter array list is a sorted list. 
	 * 
	 * @param intervals Array list of intervals to be sorted.
	 * @param lr If 'l', then sort is on left endpoints; if 'r', sort is on right endpoints
	 */
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
	private static Interval get(int i) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Given a set of intervals (left sorted and right sorted), extracts the left and right end points,
	 * and returns a sorted list of the combined end points without duplicates.
	 * 
	 * @param leftSortedIntervals Array list of intervals sorted according to left endpoints
	 * @param rightSortedIntervals Array list of intervals sorted according to right endpoints
	 * @return Sorted array list of all endpoints without duplicates
	 */
	public static ArrayList<Integer> getSortedEndPoints(ArrayList<Interval> leftSortedIntervals, ArrayList<Interval> rightSortedIntervals) {// COMPLETE THIS METHOD
		ArrayList<Integer> points = new ArrayList<Integer>();
		for(int i = 0; i<leftSortedIntervals.size(); i++){
			points.add(leftSortedIntervals.get(i).leftEndPoint);
			points.add(rightSortedIntervals.get(i).rightEndPoint);
		}
		for(int j = 0; j<100;j++){
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
				if(points.get(ab)==points.get(ab+1)) points.remove(ab);
			}
		}
		return points;
	}
	
	/**
	 * Builds the interval tree structure given a sorted array list of end points
	 * without duplicates.
	 * 
	 * @param endPoints Sorted array list of end points
	 * @return Root of the tree structure
	 */
	public static IntervalTreeNode buildTreeNodes(ArrayList<Integer> endPoints) {// COMPLETE THIS METHOD
		Queue<IntervalTreeNode> q = new Queue<IntervalTreeNode>();
		IntervalTreeNode t;
		for(int i=0; i<endPoints.size(); i++){
			int p = endPoints.get(i);
			t = new IntervalTreeNode(p,p,p);
			t.leftIntervals = new ArrayList<Interval>();
			t.rightIntervals = new ArrayList<Interval>();
			q.enqueue(t);
		}
		int s = q.size();//what's the difference between no parenthesis and parenthesis?
		while(s>0){
			if (s==1){
				return q.dequeue();
			}
			else{
				int temps = q.size();
				while(temps>1){
					IntervalTreeNode T1 = q.dequeue();
					IntervalTreeNode T2 = q.dequeue();
					float v1 = T1.maxSplitValue;
					float v2 = T2.minSplitValue;
					IntervalTreeNode n = new IntervalTreeNode(((v1 + v2)/ 2), T1.minSplitValue, T2.maxSplitValue);
					n.leftIntervals = new ArrayList<Interval>();
					n.rightIntervals = new ArrayList<Interval>();
					n.leftChild = T1;
					n.rightChild = T2;
					q.enqueue(n);
					temps = temps-2;
			}
			s = q.size();
			if(temps == 1)q.enqueue(q.dequeue());
			}
		}
		return q.dequeue();
	}
	
	public void mapIntervalsToTree(ArrayList<Interval> leftSortedIntervals, ArrayList<Interval> rightSortedIntervals){// COMPLETE THIS METHOD
		IntervalTreeNode T = this.root;
		for (int i = 0; i < leftSortedIntervals.size(); i++){
			Interval interval = leftSortedIntervals.get(i);
			traverse(T,leftSortedIntervals,interval,'l');
		}
		T = this.root;
		for (int i = 0; i < rightSortedIntervals.size(); i++){
			Interval interval = rightSortedIntervals.get(i);
			traverse(T,leftSortedIntervals,interval,'r');
		}
		/*for(IntervalTreeNode count = T; count != null; count = T.leftChild){
			for(int i = 0; i<count.leftIntervals.size(); i++){
				if (T.leftIntervals.contains(T.rightIntervals.get(i))){
				}
				else{
				T.leftIntervals.remove(i);
				leftIn
				}
			}
			for(int i = 0; i<count.rightIntervals.size(); i++){
				if (T.rightIntervals.contains(T.leftIntervals.get(i))){
				}
				else{
				T.rightIntervals.remove(i);
				}
			}
		}
		for(IntervalTreeNode count = T; count != null; count = T.rightChild){
			for(int i = 0; i<count.leftIntervals.size(); i++){
				if (T.leftIntervals.contains(T.rightIntervals.get(i))){
				}
				else{
				T.leftIntervals.remove(i);
				}
			}
			for(int i = 0; i<count.rightIntervals.size(); i++){
				if (T.rightIntervals.contains(T.leftIntervals.get(i))){
				}
				else{
				T.rightIntervals.remove(i);
				}
			}
		}*/
		
	}

	private void traverse(IntervalTreeNode T, ArrayList<Interval>SortedIntervals,Interval interval, char lr){
		if(lr == 'l'){
				if (T.splitValue>=interval.leftEndPoint && T.splitValue<=interval.rightEndPoint){//base case
					if (T.leftIntervals == null){
						T.leftIntervals = new ArrayList<Interval>();
					}
					T.leftIntervals.add(interval);
					return;
				}
				else if(T.splitValue>interval.rightEndPoint){
					if(T.leftChild==null){
						return;
					}
					else{
						T = T.leftChild;
						traverse(T,SortedIntervals,interval,'l');
					}
				}
				else{
					if(T.rightChild==null){
						return;
					}
					else{
						T = T.rightChild;
						traverse(T,SortedIntervals,interval,'l');
					}
				}
			}
		 else if (lr == 'r'){
				if (T.splitValue>=interval.leftEndPoint && T.splitValue<=interval.rightEndPoint){//base case
					if (T.rightIntervals == null){
						T.rightIntervals = new ArrayList<Interval>();
					}
					T.rightIntervals.add(interval);
					return;
				}
				else if(T.splitValue>interval.rightEndPoint){
					if(T.leftChild==null){
						return;
					}
					else{
						T = T.leftChild;
						traverse(T,SortedIntervals,interval,'r');
					}
				}
				else{
					if(T.rightChild==null){
						return;
					}
					else{
						T = T.rightChild;
						traverse(T,SortedIntervals,interval,'r');
					}
				}
			}
		
		else {
		return;
		}
	}
	/**
	 * Gets all intervals in this interval tree that intersect with a given interval.
	 * 
	 * @param q The query interval for which intersections are to be found
	 * @return Array list of all intersecting intervals; size is 0 if there are no intersections
	 */
	public ArrayList<Interval> findIntersectingIntervals(Interval q){// COMPLETE THIS METHOD
		ArrayList<Interval>ResultList = new ArrayList<Interval>();
		return Q(root, q, ResultList);
	}
	private ArrayList<Interval>Q(IntervalTreeNode R, Interval interval,ArrayList<Interval>ResultList){
		if (R == null){
			return ResultList;
		}
		ArrayList<Interval>LeftIntervals = R.leftIntervals;
		ArrayList<Interval>RightIntervals = R.rightIntervals;
		IntervalTreeNode Lsub = R.leftChild;
		IntervalTreeNode Rsub = R.rightChild;
		if (R.splitValue>=interval.leftEndPoint && R.splitValue<=interval.rightEndPoint){
			for(int i = 0; i< LeftIntervals.size() && LeftIntervals.get(i).intersects(interval); i++){
				Interval x = LeftIntervals.get(i);
				ResultList.add(x);
			}
			Q(Lsub, interval, ResultList);
			Q(Rsub, interval, ResultList);
		} else if (R.splitValue<interval.leftEndPoint){
			for(int i = RightIntervals.size()-1 ; i >= 0 && RightIntervals.get(i).intersects(interval); i--){
				Interval x = RightIntervals.get(i);
				ResultList.add(x);
			}
			Q(Rsub, interval, ResultList);
		} else if(R.splitValue>interval.rightEndPoint){
			for(int i = 0; i< LeftIntervals.size() && LeftIntervals.get(i).intersects(interval); i++){
				Interval x = LeftIntervals.get(i);
				ResultList.add(x);
			}
			Q(Lsub,interval,ResultList);
		}
		return ResultList;
	}
}