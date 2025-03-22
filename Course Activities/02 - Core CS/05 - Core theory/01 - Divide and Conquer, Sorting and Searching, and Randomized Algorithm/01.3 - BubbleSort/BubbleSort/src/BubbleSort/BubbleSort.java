package BubbleSort;

public class BubbleSort {

	public int[] sort(int[] arr) {
		int swaps, j;
		int stopper = arr.length;
		boolean unsorted = true;
		while (unsorted) {
			swaps = 0;
			int key = arr[0];
			for (j = 0; j < stopper-1; j++) {
				if (key > arr[j+1]) {
					arr[j] = arr[j+1];
					arr[j+1] = key;
					swaps++;
					continue;
				}
				key = arr[j+1];
			}
			stopper = j;
			unsorted = swaps > 0;
		}
		return arr;
	}

}
