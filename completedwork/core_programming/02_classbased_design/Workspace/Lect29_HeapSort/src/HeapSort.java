import java.util.ArrayList;

class Heap {
	ArrayList<Integer> heap;
	Heap(ArrayList<Integer> heap) {
		buildHeap_upHeap(heap);
	}
	Heap() {
		this.heap = new ArrayList<Integer>();
	}
	// transforms the given ArrayList into a heap tree ordered ArrayList
	void buildHeap_downHeap(ArrayList<Integer> list) {
		this.heap = list;
		for (int i = (list.size() - 1) / 2; i >= 0; i--) {
			this.downHeap(i);
		}
	}
	// transforms the given ArrayList into a heap tree ordered ArrayList
	void buildHeap_upHeap(ArrayList<Integer> list) {
		this.heap = new ArrayList<Integer>();
		for (int i = 0; i < list.size(); i++) {
			this.heap.add(list.get(i));
			this.upHeap(i);
		}
	}

	// compares child with parent and swaps if parent is smaller
	void upHeap(int childIndex) {
		int parentIndex = (childIndex - 1) / 2;
		if (heap.get(parentIndex) < heap.get(childIndex)) {
			this.swap(parentIndex, childIndex);
			upHeap(parentIndex);
		}
	}
	// recursively compares parent with children and swaps if child is bigger
	void downHeap(int parentIndex) {
		this.downHeapMaxDepth(parentIndex, this.heap.size());
	}
	void downHeapMaxDepth(int parentIndex, int maxDepthIndex) {
		int leftChildIndex = 2 * parentIndex + 1;
		int rightChildIndex = 2 * parentIndex + 2;
		if (rightChildIndex < maxDepthIndex) {
			if (heap.get(leftChildIndex) > heap.get(rightChildIndex)) {
				if (heap.get(leftChildIndex) > heap.get(parentIndex)) {
					// swap with left child
					this.swap(parentIndex, leftChildIndex);
					this.downHeapMaxDepth(leftChildIndex, maxDepthIndex);
				}
			} else if (heap.get(rightChildIndex) > heap.get(parentIndex)) {
				// swap with right child
				this.swap(parentIndex, rightChildIndex);
				this.downHeapMaxDepth(rightChildIndex, maxDepthIndex);
			}
		} else if (leftChildIndex < maxDepthIndex) {
			if (heap.get(leftChildIndex) > heap.get(parentIndex)) {
				// swap with left child
				this.swap(parentIndex, leftChildIndex);
				this.downHeapMaxDepth(leftChildIndex, maxDepthIndex);
			}
		}
	}

	void downHeapMaxDepth_broken(int parentIndex, int maxDepthIndex) {
		int leftChildIndex = 2 * parentIndex + 1;
		int rightChildIndex = 2 * parentIndex + 2;
		if (rightChildIndex < maxDepthIndex &&
				heap.get(leftChildIndex) > heap.get(rightChildIndex)) {
			if (heap.get(leftChildIndex) > heap.get(parentIndex)) {
				this.swap(parentIndex, leftChildIndex);
				this.downHeapMaxDepth_broken(leftChildIndex, maxDepthIndex);
			} else if (heap.get(rightChildIndex) > heap.get(parentIndex)) {
				this.swap(parentIndex, rightChildIndex);
				this.downHeapMaxDepth_broken(rightChildIndex, maxDepthIndex);
			}
		}
	}
	// swaps the parent and child items
	void swap(int parentIndex, int childIndex) {
		int temp = heap.get(parentIndex);
		heap.set(parentIndex, heap.get(childIndex));
		heap.set(childIndex, temp);
	}

	// adds item to last open space and upHeaps until correctly sorted
	void add(int item) {
		this.heap.add(item);
		this.upHeap(this.heap.size() - 1);
	}
	// swaps first item with last, saves last, re-sorts heap, returns saved item
	int removeMax() {
		int lastIndex = this.heap.size() - 1;
		this.swap(0, lastIndex);
		int response = this.heap.remove(lastIndex);
		this.downHeap(0);
		return response;
	}
	// returns a fully reverse sorted ArrayList of heap
	ArrayList<Integer> sort() {
		int heapSize = this.heap.size();
		for (int i = 0; i < heapSize; i++) {
			int lastSorted = (heapSize - 1) - i;
			// moves highest priority to lastSorted slot
			this.swap(0, lastSorted);
			// down heaps new top of tree stopping before getting to sorted list
			this.downHeapMaxDepth(0, lastSorted);
		}
		return this.heap;
	}
}
