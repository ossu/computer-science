import java.util.Deque;
import java.util.ArrayDeque;
import java.util.ArrayList;

// Represents a mutable collection of items
interface ICollection<T> {
	// Is this collection empty?
	boolean isEmpty();
	// EFFECT: adds the item to the collection
	void add(T item);
	// Returns the first item of the collection
	// EFFECT: removes that first item
	T remove();
}

// first in first out
class Stack<T> implements ICollection<T> {
	Deque<T> contents;
	Stack() {
		this.contents = new ArrayDeque<T>();
	}
	public boolean isEmpty() {
		return this.contents.isEmpty();
	}
	public T remove() {
		return this.contents.removeLast();
	}
	public void add(T item) {
		this.contents.addFirst(item);
	}
}

// last in last out
class Queue<T> implements ICollection<T> {
	Deque<T> contents;
	Queue() {
		this.contents = new ArrayDeque<T>();
	}
	public boolean isEmpty() {
		return this.contents.isEmpty();
	}
	public T remove() {
		return this.contents.removeLast();
	}
	public void add(T item) {
		this.contents.addLast(item); // NOTE: Different from Stack!
	}
}

// highest priority out
class PriorityQueue<T> implements ICollection<T> {
	ArrayList<T> heap;
	IComparator<T> comp;
	PriorityQueue(ArrayList<T> inputList, IComparator<T> comp) {
		this.comp = comp;
		buildHeap(inputList);
	}
	PriorityQueue(IComparator<T> comp) {
		this(new ArrayList<T>(), comp);
	}
	void buildHeap(ArrayList<T> inputList) {
		this.heap = inputList;
		for (int i = inputList.size() - 1; i >= 0; i--) {
			this.downHeap(i);
		}
	}
	// swaps the parent and child items
	void swap(int parentIndex, int childIndex) {
		T temp = heap.get(parentIndex);
		heap.set(parentIndex, heap.get(childIndex));
		heap.set(childIndex, temp);
	}
	// recursively uses comp to compare child with parent 
	// and swaps if child is higher priority
	void upHeap(int childIndex) {
		int parentIndex = (childIndex - 1) / 2;
		// comp will return >0 if left has higher priority than right
		// ergo swap when comp returns <0
		if (comp.compare(heap.get(parentIndex), heap.get(childIndex)) < 0) {
			this.swap(parentIndex, childIndex);
			this.upHeap(parentIndex);
		}
	}
	// recursively uses comp to compare parent with children 
	// and swaps if child is higher priority
	void downHeap(int parentIndex) {
		this.downHeapMaxDepth(parentIndex, this.heap.size());
	}
	void downHeapMaxDepth(int parentIndex, int maxDepthIndex) {
		int leftChildIndex = 2 * parentIndex + 1;
		int rightChildIndex = 2 * parentIndex + 2;
		if (rightChildIndex < maxDepthIndex) {
			if (comp.compare(heap.get(leftChildIndex), heap.get(rightChildIndex)) > 0) {
				if (comp.compare(heap.get(parentIndex), heap.get(leftChildIndex)) < 0) {
					this.swap(parentIndex, leftChildIndex);
					this.downHeapMaxDepth(leftChildIndex, maxDepthIndex);
				}
			} else if (comp.compare(heap.get(parentIndex), heap.get(rightChildIndex)) < 0) {
				this.swap(parentIndex, rightChildIndex);
				this.downHeapMaxDepth(rightChildIndex, maxDepthIndex);
			}
		} else if (leftChildIndex < maxDepthIndex) {
			if (comp.compare(heap.get(parentIndex), heap.get(leftChildIndex)) < 0) {
				this.swap(parentIndex, leftChildIndex);
				this.downHeapMaxDepth(leftChildIndex, maxDepthIndex);
			}
		}
	}

	// Interface methods
	public boolean isEmpty() {
		return this.heap.isEmpty();
	}
	public void add(T item) {
		this.heap.add(item);
		this.upHeap(heap.size() - 1);
	}
	public T remove() {
		int lastIndex = this.heap.size() - 1;
		this.swap(0, lastIndex);
		T response = this.heap.remove(lastIndex);
		this.downHeap(0);
		return response;
	}

}
