import java.util.ArrayList;
import java.util.Arrays;
import tester.Tester;

class PQExamples {
	ArrayList<Integer> unsortedList;
	ArrayList<Integer> upHeapedList;
	ArrayList<Integer> downHeapedList;
	ArrayList<Integer> sortedList;
	ArrayList<Integer> worstCaseList;

	PriorityQueue<Integer> heapEmptyParentSmaller;
	PriorityQueue<Integer> heapPopulatedParentSmaller;
	PriorityQueue<Integer> heapGreaterTop;

	IComparator<Integer> compParentGreater;
	IComparator<Integer> compParentSmaller;

	void init() {
		unsortedList = new ArrayList<Integer>(
				Arrays.asList(10, 60, 15, 30, 50, 40, 20, 80, 20, 50));
		downHeapedList = new ArrayList<Integer>(
				Arrays.asList(10, 20, 15, 30, 50, 40, 20, 80, 60, 50));
		upHeapedList = new ArrayList<Integer>(
				Arrays.asList(80, 60, 40, 50, 50, 15, 20, 10, 20, 30));
		worstCaseList = new ArrayList<Integer>(
				Arrays.asList(9, 8, 7, 6, 5, 4, 3, 2, 1));

		compParentGreater = new ParentLargerInteger();
		compParentSmaller = new ParentSmallerInteger();
		heapEmptyParentSmaller = new PriorityQueue<Integer>(compParentSmaller);
		heapPopulatedParentSmaller = new PriorityQueue<Integer>(unsortedList, compParentSmaller);
	}
	void testSwap(Tester t) {
		init();
		t.checkExpect(this.heapEmptyParentSmaller.heap.size(), 0);
		heapEmptyParentSmaller.heap.add(10);
		heapEmptyParentSmaller.heap.add(20);
		t.checkExpect(this.heapEmptyParentSmaller.heap.size(), 2);
		t.checkExpect(this.heapEmptyParentSmaller.heap.get(0), 10);
		t.checkExpect(this.heapEmptyParentSmaller.heap.get(1), 20);
		heapEmptyParentSmaller.swap(0, 1);
		t.checkExpect(this.heapEmptyParentSmaller.heap.size(), 2);
		t.checkExpect(this.heapEmptyParentSmaller.heap.get(0), 20);
		t.checkExpect(this.heapEmptyParentSmaller.heap.get(1), 10);
	}
	void testUpHeap(Tester t) {
		init();
		t.checkExpect(heapEmptyParentSmaller.heap.size(), 0);
		heapEmptyParentSmaller.heap.add(20);
		heapEmptyParentSmaller.upHeap(0);
		t.checkExpect(heapEmptyParentSmaller.heap.size(), 1);
		t.checkExpect(heapEmptyParentSmaller.heap.get(0), 20);
		heapEmptyParentSmaller.heap.add(10);
		heapEmptyParentSmaller.upHeap(1);
		t.checkExpect(heapEmptyParentSmaller.heap.size(), 2);
		t.checkExpect(heapEmptyParentSmaller.heap.get(0), 10);
		t.checkExpect(heapEmptyParentSmaller.heap.get(1), 20);
		heapEmptyParentSmaller.heap.add(30);
		heapEmptyParentSmaller.upHeap(2);
		t.checkExpect(heapEmptyParentSmaller.heap.size(), 3);
		t.checkExpect(heapEmptyParentSmaller.heap.get(0), 10);
		t.checkExpect(heapEmptyParentSmaller.heap.get(1), 20);
		t.checkExpect(heapEmptyParentSmaller.heap.get(2), 30);
		heapEmptyParentSmaller.heap.add(20);
		heapEmptyParentSmaller.upHeap(3);
		t.checkExpect(heapEmptyParentSmaller.heap.size(), 4);
		t.checkExpect(heapEmptyParentSmaller.heap.get(0), 10);
		t.checkExpect(heapEmptyParentSmaller.heap.get(1), 20);
		t.checkExpect(heapEmptyParentSmaller.heap.get(2), 30);
		t.checkExpect(heapEmptyParentSmaller.heap.get(3), 20);
		heapEmptyParentSmaller.heap.add(20);
		heapEmptyParentSmaller.upHeap(4);
		heapEmptyParentSmaller.heap.add(20);
		heapEmptyParentSmaller.upHeap(5);
		t.checkExpect(heapEmptyParentSmaller.heap.size(), 6);
		t.checkExpect(heapEmptyParentSmaller.heap.get(0), 10);
		t.checkExpect(heapEmptyParentSmaller.heap.get(1), 20);
		t.checkExpect(heapEmptyParentSmaller.heap.get(2), 20);
		t.checkExpect(heapEmptyParentSmaller.heap.get(3), 20);
		t.checkExpect(heapEmptyParentSmaller.heap.get(4), 20);
		t.checkExpect(heapEmptyParentSmaller.heap.get(5), 30);
	}
	void testDownHeap(Tester t) {
		init();
		t.checkExpect(this.heapEmptyParentSmaller.heap.size(), 0);
		heapEmptyParentSmaller.heap.add(0, 50);
		heapEmptyParentSmaller.downHeap(0);
		t.checkExpect(this.heapEmptyParentSmaller.heap.size(), 1);
		t.checkExpect(this.heapEmptyParentSmaller.heap.get(0), 50);
		heapEmptyParentSmaller.heap.add(0, 40);
		heapEmptyParentSmaller.downHeap(0);
		t.checkExpect(this.heapEmptyParentSmaller.heap.get(0), 40);
		t.checkExpect(this.heapEmptyParentSmaller.heap.get(1), 50);
		heapEmptyParentSmaller.heap.add(0, 60);
		heapEmptyParentSmaller.downHeap(0);
		t.checkExpect(this.heapEmptyParentSmaller.heap.get(0), 40);
		t.checkExpect(this.heapEmptyParentSmaller.heap.get(1), 60);
		t.checkExpect(this.heapEmptyParentSmaller.heap.get(2), 50);
		heapEmptyParentSmaller.heap.add(0, 30);
		heapEmptyParentSmaller.downHeap(0);
		t.checkExpect(this.heapEmptyParentSmaller.heap.get(0), 30);
		t.checkExpect(this.heapEmptyParentSmaller.heap.get(1), 40);
		t.checkExpect(this.heapEmptyParentSmaller.heap.get(2), 60);
		t.checkExpect(this.heapEmptyParentSmaller.heap.get(3), 50);
		heapEmptyParentSmaller.heap.add(0, 70);
		heapEmptyParentSmaller.downHeap(0);
		t.checkExpect(this.heapEmptyParentSmaller.heap.get(0), 30);
		t.checkExpect(this.heapEmptyParentSmaller.heap.get(1), 50);
		t.checkExpect(this.heapEmptyParentSmaller.heap.get(2), 40);
		t.checkExpect(this.heapEmptyParentSmaller.heap.get(3), 60);
		t.checkExpect(this.heapEmptyParentSmaller.heap.get(4), 70);
	}
	void testBuildDownHeap(Tester t) {
		init();
		heapEmptyParentSmaller.buildHeap(unsortedList);
		t.checkExpect(heapEmptyParentSmaller.heap, downHeapedList);
		ArrayList<Integer> worstDownList = new ArrayList<Integer>(Arrays.asList(
				1, 2, 3, 6, 5, 4, 7, 8, 9));
		init();
		heapEmptyParentSmaller.buildHeap(worstCaseList);
		t.checkExpect(this.heapEmptyParentSmaller.heap, worstDownList);
	}
	void testAdd(Tester t) {
		init();
		ArrayList<Integer> addedList_90 = new ArrayList<Integer>(
				Arrays.asList(10, 20, 15, 30, 50, 40, 20, 80, 60, 50, 90));
		ArrayList<Integer> addedList_9005 = new ArrayList<Integer>(
				Arrays.asList(5, 20, 10, 30, 50, 15, 20, 80, 60, 50, 90, 40));
		this.heapPopulatedParentSmaller.add(90);
		t.checkExpect(this.heapPopulatedParentSmaller.heap, addedList_90);
		this.heapPopulatedParentSmaller.add(5);
		t.checkExpect(this.heapPopulatedParentSmaller.heap, addedList_9005);
	}
	void testRemove(Tester t) {
		init();
		t.checkExpect(this.heapPopulatedParentSmaller.remove(), 10);
		ArrayList<Integer> removedList = new ArrayList<Integer>(
				Arrays.asList(15, 20, 20, 30, 50, 40, 50, 80, 60));
		t.checkExpect(this.heapPopulatedParentSmaller.heap, removedList);
	}
}
