import tester.Tester;
import java.util.ArrayList;
import java.util.Arrays;

class Stack<T> {
	Deque<T> contents;

	Stack(Deque<T> contents) {
		this.contents = contents;
	}
	Stack() {
		this.contents = new Deque<T>();
	}

	//adds an item to the stack
	void push(T item) {
		this.contents.addAtHead(new Node<T>(item));
	}
	// returns true if contents are empty
	boolean isEmpty() {
		if (contents.size() == 0) {
			return true;
		} else {
			return false;
		}
	}
	//removes and returns the top of the stack
	T pop() {
		return this.contents.removeFromHead().getData();
	}
}

class Utils {
	// Using loops and one or more stacks, define the method that reverses an ArrayList: 
	<T> ArrayList<T> reverse(ArrayList<T> source) {
		ArrayList<T> response = new ArrayList<T>();
		Stack<T> intermediate = new Stack<T>();
		int size = 0;
		for (T item : source) {
			intermediate.push(item);
			size++;
		}
		for (int i = 0; i < size; i++) {
			response.add(i, intermediate.pop());
		}
		return response;
	}
}

class StackExamples {
	Stack<Runner> s0;
	Stack<Runner> s1;
	Deque<Runner> dqAns;

	Runner amy;
	Runner bob;
	Runner cale;
	Runner dave;
	Runner stillBob;
	Runner notBob;
	void init() {
		amy = new Runner(50, "amy");
		bob = new Runner(40, "bob");
		cale = new Runner(30, "cale");
		dave = new Runner(20, "dave");
		stillBob = new Runner(40, "bob");
		notBob = new Runner(20, "bob");

		s0 = new Stack<Runner>();
		s1 = new Stack<Runner>();
		dqAns = new Deque<Runner>();
	}
	void testPush(Tester t) {
		init();
		t.checkExpect(s1.contents.header.next, s1.contents.header);
		s1.push(dave);
		t.checkExpect(s1.contents.header.next,
				new Node<Runner>(dave, dqAns.header, dqAns.header));
		s1.push(cale);
		t.checkExpect(s1.contents.header.next,
				new Node<Runner>(cale, dqAns.header, dqAns.header.prev));
	}
	void testIsEmpty(Tester t) {
		init();
		t.checkExpect(s0.isEmpty(), true);
		t.checkExpect(s1.isEmpty(), true);
		s1.push(dave);
		t.checkExpect(s1.isEmpty(), false);
	}
	void testPop(Tester t) {
		init();
		t.checkException(
				new RuntimeException("Cannot remove item from empty list"),
				s1,
				"pop");
		s1.push(dave);
		t.checkExpect(s1.pop(), dave);
		s1.push(dave);
		s1.push(cale);
		s1.push(bob);
		s1.push(amy);
		t.checkExpect(s1.pop(), amy);
		t.checkExpect(s1.pop(), bob);
		t.checkExpect(s1.pop(), cale);
		t.checkExpect(s1.pop(), dave);
	}
	void testReverseArrayList(Tester t) {
		ArrayList<Runner> fwd = new ArrayList<Runner>(Arrays.asList(amy, bob, cale, dave));
		ArrayList<Runner> rev = new ArrayList<Runner>(Arrays.asList(dave, cale, bob, amy));
		t.checkExpect(new Utils().reverse(fwd), rev);
	}
}
