//        +--------------------+
//        | Deque<T>           |
//        +--------------------+
//        | Sentinel<T> header |-+
//        +--------------------+ |
//                               |
//    +--------------------------+
//    |
//    |
//    |      +---------------+
//    |      | ANode<T>      |
//    |      +---------------+
//    |      | ANode<T> next |
//    |      | ANode<T> prev |
//    |      +---------------+
//    |         /_\     /_\
//    |          |       |
//    |     +----+       |
//    V     |            |
//+-------------+      +---------+
//| Sentinel<T> |      | Node<T> |
//+-------------+      +---------+
//+-------------+      | T data  |
//                     +---------+
//
//
//interface IPred<T> {
//	boolean apply(T t);
//}
//
//class MatchToData<T> implements IPred<T> {
//	T match;
//	MatchToData(T match) {
//		this.match = match;
//	}
//	public boolean apply(T t) {
//		return t.equals(match);
//	}
//}

abstract class ANode<T> {
	ANode<T> next;
	ANode<T> prev;
	ANode(ANode<T> next, ANode<T> prev) {
		this.next = next;
		this.prev = prev;
	}
	// inserts this node between before and after nodes
	void insertBetween(ANode<T> next, ANode<T> prev) {
		if (prev == null || next == null) {
			throw new IllegalArgumentException("Cannot assign next or prev to null");
		}
		prev.next = this;
		this.prev = prev;
		this.next = next;
		next.prev = this;
	}
	// inserts this node after provided node
	void insertAfter(ANode<T> prev) {
		this.insertBetween(prev.next, prev);
	}
	// inserts this node before provided node
	void insertBefore(ANode<T> next) {
		this.insertBetween(next, next.prev);
	}
	int size() {
		if (this.next == null) {
			return 0;
		} else {
			return this.next.sizeHelper(0);
		}
	}
	abstract void remove();
	abstract int sizeHelper(int cnt);
	abstract ANode<T> find(IPred<T> p);
}

class Sentinel<T> extends ANode<T> {
	Sentinel(ANode<T> next, ANode<T> prev) {
		super(next, prev);
	}
	Sentinel() {
		this(null, null);
		this.insertBetween(this, this);
	}
	int sizeHelper(int cnt) {
		return cnt;
	}
	ANode<T> removeNext() {
		if (this.next == this) {
			throw new RuntimeException("Cannot remove item from empty list");
		}
		ANode<T> r = this.next;
		this.next.remove();
		return r;
	}
	ANode<T> removePrev() {
		if (this.next == this) {
			throw new RuntimeException("Cannot remove item from empty list");
		}
		ANode<T> r = this.prev;
		this.prev.remove();
		return r;
	}
	void remove() {
		throw new RuntimeException("Cannot remove sentinel node");
	}
	ANode<T> find(IPred<T> p) {
		return this;
	}
}

class Node<T> extends ANode<T> {
	T data;
	Node(T data, ANode<T> next, ANode<T> prev) {
		super(next, prev);
		this.data = data;
		this.insertBetween(prev, next);
	}
	Node(T data) {
		super(null, null);
		this.data = data;
	}
	int sizeHelper(int cnt) {
		return this.next.sizeHelper(cnt + 1);
	}
	void remove() {
		this.prev.next = this.next;
		this.next.prev = this.prev;
	}
	ANode<T> find(IPred<T> pred) {
		if (pred.apply(this.data)) {
			return this;
		} else {
			return this.next.find(pred);
		}
	}
}

class Deque<T> {
	Sentinel<T> header;
	Deque(Sentinel<T> header) {
		this.header = header;
	}
	Deque() {
		this(new Sentinel<T>());
	}
	// returns the number of nodes in a list Deque, not including the header node
	int size() {
		return this.header.size(); // 
	}
	// inserts node n at the front of the list.
	void addAtHead(Node<T> n) {
		n.insertAfter(this.header);
	}
	// inserts node n at the end of the list.
	void addAtTail(Node<T> n) {
		n.insertBefore(this.header);
	}
	// returns and removes the first node from this Deque. 
	// Throw a RuntimeException if an attempt is made to remove
	// from an empty list. 
	ANode<T> removeFromHead() {
		return this.header.removeNext();
	}
	// returns and removes the last node from this Deque. 
	// Throw a RuntimeException if an attempt is made to remove
	// from an empty list.
	ANode<T> removeFromTail() {
		return this.header.removePrev();
	}
	// returns the first node in this Deque where the predicate returns true
	// if nothing is found return header
	ANode<T> find(IPred<T> p) {
		// start at first node so when we hit sentinel we return sentinel
		return this.header.next.find(p);
	}
	// removes the given node from this Deque
	// If the given node is the Sentinel header, the method does nothing
	void removeNode(Node<T> n) {
		ANode<T> found = this.find(new MatchToData<T>(n.data));
		// we dont want to remove the sentinel node
		if (found instanceof Sentinel) {
			return;
		}
		found.remove();
	}
}
