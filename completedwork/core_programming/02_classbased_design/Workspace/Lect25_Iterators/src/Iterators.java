import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import tester.Tester;

//// Represents the ability to produce a sequence of values
//// of type T, one at a time
//interface Iterator<T> {
//	// Does this sequence have at least one more value?
//	boolean hasNext();
//	// Get the next value in this sequence
//	// EFFECT: Advance the iterator to the subsequent value
//	T next();
//	// EFFECT: Remove the item just returned by next()
//	// NOTE: This method may not be supported by every iterator; ignore it for now
//	void remove();
//}

//Represents anything that can be iterated over
interface Iterable<T> {
	// Returns an iterator over this collection
	Iterator<T> iterator();
}

class ArrayListIterator<T> implements Iterator<T> {
	// the list of items that this iterator iterates over
	ArrayList<T> items;
	// the index of the next item to be returned
	int nextIdx;
	// Construct an iterator for a given ArrayList
	ArrayListIterator(ArrayList<T> items) {
		this.items = items;
		this.nextIdx = 0;
	}
	//Does this sequence (of items in the array list) have at least one more value?
	public boolean hasNext() {
		return this.nextIdx < this.items.size();
	}
	//Get the next value in this sequence
	//EFFECT: Advance the iterator to the subsequent value
	public T next() {
		T answer = this.items.get(this.nextIdx);
		this.nextIdx++;
		return answer;
	}
	public void remove() {
		throw new UnsupportedOperationException("Don't do this!");
	}
}

class IListIterator<T> implements Iterator<T> {
	IList<T> items;
	IListIterator(IList<T> items) {
		this.items = items;
	}
	public boolean hasNext() {
		return this.items.isCons();
	}
	public T next() {
		ConsList<T> itemsAsCons = this.items.asCons();
		T answer = itemsAsCons.first;
		this.items = itemsAsCons.rest;
		return answer;
	}
	public void remove() {
		throw new UnsupportedOperationException("Don't do this!");
	}
}

class DequeForwardIterator<T> implements Iterator<T> {
	ANode<T> currentNode;
	DequeForwardIterator(Deque<T> deque) {
		this.currentNode = deque.header;
	}
	public boolean hasNext() {
		return this.currentNode.next.isNode();
	}
	public T next() {
		T result = this.currentNode.next.getData();
		this.currentNode = this.currentNode.next;
		return result;
	}
	public void remove() {
		throw new UnsupportedOperationException("Don't do this!");
	}
}

class DequeReverseIterator<T> implements Iterator<T> {
	ANode<T> currentNode;
	DequeReverseIterator(Deque<T> deque) {
		this.currentNode = deque.header;
	}
	public boolean hasNext() {
		return this.currentNode.prev.isNode();
	}
	public T next() {
		T result = this.currentNode.prev.getData();
		this.currentNode = this.currentNode.prev;
		return result;
	}
	public void remove() {
		throw new UnsupportedOperationException("Don't do this!");
	}
}

class FibonacciIterator implements Iterator<Integer> {
	int prevVal = 0;
	int curVal = 1;
	// There are always more Fibonacci numbers
	public boolean hasNext() {
		return true;
	}
	// Compute the next Fibonacci number
	public Integer next() {
		int answer = this.prevVal + this.curVal;
		this.prevVal = this.curVal;
		this.curVal = answer;
		return answer;
	}
	public void remove() {
		throw new UnsupportedOperationException("Don't do this!");
	}
}

class EveryOtherIter<T> implements Iterator<T> {
	Iterator<T> source;
	EveryOtherIter(Iterator<T> source) {
		this.source = source;
	}
	public boolean hasNext() {
		// this sequence has a next item if the source does
		return this.source.hasNext();
	}
	public T next() {
		T answer = this.source.next(); // gets the answer, and advances the source
		// We need to have the source "skip" the next value
		if (this.source.hasNext()) {
			this.source.next(); // get the next value and ignore it
		}
		return answer;
	}
	public void remove() {
		// We can remove an item if our source can remove the item
		this.source.remove(); // so just delegate to the source
	}
}

