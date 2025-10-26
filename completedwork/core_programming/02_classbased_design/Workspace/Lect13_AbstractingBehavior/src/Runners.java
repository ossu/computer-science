import tester.Tester;

// Method that compares two runners by some criterion
interface ICompRunners {
	// is the first runner 'better' than the second runner?
	public boolean compRunners(Runner r1, Runner r2);
}

// Compare runners by their names
class ByName implements ICompRunners {
	// is runner r1's name before runner r2's?
	public boolean compRunners(Runner r1, Runner r2) {
		return r1.name.compareTo(r2.name) < 0;
	}
}

// Compare runners by age
class ByAge implements ICompRunners {
	// is runner r1 younger than runner r2?
	public boolean compRunners(Runner r1, Runner r2) {
		return r1.age < r2.age;
	}
}

// to represent a runner in the Boston Marathon
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

	/* TEMPLATE
	Fields:
	  ... this.name ...     -- String
	  ... this.age ...      -- int
	  ... this.bib ...      -- int
	  ... this.isMale ...   -- isMale
	  ... this.pos ...      -- int
	  ... this.time ...     -- int
	Methods:
	... this.byName(Runner) ...    -- boolean
	... this.byAge(Runner) ...     -- boolean
	*/
	// determine whether this runner's name comes before the given one's
	boolean byName(Runner r) {
		return this.name.compareTo(r.name) < 0;
	}

	// determine whether this runner is younger than the given runner
	boolean byAge(Runner r) {
		return this.age < r.age;
	}
}

// to represent a list of runners in the Boston Marathon
abstract class ALoR {

	// produce a sorted list from this list of runners - by name
	abstract ALoR sortByName();

	// produce a sorted list from this list of runners - by age
	abstract ALoR sortByAge();

	// produce a sorted list from this list of runners as given by comp
	abstract ALoR sort(ICompRunners comp);

	// insert the given runner into this sorted by name list of runners
	abstract ALoR insertByName(Runner r);

	// insert the given runner into this sorted by age list of runners
	abstract ALoR insertByAge(Runner r);

	// insert the given runner into this sorted by name list of runners
	abstract ALoR insert(Runner r, ICompRunners comp);
}

// to represent an empty list of runners in Boston Marathon
class MTLoR extends ALoR {

	MTLoR() {}

	ALoR sortByName() {
		return this;
	}
	ALoR sortByAge() {
		return this;
	}
	ALoR sort(ICompRunners comp) {
		return this;
	}

	ALoR insertByName(Runner r) {
		return new ConsLoR(r, this);
	}
	ALoR insertByAge(Runner r) {
		return new ConsLoR(r, this);
	}
	ALoR insert(Runner r, ICompRunners comp) {
		return new ConsLoR(r, this);
	}
}

// to represent a nonempty list of runners in Boston Marathon
class ConsLoR extends ALoR {
	Runner first;
	ALoR rest;

	ConsLoR(Runner first, ALoR rest) {
		this.first = first;
		this.rest = rest;
	}

	/* TEMPLATE
	Fields:
	  ... this.first ...     -- Runner
	  ... this.rest ...      -- ALoR
	  
	Methods:
	... this.sortByName() ...          -- ALoR
	... this.sortByAge() ...           -- ALoR
	... this.sort(ICompRunners) ...    -- ALoR
	... this.insertByName() ...          -- ALoR
	... this.insertByAge() ...           -- ALoR
	... this.insert(ICompRunners) ...    -- ALoR
	
	Methods for fields:
	... this.first.byName(Runner) ...    -- boolean
	... this.first.byAge(Runner) ...     -- boolean
	
	... this.rest.sortByName() ...          -- ALoR
	... this.rest.sortByAge() ...           -- ALoR
	... this.rest.sort(ICompRunners) ...    -- ALoR
	... this.rest.insertByName() ...          -- ALoR
	... this.rest.insertByAge() ...           -- ALoR
	... this.rest.insert(ICompRunners) ...    -- ALoR
	*/
	// produce a sorted list (by names) from this list of runners
	ALoR sortByName() {
		// insert the first item into the sorted rest of this list
		return this.rest.sortByName().insertByName(this.first);
	}

	// produce a sorted list (by ages) from this list of runners
	ALoR sortByAge() {
		// insert the first item into the sorted rest of this list
		return this.rest.sortByAge().insertByAge(this.first);
	}

	// produce a sorted list from this list of runners
	ALoR sort(ICompRunners comp) {
		// insert the first item into the sorted rest of this list
		return this.rest.sort(comp).insert(this.first, comp);
	}

	// insert the given runner (by name) into this sorted list of runners
	ALoR insertByName(Runner r) {
		if (r.byName(this.first))
			return new ConsLoR(r, this);
		else
			return new ConsLoR(this.first, this.rest.insertByName(r));
	}

	// insert the given runner (by name) into this sorted list of runners
	ALoR insertByAge(Runner r) {
		if (r.byAge(this.first))
			return new ConsLoR(r, this);
		else
			return new ConsLoR(this.first, this.rest.insertByAge(r));
	}

	// insert the given runner into this sorted list of runners
	ALoR insert(Runner r, ICompRunners comp) {
		if (comp.compRunners(r, this.first))
			return new ConsLoR(r, this);
		else
			return new ConsLoR(this.first, this.rest.insert(r, comp));
	}
}

// Examples and tests for the Boston Marathon program
class ExamplesMarathon {

	Runner johnny = new Runner("Kelly", 100, 999, true, 30, 360);
	Runner frank = new Runner("Shorter", 32, 888, true, 245, 130);
	Runner bill = new Runner("Rogers", 36, 777, true, 119, 129);
	Runner joan = new Runner("Benoit", 29, 444, false, 18, 155);

	ALoR mtlist = new MTLoR();
	ALoR list1 = new ConsLoR(johnny, new ConsLoR(joan, mtlist));
	ALoR list2 = new ConsLoR(frank, new ConsLoR(bill, list1));

	//test the method sortByName for lists of runners
	boolean testSortNames1(Tester t) {
		return t.checkExpect(this.list2.sortByName(),
				new ConsLoR(joan,
						new ConsLoR(johnny,
								new ConsLoR(bill,
										new ConsLoR(frank, mtlist)))));
	}

	// test the method sortByAge for lists of runners
	boolean testSortAges1(Tester t) {
		return t.checkExpect(this.list2.sortByAge(),
				new ConsLoR(joan,
						new ConsLoR(frank,
								new ConsLoR(bill,
										new ConsLoR(johnny, mtlist)))));
	}

	//test the method sort for lists of runners using ByName function object
	boolean testSortNames2(Tester t) {
		return t.checkExpect(this.list2.sort(new ByName()),
				new ConsLoR(joan,
						new ConsLoR(johnny,
								new ConsLoR(bill,
										new ConsLoR(frank, mtlist)))));
	}

	//test the method sort for lists of runners using ByAge function object
	boolean testSortAges2(Tester t) {
		return t.checkExpect(this.list2.sort(new ByAge()),
				new ConsLoR(joan,
						new ConsLoR(frank,
								new ConsLoR(bill,
										new ConsLoR(johnny, mtlist)))));
	}
}
