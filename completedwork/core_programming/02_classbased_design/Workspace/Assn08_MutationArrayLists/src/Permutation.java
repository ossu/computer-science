import java.util.*;

interface IPred<T> {
	boolean apply(T t);
}

class MatchToData<T> implements IPred<T> {
	T match;
	MatchToData(T match) {
		this.match = match;
	}
	public boolean apply(T t) {
		return t.equals(match);
	}
}

class ArrayUtils {
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
}

/**
 * A class that defines a new permutation code, as well as methods for encoding
 * and decoding of the messages that use this code.
 */
class PermutationCode {
	// The original list of characters to be encoded
	ArrayList<Character> alphabet = new ArrayList<Character>(Arrays.asList(
			'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
			'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
			't', 'u', 'v', 'w', 'x', 'y', 'z'));

	ArrayList<Character> code = new ArrayList<Character>(26);

	// A random number generator
	Random rand = new Random();

	// Create a new instance of the encoder/decoder with a new permutation code 
	PermutationCode() {
		this.code = this.initEncoder(rand);
	}
	PermutationCode(int seed) {
		this.code = this.initEncoder(new Random(seed));
	}
	// Create a new instance of the encoder/decoder with the given code 
	PermutationCode(ArrayList<Character> code) {
		this.code = code;
	}

	// Initialize the encoding permutation of the characters
	ArrayList<Character> initEncoder(Random rand) {
		ArrayList<Character> tempArray = new ArrayList<Character>(this.alphabet);
		ArrayList<Character> resultArray = new ArrayList<Character>();
		int size = tempArray.size();
		for (int i = 0; i < size; i++) {
			resultArray.add(tempArray.remove(rand.nextInt(size - i)));
		}
		return resultArray;
	}
	// produce an encoded String from the given String
	String encode(String source) {
		String r = "";
		ArrayUtils u = new ArrayUtils();
		for (int i = 0; i < source.length(); i++) {
			if (source.charAt(i) == ' ') {
				r = r + ' ';
			} else {
				int alphabetIdx = u.find(this.alphabet, new MatchToData<Character>(source.charAt(i)));
				if (alphabetIdx < 0) {
					throw new RuntimeException(
							"Could not encode given character: " + source.charAt(i));
				}
				r = r + code.get(alphabetIdx);
			}
		}
		return r;
	}
	// produce a decoded String from the given String
	String decode(String source) {
		String r = "";
		ArrayUtils u = new ArrayUtils();
		for (int i = 0; i < source.length(); i++) {
			if (source.charAt(i) == ' ') {
				r = r + ' ';
			} else {
				int alphabetIdx = u.find(this.code, new MatchToData<Character>(source.charAt(i)));
				if (alphabetIdx < 0) {
					throw new RuntimeException("Could not decode given character: " + source.charAt(i));
				}
				r = r + alphabet.get(alphabetIdx);
			}
		}
		return r;
	}
}
