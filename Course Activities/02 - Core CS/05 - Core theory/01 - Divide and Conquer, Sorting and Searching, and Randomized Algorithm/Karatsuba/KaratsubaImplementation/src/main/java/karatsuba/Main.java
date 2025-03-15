package karatsuba;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
	
	private static final Logger logger 
    = LoggerFactory.getLogger(Main.class);
	
	public static void main(String[] args) {
		// 10^nAC + 10^(n/2)(AD + BC) + BD
		logger.info("Main class is running.");
		int x = 1234;
		int y = 5678;
		Karatsuba karatsuba = new KaratsubaImplementation();
		karatsuba.multiply(x, y);
	}
}