class TakeN<T> implements Iterator<T> {
	Iterator<T> source;
	int howMany;
	int countSoFar;
	TakeN(Iterator<T> source, int n) {
		this.source = source;
		this.howMany = n;
		this.countSoFar = 0;
	}
	public boolean hasNext() {
		return (countSoFar < howMany) && source.hasNext();
	}
	public T next() {
		countSoFar = countSoFar + 1;
		return source.next();
	}
	public void remove() {
		// We can remove an item if our source can remove the item
		this.source.remove(); // so just delegate to the source
	}
}

class AlternateIter<T> implements Iterator<T> {
	Iterator<T> iter1;
	Iterator<T> iter2;
	int currentIter;
	AlternateIter(Iterator<T> iter1, Iterator<T> iter2) {
		this.iter1 = iter1;
		this.iter2 = iter2;
	}
	public boolean hasNext() {
		// this sequence has a next item if the source does
		if (currentIter % 2 == 0) {
			return iter1.hasNext();
		} else {
			return iter2.hasNext();
		}
	}
	public T next() {
		// this sequence has a next item if the source does
		if (currentIter % 2 == 0) {
			currentIter++;
			return iter1.next();
		} else {
			currentIter++;
			return iter2.next();
		}
	}
	public void remove() {
		// We can remove an item if our source can remove the item
		// this sequence has a next item if the source does
		if (currentIter % 2 == 0) {
			iter1.remove();
		} else {
			iter2.remove();
		}
	}
}

class BreadthFirstIterator<T> implements Iterator<T> {
	Deque<IBinaryTree<T>> worklist;
	BreadthFirstIterator(IBinaryTree<T> source) {
		this.worklist = new Deque<IBinaryTree<T>>();
		this.addIfNotLeaf(source);
	}
	// EFFECT: only adds the given binary-tree if it's not a leaf
	void addIfNotLeaf(IBinaryTree<T> bt) {
		if (bt.isNode()) {
			this.worklist.addAtTail(new Node<IBinaryTree<T>>(bt));
		}
	}
	public boolean hasNext() {
		// we have a next item if the worklist isn't empty
		return this.worklist.size() > 0;
	}
	public T next() {
		// Get (and remove) the first item on the worklist --
		// and we know it must be a BTNode
		BSTNode<T> node = this.worklist.removeFromHead().getData().getNode();
		// Add the children of the node to the tail of the list
		this.addIfNotLeaf(node.left);
		this.addIfNotLeaf(node.right);
		// return the answer
		return node.data;
	}
	public void remove() {
		throw new UnsupportedOperationException("Don't do this!");
	}
}

class PreOrderIterator<T> implements Iterator<T> {
	Deque<IBinaryTree<T>> worklist;
	PreOrderIterator(IBinaryTree<T> source) {
		this.worklist = new Deque<IBinaryTree<T>>();
		this.addIfNotLeaf(source);
	}
	// EFFECT: only adds the given binary-tree if it's not a leaf
	void addIfNotLeaf(IBinaryTree<T> bt) {
		if (bt.isNode()) {
			this.worklist.addAtHead(new Node<IBinaryTree<T>>(bt));
		}
	}
	public boolean hasNext() {
		// we have a next item if the worklist isn't empty
		return this.worklist.size() > 0;
	}
	public T next() {
		// Get (and remove) the first item on the worklist,
		// but we must convert to correct data type --
		// and we know it must be a BTNode
		BSTNode<T> node = this.worklist.removeFromHead().getData().getNode();
		// Add the children of the node to the head of the list
		this.addIfNotLeaf(node.right);
		this.addIfNotLeaf(node.left);
		// return the answer
		return node.data;
	}
	public void remove() {
		throw new UnsupportedOperationException("Don't do this!");
	}
}

class PostOrderIterator<T> implements Iterator<T> {
	Deque<IBinaryTree<T>> worklist;
	PostOrderIterator(IBinaryTree<T> source) {
		this.worklist = new Deque<IBinaryTree<T>>();
		this.addAllNodes(source);
	}
	// EFFECT: recursively adds all nodes in tree
	void addAllNodes(IBinaryTree<T> bt) {
		if (bt.isNode()) {
			this.worklist.addAtHead(new Node<IBinaryTree<T>>(bt));
			this.addAllNodes(bt.getRight());
			this.addAllNodes(bt.getLeft());
		}
	}
	public boolean hasNext() {
		// we have a next item if the worklist isn't empty
		return this.worklist.size() > 0;
	}
	//           d
	//     b           f
	//  a     c     e     g
	// acbefgd
	public T next() {
		// Get (and remove) the first item on the worklist,
		// but we must convert to correct data type --
		// and we know it must be a BTNode
		BSTNode<T> node = this.worklist.removeFromHead().getData().getNode();
		// return the answer
		return node.data;
	}
	public void remove() {
		throw new UnsupportedOperationException("Don't do this!");
	}
}

