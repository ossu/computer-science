package SelectionSort;

public class SelectionSort {
	public int[] sort(int[] arr) {
	     for (int i=0; i<arr.length; i++) {
	         for (int j = i+1; j<arr.length;j++) {
	             boolean isCurrentSmallerThanSpot = arr[j] < arr[i];
	             if (isCurrentSmallerThanSpot) {
	                 arr = swapPlaces(arr, i, j);
	             }
	         }
	     }
	     return arr;
	 }
	 private int[] swapPlaces(int[] arr, int i, int j) {
	     int temp = arr[i];
	     arr[i] = arr[j];
	     arr[j] = temp;
	     return arr;
	 }
}
