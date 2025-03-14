package karatsuba;

public class KaratsubaImplementation<T> implements Karatsuba {
	
	public KaratsubaImplementation() {
		
	}
	
	public KaratsubaImplementation(T x, T y) {
		// byte, double, float, int, long, and short
		// accept any number type, converts to int, makes manipulations and then return same number type
		// convert it all to long
		// if nSum is 2, convert to byte and make operations there
		// if nSum is 3 to 4 convert to short
		// if nSum is 5 to 9 convert to int
		// if nSum is 10 to 18 or 20 (check to see max value)
		// convert to bigInteger or bigdecimal if that is the case
		
		//check how to do it if its a decimal too
			
		// versioning and packaging with maven
		// integrated tests with maven
		// testing
		// logging
		// auto documentation
		
		
		// return type will be always object but with another type inside
	}
	
	@Override
	public int multiply(int x, int y) {
		int result;
		if (x < 10 && y < 10) {
			result = x*y;
			return result;
		}
		String[] xyStr = intNormalizer(x, y);
		int n = xyStr[0].length();
		int[] abcd = intFragmenter(xyStr, n);
		int a = abcd[0];
		int b = abcd[1];
		int c = abcd[2];
		int d = abcd[3];
		
		int ac = multiply(a, c);
		int bd = multiply(b, d);
		int ADPlusBC = multiply(a+b,c+d) - ac - bd;
		int tenFactorOne = (int) Math.pow(10, n);
		int tenFactorTwo = (int) Math.pow(10, n/2);
		int firstFactor = tenFactorOne*ac;
		int secondFactor = tenFactorTwo*ADPlusBC;
				// 10^nAC + 10^(n/2)(AD + BC) + BD
		result = firstFactor + secondFactor + bd;
		return result;
	}
	
	// this method returns an array that contains abcd for the karatsuba formula
	@Override
	public int[] intFragmenter(String[] xyStr, int n) {
		int strHalfPoint = n/2;
		if (strHalfPoint == 1) {
			int a = Integer.parseInt(String.valueOf(xyStr[0].charAt(0)));
			int b = Integer.parseInt(String.valueOf(xyStr[0].charAt(1)));
			int c = Integer.parseInt(String.valueOf(xyStr[1].charAt(0)));
			int d = Integer.parseInt(String.valueOf(xyStr[1].charAt(1)));
			return new int[] {a, b, c, d};
		} 
		int a = Integer.parseInt(xyStr[0].substring(0, strHalfPoint));
		int b = Integer.parseInt(xyStr[0].substring(strHalfPoint));
		int c = Integer.parseInt(xyStr[1].substring(0, strHalfPoint));
		int d = Integer.parseInt(xyStr[1].substring(strHalfPoint));
		return new int[] {a, b, c, d};
	}
	
	// This method exists to add left zeroes to the strings to make their size even and equal
	@Override
	public String[] intNormalizer(int x, int y) {
		String xStr =  Integer.toString(x);
		String yStr =  Integer.toString(y);
		int xSize = xStr.length();
		int ySize = yStr.length();
		
		while (!isEven(xSize) || !isEven(ySize) || xSize != ySize) {
			if (!isEven(xSize) || (xSize < ySize)) {
				xStr = addLeftZero(xStr);
				xSize = xStr.length();
			} 
			if (!isEven(ySize) || (ySize < xSize)) {
				yStr = addLeftZero(yStr);
				ySize = yStr.length();
			}
		}
		return new String[] {xStr, yStr};
	}
	
	public String addLeftZero(String str) {
		return "0" + str;
	}
	public boolean isEven(int numb) {
		if (numb%2 != 0) {
			return false;
		}
		return true;
	}
}
