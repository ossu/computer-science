import tester.Tester;

class Runner {
	String name;
	int age;
	int bib;
	boolean isMale;
	int pos;
	int time;

	Runner(String name, int age, int bib, boolean isMale, int pos, int time) {
		this.name = name;
		this.age = age;
		this.bib = bib;
		this.isMale = isMale;
		this.pos = pos;
		this.time = time;
	}

	public boolean isMaleRunner() {
		return this.isMale;
	}
	public boolean isFemaleRunner() {
		return !this.isMale;
	}
	boolean finishesBefore(Runner r) {
		return this.time < r.time;
	}

}

interface ILoRunner {
	ILoRunner sortBy(ICompareRunners comp);
	ILoRunner insertBy(ICompareRunners comp, Runner r);
	//Finds the fastest Runner in this list of Runners
	Runner findWinner();
	//Finds the first Runner in this list of Runners
	Runner getFirst();
	Runner findMin(ICompareRunners comp);
	Runner findMinHelp(ICompareRunners comp, Runner acc);
	Runner findMax(ICompareRunners comp);
}

class MtLoRunner implements ILoRunner {
	MtLoRunner() {}
	public ILoRunner sortBy(ICompareRunners comp) {
		return this;
	}
	public ILoRunner insertBy(ICompareRunners comp, Runner r) {
		return new ConsLoRunner(r, this);
	}
	public Runner findWinner() {
		throw new RuntimeException("No winner of an empty list of Runners");
	}
	public Runner getFirst() {
		throw new RuntimeException("No first value of an empty list of Runners");
	}
	public Runner findMin(ICompareRunners comp) {
		throw new RuntimeException("No min value of an empty list of Runners");
	}
	public Runner findMinHelp(ICompareRunners comp, Runner acc) {
		return acc;
	}
	public Runner findMax(ICompareRunners comp) {
		throw new RuntimeException("No max value of an empty list of Runners");
	}
}

class ConsLoRunner implements ILoRunner {
	Runner first;
	ILoRunner rest;
	ConsLoRunner(Runner first, ILoRunner rest) {
		this.first = first;
		this.rest = rest;
	}
	public ILoRunner sortBy(ICompareRunners comp) {
		return this.rest.sortBy(comp).insertBy(comp, this.first);
	}
	public ILoRunner insertBy(ICompareRunners comp, Runner r) {
		if (comp.comesBefore(this.first, r) < 0) {
			return new ConsLoRunner(this.first, this.rest.insertBy(comp, r));
		} else {
			return new ConsLoRunner(r, this);
		}
	}
	public Runner findWinner() {
		return this.sortBy(new CompareByTime()).getFirst();
	}
	public Runner getFirst() {
		return this.first;
	}
	public Runner findMin(ICompareRunners comp) {
		return rest.findMinHelp(comp, this.first);
	}
	public Runner findMinHelp(ICompareRunners comp, Runner acc) {
		if (comp.comesBefore(acc, this.first) < 0) {
			return this.rest.findMinHelp(comp, acc);
		} else {
			return this.rest.findMinHelp(comp, this.first);
		}
	}
	public Runner findMax(ICompareRunners comp) {
		return rest.findMinHelp(new ReverseComparator(comp), this.first);
	}
}

class ExamplesMarathon {

	Runner johnny = new Runner("Johnny", 97, 999, true, 30, 360);
	Runner frank = new Runner("Frank", 32, 888, true, 245, 130);
	Runner bill = new Runner("Bill", 36, 777, true, 119, 129);
	Runner joan = new Runner("Joan", 29, 444, false, 18, 155);

	ILoRunner mtlist = new MtLoRunner();
	ILoRunner list1 = new ConsLoRunner(johnny, new ConsLoRunner(joan, mtlist));
	ILoRunner list2 = new ConsLoRunner(frank, new ConsLoRunner(bill, list1));

	ILoRunner list_sortedTime = new ConsLoRunner(this.bill, new ConsLoRunner(this.frank,
			new ConsLoRunner(this.joan, new ConsLoRunner(this.johnny, new MtLoRunner()))));
	ILoRunner list_reverseSortTime = new ConsLoRunner(this.johnny, new ConsLoRunner(this.joan,
			new ConsLoRunner(this.frank, new ConsLoRunner(this.bill, mtlist))));
	ILoRunner list_sortedName = new ConsLoRunner(this.bill, new ConsLoRunner(this.frank,
			new ConsLoRunner(this.joan, new ConsLoRunner(this.johnny, mtlist))));

	boolean testSort(Tester t) {
		return t.checkExpect(this.list2.sortBy(new CompareByTime()), this.list_sortedTime) &&
						t.checkExpect(this.list2.sortBy(new ReverseComparator(new CompareByTime())),
								this.list_reverseSortTime) &&
						t.checkExpect(this.list2.sortBy(new CompareByName()), this.list_sortedName);
	}
	boolean testFindWinner(Tester t) {
		return t.checkExpect(this.list2.findWinner(), this.bill) &&
						t.checkExpect(this.list2.findMin(new CompareByTime()), this.bill) &&
						t.checkExpect(this.list2.findMax(new CompareByTime()), this.johnny) &&
						t.checkExpect(this.list2.findMin(new CompareByName()), this.bill);
	}
}
