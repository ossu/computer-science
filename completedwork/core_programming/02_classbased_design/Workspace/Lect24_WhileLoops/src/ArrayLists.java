import tester.Tester;
import java.util.ArrayList;
import java.util.Arrays;

//Represents functions of signature A -> R, for some argument type A and
//result type R
interface IFunc<A, R> {
	R apply(A input);
}

interface IFunc2<A1, A2, R> {
	R apply(A1 arg1, A2 arg2);
}

interface IPred<T> {
	boolean apply(T t);
}

interface IComparator<T> {
	int compare(T t1, T t2);
}

class DoubleInteger implements IFunc<Integer, Integer> {
	public Integer apply(Integer input) {
		return input * 2;
	}
}

class IntToString implements IFunc<Integer, String> {
	public String apply(Integer input) {
		return input.toString();
	}
}

class SumIntegers implements IFunc2<Integer, Integer, Integer> {
	public Integer apply(Integer arg1, Integer arg2) {
		return arg1 + arg2;
	}
}

class AppendIntToString implements IFunc2<Integer, String, String> {
	public String apply(Integer arg1, String arg2) {
		return arg2 + arg1.toString();
	}
}

class MatchString implements IPred<String> {
	String find;
	MatchString(String find) {
		this.find = find;
	}
	public boolean apply(String s) {
		return this.find.compareTo(s) == 0;
	}
}

class CompareStrings implements IComparator<String> {
	public int compare(String s1, String s2) {
		return s1.compareTo(s2);
	}
}

class CompareInts implements IComparator<Integer> {
	public int compare(Integer i1, Integer i2) {
		return i1 - i2;
	}
}

