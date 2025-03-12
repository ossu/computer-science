package karatsuba;

public interface Karatsuba {

	// 
	public int multiply(int x, int y);
	
	// This method exists to add left zeroes to the strings to make their size even and equal. n must be even.
	String[] intNormalizer(int x, int y);
	
	// this method returns an array that contains abcd for the karatsuba formula
	int[] intFragmenter(String[] xyStr, int n);
}