class InOrderIterator<T> implements Iterator<T> {
	Deque<IBinaryTree<T>> worklist;
	InOrderIterator(IBinaryTree<T> source) {
		this.worklist = new Deque<IBinaryTree<T>>();
		this.addAllNodes(source);
	}
	// EFFECT: recursively adds all nodes in tree
	void addAllNodes(IBinaryTree<T> bt) {
		if (bt.isNode()) {
			this.addAllNodes(bt.getRight());
			this.worklist.addAtHead(new Node<IBinaryTree<T>>(bt));
			this.addAllNodes(bt.getLeft());
		}
	}
	public boolean hasNext() {
		// we have a next item if the worklist isn't empty
		return this.worklist.size() > 0;
	}
	//           d
	//     b           f
	//  a     c     e     g
	//        abcdefg
	public T next() {
		// Get (and remove) the first item on the worklist,
		// but we must convert to correct data type --
		// and we know it must be a BTNode
		BSTNode<T> node = this.worklist.removeFromHead().getData().getNode();
		// return the answer
		return node.data;
	}
	public void remove() {
		throw new UnsupportedOperationException("Don't do this!");
	}
}

class Examples {
	ArrayList<String> strArray;
	IList<String> strList;
	Deque<String> deq;
	IBinaryTree<String> bst;
	IComparator<String> comp;

	// simple testing functions
	String concate(Iterator<String> iter) {
		String result = "";
		while (iter.hasNext()) {
			result = result + iter.next();
		}
		return result;
	}
	String intConcate(Iterator<Integer> iter) {
		String result = "";
		while (iter.hasNext()) {
			result = result + iter.next();
		}
		return result;
	}

	void init() {
		strArray = new ArrayList<String>(Arrays.asList(
				"a", "b", "c"));

		strList = new ConsList<String>("a",
				new ConsList<String>("b", new ConsList<String>("c",
						new MtList<String>())));

		deq = new Deque<String>();
		deq.addAtTail(new Node<String>("a"));
		deq.addAtTail(new Node<String>("b"));
		deq.addAtTail(new Node<String>("c"));
		deq.addAtTail(new Node<String>("d"));
		deq.addAtTail(new Node<String>("e"));

		//           d
		//     b           f
		//  a     c     e     g
		comp = new StringByAlpha();
		bst = new BSTNode<String>("d", new Leaf<String>(), new Leaf<String>());
		bst = bst.insert("b", comp);
		bst = bst.insert("f", comp);
		bst = bst.insert("a", comp);
		bst = bst.insert("c", comp);
		bst = bst.insert("e", comp);
		bst = bst.insert("g", comp);
	}

	void testNext(Tester t) {
		init();
		t.checkExpect(this.concate(new ArrayListIterator<String>(strArray)), "abc");
		t.checkExpect(this.concate(new IListIterator<String>(strList)), "abc");
		t.checkExpect(this.concate(new DequeForwardIterator<String>(deq)), "abcde");
		t.checkExpect(this.concate(new DequeReverseIterator<String>(deq)), "edcba");
		t.checkExpect(this.concate(new EveryOtherIter<String>(
				new DequeForwardIterator<String>(deq))), "ace");
		t.checkExpect(this.intConcate(new TakeN<Integer>(new FibonacciIterator(), 5)), "12358");

		t.checkExpect(this.concate(new AlternateIter<String>(
				new ArrayListIterator<String>(strArray), new IListIterator<String>(strList))),
				"aabbcc");
		t.checkExpect(this.concate(new BreadthFirstIterator<String>(bst)), "dbfaceg");
		t.checkExpect(this.concate(new PreOrderIterator<String>(bst)), "dbacfeg");
		t.checkExpect(this.concate(new PostOrderIterator<String>(bst)), "acbegfd");
		t.checkExpect(this.concate(new InOrderIterator<String>(bst)), "abcdefg");
	}
}
