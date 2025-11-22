interface IList<T> {
	IList<T> sort(IComparator<T> comp);
	IList<T> insertBy(IComparator<T> comp, T t);
	int length();
	int lengthHelper(int sum);
}

class MtList<T> implements IList<T> {
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
}

class ConsList<T> implements IList<T> {
	T first;
	IList<T> rest;
	ConsList(T first, IList<T> rest) {
		this.first = first;
		this.rest = rest;
	}
	public IList<T> sort(IComparator<T> comp) {
		return this.rest.sort(comp).insertBy(comp, this.first);
	}
	public IList<T> insertBy(IComparator<T> comp, T t) {
		if (comp.compare(this.first, t) < 0) {
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
}
