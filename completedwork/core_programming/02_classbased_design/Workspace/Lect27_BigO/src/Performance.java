interface IList<T> {
	int length();
}

class MtList<T> implements IList<T> {
	public int length() {
		return 0;
	}
}

class ConsList<T> implements IList<T> {
	T first;
	IList<T> rest;
	public int length() {
		return 1 + this.rest.length();
	}
}