class ArrayUtils {
	// EFFECT: Exchanges the values at the given two indices in the given array
	<T> void swap(ArrayList<T> arr, int index1, int index2) {
		T oldValueAtIndex1 = arr.get(index1);
		T oldValueAtIndex2 = arr.get(index2);

		arr.set(index2, oldValueAtIndex1);
		arr.set(index1, oldValueAtIndex2);
	}
	<T, U> ArrayList<U> map(ArrayList<T> arr, IFunc<T, U> func) {
		ArrayList<U> result = new ArrayList<U>();
		for (T t : arr) {
			result.add(func.apply(t));
		}
		return result;
	}
	<T, U> U foldr(ArrayList<T> arr, IFunc2<T, U, U> func, U base) {
		U result = base;
		for (T t : arr) {
			result = func.apply(t, result);
		}
		return result;
	}
	<T, U> U foldl(ArrayList<T> arr, IFunc2<T, U, U> func, U base) {
		return this.foldlHelp(arr, func, base, arr.size() - 1);
	}
	<T, U> U foldlHelp(ArrayList<T> source, IFunc2<T, U, U> func, U base, int curIdx) {
		U result = base;
		if (curIdx < 0) {
			return result;
		} else {
			result = func.apply(source.get(curIdx), result);
			return this.foldlHelp(source, func, result, curIdx - 1);
		}
	}
	<T, U> U foldl_Loop(ArrayList<T> arr, IFunc2<T, U, U> func, U base) {
		ArrayList<T> reversedList = this.reverseList(arr);
		return this.foldr(reversedList, func, base);
	}
	<T> ArrayList<T> reverseList(ArrayList<T> arr) {
		ArrayList<T> result = new ArrayList<T>();
		for (T t : arr) {
			result.add(0, t);
		}
		return result;
	}
	//Returns the index of the first item passing the predicate,
	//or -1 if no such item was found
	<T> int find(ArrayList<T> arr, IPred<T> whichOne) {
		return this.findHelp(arr, whichOne, 0);
	}
	// Returns the index of the first item passing the predicate at or after the
	//given index, or -1 if no such such item was found
	<T> int findHelp(ArrayList<T> arr, IPred<T> whichOne, int index) {
		if (index >= arr.size()) {
			return -1;
		} else if (whichOne.apply(arr.get(index))) {
			return index;
		} else {
			return findHelp(arr, whichOne, index + 1);
		}
	}
	// Returns the index of the target string in the given ArrayList, or -1 if the string is not found
	//Assumes that the given ArrayList is sorted aphabetically
	int binarySearch_v1(ArrayList<String> strings, String target) {
		return binarySearchHelp_v1(strings, target, 0, strings.size() - 1);
	}
	int binarySearchHelp_v1(ArrayList<String> strings, String target, int lowIdx, int highIdx) {
		int midIdx = (lowIdx + highIdx) / 2;
		if (lowIdx > highIdx) {
			return -1;                                                           // not found
		} else if (target.compareTo(strings.get(midIdx)) == 0) {
			return midIdx;                                                       // found it!
		} else if (target.compareTo(strings.get(midIdx)) > 0) {
			return this.binarySearchHelp_v1(strings, target, midIdx + 1, highIdx); // too low
		} else {
			return this.binarySearchHelp_v1(strings, target, lowIdx, midIdx - 1); // too high
		}
	}
	//Returns the index of the target string in the given ArrayList, or -1 if the string is not found
	//Assumes that the given ArrayList is sorted aphabetically
	int binarySearch_v2(ArrayList<String> strings, String target) {
		return this.binarySearchHelp_v2(strings, target, 0, strings.size());
	}
	//Returns the index of the target string in the given ArrayList, or -1 if the string is not found
	//Assumes that the given ArrayList is sorted aphabetically
	//Assumes that [lowIdx, highIdx) is a semi-open interval of indices
	int binarySearchHelp_v2(ArrayList<String> strings, String target, int lowIdx, int highIdx) {
		int midIdx = (lowIdx + highIdx) / 2;
		if (lowIdx >= highIdx) {
			return -1;                                                           // not found
		} else if (target.compareTo(strings.get(midIdx)) == 0) {
			return midIdx;                                                       // found it!
		} else if (target.compareTo(strings.get(midIdx)) > 0) {
			return this.binarySearchHelp_v2(strings, target, midIdx + 1, highIdx); // too low
		} else {
			return this.binarySearchHelp_v2(strings, target, lowIdx, midIdx);     // too high
		}
	}
	<T> int gen_binarySearch_v2(ArrayList<T> arr, T target, IComparator<T> comp) {
		return this.gen_binarySearchHelp_v2(arr, target, comp, 0, arr.size());
	}
	<T> int gen_binarySearchHelp_v2(ArrayList<T> arr, T target, IComparator<T> comp,
			int lowIdx, int highIdx) {
		int midIdx = (lowIdx + highIdx) / 2;
		if (lowIdx >= highIdx) {
			return -1;
		} else if (comp.compare(target, arr.get(midIdx)) == 0) {
			return midIdx;
		} else if (comp.compare(target, arr.get(midIdx)) > 0) {
			return this.gen_binarySearchHelp_v2(arr, target, comp, midIdx + 1, highIdx);
		} else {
			return this.gen_binarySearchHelp_v2(arr, target, comp, lowIdx, midIdx);
		}
	}
	// EFFECT: Sorts the given list of strings alphabetically
	void sort(ArrayList<String> arr) {
		for (int idx = 0; idx < arr.size(); idx = idx + 1) {
			int idxOfMinValue = this.findMinValue(arr, idx, idx + 1);
			this.swap(arr, idx, idxOfMinValue);
		}
	}
	// new while loop version
	int findMinValue(ArrayList<String> arr, int minValIdx, int currIdx) {
		while (currIdx < arr.size()) {
			if (arr.get(minValIdx).compareTo(arr.get(currIdx)) < 0) {
				return this.findMinValue(arr, minValIdx, currIdx + 1);
			} else {
				return this.findMinValue(arr, currIdx, currIdx + 1);
			}
		}
		return minValIdx;
	}
	// old for loop version
	int findMinValue_old(ArrayList<String> arr, int minValIdx, int currIdx) {
		if (currIdx >= arr.size()) {
			return minValIdx;
		} else if (arr.get(minValIdx).compareTo(arr.get(currIdx)) < 0) {
			return this.findMinValue(arr, minValIdx, currIdx + 1);
		} else {
			return this.findMinValue(arr, currIdx, currIdx + 1);
		}
	}
	// returns an ArrayList consisting of one item from arr1, then one from arr2, alternating until one list is expended
	// attaches rest of other list to the end;
	<T> ArrayList<T> interleave(ArrayList<T> arr1, ArrayList<T> arr2) {
		ArrayList<T> r = new ArrayList<T>();
		for (int idx = 0; idx < arr1.size() || idx < arr2.size(); idx++) {
			if (idx >= arr1.size()) {
				r.add(arr2.get(idx));
			} else if (idx >= arr2.size()) {
				r.add(arr1.get(idx));
			} else {
				r.add(arr1.get(idx));
				r.add(arr2.get(idx));
			}
		}
		return r;
	}
	// returns a new list containing the first, third, fifth ... items of the list, followed by the second, fourth, sixth ... items.
	<T> ArrayList<T> unshuffle(ArrayList<T> arr) {
		ArrayList<T> list1 = new ArrayList<T>();
		ArrayList<T> list2 = new ArrayList<T>();
		for (int idx = 0; idx < arr.size(); idx++) {
			if (idx % 2 == 0) {
				list1.add(arr.get(idx));
			} else {
				list2.add(arr.get(idx));
			}
		}
		list1.addAll(list2);
		return list1;
	}
	<U> ArrayList<U> buildList(int n, IFunc<Integer, U> func) {
		ArrayList<U> resultArray = new ArrayList<U>();
		for (int i = 0; i < n; i++) {
			resultArray.add(func.apply(i));
		}
		return resultArray;
	}
}

