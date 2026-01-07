interface IPred<T> {
	boolean apply(T t);
}

interface IFunc<A, R> {
	R apply(A arg);
}

interface IFunc2<A1, A2, R> {
	R apply(A1 arg1, A2 arg2);
}

interface IComparator<T> {
	// Returns a negative number if t1 priority is less than t2 priority==-
	int compare(T t1, T t2);
}

class ParentLargerInteger implements IComparator<Integer> {
	// returns true (>0) if left has higher priority (larger int)
	public int compare(Integer left, Integer right) {
		return left - right;
	}
}

class ParentSmallerInteger implements IComparator<Integer> {
	// returns true (>0) if left has a higher priority (smaller int)
	public int compare(Integer left, Integer right) {
		return right - left;
	}
}

class ParentLargerEdge implements IComparator<Hall> {
	// returns true (>0) if left edge has a higher priority (lower weight) 
	public int compare(Hall left, Hall right) {
		return left.weight - right.weight;
	}
}

class ParentSmallerEdge implements IComparator<Hall> {
	// returns true (>0) if left edge has a higher priority (higher weight) 
	public int compare(Hall left, Hall right) {
		return right.weight - left.weight;
	}
}
