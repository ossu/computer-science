package InsertionSort;

public class InsertionSort {
	public int[] sort(int[] arr) {
		for (int limiter=1; limiter < arr.length; limiter++) {
			int i = 1;
			int j = limiter;
			int border = arr[limiter];
			while (limiter>=i && border < arr[limiter-i]) {
				arr[j] = arr[j-1];
				arr[j-1] = border;
				i++;
				j--;
			}
		}
		
		return arr;
	}
}
