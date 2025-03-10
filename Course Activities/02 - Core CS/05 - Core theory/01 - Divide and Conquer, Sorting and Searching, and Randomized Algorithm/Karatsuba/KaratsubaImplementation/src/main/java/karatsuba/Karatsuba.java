package karatsuba;

public class Karatsuba {
	
	public int multiply(int x, int y) {
		int xSize = Integer.toString(x).length();
		int ySize = Integer.toString(y).length();
		int n = xSize;

		if (ySize > xSize) {
			n = ySize;
		}
		if (xSize==1 && ySize==1)  {
			return x*y;
		}
		int a;
		int b;
		int c;
		int d;
		int[] splitX = new int[2];
		int[] splitY = new int[2];
		if (xSize > 1) {
			splitX = intFragmenter(x);
			a = splitX[0];
			b = splitX[1];
		} else {
			a = 0;
			b = x;
		}
		if (ySize > 1) {
			splitY = intFragmenter(y);
			c = splitY[0];
			d = splitY[1];
		} else {
			c = 0;
			d = y;
		}
		
		
		int ac = multiply(a, c);
		int bd = multiply(b, d);
		int ADPlusBC = multiply(a+b,c+d) - ac - bd;
		int tenFactorOne = (int) Math.pow(10, n);
		int tenFactorTwo = (int) Math.pow(10, n/2);
		int firstFactor = tenFactorOne*ac;
		int secondFactor = tenFactorTwo*ADPlusBC;
				// 10^nAC + 10^(n/2)(AD + BC) + BD
		return firstFactor + secondFactor + bd;
	}
	
	public int[] intFragmenter(int numb) {
		String numbStr = Integer.toString(numb);
		int numbSize = numbStr.length();
		boolean sizeIsEven = this.isEven(numbSize);
		int strHalfPoint = numbSize/2;
		
		if (numbSize==1) {
			return null;
		}
		if (!sizeIsEven) {
			strHalfPoint = (numbSize-1)/2;
		}
		
		return this.parseStringIntoTwoIntArray(numbStr, strHalfPoint);
		
	}
	
	public int intFragmenter(int x, int y) {
		String xStr =  Integer.toString(x);
		String yStr =  Integer.toString(y);
		
		
		return 0;
	}
	
	public String[] stringNormalizer(String xStr, String yStr) {
		// This method exists to add left zeroes to the strings to make their size even and equal
		int xSize = xStr.length();
		int ySize = yStr.length();
		
		while (!isEven(xSize) || !isEven(ySize) || xSize != ySize) {
			if (!isEven(xSize) || (xSize > ySize)) {
				xStr = addLeftZero(xStr);
			} 
			if (!isEven(ySize) || (ySize > xSize)) {
				yStr = addLeftZero(yStr);
			}
			xSize = xStr.length();
			ySize = yStr.length();
		}
		return new String[] {xStr, yStr};
	}
	
	public String addLeftZero(String str) {
		return "";
	}
	
	public boolean isEven(int numb) {
		if (numb/2==0) {
			return false;
		}
		return true;
	}
	
	public int[] parseStringIntoTwoIntArray(String str, int strHalfPoint) {
		int a;
		int b;
		if (strHalfPoint == 1) {
			a = Integer.parseInt(String.valueOf(str.charAt(0)));
			b = Integer.parseInt(String.valueOf(str.charAt(1)));
		} else {
			a = Integer.parseInt(str.substring(0, strHalfPoint-1));
			b = Integer.parseInt(str.substring(strHalfPoint));
		}
		return new int[] {a, b};
	}
}
