package InsertionSort;

public class InsertionSort {
	public int[] sort(int[] arr) {
		for (int limiter=1; limiter < arr.length; limiter++) {
			int i = 1;
			int j = limiter;
			int border = arr[limiter];
			while (limiter>=i && border < arr[limiter-i]) {
				arr[j] = arr[limiter-i];
				i++;
				j--;
			}
			arr[j] = border;
		}
		
		return arr;
	}
}