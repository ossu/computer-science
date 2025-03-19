package karatsuba;

import java.util.Arrays;

/**
 * 
 * This class will handle the fragmentation of the karatsuba multiplication
 * 
 */
public class Fragmenter {
	
	private int x;
	private int y;
	private boolean fragmentable = false;
	private int[] abcd = new int[4];
	
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
	public boolean isFragmentable() {
		return fragmentable;
	}
	public void setFragmentable(boolean shouldFragment) {
		this.fragmentable = shouldFragment;
	}
	public void setAbcd(int[] abcd) {
		this.abcd = abcd;
	}
	public int[] getAbcd() {
		return abcd;
	}
	@Override
	public String toString() {
		return "Fragmenter [x=" + x + ", y=" + y + ", fragmentable=" + fragmentable + ", abcd="
				+ Arrays.toString(abcd) + "]";
	}
	/**
	 * This constructor exists to allow manual fragmentation without necessarily making the instance with the inputs
	 */
	public Fragmenter() {}
	/**
	 * This constructor will make the instance and automatically fragment the parameters
	 * 
	 * @param x any int to fragment into ab
	 * @param y any int to fragment into cd
	 */
	public Fragmenter(int x, int y) {
		this.x = x;
		this.y = y;
	}
	/**
	 * 
	 * @param x any int to fragment into ab
	 * @param y any int to fragment into cd
	 * @return will return a boolean if both x and y are less than 10
	 */
	public boolean shouldFragment(int x, int y) {
		if (x > 10 || y > 10) {
			this.fragmentable = true;
		}
		return fragmentable;
	}
	// make the normalization private and keep it this way to make it more friendly to do manually
	public int[] fragment(int i, int j) {
		// TODO Auto-generated method stub
		return null;
	}
}
