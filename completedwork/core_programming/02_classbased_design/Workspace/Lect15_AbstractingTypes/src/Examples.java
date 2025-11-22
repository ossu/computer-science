import tester.Tester;

class Examples {
	Book htdp = new Book("HtDP", "MF", 0.0, 2014);
	Book hp = new Book("HP & the search for more money", "JKR", 9000.00, 2015);
	Book gatsby = new Book("The Great Gatsby", "FSF", 15.99, 1930);
	IList<Book> mtBooklist = new MtList<Book>();
	IList<Book> twoBooks = new ConsList<Book>(this.htdp,
			new ConsList<Book>(this.hp, this.mtBooklist));
	IList<Book> threeBooks = new ConsList<Book>(this.gatsby, this.twoBooks);

	Runner johnny = new Runner("Johnny", 97, 999, true, 30, 360);
	Runner frank = new Runner("Frank", 32, 888, true, 245, 130);
	Runner bill = new Runner("Bill", 36, 777, true, 119, 129);
	Runner joan = new Runner("Joan", 29, 444, false, 18, 155);

	IList<Runner> mtRunnerlist = new MtList<Runner>();
	IList<Runner> runnerlist1 = new ConsList<Runner>(johnny,
			new ConsList<Runner>(joan, mtRunnerlist));
	IList<Runner> runnerlist2 = new ConsList<Runner>(frank, new ConsList<Runner>(bill, runnerlist1));

	IList<Runner> list_sortedTime = new ConsList<Runner>(this.bill, new ConsList<Runner>(this.frank,
			new ConsList<Runner>(this.joan, new ConsList<Runner>(this.johnny, mtRunnerlist))));
	IList<Runner> list_reverseSortTime = new ConsList<Runner>(this.johnny,
			new ConsList<Runner>(this.joan,
					new ConsList<Runner>(this.frank, new ConsList<Runner>(this.bill, mtRunnerlist))));
	IList<Runner> list_sortedName = new ConsList<Runner>(this.bill, new ConsList<Runner>(this.frank,
			new ConsList<Runner>(this.joan, new ConsList<Runner>(this.johnny, mtRunnerlist))));

	boolean testFindPredMethods(Tester t) {
		return t.checkExpect(this.runnerlist2.filter(new RunnerIsFemale()),
				new ConsList<Runner>(this.joan, mtRunnerlist)) &&
						t.checkExpect(this.runnerlist2.filter(new RunnerIsMale()),
								new ConsList<Runner>(this.frank,
										new ConsList<Runner>(this.bill,
												new ConsList<Runner>(this.johnny, mtRunnerlist)))) &&
						t.checkExpect(this.runnerlist1.filter(new RunnerTimeUnder4hr()),
								new ConsList<Runner>(joan, mtRunnerlist)) &&
						t.checkExpect(this.runnerlist2.filter(new RunnerTimeUnder4hr()),
								new ConsList<Runner>(this.frank,
										new ConsList<Runner>(this.bill,
												new ConsList<Runner>(this.joan, mtRunnerlist))));
	}
	boolean testCombinedQuestions(Tester t) {
		return t.checkExpect(this.runnerlist2.filter(
				new AndPredicate(new RunnerIsMale(), new RunnerTimeUnder4hr())),
				new ConsList<Runner>(this.frank,
						new ConsList<Runner>(this.bill, mtRunnerlist))) &&
						t.checkExpect(this.runnerlist2.filter(
								new AndPredicate(new RunnerIsFemale(),
										new AndPredicate(new RunnerTimeUnder4hr(),
												new RunnerIsInFirst50()))),
								new ConsList<Runner>(this.joan, mtRunnerlist));
	}
	boolean testOrQuestions(Tester t) {
		return t.checkExpect(this.runnerlist2.filter(
				new OrPredicate(new RunnerIsFemale(), new RunnerTimeUnder4hr())),
				new ConsList<Runner>(this.frank,
						new ConsList<Runner>(this.bill, new ConsList<Runner>(this.joan, mtRunnerlist))));
	}
	boolean testSort(Tester t) {
		return t.checkExpect(this.runnerlist2.sort(new CompareByTime()), this.list_sortedTime) &&
						t.checkExpect(this.runnerlist2.sort(new CompareByName()), this.list_sortedName);
	}
	boolean testLength(Tester t) {
		return t.checkExpect(this.runnerlist2.length(), 4) &&
						t.checkExpect(this.runnerlist1.length(), 2) &&
						t.checkExpect(this.mtRunnerlist.length(), 0) &&
						t.checkExpect(this.threeBooks.length(), 3);
	}

