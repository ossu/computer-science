package karatsuba;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

// This class will hold the state of the multiplication
public class FragmenterTest {
	
	
	/*
	 * i want it to have two classes to separate the karaimpl class
	 * One class will hold the state of the multiplication with all values
	 * Another class will handle the string normalization and separation
	 * The karatsuba class will handle the calculation
	 * 
	 * The karatsuba class will handle the calcs and will keep the state
	 * while another class will handle the fragmentation
	*/
	
	private Injector injector = new Injector();
	
	@Test
	public void returnFalseForSingleDigits() {
		Fragmenter fragmenter = injector.getFragmenterInstance();
		boolean answer = fragmenter.shouldFragment(8,9);
	}

	@Test
	public void returnTrueForDoubleWithSingleDigit() {
		Fragmenter fragmenter = injector.getFragmenterInstance();
		assertTrue(fragmenter.shouldFragment(9,12));
	}
	
	@Test
	public void returnFalseForSingleDigitsAttribute() {
		Fragmenter fragmenter = injector.getFragmenterInstance();
		fragmenter.shouldFragment(9,8);
		assertFalse(fragmenter.isFragmentable());
	}

	@Test
	public void returnTrueForDoubleWithSingleDigitAttribute() {
		Fragmenter fragmenter = injector.getFragmenterInstance();
		fragmenter.shouldFragment(9,12);
		assertTrue(fragmenter.isFragmentable());
	}
	
	@Test
	public void fragmentingSingleDigit() {
		Fragmenter fragmenter = injector.getFragmenterInstance();
		String[] abcd = fragmenter.fragment(8,12);
		assertEquals(abcd, new String[] {"0", "8", "1", "2"});
	}
	
	@Test
	public void fragmentingSingleDigitWithConstructor() {
		Fragmenter fragmenter = injector.getFragmenterInstance(8, 12);
		assertEquals(fragmenter.getAbcd(), new String[] {"0", "8", "1", "2"});
	}
	
	@Test
	public void 
}






































