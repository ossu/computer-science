import java.util.ArrayList;
import java.util.Arrays;
import tester.Tester;

class ArrayUtils {
	<T> void swap(ArrayList<T> arr, int indexLo, int indexHi) {
		T temp = arr.get(indexHi);
		arr.set(indexHi, arr.get(indexLo));
		arr.set(indexLo, temp);
	}
	//EFFECT: Sorts the given ArrayList according to the given comparator
	<T> void quicksortCopying(ArrayList<T> arr, IComparator<T> comp) {
		// Create a temporary array
		ArrayList<T> temp = new ArrayList<T>();
		// Make sure the temporary array is exactly as big as the given array
		for (int i = 0; i < arr.size(); i = i + 1) {
			temp.add(arr.get(i));
		}
		quicksortCopyingHelp(arr, temp, comp, 0, arr.size());
	}
	//EFFECT: sorts the source array according to comp, in the range of indices [loIdx, hiIdx)
	<T> void quicksortCopyingHelp(ArrayList<T> source, ArrayList<T> temp, IComparator<T> comp,
			int loIdx, int hiIdx) {
		// Step 0: check for completion
		if (loIdx >= hiIdx) {
			return; // There are no items to sort
		}
		// Step 1: select pivot
		T pivot = source.get(loIdx);
		// Step 2: partition items to lower or upper portions of the temp list
		int pivotIdx = partitionCopying(source, temp, comp, loIdx, hiIdx, pivot);
		// Step 4: sort both halves of the list
		quicksortCopyingHelp(source, temp, comp, loIdx, pivotIdx);
		quicksortCopyingHelp(source, temp, comp, pivotIdx + 1, hiIdx);
	}
	//Returns the index where the pivot element ultimately ends up in the sorted source
	//EFFECT: Modifies the source and comp lists in the range [loIdx, hiIdx) such that
	//        all values to the left of the pivot are less than (or equal to) the pivot
	//        and all values to the right of the pivot are greater than it
	<T> int partitionCopying(ArrayList<T> source, ArrayList<T> temp, IComparator<T> comp,
			int loIdx, int hiIdx, T pivot) {
		int curLo = loIdx;
		int curHi = hiIdx - 1;
		// Notice we skip the loIdx index, because that's where the pivot was
		for (int i = loIdx + 1; i < hiIdx; i = i + 1) {
			if (comp.compare(source.get(i), pivot) <= 0) { // lower
				temp.set(curLo, source.get(i));
				curLo = curLo + 1; // advance the current lower index
			} else { // upper
				temp.set(curHi, source.get(i));
				curHi = curHi - 1; // advance the current upper index
			}
		}
		temp.set(curLo, pivot); // place the pivot in the remaining spot
		// Step 3: copy all items back into the source
		for (int i = loIdx; i < hiIdx; i = i + 1) {
			source.set(i, temp.get(i));
		}
		return curLo;
	}

