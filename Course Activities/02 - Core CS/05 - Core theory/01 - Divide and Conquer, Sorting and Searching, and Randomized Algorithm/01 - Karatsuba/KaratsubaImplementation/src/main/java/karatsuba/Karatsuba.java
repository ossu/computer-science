package karatsuba;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class will handle the karatsuba formula implementation
 * 
 * @author Reborn
 * @since 2025
 * @version 0.1.0-alpha-SNAPSHOT
 */
public class Karatsuba{
	private static final Logger logger 
    = LoggerFactory.getLogger(Karatsuba.class);
	
	private int x;
	private int y;
	private int a;
	private int b;
	private int c;
	private int d;
	private int ac;
	private int bd;
	private int ad_bc;
	private int first_factor;
	private int second_factor;
	public int result;
	
	/*
	what does this class do:
	-------------
	- transform values to strings
	- normalizes strings
	- gets abcd values
	-------------------
	- gets calculates: ac, (ad + bc), bd
	- multiplies value x by y by implementing karatsuba formula/algo
	*/
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getA() {
		return a;
	}
	public void setA(int a) {
		this.a = a;
	}
	public int getB() {
		return b;
	}
	public void setB(int b) {
		this.b = b;
	}
	public int getC() {
		return c;
	}
	public void setC(int c) {
		this.c = c;
	}
	public int getD() {
		return d;
	}
	public void setD(int d) {
		this.d = d;
	}
	public int getAc() {
		return ac;
	}
	public void setAc(int ac) {
		this.ac = ac;
	}
	public int getBd() {
		return bd;
	}
	public void setBd(int bd) {
		this.bd = bd;
	}
	public int getAd_bc() {
		return ad_bc;
	}
	public void setAd_bc(int ad_bc) {
		this.ad_bc = ad_bc;
	}
	public int getFirst_factor() {
		return first_factor;
	}
	public void setFirst_factor(int first_factor) {
		this.first_factor = first_factor;
	}
	public int getSecond_factor() {
		return second_factor;
	}
	public void setSecond_factor(int second_factor) {
		this.second_factor = second_factor;
	}
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	
	@Override
	public String toString() {
		return "Karatsuba [x=" + x + ", y=" + y + ", a=" + a + ", b=" + b + ", c=" + c + ", d=" + d + ", ac=" + ac
				+ ", bd=" + bd + ", ad_bc=" + ad_bc + ", first_factor=" + first_factor + ", second_factor="
				+ second_factor + ", result=" + result + "]";
	}
	public Karatsuba() {
		logger.info("Karatsuba object created.");
	}
	
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
	
	public Karatsuba(int x, int y) {
		this.x = x;
		this.y = y;
		this.result = this.multiply(x, y);
	}
	/**
	 * this method is the main method to multiply the two numbers it'll
	 * recursively call itself to separate all double digit and above
	 * into abcd and compute the karatsuba formula to return the result
	 * 
	 * @param x any integer to multiply
	 * @param y any integer to multiply
	 * @return will return the final integer result of the multiplication of x and y
	 *  
	 */

	public int multiply(int x, int y) {
		logger.info("Started multiplication process with values of {} and {}",x, y);
		int result;
		if (x < 10 && y < 10) {
			result = x*y;
			logger.info("Single digit multiplication ended with result of {}",result);
			return result;
		}
		String[] xyStr = intNormalizer(x, y);
		int n = xyStr[0].length();
		int[] abcd = intFragmenter(xyStr, n);
		int a = abcd[0];
		int b = abcd[1];
		int c = abcd[2];
		int d = abcd[3];
		logger.info("ABCD values determined {} {} {} {}",a,b,c,d);
		int ac = multiply(a, c);
		int bd = multiply(b, d);
		int ADPlusBC = multiply(a+b,c+d) - ac - bd;
		logger.info("ad + bc value determined as {}", ADPlusBC);
		int tenFactorOne = (int) Math.pow(10, n);
		int tenFactorTwo = (int) Math.pow(10, n/2);
		int firstFactor = tenFactorOne*ac;
		logger.info("First factor value detemirned as {}", firstFactor);
		int secondFactor = tenFactorTwo*ADPlusBC;
		logger.info("Second factor value determined as {}",secondFactor);
				// 10^nAC + 10^(n/2)(AD + BC) + BD
		result = firstFactor + secondFactor + bd;
		logger.info("Result of the multiplication between {} and {} is {}",x,y,result);
		return result;
	}
	
