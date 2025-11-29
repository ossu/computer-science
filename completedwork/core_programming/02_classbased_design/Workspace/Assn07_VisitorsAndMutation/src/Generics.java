// Represents functions of signature A -> R, for some argument type A and
// result type R
interface IFunc<A, R> {
	R apply(A input);
}

interface IFunc2<A1, A2, R> {
	R apply(A1 arg1, A2 arg2);
}

interface IPred<T> {
	boolean apply(T t);
}

// generic list
interface IList<T> {
	T getFirst(T t);
	// returns true if t is included in list
	boolean contains(T t);
	// returns true if list contains two of the same item
	boolean containsDuplicate();
	// returns an int of the number of times t is found in list
	int count(T t);
	int countHelper(T t, int cnt);
	// adds lot to end of this list
	IList<T> appendList(IList<T> lot);
	IList<T> filter(IPred<T> pred);
	// map over a list, and produce a new list with a (possibly different)
	// element type
	<U> IList<U> map(IFunc<T, U> f);
	// same as map but where the result of IFunc is an IList<U> 
	<U> IList<U> mapList(IFunc<T, IList<U>> f);
	<U> U foldr(IFunc2<T, U, U> func, U base);
	<U> U findSolutionOrElse(IFunc<T, U> convert, IPred<U> pred, U backup);
	T getFirstOrBackup(T backup);
}

// empty generic list
class MtList<T> implements IList<T> {
	public T getFirst(T t) {
		return t;
	}
	public boolean contains(T t) {
		return false;
	}
	public boolean containsDuplicate() {
		return false;
	}
	public int count(T t) {
		return 0;
	}
	public int countHelper(T t, int cnt) {
		return cnt;
	}
	public IList<T> appendList(IList<T> lot) {
		return lot;
	}
	public IList<T> filter(IPred<T> pred) {
		return this;
	}
	public <U> IList<U> map(IFunc<T, U> f) {
		return new MtList<U>();
	}
	public <U> IList<U> mapList(IFunc<T, IList<U>> f) {
		return new MtList<U>();
	}
	public <U> U foldr(IFunc2<T, U, U> func, U base) {
		return base;
	}
	public <U> U findSolutionOrElse(IFunc<T, U> convert, IPred<U> pred, U backup) {
		return backup;
	}
	public T getFirstOrBackup(T backup) {
		return backup;
	}
}

// non-empty generic list
class ConsList<T> implements IList<T> {
	T first;
	IList<T> rest;

	ConsList(T first, IList<T> rest) {
		this.first = first;
		this.rest = rest;
	}
	public T getFirst(T t) {
		return this.first;
	}
	public boolean contains(T t) {
		if (this.first.equals(t)) {
			return true;
		} else {
			return this.rest.contains(t);
		}
	}
	public boolean containsDuplicate() {
		if (this.rest.contains(this.first)) {
			return true;
		} else {
			return this.rest.containsDuplicate();
		}
	}
	public int count(T t) {
		return this.countHelper(t, 0);
	}
	public int countHelper(T t, int cnt) {
		if (this.first.equals(t)) {
			return this.rest.countHelper(t, cnt + 1);
		} else {
			return this.rest.countHelper(t, cnt);
		}
	}
	public IList<T> appendList(IList<T> lot) {
		return new ConsList<T>(this.first, this.rest.appendList(lot));
	}
	public IList<T> filter(IPred<T> pred) {
		if (pred.apply(this.first)) {
			return new ConsList<T>(this.first, this.rest.filter(pred));
		} else {
			return this.rest.filter(pred);
		}
	}
	public <U> IList<U> map(IFunc<T, U> f) {
		return new ConsList<U>(f.apply(this.first), this.rest.map(f));
	}
	public <U> IList<U> mapList(IFunc<T, IList<U>> f) {
		return this.rest.mapList(f).appendList(f.apply(this.first));
	}
	public <U> U foldr(IFunc2<T, U, U> func, U base) {
		return func.apply(this.first, this.rest.foldr(func, base));
	}
	public <U> U findSolutionOrElse(IFunc<T, U> convert, IPred<U> pred, U backup) {
		return this.map(convert)       // List<T> -> List<U>
				.filter(pred)              // List<U> -> List<U> (filtered where pred == true)
				.getFirstOrBackup(backup); // List<U> -> U       (first value of list where pred == true)
	}
	public T getFirstOrBackup(T backup) {
		return this.first;
	}
}
