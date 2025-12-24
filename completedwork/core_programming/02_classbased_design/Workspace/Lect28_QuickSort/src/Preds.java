interface IPred<T> {
	boolean apply(T t);
}

interface IFunc<A, R> {
	R apply(A arg);
}

interface IFunc2<A1, A2, R> {
	R apply(A1 arg1, A2 arg2);
}

class RunnerName implements IFunc<Runner, String> {
	public String apply(Runner r) {
		return r.name;
	}
}

class BookByAuthor implements IPred<Book> {
	public boolean apply(Book b) {
		return false;
	}
}

interface IComparator<T> {
	// Returns a negative number if r1.time is less than r2.time (0 for equal)
	int compare(T t1, T t2);
}

class StringAlphabetical implements IComparator<String> {
	public int compare(String s1, String s2) {
		return s1.compareTo(s2);
	}
}

class CompareByTime implements IComparator<Runner> {
	public int compare(Runner r1, Runner r2) {
		return r1.time - r2.time;
	}
}

class CompareByName implements IComparator<Runner> {
	public int compare(Runner r1, Runner r2) {
		return r1.name.compareTo(r2.name);
	}
}

class ReverseComparator implements IComparator<Runner> {
	IComparator<Runner> comp;
	ReverseComparator(IComparator<Runner> comp) {
		this.comp = comp;
	}
	public int compare(Runner r1, Runner r2) {
		return this.comp.compare(r1, r2) * -1;
	}
}

class RunnerIsMale implements IPred<Runner> {
	public boolean apply(Runner r) {
		return r.isMale;
	}
}

class RunnerIsFemale implements IPred<Runner> {
	public boolean apply(Runner r) {
		return !r.isMale;
	}
}

class RunnerIsInFirst50 implements IPred<Runner> {
	public boolean apply(Runner r) {
		return r.pos <= 50;
	}
}

class RunnerTimeUnder4hr implements IPred<Runner> {
	public boolean apply(Runner r) {
		return r.time < 4 * 60;
	}
}

class AndPredicate implements IPred<Runner> {
	IPred<Runner> p1;
	IPred<Runner> p2;
	AndPredicate(IPred<Runner> p1, IPred<Runner> p2) {
		this.p1 = p1;
		this.p2 = p2;
	}
	public boolean apply(Runner r) {
		return this.p1.apply(r) && this.p2.apply(r);
	}
}

class OrPredicate implements IPred<Runner> {
	IPred<Runner> p1;
	IPred<Runner> p2;
	OrPredicate(IPred<Runner> p1, IPred<Runner> p2) {
		this.p1 = p1;
		this.p2 = p2;
	}
	public boolean apply(Runner r) {
		return this.p1.apply(r) || this.p2.apply(r);
	}
}
