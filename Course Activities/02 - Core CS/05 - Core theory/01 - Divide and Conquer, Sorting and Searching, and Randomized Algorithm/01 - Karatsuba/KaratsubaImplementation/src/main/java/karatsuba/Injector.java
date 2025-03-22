package karatsuba;


/**
 * This class will handle the dependency injection of the project
 */
public class Injector {
	
	/**
	 * 
	 * When this method is called, the constructor will auto multiply with the parameters
	 * 
	 * @param x any int to construct the Karatsuba instance
	 * @param y any int to construct the Karatsuba instance
	 * @return Will return the Karatsuba instance
	 */
	public Karatsuba getKaratsubaInstance(int x, int y) {
		return new Karatsuba(x, y);
	}
	/**
	 * This constructor exists to execute manual multiplying byt calling the multiply method of the Karatsuba instance
	 * 
	 * @return Will return the Karatsuba instance
	 */
	public Karatsuba getKaratsubaInstance() {
		return new Karatsuba();
	}
	public Fragmenter getFragmenterInstance(int x, int y) {
		return new Fragmenter(x, y);
	}
	/**
	 * This constructor exists to execute manual multiplying byt calling the multiply method of the Karatsuba instance
	 * 
	 * @return Will return the Karatsuba instance
	 */
	public Fragmenter getFragmenterInstance() {
		return new Fragmenter();
	}
}
