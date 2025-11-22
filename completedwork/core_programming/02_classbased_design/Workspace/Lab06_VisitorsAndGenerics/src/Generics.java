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
	IList<T> filter(IPred<T> pred);
	// map over a list, and produce a new list with a (possibly different)
	// element type
	<U> IList<U> map(IFunc<T, U> f);
	<U> U foldr(IFunc2<T, U, U> func, U base);
	<U> U findSolutionOrElse(IFunc<T, U> convert, IPred<U> pred, U backup);
	T getFirstOrBackup(T backup);
}

// empty generic list
class MtList<T> implements IList<T> {
	public T getFirst(T t) {
		return t;
	}
	public IList<T> filter(IPred<T> pred) {
		return this;
	}
	public <U> IList<U> map(IFunc<T, U> f) {
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