	IList<Circle> circs = new ConsList<Circle>(new Circle(3, 4, 5),
			new MtList<Circle>());
	IList<Double> circPerims = circs.map(new CirclePerimeter());
	boolean testMap(Tester t) {
		return t.checkExpect(this.circs.map(new CirclePerimeter()), circPerims);
	}
	boolean testFoldr(Tester t) {
		return t.checkExpect(new Utils().totalPrice(threeBooks), 9015.99);
	}
	IShape c1 = new Circle(3, 4, 5);
	IShape c2 = new Circle(4, 5, 6);
	IShape c3 = new Circle(3, 4, 5);

	IShape r1 = new Rect(3, 4, 5, 5);
	IShape r2 = new Rect(4, 5, 6, 7);
	IShape r3 = new Rect(3, 4, 5, 5);

	//In test method in an Examples class
	IShape s1 = new Square(3, 4, 5);
	IShape s2 = new Square(4, 5, 6);
	IShape s3 = new Square(3, 4, 5);

	IShape t1 = new Triangle(2, 3, 4, 4);
	IShape t2 = new Triangle(2, 3, 4, 4);

	IShape cmo1 = new Combo(c1, c2);
	IShape cmo2 = new Combo(c1, c2);

	boolean testSameShape(Tester t) {
		return t.checkExpect(c2.sameShape(c1), false) &&
						t.checkExpect(c1.sameShape(c2), false) &&
						t.checkExpect(c3.sameShape(c1), true) &&
						t.checkExpect(c1.sameShape(c3), true) &&

						t.checkExpect(r1.sameShape(r2), false) &&
						t.checkExpect(r2.sameShape(r1), false) &&
						t.checkExpect(r1.sameShape(r3), true) &&
						t.checkExpect(r3.sameShape(r1), true) &&

						t.checkExpect(s1.sameShape(s2), false) &&
						t.checkExpect(s2.sameShape(s1), false) &&
						t.checkExpect(s1.sameShape(s3), true) &&
						t.checkExpect(s3.sameShape(s1), true) &&

						t.checkExpect(s1.sameShape(r2), false) && // Good
						t.checkExpect(r2.sameShape(s1), false) && // Good
						// Comparing a Square with a Rect of the same size
						t.checkExpect(s1.sameShape(r1), false) && // Good
						t.checkExpect(r1.sameShape(s1), false) && // Not so good

						t.checkExpect(t1.sameShape(c1), false) &&
						t.checkExpect(t1.sameShape(s1), false) &&
						t.checkExpect(t1.sameShape(r1), false) &&
						t.checkExpect(t1.sameShape(t2), true) &&
						t.checkExpect(t2.sameShape(t1), true) &&

						t.checkExpect(cmo1.sameShape(c1), false) &&
						t.checkExpect(cmo1.sameShape(s1), false) &&
						t.checkExpect(cmo1.sameShape(r1), false) &&
						t.checkExpect(cmo1.sameShape(t2), false) &&
						t.checkExpect(cmo1.sameShape(cmo2), true) &&
						t.checkExpect(cmo2.sameShape(cmo1), true);

	}
	boolean testGetArea(Tester t) {
		return t.checkInexact(this.c1.getArea(), 31.41, 0.01) &&
						t.checkInexact(this.r1.getArea(), 25.0, 0.01) &&
						t.checkInexact(this.s1.getArea(), 25.0, 0.01) &&
						t.checkInexact(this.t1.getArea(), 8.0, 0.01) &&
						t.checkInexact(this.cmo1.getArea(), 69.11, 0.01);
	}
}
