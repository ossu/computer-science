package InsertionSort;

import java.util.Arrays;

public class Main {


	//Selection sort is the algorithm to sort one spot at a time
	//Comb the entire array, select the smallest and place it at the beginning
	public static void main(String[] args) {
		int[] toSort = new int[] {4, 5, 9, 3, 54, 2, 34, 2134, 2, 443, 23, 12, 4, 663, 123, 42};
		InsertionSort sorter = new InsertionSort();
		int[] sorted = sorter.sort(toSort);
		System.out.println(Arrays.toString(sorted));
	}
}