	/**
	 * This method will divide x and y into four parts
	 * should only be used after strings are normalized
	 * 
	 * @param xyStr String array with x and y values as strings
	 * @param n Size of the numbers
	 * @return will return an int array with the abcd values
	 * 
	 */

	public int[] intFragmenter(String[] xyStr, int n) {
		
		logger.info("Starting fragmentation of {} and {}",x,y);
		int strHalfPoint = n/2;
		if (strHalfPoint == 1) {
			int a = Integer.parseInt(String.valueOf(xyStr[0].charAt(0)));
			int b = Integer.parseInt(String.valueOf(xyStr[0].charAt(1)));
			int c = Integer.parseInt(String.valueOf(xyStr[1].charAt(0)));
			int d = Integer.parseInt(String.valueOf(xyStr[1].charAt(1)));
			logger.info("Ending fragmentation of double digit numbers with {} {} {} {}", a, b, c, d);
			return new int[] {a, b, c, d};
		} 
		int a = Integer.parseInt(xyStr[0].substring(0, strHalfPoint));
		int b = Integer.parseInt(xyStr[0].substring(strHalfPoint));
		int c = Integer.parseInt(xyStr[1].substring(0, strHalfPoint));
		int d = Integer.parseInt(xyStr[1].substring(strHalfPoint));
		logger.info("Ending fragmentation with {} {} {} {}", a,b,c,d);
		return new int[] {a, b, c, d};
	}
	
	/**
	 * This method exists to add left zeroes to the strings to make their size even and equal
	 * it is necessary to make sure that the fragmenter does its job properly and avoid
	 * fragmenting into unneven or odd sized numbers
	 * 
	 * @param x first number to multiply
	 * @param y second number to multiply
	 * @return will return a string array with x and y both normalized
	 */

	public String[] intNormalizer(int x, int y) {
		
		logger.info("Starting string normalization with {} {}",x,y);
		String xStr =  Integer.toString(x);
		String yStr =  Integer.toString(y);
		int xSize = xStr.length();
		int ySize = yStr.length();
		
		while (!isEven(xSize) || !isEven(ySize) || xSize != ySize) {
			if (!isEven(xSize) || (xSize < ySize)) {
				logger.info("Determined that {} isn't normalized",xStr);
				xStr = addLeftZero(xStr);
				xSize = xStr.length();
			} 
			if (!isEven(ySize) || (ySize < xSize)) {
				logger.info("Determined that {} isn't normalized",yStr);
				yStr = addLeftZero(yStr);
				ySize = yStr.length();
			}
		}
		logger.info("Ending string normalization with {} {}",xStr,yStr);
		return new String[] {xStr, yStr};
	}
	
	/**
	 * This method exists only to add some clearance while reading the code
	 * 
	 * @param str Number string
	 * @return add the string with a left zero appended
	 * 
	 */
	public String addLeftZero(String str) {
		
		logger.info("Added a left zero to {} resulting in 0{}",str,str);
		return "0" + str;
	}
	
	/**
	 * This method will check if the input is even or odd
	 * and return a boolean true if even and false if odd
	 * 
	 * @param numb Integer number
	 * @return will return a boolean with true if even and false if odd
	 * 
	 */
	public boolean isEven(int numb) {
		if (numb%2 != 0) {
			logger.info("Determined that {} is odd", numb);
			return false;
		}
		logger.info("Determined that {} is even", numb);
		return true;
	}
}
