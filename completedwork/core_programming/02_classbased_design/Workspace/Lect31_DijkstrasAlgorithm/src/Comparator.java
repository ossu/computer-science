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

class HigherVertexWeight implements IComparator<Vertex> {
	// returns true (>0) if left has a higher priority (larger weight)
	public int compare(Vertex left, Vertex right) {
		return left.weight - right.weight;
	}
}

class SmallerVertexWeight implements IComparator<Vertex> {
	// returns true (>0) if left has a higher priority (smaller weight)
	public int compare(Vertex left, Vertex right) {
		return right.weight - left.weight;
	}
}

class SmallerPathCost implements IComparator<Path> {
	// returns true (>0) if left has a lower cost (cumulative cost)
	public int compare(Path left, Path right) {
		return right.weight - left.weight;
	}
}
