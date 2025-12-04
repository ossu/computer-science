interface IComparator<T> {
	public int compare(T t1, T t2);
}

class StringByAlpha implements IComparator<String> {
	public int compare(String str1, String str2) {
		return str1.compareTo(str2);
	}
}

abstract class IBinaryTree<T> {
	public T getData() {
		throw new RuntimeException("No data item in an empty tree");
	}
	public IBinaryTree<T> getLeft() {
		return this;
	}
	public IBinaryTree<T> getRight() {
		return this;
	}
	public IBinaryTree<T> order(IComparator<T> comp) {
		return this;
	}
	public IBinaryTree<T> insert(T t, IComparator<T> comp) {
		return new BSTNode<T>(t, new Leaf<T>(), new Leaf<T>());
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
	public IBinaryTree<T> removeLeftMost(IComparator<T> comp) {
		throw new RuntimeException("No left item of an empty tree");
	}
	public IBinaryTree<T> removeRightMost(IComparator<T> comp) {
		throw new RuntimeException("No right item of an empty tree");
	}
	public IBinaryTree<T> removeLeftMostHelper(T t, IComparator<T> comp) {
		return this;
	}
	public IBinaryTree<T> removeRightMostHelper(T t, IComparator<T> comp) {
		return this;
	}
	public boolean sameTree(IBinaryTree<T> that, IComparator<T> comp) {
		return that.sameLeaf();
	}
	public boolean sameNode(T t, IBinaryTree<T> that, IComparator<T> comp) {
		return false;
	}
	public boolean sameLeaf() {
		return false;
	}
	public boolean sameData(IBinaryTree<T> that, IComparator<T> thisComp, IComparator<T> thatComp) {
		return that.sameLeaf();
	}
	public boolean sameDataHelper(IBinaryTree<T> that, IComparator<T> comp) {
		return true;
	}
	public IList<T> buildList(IComparator<T> comp) {
		return new MtList<T>();
	}
	abstract boolean isNode();
	abstract BSTNode<T> getNode();
}

class Leaf<T> extends IBinaryTree<T> {
	Leaf() {}
	@Override
	public boolean sameLeaf() {
		return true;
	}
	public boolean isNode() {
		return false;
	}
	public BSTNode<T> getNode() {
		throw new RuntimeException("Cannot return node of leaf");
	}
}

class BSTNode<T> extends IBinaryTree<T> {
	T data;
	IBinaryTree<T> left;
	IBinaryTree<T> right;
	BSTNode(T data, IBinaryTree<T> left, IBinaryTree<T> right) {
		this.data = data;
		this.left = left;
		this.right = right;
	}
	@Override
	public IBinaryTree<T> getLeft() {
		return this.left;
	}
	@Override
	public IBinaryTree<T> getRight() {
		return this.right;
	}
	@Override
	public T getData() {
		return this.data;
	}

	@Override
	public IBinaryTree<T> insert(T t, IComparator<T> comp) {
		if (comp.compare(this.data, t) > 0) {
			return new BSTNode<T>(this.data, this.left.insert(t, comp), this.right);
		} else {
			return new BSTNode<T>(this.data, this.left, this.right.insert(t, comp));
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
	public IBinaryTree<T> removeRightMost(IComparator<T> comp) {
		T rightMost = this.getRightMost(comp);
		if (comp.compare(this.data, rightMost) == 0) {
			return this.left;
		} else {
			return new BSTNode<T>(this.data,
					this.left,
					this.right.removeRightMostHelper(rightMost, comp));
		}
	}
	@Override
	public IBinaryTree<T> removeLeftMost(IComparator<T> comp) {
		T leftMost = this.getLeftMost(comp);
		if (comp.compare(this.data, leftMost) == 0) {
			return this.right;
		} else {
			return new BSTNode<T>(this.data,
					this.left.removeLeftMostHelper(leftMost, comp),
					this.right);
		}
	}
	@Override
	public IBinaryTree<T> removeRightMostHelper(T t, IComparator<T> comp) {
		if (comp.compare(this.data, t) == 0) {
			return this.left;
		} else {
			return new BSTNode<T>(this.data,
					this.left,
					this.right.removeRightMostHelper(t, comp));
		}
	}
	@Override
	public IBinaryTree<T> removeLeftMostHelper(T t, IComparator<T> comp) {
		if (comp.compare(this.data, t) == 0) {
			return this.right;
		} else {
			return new BSTNode<T>(this.data,
					this.left.removeLeftMostHelper(t, comp),
					this.right);
		}
	}
	@Override
	public boolean sameTree(IBinaryTree<T> that, IComparator<T> comp) {
		return that.sameNode(this.data, this, comp);
	}
	@Override
	public boolean sameNode(T t, IBinaryTree<T> that, IComparator<T> comp) {
		return comp.compare(this.data, t) == 0 &&
						this.left.sameTree(that.getLeft(), comp) &&
						this.right.sameTree(that.getRight(), comp);
	}
	@Override
	public boolean sameData(IBinaryTree<T> that, IComparator<T> thisComp, IComparator<T> thatComp) {
		return this.sameDataHelper(that, thatComp) &&
						that.sameDataHelper(this, thisComp);
	}
	@Override
	public boolean sameDataHelper(IBinaryTree<T> that, IComparator<T> comp) {
		return that.present(this.getData(), comp) &&
						this.left.sameDataHelper(that, comp) &&
						this.right.sameDataHelper(that, comp);
	}
	public IList<T> buildList(IComparator<T> comp) {
		return new ConsList<T>(this.getLeftMost(comp),
				this.removeLeftMost(comp).buildList(comp));
	}
	public boolean isNode() {
		return true;
	}
	public BSTNode<T> getNode() {
		return this;
	}
}
