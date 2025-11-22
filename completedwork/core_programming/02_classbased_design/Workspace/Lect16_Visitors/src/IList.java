interface IList<T> {
	<U> IList<U> map(IFunc<T, U> func);
	<U> U foldr(IFunc2<T, U, U> func, U base);
}

class MtList<T> implements IList<T> {
	public <U> IList<U> map(IFunc<T, U> f) {
		return new MtList<U>();
	}
	public <U> U foldr(IFunc2<T, U, U> func, U base) {
		return base;
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
}
