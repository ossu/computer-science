class Book {
	String title;
	String author;
	int price;
	Book(String title, String author, int price) {
		this.title = title;
		this.author = author;
		this.price = price;
	}
	public boolean isSame(Book that) {
		return this.title.compareTo(that.title) == 0 &&
						this.author.compareTo(that.author) == 0 &&
						this.price == that.price;
	}
}

interface IComparator<T> {
	public int compare(T t1, T t2);
}

class BooksByTitle implements IComparator<Book> {
	public int compare(Book b1, Book b2) {
		return b1.title.compareTo(b2.title);
	}
}

class BooksByAuthor implements IComparator<Book> {
	public int compare(Book b1, Book b2) {
		return b1.author.compareTo(b2.author);
	}
}

class BooksByPrice implements IComparator<Book> {
	public int compare(Book b1, Book b2) {
		return b1.price - b2.price;
	}
}

abstract class ABST<T> {
	public T getData() {
		throw new RuntimeException("No data item in an empty tree");
	}
	public ABST<T> getLeft() {
		return this;
	}
	public ABST<T> getRight() {
		return this;
	}
	public ABST<T> order(IComparator<T> comp) {
		return this;
	}
	public ABST<T> insert(T t, IComparator<T> comp) {
		return new Node<T>(t, new Leaf<T>(), new Leaf<T>());
	}
	public boolean present(T t, IComparator<T> comp) {
		return false;
	}
	public T getLeftMost(IComparator<T> comp) {
		throw new RuntimeException("No leftmost item of an empty tree");
	}
	public T getRightMost(IComparator<T> comp) {
		throw new RuntimeException("No rightmost item of an empty tree");
	}
	public T getLeftMostHelper(T rsf, IComparator<T> comp) {
		return rsf;
	}
	public T getRighttMostHelper(T rsf, IComparator<T> comp) {
		return rsf;
	}
	public ABST<T> removeLeftMost(IComparator<T> comp) {
		throw new RuntimeException("No left item of an empty tree");
	}
	public ABST<T> removeRightMost(IComparator<T> comp) {
		throw new RuntimeException("No right item of an empty tree");
	}
	public ABST<T> removeLeftMostHelper(T t, IComparator<T> comp) {
		return this;
	}
	public ABST<T> removeRightMostHelper(T t, IComparator<T> comp) {
		return this;
	}
	public boolean sameTree(ABST<T> that, IComparator<T> comp) {
		return that.sameLeaf();
	}
	public boolean sameNode(T t, ABST<T> that, IComparator<T> comp) {
		return false;
	}
	public boolean sameLeaf() {
		return false;
	}
	public boolean sameData(ABST<T> that, IComparator<T> thisComp, IComparator<T> thatComp) {
		return that.sameLeaf();
	}
	public boolean sameDataHelper(ABST<T> that, IComparator<T> comp) {
		return true;
	}
	public IList<T> buildList(IComparator<T> comp) {
		return new MtList<T>();
	}
}

class Leaf<T> extends ABST<T> {
	Leaf() {}
	@Override
	public boolean sameLeaf() {
		return true;
	}
}

class Node<T> extends ABST<T> {
	T data;
	ABST<T> left;
	ABST<T> right;
	Node(T data, ABST<T> left, ABST<T> right) {
		this.data = data;
		this.left = left;
		this.right = right;
	}
	@Override
	public ABST<T> getLeft() {
		return this.left;
	}
	@Override
	public ABST<T> getRight() {
		return this.right;
	}
	@Override
	public T getData() {
		return this.data;
	}

	@Override
	public ABST<T> insert(T t, IComparator<T> comp) {
		if (comp.compare(this.data, t) > 0) {
			return new Node<T>(this.data, this.left.insert(t, comp), this.right);
		} else {
			return new Node<T>(this.data, this.left, this.right.insert(t, comp));
		}
	}
	@Override
	public boolean present(T t, IComparator<T> comp) {
		int r = comp.compare(this.data, t);
		if (r == 0) {
			return true;
		} else if (r > 0) {
			return this.left.present(t, comp);
		} else {
			return this.right.present(t, comp);
		}
	}
	@Override
	public T getLeftMost(IComparator<T> comp) {
		return this.left.getLeftMostHelper(this.data, comp);
	}
	@Override
	public T getRightMost(IComparator<T> comp) {
		return this.right.getRighttMostHelper(this.data, comp);
	}
	@Override
	public T getLeftMostHelper(T rsf, IComparator<T> comp) {
		return this.left.getLeftMostHelper(this.data, comp);
	}
	@Override
	public T getRighttMostHelper(T rsf, IComparator<T> comp) {
		return this.right.getRighttMostHelper(this.data, comp);
	}
	@Override
	public ABST<T> removeRightMost(IComparator<T> comp) {
		T rightMost = this.getRightMost(comp);
		if (comp.compare(this.data, rightMost) == 0) {
			return this.left;
		} else {
			return new Node<T>(this.data,
					this.left,
					this.right.removeRightMostHelper(rightMost, comp));
		}
	}
	@Override
	public ABST<T> removeLeftMost(IComparator<T> comp) {
		T leftMost = this.getLeftMost(comp);
		if (comp.compare(this.data, leftMost) == 0) {
			return this.right;
		} else {
			return new Node<T>(this.data,
					this.left.removeLeftMostHelper(leftMost, comp),
					this.right);
		}
	}
	@Override
	public ABST<T> removeRightMostHelper(T t, IComparator<T> comp) {
		if (comp.compare(this.data, t) == 0) {
			return this.left;
		} else {
			return new Node<T>(this.data,
					this.left,
					this.right.removeRightMostHelper(t, comp));
		}
	}
	@Override
	public ABST<T> removeLeftMostHelper(T t, IComparator<T> comp) {
		if (comp.compare(this.data, t) == 0) {
			return this.right;
		} else {
			return new Node<T>(this.data,
					this.left.removeLeftMostHelper(t, comp),
					this.right);
		}
	}
	@Override
	public boolean sameTree(ABST<T> that, IComparator<T> comp) {
		return that.sameNode(this.data, this, comp);
	}
	@Override
	public boolean sameNode(T t, ABST<T> that, IComparator<T> comp) {
		return comp.compare(this.data, t) == 0 &&
						this.left.sameTree(that.getLeft(), comp) &&
						this.right.sameTree(that.getRight(), comp);
	}
	@Override
	public boolean sameData(ABST<T> that, IComparator<T> thisComp, IComparator<T> thatComp) {
		return this.sameDataHelper(that, thatComp) &&
						that.sameDataHelper(this, thisComp);
	}
	@Override
	public boolean sameDataHelper(ABST<T> that, IComparator<T> comp) {
		return that.present(this.getData(), comp) &&
						this.left.sameDataHelper(that, comp) &&
						this.right.sameDataHelper(that, comp);
	}
	public IList<T> buildList(IComparator<T> comp) {
		return new ConsList<T>(this.getLeftMost(comp),
				this.removeLeftMost(comp).buildList(comp));
	}
}
