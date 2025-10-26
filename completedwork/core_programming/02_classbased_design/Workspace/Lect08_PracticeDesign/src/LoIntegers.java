import tester.Tester;

interface ILoInt {
	// returns true if int in list is even, pos & odd, and btwn 5 & 10 inclusive
	boolean containsA();
	// returns true same as A but number my satisfy only one requirement
	boolean containsB();
	// returns true same as B but only contain 3 nums which satisfy requirements
	boolean containsC();
	// returns true if i meets any requirement
	boolean tryInt(int i);
	boolean containsEven();
	boolean containsPosOdd();
	boolean containsBetween();
	boolean containsEvenHelp(int i);
	boolean containsPosOddHelp(int i);
	boolean containsBetweenHelp(int i);
}

class MtLoInt implements ILoInt {
	MtLoInt() {}

	public boolean containsA() {
		return false;
	}
	public boolean containsB() {
		return false;
	}
	public boolean containsC() {
		return true;
	}

	public boolean tryInt(int i) {
		return false;
	}
	public boolean containsEven() {
		return false;
	}
	public boolean containsPosOdd() {
		return false;
	}
	public boolean containsBetween() {
		return false;
	}
	public boolean containsEvenHelp(int i) {
		return false;
	}
	public boolean containsPosOddHelp(int i) {
		return false;
	}
	public boolean containsBetweenHelp(int i) {
		return false;
	}
}

class ConsLoInt implements ILoInt {
	int first;
	ILoInt rest;
	ConsLoInt(int first, ILoInt rest) {
		this.first = first;
		this.rest = rest;
	}

	public boolean containsA() {
		return this.containsEven() &&
						this.containsPosOdd() &&
						this.containsBetween();
	}
	public boolean containsB() {
		return this.tryInt(first);
	}
	public boolean containsC() {
		return this.tryInt(this.first) &&
						this.rest.containsC();
	}

	public boolean tryInt(int i) {
		return this.containsEvenHelp(i) ||
						this.containsPosOddHelp(i) ||
						this.containsBetweenHelp(i);
	}
	public boolean containsEven() {
		return this.containsEvenHelp(this.first);
	}
	public boolean containsEvenHelp(int i) {
		if (i % 2 == 0) {
			return true;
		} else {
			return this.rest.containsEven();
		}
	}
	public boolean containsPosOdd() {
		return this.containsPosOddHelp(this.first);
	}
	public boolean containsPosOddHelp(int i) {
		if (this.first > 0 && this.first % 2 != 0) {
			return true;
		} else {
			return this.rest.containsPosOdd();
		}
	}
	public boolean containsBetween() {
		return this.containsBetweenHelp(this.first);
	}
	public boolean containsBetweenHelp(int i) {
		if (this.first >= 5 && this.first <= 10) {
			return true;
		} else {
			return this.rest.containsBetween();
		}
	}
}

class ExamplesLoInts {
	ILoInt a_s = new ConsLoInt(6, new ConsLoInt(5, new MtLoInt()));
	ILoInt a_f = new ConsLoInt(4, new ConsLoInt(3, new MtLoInt()));

	ILoInt b_s = new ConsLoInt(6, new ConsLoInt(5, new ConsLoInt(6, new MtLoInt())));
	ILoInt b_f = new ConsLoInt(6, new ConsLoInt(5, new MtLoInt()));

	ILoInt c_s = new ConsLoInt(6, new ConsLoInt(5, new ConsLoInt(6, new MtLoInt())));
	ILoInt c_f = new ConsLoInt(6,
			new ConsLoInt(5, new ConsLoInt(6, new ConsLoInt(42, new MtLoInt()))));

	ILoInt even_f = new ConsLoInt(5, new ConsLoInt(3, new MtLoInt()));
	ILoInt posodd_f = new ConsLoInt(4, new ConsLoInt(2, new MtLoInt()));

	boolean testContainsA(Tester t) {
		return t.checkExpect(this.a_s.containsA(), true) &&
						t.checkExpect(this.a_f.containsA(), false);
	}
	// boolean testContainsB(Tester t) {
	// return t.checkExpect(this.b_s.containsB(), true) &&
	// t.checkExpect(this.b_f.containsB(), false);
	// }
	boolean testContainsC(Tester t) {
		return t.checkExpect(this.c_s.containsC(), true) &&
						t.checkExpect(this.c_f.containsC(), false);
	}
	boolean testContainsEven(Tester t) {
		return t.checkExpect(this.a_s.containsEven(), true) &&
						t.checkExpect(this.even_f.containsEven(), false);
	}
	boolean testContainsPosOdd(Tester t) {
		return t.checkExpect(this.a_s.containsPosOdd(), true) &&
						t.checkExpect(this.posodd_f.containsPosOdd(), false);
	}
	boolean testContainsBetween(Tester t) {
		return t.checkExpect(this.a_s.containsBetween(), true) &&
						t.checkExpect(this.posodd_f.containsBetween(), false);
	}
}
