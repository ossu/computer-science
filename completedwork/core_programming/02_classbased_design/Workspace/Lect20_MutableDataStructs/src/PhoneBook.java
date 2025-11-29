import tester.Tester;

class Person {
	String name;
	int phone;
	Person(String name, int phone) {
		this.name = name;
		this.phone = phone;
	}
	// Returns true when the given person has the same name and phone number as this person
	boolean samePerson(Person that) {
		return this.name.equals(that.name) && this.phone == that.phone;
	}
	// EFFECT: modifies this person's phone number to be the given one
	void changeNum(int newNum) {
		this.phone = newNum;
	}
}

// Represents a sentinel at the start, a node in the middle,
// or the empty end of a list
abstract class APersonList {
	APersonList() {} // nothing to do here
	abstract boolean contains(String name);
	abstract int findPhoneNum(String name);
	abstract void changePhone(String name, int newNum);
	abstract void removePerson(String name);
	abstract void removePersonHelp(String name, ANode prev);
}

// Represents a node in a list that has some list after it
class ANode extends APersonList {
	APersonList rest;
	ANode(APersonList rest) {
		this.rest = rest;
	}
	boolean contains(String name) {
		return false;
	}
	int findPhoneNum(String name) {
		return -1;
	}
	void changePhone(String name, int newNum) {
		return;
	}
	void removePerson(String name) {
		return;
	}
	void removePersonHelp(String name, ANode prev) {
		return;
	}
}

// Represents the dummy node before the first actual node of the list
class Sentinel extends ANode {
	Sentinel(APersonList rest) {
		super(rest);
	}
	boolean contains(String name) {
		return this.rest.contains(name);
	}
	int findPhoneNum(String name) {
		return this.rest.findPhoneNum(name);
	}
	void removePerson(String name) {
		this.rest.removePersonHelp(name, this);
	}
	void removePersonHelp(String name, ANode prev) {
		throw new RuntimeException("Can't try to remove on a Sentinel!");
	}
	void addPerson(Person p) {
		this.rest = new ConsLoPerson(p, this.rest);
	}
}

// Represents the empty end of the list
class MtLoPerson extends APersonList {
	public boolean contains(String name) {
		return false;
	}
	public int findPhoneNum(String name) {
		return -1;
	}
	public void changePhone(String name, int newNum) {
		return;
	}
	public void removePerson(String name) {
		return;
	}
	void removePersonHelp(String name, ANode prev) {
		return;
	}
}

class ConsLoPerson extends ANode {
	Person first;
	ConsLoPerson(Person data, APersonList rest) {
		super(rest);
		this.first = data;
	}
	public boolean contains(String name) {
		if (this.first.name == name) {
			return true;
		} else {
			return this.rest.contains(name);
		}
	}
	public int findPhoneNum(String name) {
		if (this.first.name == name) {
			return this.first.phone;
		} else {
			return this.rest.findPhoneNum(name);
		}
	}
	public void changePhone(String name, int newNum) {
		if (this.first.name.equals(name)) {
			this.first.changeNum(newNum);
		} else {
			this.rest.changePhone(name, newNum);
		}
	}
	public void removePerson(String name) {
		this.rest.removePersonHelp(name, this);
	}
	void removePersonHelp(String name, ANode prev) {
		if (this.first.name.equals(name)) {
			prev.rest = this.rest;
		} else {
			this.rest.removePersonHelp(name, this);
		}
	}
}

class MutablePersonList {
	Sentinel sentinel;
	MutablePersonList(Sentinel sentinel) {
		this.sentinel = sentinel;
	}
	MutablePersonList() {
		this.sentinel = new Sentinel(new MtLoPerson());
	}
	boolean contains(String name) {
		return this.sentinel.contains(name);
	}
	int findPhoneNum(String name) {
		return this.sentinel.findPhoneNum(name);
	}
	void changePhone(String name, int newNum) {
		this.sentinel.changePhone(name, newNum);
	}
	void removePerson(String name) {
		this.sentinel.rest.removePersonHelp(name, this.sentinel);
	}
	void addPerson(Person p) {
		this.sentinel.addPerson(p);
	}
}

class Examples {
	Person anne;
	Person bob;
	Person clyde;
	Person dana;
	Person eric;
	Person frank;
	Person gail;
	Person henry;
	Person irene;
	Person jenny;
	APersonList friends, family, work;

