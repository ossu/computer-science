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

//interface ILoPerson {
//	// Returns true if this list contains a person with the given name
//	boolean contains(String name);
//	// Finds the person in this list with the given name and returns their phone number,
//	// or -1 if no such person is found
//	int findPhoneNum(String name);
//	//Change the phone number for the person in this list with the given name
//	void changePhone(String name, int newNum);
//}
//
//class MtLoPerson implements ILoPerson {
//	public boolean contains(String name) {
//		return false;
//	}
//	public int findPhoneNum(String name) {
//		return -1;
//	}
//	public void changePhone(String name, int newNum) {
//		return;
//	}
//}
//
//class ConsLoPerson implements ILoPerson {
//	Person first;
//	ILoPerson rest;
//	ConsLoPerson(Person first, ILoPerson rest) {
//		this.first = first;
//		this.rest = rest;
//	}
//	public boolean contains(String name) {
//		if (this.first.name == name) {
//			return true;
//		} else {
//			return this.rest.contains(name);
//		}
//	}
//	public int findPhoneNum(String name) {
//		if (this.first.name == name) {
//			return this.first.phone;
//		} else {
//			return this.rest.findPhoneNum(name);
//		}
//	}
//	public void changePhone(String name, int newNum) {
//		if (this.first.name.equals(name)) {
//			this.first.changeNum(newNum);
//		} else {
//			this.rest.changePhone(name, newNum);
//		}
//	}
//}

interface IPred<T> {
	boolean apply(T t);
}

class WhichOne implements IPred<Person> {
	Person find;
	WhichOne(Person find) {
		this.find = find;
	}
	public boolean apply(Person p) {
		return p.name == find.name;
	}
}

//result type R
interface IFunc<A, R> {
	R apply(A input);
}

class WhatToDo implements IFunc<Person, Void> {
	int number;
	WhatToDo(int number) {
		this.number = number;
	}
	public Void apply(Person p) {
		p.changeNum(number);
	}
}

interface IList<T> {
	//Finds and returns the person in this list matching the given predicate,
	//or null if no such person is found
	T find(IPred<T> whichOne, IFunc<T, Void> whatToDo);
}

class MtList<T> implements IList<T> {

	public Void find(IPred<T> whichOne, IFunc<T, Void> whatToDo) {
		return null;
	}
}

class ConsList<T> implements IList<T> {
	T first;
	IList<T> rest;
	ConsList(T first, IList<T> rest) {
		this.first = first;
		this.rest = rest;
	}
	public T find(IPred<T> whichOne) {
		if (whichOne.apply(this.first)) {
			return this.first;
		} else {
			return this.rest.find(whichOne);
		}
	}
	public Void find(IPred<T> whichOne, IFunc<T, Void> whatToDo) {
		if (whichOne.apply(this.first)) {
			whatToDo.apply(this.first);
		} else {
			this.rest.find(whichOne, whatToDo);
		}
		return null;
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
	IList<Person> friends, family, work;

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
		//		this.friends = new ConsLoPerson(this.anne, new ConsLoPerson(this.clyde,
		//				new ConsLoPerson(this.gail, new ConsLoPerson(this.frank,
		//						new ConsLoPerson(this.jenny, new MtLoPerson())))));
		//		this.family = new ConsLoPerson(this.anne, new ConsLoPerson(this.dana,
		//				new ConsLoPerson(this.frank, new MtLoPerson())));
		//		this.work = new ConsLoPerson(this.bob, new ConsLoPerson(this.clyde,
		//				new ConsLoPerson(this.dana, new ConsLoPerson(this.eric,
		//						new ConsLoPerson(this.henry, new ConsLoPerson(this.irene,
		//								new MtLoPerson()))))));
		this.friends = new ConsList<Person>(this.anne, new ConsList<Person>(this.clyde,
				new ConsList<Person>(this.gail, new ConsList<Person>(this.frank,
						new ConsList<Person>(this.jenny, new MtList<Person>())))));
		this.family = new ConsList<Person>(this.anne, new ConsList<Person>(this.dana,
				new ConsList<Person>(this.frank, new MtList<Person>())));
		this.work = new ConsList<Person>(this.bob, new ConsList<Person>(this.clyde,
				new ConsList<Person>(this.dana, new ConsList<Person>(this.eric,
						new ConsList<Person>(this.henry, new ConsList<Person>(this.irene,
								new MtList<Person>()))))));
	}

	void testFindPhoneNum(Tester t) {
		this.initData();
		t.checkExpect(this.friends.find("Frank"), 7294);
		t.checkExpect(this.family.find("Frank"),
				this.friends.find("Frank"));
		t.checkExpect(this.frank.phone, 7294);
		this.family.changePhone(this.frank, 9021);
		t.checkExpect(this.friends.find("Frank"), 9021);
		t.checkExpect(this.family.find("Frank"),
				this.friends.find("Frank"));
		t.checkExpect(this.frank.phone, 9021);
	}

	//	void testFindPhoneNum(Tester t) {
	//		this.initData();
	//		t.checkExpect(this.friends.findPhoneNum("Frank"), 7294);
	//		t.checkExpect(this.family.findPhoneNum("Frank"),
	//				this.friends.findPhoneNum("Frank"));
	//		t.checkExpect(this.frank.phone, 7294);
	//		this.family.changePhone("Frank", 9021);
	//		t.checkExpect(this.friends.findPhoneNum("Frank"), 9021);
	//		t.checkExpect(this.family.findPhoneNum("Frank"),
	//				this.friends.findPhoneNum("Frank"));
	//		t.checkExpect(this.frank.phone, 9021);
	//	}
	//	void testChangeNum(Tester t) {
	//		this.initData();
	//		t.checkExpect(this.frank.phone, 7294);
	//		this.frank.changeNum(9021);
	//		t.checkExpect(this.frank.phone, 9021);
	//	}
	//	void testAliasing(Tester t) {
	//		// Create two Person objects that are the same
	//		Person johndoe1 = new Person("John Doe", 12345);
	//		Person johndoe2 = new Person("John Doe", 12345);
	//		// Alias johndoe1 to johndoe3
	//		Person johndoe3 = johndoe1;
	//
	//		// Check that all three John Does are the same according to samePerson
	//		t.checkExpect(johndoe1.samePerson(johndoe2), true);
	//		t.checkExpect(johndoe1.samePerson(johndoe3), true);
	//
	//		// Modify johndoe1
	//		johndoe1.name = "Johnny Deere";
	//
	//		// Now let's try the same tests.  Which of them will pass?
	//		t.checkExpect(johndoe1.samePerson(johndoe2), false);
	//		t.checkExpect(johndoe1.samePerson(johndoe3), true);
	//	}

}
