package karatsuba;
import static org.junit.Assert.*;
import org.junit.Test;


public class KaratsubaImplementationTest {

	private Karatsuba karatsuba = new KaratsubaImplementation();
	
	@Test
	public void singleDigitMultiplication() {
		assertEquals(karatsuba.multiply(4, 7), 28);
	}
	
	@Test
	public void doubleDigitMultiplication() {
		assertEquals(karatsuba.multiply(21, 89), 1869);
	}
	
	@Test
	public void tripleDigitMultiplication() {
		assertEquals(karatsuba.multiply(313, 862), 269806);
	}
	
	@Test
	public void quadDigitMultiplication() {
		assertEquals(karatsuba.multiply(1234, 5678), 7006652);
	}
	
	@Test
	public void quadDigitMultiplicationInverse() {
		assertEquals(karatsuba.multiply(5678, 1234), 7006652);
	}
	
	@Test
	public void fiveDigitMultiplication() {
		assertEquals(karatsuba.multiply(45689, 752), 34358128);
	}
	@Test
	public void sevenDigitMultiplication() {
		assertEquals(karatsuba.multiply(34984916, 6521984), 2.28171062393e14);
	}
	
	@Test
	public void unnevenSevenDigitMultiplication() {
		long result = Long.parseLong("1125628350426");
		assertEquals(karatsuba.multiply(3519, 319871654), result);
	}
}
