import java.util.ArrayList;
import java.util.Arrays;
import tester.Tester;

class Examples {
	ArrayList<Integer> unsortedList;
	ArrayList<Integer> upHeapedList;
	ArrayList<Integer> sortedList;
	ArrayList<Integer> worstCaseList;

	Heap heap0;
	Heap heap1;

	void init() {
		unsortedList = new ArrayList<Integer>(
				Arrays.asList(10, 60, 15, 30, 50, 40, 20, 80, 20, 50));
		upHeapedList = new ArrayList<Integer>(
				Arrays.asList(80, 60, 40, 50, 50, 15, 20, 10, 20, 30));
		worstCaseList = new ArrayList<Integer>(
				Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
		heap1 = new Heap(unsortedList);
	}
	void testSwap(Tester t) {
		heap0 = new Heap();
		t.checkExpect(this.heap0.heap.size(), 0);
		heap0.heap.add(10);
		heap0.heap.add(20);
		t.checkExpect(this.heap0.heap.size(), 2);
		t.checkExpect(this.heap0.heap.get(0), 10);
		t.checkExpect(this.heap0.heap.get(1), 20);
		heap0.swap(0, 1);
		t.checkExpect(this.heap0.heap.size(), 2);
		t.checkExpect(this.heap0.heap.get(0), 20);
		t.checkExpect(this.heap0.heap.get(1), 10);
	}
	void testUpHeap(Tester t) {
		heap0 = new Heap();
		t.checkExpect(heap0.heap.size(), 0);
		heap0.heap.add(20);
		heap0.upHeap(0);
		t.checkExpect(heap0.heap.size(), 1);
		t.checkExpect(heap0.heap.get(0), 20);
		heap0.heap.add(10);
		heap0.upHeap(1);
		t.checkExpect(heap0.heap.size(), 2);
		t.checkExpect(heap0.heap.get(0), 20);
		t.checkExpect(heap0.heap.get(1), 10);
		heap0.heap.add(30);
		heap0.upHeap(2);
		t.checkExpect(heap0.heap.size(), 3);
		t.checkExpect(heap0.heap.get(0), 30);
		t.checkExpect(heap0.heap.get(1), 10);
		t.checkExpect(heap0.heap.get(2), 20);
		heap0.heap.add(20);
		heap0.upHeap(3);
		t.checkExpect(heap0.heap.size(), 4);
		t.checkExpect(heap0.heap.get(0), 30);
		t.checkExpect(heap0.heap.get(1), 20);
		t.checkExpect(heap0.heap.get(2), 20);
		t.checkExpect(heap0.heap.get(3), 10);
	}
	void testBuildDownHeap(Tester t) {
		init();
		//                10
		//         60          15
		//   (30)      50    40  20
		// 80    20  50

		//                10
		//         60         (15)
		//    80       50    40  20
		// 30    20  50

		//                10
		//        (60)         40
		//    80       50    15  20
		// 30    20  50

		//               (10)
		//        80           40
		//    60       50    15  20
		// 30    20  50

		//                80
		//        60           40
		//    30       50    15  20
		// 10    20  50
		ArrayList<Integer> downHeapedList = new ArrayList<Integer>(Arrays.asList(
				80, 60, 40, 30, 50, 15, 20, 10, 20, 50));
		heap0 = new Heap();
		heap0.buildHeap_downHeap(unsortedList);
		t.checkExpect(heap0.heap, downHeapedList);
		//           1
		//       2       3
		//    (4)  5   6   7
		//    8 9 x x x x x x

		//           1
		//       2      (3)
		//     9   5   6   7
		//    8 4 x x x x x x

		//           1
		//      (2)      7 
		//     9   5   6   3
		//    8 4 x x x x x x

		//          (1)
		//       9       7 
		//     8   5   6   3
		//    2 4 x x x x x x

		//           9
		//       8       7 
		//     4   5   6   3
		//    2 1 x x x x x x
		ArrayList<Integer> worstDownList = new ArrayList<Integer>(Arrays.asList(
				9, 8, 7, 4, 5, 6, 3, 2, 1));
		init();
		heap0.buildHeap_downHeap(worstCaseList);
		t.checkExpect(this.heap0.heap, worstDownList);
	}
	void testBuildUpHeap(Tester t) {
		init();
		//   10
		//(60)(15)

		//     60
		//   10  15
		//(30)

		//    60
		//  30  15
		//10 (50)

		//       60
		//  50       15
		//10  30  (40)

		//         60
		//     50      40
		//   10  30  15  20
		//(80)

		//             80
		//       60         40
		//   50      30   15  20
		// 10  20 (50)

		//             80
		//       60         40
		//   50      50   15  20
		// 10  20  30
		heap0 = new Heap();
		heap0.buildHeap_upHeap(unsortedList);
		t.checkExpect(heap0.heap, upHeapedList);
	}
	void testConstructor(Tester t) {
		init();
		heap1 = new Heap(unsortedList);
		t.checkExpect(heap1.heap, upHeapedList);
	}
	void testDownHeap(Tester t) {
		init();
		t.checkExpect(heap1.heap.size(), 10);
		heap1.swap(0, 9);;
		heap1.downHeap(0);

		//             60
		//       50         40
		//   50      80   15  20
		// 10  20  30
		t.checkExpect(heap1.heap.size(), 10);
		t.checkExpect(heap1.heap.get(0), 60);
		t.checkExpect(heap1.heap.get(1), 50);
		t.checkExpect(heap1.heap.get(4), 80);
		t.checkExpect(heap1.heap.get(9), 30);
	}
	void testAdd(Tester t) {
		init();
		ArrayList<Integer> addedList = new ArrayList<Integer>(
				Arrays.asList(90, 80, 40, 50, 60, 15, 20, 10, 20, 30, 50));
		this.heap1.add(90);
		t.checkExpect(this.heap1.heap, addedList);
		addedList.add(10);
		this.heap1.add(10);
		t.checkExpect(this.heap1.heap, addedList);
	}
	void testRemoveMax(Tester t) {
		init();
		t.checkExpect(this.heap1.removeMax(), 80);
		ArrayList<Integer> removedList = new ArrayList<Integer>(
				Arrays.asList(60, 50, 40, 50, 30, 15, 20, 10, 20));
		t.checkExpect(this.heap1.heap, removedList);
	}
	void testSort(Tester t) {
		init();
		ArrayList<Integer> sortedList = new ArrayList<Integer>(
				Arrays.asList(10, 15, 20, 20, 30, 40, 50, 50, 60, 80));
		t.checkExpect(this.heap1.sort(), sortedList);
	}
}
