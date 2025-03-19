package karatsuba;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class InjectorTest {

	private Injector injector = new Injector();
	
	@Test
	public void testKaratsubaInjectionWithArguments() {
		Karatsuba karatsuba = new Karatsuba(8,9);
		Karatsuba injection = injector.getKaratsubaInstance(8,9);
		assertTrue(karatsuba.toString().equals(injection.toString()));
	}
	
	@Test
	public void testKaratsubaInjectionWithoutArguments() {
		Karatsuba karatsuba = new Karatsuba();
		Karatsuba injection = injector.getKaratsubaInstance();
		assertTrue(karatsuba.toString().equals(injection.toString()));
	}
	
	@Test
	public void testFragmenterInjectionWithoutArguments() {
		Fragmenter fragmenter = new Fragmenter();
		Fragmenter injection = injector.getFragmenterInstance();
		assertTrue(fragmenter.toString().equals(injection.toString()));
	}
	
	@Test
	public void testFragmenterInjection() {
		Fragmenter fragmenter = new Fragmenter(21,43);
		Fragmenter injection = injector.getFragmenterInstance(21,43);
		assertTrue(fragmenter.toString().equals(injection.toString()));
	}
}
