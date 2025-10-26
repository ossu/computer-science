
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

}

interface ILoRunner {

	ILoRunner findAllMaleRunners();
	ILoRunner findAllFemaleRunners();
	ILoRunner find(IRunnerPredicate pred);

}

class MtLoRunner implements ILoRunner {
	MtLoRunner() {}

	public ILoRunner findAllMaleRunners() {
		return this;
	}
	public ILoRunner findAllFemaleRunners() {
		return this;
	}

	public ILoRunner find(IRunnerPredicate pred) {
		return this;
	}
}

class ConsLoRunner implements ILoRunner {
	Runner first;
	ILoRunner rest;

	ConsLoRunner(Runner first, ILoRunner rest) {
		this.first = first;
		this.rest = rest;
	}

	public ILoRunner find(IRunnerPredicate pred) {
		if (pred.apply(this.first)) {
			return new ConsLoRunner(this.first, this.rest.find(pred));
		} else {
			return this.rest.find(pred);
		}
	}

	public ILoRunner findAllMaleRunners() {
		if (this.first.isMale) {
			return new ConsLoRunner(this.first, this.rest.findAllMaleRunners());
		} else {
			return this.rest.findAllMaleRunners();
		}
	}
	public ILoRunner findAllFemaleRunners() {
		if (!this.first.isMale) {
			return new ConsLoRunner(this.first, this.rest.findAllFemaleRunners());
		} else {
			return this.rest.findAllFemaleRunners();
		}
	}
}

interface IRunnerPredicate {
	boolean apply(Runner r);
}

class RunnerIsMale implements IRunnerPredicate {
	public boolean apply(Runner r) {
		return r.isMale;
	}
}

class RunnerIsFemale implements IRunnerPredicate {
	public boolean apply(Runner r) {
		return !r.isMale;
	}
}

class RunnerIsInFirst50 implements IRunnerPredicate {
	public boolean apply(Runner r) {
		return r.pos <= 50;
	}
}

class RunnerTimeUnder4hr implements IRunnerPredicate {
	public boolean apply(Runner r) {
		return r.time < 4 * 60;
	}
}

class AndPredicate implements IRunnerPredicate {
	IRunnerPredicate p1;
	IRunnerPredicate p2;
	AndPredicate(IRunnerPredicate p1, IRunnerPredicate p2) {
		this.p1 = p1;
		this.p2 = p2;
	}
	public boolean apply(Runner r) {
		return this.p1.apply(r) && this.p2.apply(r);
	}
}

class OrPredicate implements IRunnerPredicate {
	IRunnerPredicate p1;
	IRunnerPredicate p2;
	OrPredicate(IRunnerPredicate p1, IRunnerPredicate p2) {
		this.p1 = p1;
		this.p2 = p2;
	}
	public boolean apply(Runner r) {
		return this.p1.apply(r) || this.p2.apply(r);
	}
}

// Examples and tests for the Boston Marathon program
class ExamplesMarathon {

	Runner johnny = new Runner("Kelly", 97, 999, true, 30, 360);
	Runner frank = new Runner("Shorter", 32, 888, true, 245, 130);
	Runner bill = new Runner("Rogers", 36, 777, true, 119, 129);
	Runner joan = new Runner("Benoit", 29, 444, false, 18, 155);

	ILoRunner mtlist = new MtLoRunner();
	ILoRunner list1 = new ConsLoRunner(johnny, new ConsLoRunner(joan, mtlist));
	ILoRunner list2 = new ConsLoRunner(frank, new ConsLoRunner(bill, list1));

	boolean testFindMethods(Tester t) {
		return t.checkExpect(this.list2.findAllFemaleRunners(),
				new ConsLoRunner(this.joan, new MtLoRunner())) &&
						t.checkExpect(this.list2.findAllMaleRunners(),
								new ConsLoRunner(this.frank,
										new ConsLoRunner(this.bill,
												new ConsLoRunner(this.johnny, new MtLoRunner()))));
	}
	boolean testFindPredMethods(Tester t) {
		return t.checkExpect(this.list2.find(new RunnerIsFemale()),
				new ConsLoRunner(this.joan, new MtLoRunner())) &&
						t.checkExpect(this.list2.find(new RunnerIsMale()),
								new ConsLoRunner(this.frank,
										new ConsLoRunner(this.bill,
												new ConsLoRunner(this.johnny, new MtLoRunner())))) &&
						t.checkExpect(this.list1.find(new RunnerTimeUnder4hr()),
								new ConsLoRunner(joan, mtlist)) &&
						t.checkExpect(this.list2.find(new RunnerTimeUnder4hr()),
								new ConsLoRunner(this.frank,
										new ConsLoRunner(this.bill,
												new ConsLoRunner(this.joan, new MtLoRunner()))));
	}
	boolean testCombinedQuestions(Tester t) {
		return t.checkExpect(this.list2.find(
				new AndPredicate(new RunnerIsMale(), new RunnerTimeUnder4hr())),
				new ConsLoRunner(this.frank,
						new ConsLoRunner(this.bill, new MtLoRunner()))) &&
						t.checkExpect(this.list2.find(
								new AndPredicate(new RunnerIsFemale(),
										new AndPredicate(new RunnerTimeUnder4hr(),
												new RunnerIsInFirst50()))),
								new ConsLoRunner(this.joan, new MtLoRunner()));
	}
	boolean testOrQuestions(Tester t) {
		return t.checkExpect(this.list2.find(
				new OrPredicate(new RunnerIsFemale(), new RunnerTimeUnder4hr())),
				new ConsLoRunner(this.frank,
						new ConsLoRunner(this.bill, new ConsLoRunner(this.joan, new MtLoRunner()))));

	}
}
