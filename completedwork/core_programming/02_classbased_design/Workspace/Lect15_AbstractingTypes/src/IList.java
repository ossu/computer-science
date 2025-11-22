interface IList<T> {
	IList<T> filter(IPred<T> pred);
	IList<T> sort(IComparator<T> comp);
	IList<T> insertBy(IComparator<T> comp, T t);
	int length();
	int lengthHelper(int sum);
	<U> IList<U> map(IFunc<T, U> f);
	<U> U foldr(IFunc2<T, U, U> func, U base);
}

class MtList<T> implements IList<T> {
	public IList<T> filter(IPred<T> pred) {
		return this;
	}
	public IList<T> sort(IComparator<T> comp) {
		return this;
	}
	public IList<T> insertBy(IComparator<T> comp, T t) {
		return new ConsList<T>(t, this);
	}
	public int length() {
		return 0;
	}
	public int lengthHelper(int sum) {
		return sum;
	}
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
	public IList<T> filter(IPred<T> pred) {
		if (pred.apply(this.first)) {
			return new ConsList<T>(this.first, this.rest.filter(pred));
		} else {
			return this.rest.filter(pred);
		}
	}
	public IList<T> sort(IComparator<T> comp) {
		return this.rest.sort(comp).insertBy(comp, this.first);
	}
	public IList<T> insertBy(IComparator<T> comp, T t) {
		if (comp.comesBefore(this.first, t) < 0) {
			return new ConsList<T>(this.first, this.rest.insertBy(comp, t));
		}
		return new ConsList<T>(t, this);
	}
	public int length() {
		return this.lengthHelper(0);
	}
	public int lengthHelper(int sum) {
		return this.rest.lengthHelper(1 + sum);
	}
	public <U> IList<U> map(IFunc<T, U> f) {
		return new ConsList<U>(f.apply(this.first), this.rest.map(f));
	}
	public <U> U foldr(IFunc2<T, U, U> func, U base) {
		return func.apply(this.first,
				this.rest.foldr(func, base));
	}
}
