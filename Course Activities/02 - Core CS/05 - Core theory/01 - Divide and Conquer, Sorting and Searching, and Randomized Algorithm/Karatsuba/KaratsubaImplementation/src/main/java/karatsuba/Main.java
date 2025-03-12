package karatsuba;

public class Main {
	public static void main(String[] args) {
		// 10^nAC + 10^(n/2)(AD + BC) + BD
		int x = 1234;
		int y = 5678;
		Karatsuba karatsuba = new KaratsubaImplementation();
		int result = karatsuba.multiply(x, y);
		System.out.println(result);
	}
}
