package karatsuba;

public class KaratsubaInjector implements Injector {

	@Override
	public Karatsuba getKaratsuba() {
		return new KaratsubaImplementation();
	}
}
