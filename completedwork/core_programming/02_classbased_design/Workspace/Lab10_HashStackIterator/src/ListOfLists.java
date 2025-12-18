import tester.Tester;
import java.util.ArrayList;
import java.util.Iterator;

public class ListOfLists<T> implements Iterable<T> {
	ArrayList<ArrayList<T>> contents;
	ListOfLists(ArrayList<ArrayList<T>> contents) {
		this.contents = contents;
	}
	ListOfLists() {
		this(new ArrayList<ArrayList<T>>());
	}
	// adds a new empty ArrayList<T> to the end of the list-of-lists.
	void addNewList() {
		this.contents.add(new ArrayList<T>());
	}
	// adds the provided object to the end of the ArrayList<T> at the 
	// provided index in teh list-of-lists. If the index is invalid, 
	// this method should throw an IndexOutOfBoundsException with a 
	// suitable message.
	void add(int index, T object) {
		this.contents.get(index).add(object);
	}
	ArrayList<T> get(int index) {
		if (index < 0 && index > this.contents.size()) {
			throw new IndexOutOfBoundsException("Bad Index Value");
		}
		return this.contents.get(index);
	}
	public int size() {
		return this.contents.size();
	}
	@Override
	public Iterator<T> iterator() {
		return new ListIterator<T>(this);
	}
}

class ListIterator<T> implements Iterator<T> {
	ListOfLists<T> listofList;
	Iterator<T> currentSubListIter;
	int nextIndex;
	ListIterator(ListOfLists<T> listofList) {
		this.listofList = listofList;
		this.nextIndex = 0;
		this.currentSubListIter = listofList.get(nextIndex).iterator();
		this.nextIndex++;
	}
	public boolean hasNext() {
		return nextIndex < listofList.size() && currentSubListIter.hasNext();
	}
	public T next() {
		if (!currentSubListIter.hasNext()) {
			this.currentSubListIter = listofList.get(nextIndex).iterator();
			this.nextIndex++;
		}
		return currentSubListIter.next();
	}
	public void remove() {
		throw new UnsupportedOperationException("Remove Action Unsupported.");
	}
}

class LofLExamples {
	void testListOfLists(Tester t) {
		ListOfLists<Integer> lol = new ListOfLists<Integer>();
		t.checkExpect(lol.contents.size(), 0);
		//add 3 lists
		lol.addNewList();
		lol.addNewList();
		lol.addNewList();
		t.checkExpect(lol.contents.size(), 3);

		//add elements 1,2,3 in first list
		t.checkExpect(lol.contents.get(0).size(), 0);
		lol.add(0, 1);
		lol.add(0, 2);
		lol.add(0, 3);
		t.checkExpect(lol.contents.get(0).size(), 3);

		//add elements 4,5,6 in second list
		t.checkExpect(lol.contents.get(1).size(), 0);
		lol.add(1, 4);
		lol.add(1, 5);
		lol.add(1, 6);
		t.checkExpect(lol.contents.get(1).size(), 3);

		//add elements 7,8,9 in third list
		t.checkExpect(lol.contents.get(2).size(), 0);
		lol.add(2, 7);
		lol.add(2, 8);
		lol.add(2, 9);
		t.checkExpect(lol.contents.get(0).size(), 3);

		//iterator should return elements in order 1,2,3,4,5,6,7,8,9
		int number = 1;
		for (Integer num : lol) {
			t.checkExpect(num, number);
			number = number + 1;
		}
	}
}
