package SelectionSort;

import java.util.Arrays;

//Selection sort is the algorithm to sort one spot at a time
//Comb the entire array, select the smallest and place it at the beginning
public class Main
{
	public static void main(String[] args) {
		int[] toSort = new int[] {1, 5, 9, 3, 54, 2, 34, 2134, 2, 443, 23, 12, 4, 663, 123, 42};
		SelectionSort sorter = new SelectionSort();
		int[] sorted = sorter.sort(toSort);
		System.out.println(Arrays.toString(sorted));
	}
}