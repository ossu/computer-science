import tester.Tester;

class Counter {
	int val;
	Counter() {
		this(0);
	}
	Counter(int initialVal) {
		this.val = initialVal;
	}
	int get() {
		int ans = this.val;
		this.val = this.val + 1;
		return ans;
	}
}

class ExamplesCounters {
	boolean testCounter(Tester t) {
		Counter c1 = new Counter();  // 7
		Counter c2 = new Counter(2); // 5
		// What should these tests be?
		return t.checkExpect(c1.get(), 0)              // Test 1     - c1=0
						&&	t.checkExpect(c2.get(), 2)              // Test 2     - c2=2
						&&
						t.checkExpect(c1.get() == c1.get(), false)  // Test 3 - 1, 2
						&&
						t.checkExpect(c2.get() == c1.get(), true)  // Test 4  - 3, 3  
						&&
						t.checkExpect(c2.get() == c1.get(), true)  // Test 5  - 4, 4
						&&
						t.checkExpect(c1.get() == c1.get(), false)  // Test 6 - 5, 6
						&&
						t.checkExpect(c2.get() == c1.get(), false); // Test 7 - 7, 5
	}
}