class ExampleArrayLists {
	ArrayUtils u;
	ArrayList<Integer> nums;
	ArrayList<String> strings;
	ArrayList<Integer> nums_unsorted;
	ArrayList<String> strings_unsorted;
	ArrayList<Integer> numsInterleave;
	ArrayList<String> stringsInterleave;

	void init() {
		u = new ArrayUtils();
		nums = new ArrayList<Integer>(Arrays.asList(1, 2, 3));
		nums_unsorted = new ArrayList<Integer>(Arrays.asList(2, 3, 1));

		strings = new ArrayList<String>(
				Arrays.asList("apple", "banana", "cherry", "date",
						"fig", "grape", "honeydew", "kiwi", "watermelon"));
		strings_unsorted = new ArrayList<String>(
				Arrays.asList("cherry", "apple", "watermelon", "banana",
						"fig", "date", "honeydew", "kiwi", "grape"));

		numsInterleave = new ArrayList<Integer>(Arrays.asList(1, 4, 2, 5, 3, 6));
		stringsInterleave = new ArrayList<String>(
				Arrays.asList("apple", "artichoke", "banana", "broccoli", "cherry", "carrot", "date",
						"fig", "grape", "honeydew", "kiwi", "watermelon"));
	}
	void testAdd(Tester t) {
		ArrayList<String> someStrings = new ArrayList<String>();
		someStrings.add("First string");
		someStrings.add("Second string");
		t.checkExpect(someStrings.get(0), "First string");
		t.checkExpect(someStrings.get(1), "Second string");

		// Insert this item at index 1, and move everything else back
		someStrings.add(1, "Squeezed in");
		t.checkExpect(someStrings.get(0), "First string");
		t.checkExpect(someStrings.get(1), "Squeezed in");
		t.checkExpect(someStrings.get(2), "Second string");
	}
	void testSwap(Tester t) {
		ArrayList<String> someStrings = new ArrayList<String>();
		someStrings.add("Second string");
		someStrings.add("First string");

		ArrayUtils u = new ArrayUtils();
		u.swap(someStrings, 0, 1);

		t.checkExpect(someStrings.get(0), "First string");
		t.checkExpect(someStrings.get(1), "Second string");
	}
	void testMap(Tester t) {
		init();
		t.checkExpect(this.u.map(this.nums, new DoubleInteger()),
				new ArrayList<Integer>(Arrays.asList(2, 4, 6)));
	}
	void testFold(Tester t) {
		init();
		t.checkExpect(u.foldr(nums, new SumIntegers(), 0), 6);
		t.checkExpect(u.foldr(nums, new AppendIntToString(), ""), "123");
		t.checkExpect(u.foldl(nums, new AppendIntToString(), ""), "321");
		t.checkExpect(u.foldl_Loop(nums, new AppendIntToString(), ""), "321");
	}
	void testFind(Tester t) {
		init();
		t.checkExpect(u.find(this.strings, new MatchString("apple")), 0);
		t.checkExpect(u.find(this.strings, new MatchString("cherry")), 2);
		t.checkExpect(u.find(this.strings, new MatchString("watermelon")), 8);
		t.checkExpect(u.find(this.strings, new MatchString("notFound")), -1);

		t.checkExpect(u.binarySearch_v1(strings, "apple"), 0);
		t.checkExpect(u.binarySearch_v1(this.strings, "cherry"), 2);
		t.checkExpect(u.binarySearch_v1(this.strings, "watermelon"), 8);
		t.checkExpect(u.binarySearch_v1(this.strings, "notFound"), -1);

		t.checkExpect(u.binarySearch_v2(strings, "apple"), 0);
		t.checkExpect(u.binarySearch_v2(this.strings, "cherry"), 2);
		t.checkExpect(u.binarySearch_v2(this.strings, "watermelon"), 8);
		t.checkExpect(u.binarySearch_v2(this.strings, "notFound"), -1);

		CompareStrings compStr = new CompareStrings();
		t.checkExpect(u.gen_binarySearch_v2(strings, "apple", compStr), 0);
		t.checkExpect(u.gen_binarySearch_v2(this.strings, "cherry", compStr), 2);
		t.checkExpect(u.gen_binarySearch_v2(this.strings, "watermelon", compStr), 8);
		t.checkExpect(u.gen_binarySearch_v2(this.strings, "notFound", compStr), -1);

		CompareInts compInt = new CompareInts();
		t.checkExpect(u.gen_binarySearch_v2(this.nums, 1, compInt), 0);
		t.checkExpect(u.gen_binarySearch_v2(this.nums, 2, compInt), 1);
		t.checkExpect(u.gen_binarySearch_v2(this.nums, 3, compInt), 2);
		t.checkExpect(u.gen_binarySearch_v2(this.nums, 0, compInt), -1);
	}
	void testSort(Tester t) {
		init();
		t.checkExpect(strings_unsorted.equals(strings), false);
		u.sort(strings_unsorted);
		t.checkExpect(strings_unsorted.equals(strings), true);
	}
	void testInterleave(Tester t) {
		ArrayList<Integer> moreNums = new ArrayList<Integer>(Arrays.asList(4, 5, 6));
		ArrayList<String> moreStrings = new ArrayList<String>(
				Arrays.asList("artichoke", "broccoli", "carrot"));
		init();
		t.checkExpect(u.interleave(nums, moreNums), numsInterleave);
		t.checkExpect(u.interleave(strings, moreStrings), stringsInterleave);
	}
	void testUnshuffle(Tester t) {
		init();
		ArrayList<Integer> numsUnshuffled = new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4, 5, 6));
		ArrayList<String> stringsUnshuffled = new ArrayList<String>(
				Arrays.asList("apple", "banana", "cherry", "date",
						"grape", "kiwi", "artichoke", "broccoli",
						"carrot", "fig", "honeydew", "watermelon"));

		t.checkExpect(u.unshuffle(numsInterleave), numsUnshuffled);
		t.checkExpect(u.unshuffle(stringsInterleave), stringsUnshuffled);
	}
	void testBuildList(Tester t) {
		init();
		t.checkExpect(u.buildList(5, new IntToString()),
				new ArrayList<String>(Arrays.asList("0", "1", "2", "3", "4")));
	}
}
