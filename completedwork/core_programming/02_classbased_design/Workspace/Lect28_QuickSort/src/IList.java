
interface IList<T> {
	<U> IList<U> map(IFunc<T, U> func);
	<U> U foldr(IFunc2<T, U, U> func, U base);
	boolean isCons();
	ConsList<T> asCons();
}

class MtList<T> implements IList<T> {
	public <U> IList<U> map(IFunc<T, U> f) {
		return new MtList<U>();
	}
	public <U> U foldr(IFunc2<T, U, U> func, U base) {
		return base;
	}
	public boolean isCons() {
		return false;
	}
	public ConsList<T> asCons() {
		throw new RuntimeException("Cannot return Cons of Empty List");
	}
}

class ConsList<T> implements IList<T> {
	T first;
	IList<T> rest;
	ConsList(T first, IList<T> rest) {
		this.first = first;
		this.rest = rest;
	}
	public <U> IList<U> map(IFunc<T, U> func) {
		return new ConsList<U>(func.apply(this.first), this.rest.map(func));
	}
	public <U> U foldr(IFunc2<T, U, U> func, U base) {
		return func.apply(this.first,
				this.rest.foldr(func, base));
	}
	public boolean isCons() {
		return true;
	}
	public ConsList<T> asCons() {
		return this;
	}
}
