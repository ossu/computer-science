import tester.Tester;

class Examples {
	Book eragon = new Book("Eragon", "CP", 12);
	Book gatsby = new Book("Great Gatsby, The", "FSF", 16);
	Book hp = new Book("HP & the search for more money", "JKR", 9000);
	Book htdp = new Book("HtDP", "MF", 0);
	Book lotr = new Book("Lord of the Rings", "JRRT", 10);
	Book pippi = new Book("Pippi Longstocking", "AL", 4);

	ABST<Book> leaf = new Leaf<Book>();
	ABST<Book> bst_bbt = new Node<Book>(htdp,
			new Node<Book>(gatsby, new Node<Book>(eragon, leaf, leaf), new Node<Book>(hp, leaf, leaf)),
			new Node<Book>(lotr, leaf, new Node<Book>(pippi, leaf, leaf)));
	ABST<Book> bst_bba = new Node<Book>(hp,
			new Node<Book>(eragon, new Node<Book>(pippi, leaf, leaf), new Node<Book>(gatsby, leaf, leaf)),
			new Node<Book>(lotr, leaf, new Node<Book>(htdp, leaf, leaf)));
	ABST<Book> bstPart_bbt = new Node<Book>(htdp,
			new Node<Book>(hp, leaf, leaf), new Node<Book>(lotr, leaf, leaf));

	BooksByTitle bbt = new BooksByTitle();
	BooksByAuthor bba = new BooksByAuthor();
	BooksByPrice bbp = new BooksByPrice();

	boolean testInsert(Tester t) {
		return t.checkExpect(leaf.insert(htdp, bbt),
				new Node<Book>(this.htdp, leaf, leaf)) &&
						t.checkExpect(leaf.insert(htdp, bbt)
								.insert(gatsby, bbt)
								.insert(eragon, bbt)
								.insert(hp, bbt)
								.insert(lotr, bbt)
								.insert(pippi, bbt),
								this.bst_bbt) &&
						t.checkExpect(leaf.insert(hp, bba)
								.insert(eragon, bba)
								.insert(pippi, bba)
								.insert(gatsby, bba)
								.insert(lotr, bba)
								.insert(htdp, bba),
								this.bst_bba);
	}
	boolean testPresent(Tester t) {
		return t.checkExpect(leaf.present(gatsby, bba), false) &&
						t.checkExpect(this.bst_bbt.present(gatsby, bbt), true) &&
						t.checkExpect(this.bst_bbt.present(pippi, bbt), true) &&
						t.checkExpect(this.bst_bba.present(eragon, bba), true) &&
						t.checkExpect(this.bst_bba.present(lotr, bba), true) &&
						t.checkExpect(this.bst_bba.present(htdp, bba), true) &&
						t.checkExpect(this.bst_bba.present(hp, bba), true) &&
						t.checkExpect(this.bstPart_bbt.present(gatsby, bbt), false) &&
						t.checkExpect(this.bstPart_bbt.present(pippi, bbt), false);
	}
	ABST<Book> bst_bbt_noEragon = new Node<Book>(htdp,
			new Node<Book>(gatsby, leaf, new Node<Book>(hp, leaf, leaf)),
			new Node<Book>(lotr, leaf, new Node<Book>(pippi, leaf, leaf)));
	boolean testGetDirMost(Tester t) {
		return t.checkExpect(this.bst_bbt.getLeftMost(bbt), eragon) &&
						t.checkExpect(this.bst_bbt.getRightMost(bbt), pippi) &&
						t.checkExpect(this.bst_bbt_noEragon.getLeftMost(bbt), gatsby);
	}
	ABST<Book> bst_bbt_noEragonGatsby = new Node<Book>(htdp,
			new Node<Book>(hp, leaf, leaf),
			new Node<Book>(lotr, leaf, new Node<Book>(pippi, leaf, leaf)));
	ABST<Book> bst_bbt_noEragonGatsbyHp = new Node<Book>(htdp,
			leaf,
			new Node<Book>(lotr, leaf, new Node<Book>(pippi, leaf, leaf)));
	ABST<Book> bst_bbt_noEragonGatsbyHpHtdp = new Node<Book>(lotr, leaf,
			new Node<Book>(pippi, leaf, leaf));

	boolean testRemoveDirMost(Tester t) {
		return t.checkExpect(this.bst_bbt.removeLeftMost(bbt), this.bst_bbt_noEragon) &&
						t.checkExpect(this.bst_bbt_noEragon.removeLeftMost(bbt), this.bst_bbt_noEragonGatsby) &&
						t.checkExpect(this.bst_bbt_noEragonGatsby.removeLeftMost(bbt),
								this.bst_bbt_noEragonGatsbyHp) &&
						t.checkExpect(this.bst_bbt_noEragonGatsbyHp.removeLeftMost(bbt),
								this.bst_bbt_noEragonGatsbyHpHtdp);
	}
	boolean testSameTree(Tester t) {
		return t.checkExpect(this.bst_bbt.sameTree(this.bst_bbt, bbt), true) &&
						t.checkExpect(this.bst_bba.sameTree(this.bst_bba, bba), true) &&
						t.checkExpect(this.bst_bbt.sameTree(this.bst_bba, bbt), false) &&
						t.checkExpect(this.leaf.sameTree(this.bst_bbt, bbt), false) &&
						t.checkExpect(this.bst_bba.sameTree(leaf, bba), false) &&
						t.checkExpect(this.leaf.sameTree(leaf, bba), true);
	}
	boolean testSameData(Tester t) {
		return t.checkExpect(this.bst_bbt.sameData(bst_bbt, bbt, bbt), true) &&
						t.checkExpect(this.bst_bba.sameData(bst_bba, bba, bba), true) &&
						t.checkExpect(this.bst_bbt.sameData(bst_bba, bbt, bba), true) &&
						t.checkExpect(this.bst_bba.sameData(bst_bbt, bba, bbt), true) &&
						t.checkExpect(this.bstPart_bbt.sameData(bst_bbt, bbt, bbt), false) &&
						t.checkExpect(this.bst_bbt.sameData(bstPart_bbt, bbt, bbt), false);
	}
	IList<Book> list_bbt = new ConsList<Book>(this.eragon,
			new ConsList<Book>(this.gatsby, new ConsList<Book>(hp,
					new ConsList<Book>(this.htdp, new ConsList<Book>(this.lotr,
							new ConsList<Book>(this.pippi, new MtList<Book>()))))));
	boolean testBuildList(Tester t) {
		return t.checkExpect(this.bst_bbt.buildList(bbt), this.list_bbt);
	}
}