	void initData() {
		this.anne = new Person("Anne", 1234);
		this.bob = new Person("Bob", 3456);
		this.clyde = new Person("Clyde", 6789);
		this.dana = new Person("Dana", 1357);
		this.eric = new Person("Eric", 12469);
		this.frank = new Person("Frank", 7294);
		this.gail = new Person("Gail", 9345);
		this.henry = new Person("Henry", 8602);
		this.irene = new Person("Irene", 91302);
		this.jenny = new Person("Jenny", 8675309);
		this.friends = new ConsLoPerson(this.anne, new ConsLoPerson(this.clyde,
				new ConsLoPerson(this.gail, new ConsLoPerson(this.frank,
						new ConsLoPerson(this.jenny, new MtLoPerson())))));
		this.family = new ConsLoPerson(this.anne, new ConsLoPerson(this.dana,
				new ConsLoPerson(this.frank, new MtLoPerson())));
		this.work = new ConsLoPerson(this.bob, new ConsLoPerson(this.clyde,
				new ConsLoPerson(this.dana, new ConsLoPerson(this.eric,
						new ConsLoPerson(this.henry, new ConsLoPerson(this.irene,
								new MtLoPerson()))))));
	}
	void testFindPhoneNum(Tester t) {
		this.initData();
		t.checkExpect(this.friends.findPhoneNum("Frank"), 7294);
		t.checkExpect(this.family.findPhoneNum("Frank"),
				this.friends.findPhoneNum("Frank"));
		t.checkExpect(this.frank.phone, 7294);
		this.family.changePhone("Frank", 9021);
		t.checkExpect(this.friends.findPhoneNum("Frank"), 9021);
		t.checkExpect(this.family.findPhoneNum("Frank"),
				this.friends.findPhoneNum("Frank"));
		t.checkExpect(this.frank.phone, 9021);
	}
	void testChangeNum(Tester t) {
		this.initData();
		t.checkExpect(this.frank.phone, 7294);
		this.frank.changeNum(9021);
		t.checkExpect(this.frank.phone, 9021);
	}
	void testAliasing(Tester t) {
		// Create two Person objects that are the same
		Person johndoe1 = new Person("John Doe", 12345);
		Person johndoe2 = new Person("John Doe", 12345);
		// Alias johndoe1 to johndoe3
		Person johndoe3 = johndoe1;

		// Check that all three John Does are the same according to samePerson
		t.checkExpect(johndoe1.samePerson(johndoe2), true);
		t.checkExpect(johndoe1.samePerson(johndoe3), true);

		// Modify johndoe1
		johndoe1.name = "Johnny Deere";

		// Now let's try the same tests.  Which of them will pass?
		t.checkExpect(johndoe1.samePerson(johndoe2), false);
		t.checkExpect(johndoe1.samePerson(johndoe3), true);
	}
	//Tests removing the first person in a list
	void testRemoveFirstPerson(Tester t) {
		this.initData();
		MutablePersonList list1 = new MutablePersonList(
				new Sentinel(new ConsLoPerson(this.anne, new ConsLoPerson(this.clyde,
						new ConsLoPerson(this.henry, new MtLoPerson())))));
		MutablePersonList list2 = new MutablePersonList(
				new Sentinel(new ConsLoPerson(this.anne, new ConsLoPerson(this.clyde,
						new ConsLoPerson(this.gail, new MtLoPerson())))));
		// Check initial conditions
		t.checkExpect(list1.contains("Anne"), true);
		t.checkExpect(list2.contains("Anne"), true);
		// Modify list1
		list1.removePerson("Anne");
		// Check that list1 has been modified...
		t.checkExpect(list1.contains("Anne"), false);
		// ...but that list2 has not
		t.checkExpect(list2.contains("Anne"), true);
	}
	//Tests removing a middle person in a list
	void testRemoveMiddlePerson(Tester t) {
		this.initData();
		MutablePersonList list1 = new MutablePersonList(
				new Sentinel(new ConsLoPerson(this.anne, new ConsLoPerson(this.clyde,
						new ConsLoPerson(this.henry, new MtLoPerson())))));
		MutablePersonList list2 = new MutablePersonList(
				new Sentinel(new ConsLoPerson(this.dana, new ConsLoPerson(this.clyde,
						new ConsLoPerson(this.gail, new MtLoPerson())))));
		// Check initial conditions
		t.checkExpect(list1.contains("Clyde"), true);
		t.checkExpect(list2.contains("Clyde"), true);
		// Modify list1
		list1.removePerson("Clyde");
		// Check that list1 has been modified...
		t.checkExpect(list1.contains("Clyde"), false);
		// ...but that list2 has not
		t.checkExpect(list2.contains("Clyde"), true);
	}
	//Tests removing the last person in a list
	void testRemoveLastPerson(Tester t) {
		this.initData();
		MutablePersonList list1 = new MutablePersonList(
				new Sentinel(new ConsLoPerson(this.anne, new ConsLoPerson(this.clyde,
						new ConsLoPerson(this.henry, new MtLoPerson())))));
		MutablePersonList list2 = new MutablePersonList(
				new Sentinel(new ConsLoPerson(this.dana, new ConsLoPerson(this.clyde,
						new ConsLoPerson(this.henry, new MtLoPerson())))));
		// Check initial conditions
		t.checkExpect(list1.contains("Henry"), true);
		t.checkExpect(list2.contains("Henry"), true);
		// Modify list1
		list1.removePerson("Henry");
		// Check that list1 has been modified...
		t.checkExpect(list1.contains("Henry"), false);
		// ...but that list2 has not
		t.checkExpect(list2.contains("Henry"), true);
	}
	void testAddPerson(Tester t) {
		this.initData();
		MutablePersonList list1 = new MutablePersonList(
				new Sentinel(new ConsLoPerson(this.anne, new ConsLoPerson(this.clyde,
						new ConsLoPerson(this.henry, new MtLoPerson())))));
		MutablePersonList list2 = new MutablePersonList(
				new Sentinel(new ConsLoPerson(this.dana, new ConsLoPerson(this.clyde,
						new ConsLoPerson(this.henry, new MtLoPerson())))));
		// Check initial conditions
		t.checkExpect(list1.contains("Eric"), false);
		t.checkExpect(list2.contains("Eric"), false);
		// Modify list1
		list1.addPerson(this.eric);
		// Check that list1 has been modified...
		t.checkExpect(list1.contains("Eric"), true);
		// ...but that list2 has not
		t.checkExpect(list2.contains("Eric"), false);
	}
}
