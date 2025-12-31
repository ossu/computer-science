import java.util.Deque;
import java.util.ArrayDeque;

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