	//EFFECT: Sorts the given ArrayList according to the given comparator
	<T> void quicksortPartition(ArrayList<T> arr, IComparator<T> comp) {
		quicksortHelp(arr, comp, 0, arr.size());
	}
	//EFFECT: sorts the source array according to comp, in the range of indices [loIdx, hiIdx)
	<T> void quicksortHelp(ArrayList<T> source, IComparator<T> comp,
			int loIdx, int hiIdx) {
		// Step 0: check for completion
		if (loIdx >= hiIdx) {
			return; // There are no items to sort
		}
		// Step 1: select pivot
		T pivot = source.get(loIdx);
		// Step 2: partition items to lower or upper portions of the temp list
		int pivotIdx = partition(source, comp, loIdx, hiIdx, pivot);
		// Step 3: sort both halves of the list
		quicksortHelp(source, comp, loIdx, pivotIdx);
		quicksortHelp(source, comp, pivotIdx + 1, hiIdx);
	}
	// Returns the index where the pivot element ultimately ends up in the sorted source
	//EFFECT: Modifies the source list in the range [loIdx, hiIdx) such that
	//all values to the left of the pivot are less than (or equal to) the pivot
	//and all values to the right of the pivot are greater than it
	<T> int partition(ArrayList<T> source, IComparator<T> comp,
			int loIdx, int hiIdx, T pivot) {
		int curLo = loIdx;
		int curHi = hiIdx - 1;
		while (curLo < curHi) {
			// Advance curLo until we find a too-big value (or overshoot the end of the list)
			while (curLo < hiIdx && comp.compare(source.get(curLo), pivot) <= 0) {
				curLo = curLo + 1;
			}
			// Advance curHi until we find a too-small value (or undershoot the start of the list)
			while (curHi >= loIdx && comp.compare(source.get(curHi), pivot) > 0) {
				curHi = curHi - 1;
			}
			if (curLo < curHi) {
				swap(source, curLo, curHi);
			}
		}
		swap(source, loIdx, curHi); // place the pivot in the remaining spot
		return curHi;
	}
	//EFFECT: Sorts the provided list according to the given comparator
	<T> void mergesort(ArrayList<T> arr, IComparator<T> comp) {
		// Create a temporary array
		ArrayList<T> temp = new ArrayList<T>();
		// Make sure the temporary array is exactly as big as the given array
		for (int i = 0; i < arr.size(); i = i + 1) {
			temp.add(arr.get(i));
		}
		mergesortHelp(arr, temp, comp, 0, arr.size());
	}
	//EFFECT: Sorts the provided list in the region [loIdx, hiIdx) according to the given comparator.
	//Modifies both lists in the range [loIdx, hiIdx)
	<T> void mergesortHelp(ArrayList<T> source, ArrayList<T> temp, IComparator<T> comp,
			int loIdx, int hiIdx) {
		// Step 0: stop when finished
		if (hiIdx - loIdx <= 1) {
			return; // nothing to sort
		}
		// Step 1: find the middle index
		int midIdx = (loIdx + hiIdx) / 2;
		// Step 2: recursively sort both halves
		mergesortHelp(source, temp, comp, loIdx, midIdx);
		mergesortHelp(source, temp, comp, midIdx, hiIdx);
		// Step 3: merge the two sorted halves
		merge(source, temp, comp, loIdx, midIdx, hiIdx);
	}
	//Merges the two sorted regions [loIdx, midIdx) and [midIdx, hiIdx) from source
	//into a single sorted region according to the given comparator
	//EFFECT: modifies the region [loIdx, hiIdx) in both source and temp
	<T> void merge(ArrayList<T> source, ArrayList<T> temp, IComparator<T> comp,
			int loIdx, int midIdx, int hiIdx) {
		int curLo = loIdx;   // where to start looking in the lower half-list
		int curHi = midIdx;  // where to start looking in the upper half-list
		int curCopy = loIdx; // where to start copying into the temp storage
		while (curLo < midIdx && curHi < hiIdx) {
			if (comp.compare(source.get(curLo), source.get(curHi)) <= 0) {
				// the value at curLo is smaller, so it comes first
				temp.set(curCopy, source.get(curLo));
				curLo = curLo + 1; // advance the lower index
			} else {
				// the value at curHi is smaller, so it comes first
				temp.set(curCopy, source.get(curHi));
				curHi = curHi + 1; // advance the upper index
			}
			curCopy = curCopy + 1; // advance the copying index
		}
		// copy everything that's left -- at most one of the two half-lists still has items in it
		while (curLo < midIdx) {
			temp.set(curCopy, source.get(curLo));
			curLo = curLo + 1;
			curCopy = curCopy + 1;
		}
		while (curHi < hiIdx) {
			temp.set(curCopy, source.get(curHi));
			curHi = curHi + 1;
			curCopy = curCopy + 1;
		}
		// copy everything back from temp into source
		for (int i = loIdx; i < hiIdx; i = i + 1) {
			source.set(i, temp.get(i));
		}
	}
}

class Examples {
	ArrayList<String> unsortedList;
	ArrayList<String> unmodifiedList;
	ArrayList<String> sortedList;
	void init() {
		unsortedList = new ArrayList<String>(
				Arrays.asList("grape", "cherry", "apple", "kiwi",
						"watermelon", "banana", "honeydew", "date", "fig"));
		unmodifiedList = new ArrayList<String>(
				Arrays.asList("grape", "cherry", "apple", "kiwi",
						"watermelon", "banana", "honeydew", "date", "fig"));
		sortedList = new ArrayList<String>(
				Arrays.asList("apple", "banana", "cherry", "date",
						"fig", "grape", "honeydew", "kiwi", "watermelon"));
	}
	void testQuickSort(Tester t) {
		init();
		ArrayUtils u = new ArrayUtils();

		t.checkExpect(this.unsortedList, this.unmodifiedList);
		u.quicksortCopying(this.unsortedList, new StringAlphabetical());
		t.checkExpect(this.unsortedList, this.sortedList);

		init();
		t.checkExpect(this.unsortedList, this.unmodifiedList);
		u.quicksortPartition(this.unsortedList, new StringAlphabetical());
		t.checkExpect(this.unsortedList, this.sortedList);

		init();
		t.checkExpect(this.unsortedList, this.unmodifiedList);
		u.mergesort(this.unsortedList, new StringAlphabetical());
		t.checkExpect(this.unsortedList, this.sortedList);
	}
}
