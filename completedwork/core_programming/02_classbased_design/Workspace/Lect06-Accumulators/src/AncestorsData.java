import tester.Tester;

/**
 * HtDC Lectures Lecture 3: Data Definitions: Unions
 * 
 * Copyright 2013 Viera K. Proulx This program is distributed under the terms of
 * the GNU Lesser General Public License (LGPL)
 * 
 * @since 29 August 2013
 */

interface IAT {
	// To compute the number of known ancestors of this ancestor tree
	// (excluding this ancestor tree itself)
	int count();
	int countHelper();

	// To compute how many ancestors of this ancestor tree (excluding this
	// ancestor tree itself) are women older than 40 (in the current year)?
	int femaleAncOver40();
	int femaleAncOver40Helper();

	// return the number of generations that are fully known
	int numTotalGens();

	// return the number of generations that are partially known
	int numPartialGens();

	// To compute whether this ancestor tree is well-formed: are all known
	// people younger than their parents?
	boolean wellFormed();

	boolean bornBefore(int childYob);

	// To compute the names of all the known ancestors in this ancestor tree
	// (including this ancestor tree itself)
	ILoString ancNames();
	ILoString ancNamesHelper(String childName, IAT mom, IAT dad, ILoString rsf);

	// To compute this ancestor tree's youngest grandparent
	IAT youngestGrandparent();
	IAT youngestParent();
	IAT youngerIAT(IAT other);
	IAT youngerIATHelper(IAT other, int otherYob);

	// To compute the youngest ancestor in the given generation of this ancestry
	// tree
	IAT youngestAncInGen(int gen);
}

class Unknown implements IAT {
	Unknown() {}

	public int count() {
		return 0;
	}
	public int countHelper() {
		return 0;
	}
	public int femaleAncOver40() {
		return 0;
	}
	public int femaleAncOver40Helper() {
		return 0;
	}
	public int numTotalGens() {
		return 0;
	}
	public int numPartialGens() {
		return 0;
	}
	public ILoString ancNames() {
		return new MtLoString();
	}
	public ILoString ancNamesHelper(String childName, IAT mom, IAT dad, ILoString rsf) {
		return new ConsLoString(childName, rsf);
	}
	// To determine if this Unknown is well-formed
	public boolean wellFormed() {
		return true;
	}
	public boolean bornBefore(int childYob) {
		return true;
	}
	public IAT youngestGrandparent() {
		return new Unknown();
	}
	public IAT youngestParent() {
		return new Unknown();
	}
	public IAT youngerIAT(IAT other) {
		return other;
	}
	public IAT youngerIATHelper(IAT other, int otherYob) {
		return other;
	}
	public IAT youngestAncInGen(int gen) {
		if (gen == 0) {
			return this;
		} else {
			return new Unknown();
		}
	}
}

class Person implements IAT {
	String name;
	int yob;
	boolean isMale;
	IAT mom;
	IAT dad;

	Person(String name, int yob, boolean isMale, IAT mom, IAT dad) {
		this.name = name;
		this.yob = yob;
		this.isMale = isMale;
		this.mom = mom;
		this.dad = dad;
	}
	public int count() {
		return this.mom.countHelper() + this.dad.countHelper();
	}
	public int countHelper() {
		return 1 + this.mom.countHelper() + this.dad.countHelper();
	}
	public int femaleAncOver40() {
		return this.mom.femaleAncOver40Helper() + this.dad.femaleAncOver40Helper();
	}
	public int femaleAncOver40Helper() {
		if (2015 - this.yob > 40 && this.isMale == false) {
			return 1 + this.mom.femaleAncOver40Helper() + this.dad.femaleAncOver40Helper();
		} else {
			return this.mom.femaleAncOver40Helper() + this.dad.femaleAncOver40Helper();
		}
	}
	public int numTotalGens() {
		return 1 + Math.min(this.mom.numTotalGens(), this.dad.numTotalGens());
	}
	public int numPartialGens() {
		return 1 + Math.max(this.mom.numPartialGens(), this.dad.numPartialGens());
	}
	public ILoString ancNames() {
		return this.ancNamesHelper(this.name, this.mom, this.dad, new MtLoString());
	}
	public ILoString ancNameHelper(String childName, IAT mom, IAT dad, ILoString rsf) {
		return new ConsLoString(childName, dad.ancNames());
	}
	// To determine if this Person is well-formed
	public boolean wellFormed() {
		/*
		 * Template: Fields: this.yob -- int ... others as before Methods: ... others as
		 * before this.wellFormed() -- boolean Methods of fields: ... others as before
		 * this.mom.wellFormed() -- boolean this.dad.wellFormed() -- boolean
		 */
		return this.mom.bornBefore(this.yob) &&
						this.dad.bornBefore(this.yob) &&
						this.mom.wellFormed() &&
						this.dad.wellFormed();
	}
	public boolean bornBefore(int childYob) {
		return this.yob < childYob;
	}
	public IAT youngestGrandparent() {
		return this.mom.youngestParent().youngerIAT(this.dad.youngestParent());
	}
	public IAT youngestParent() {
		return this.mom.youngerIAT(this.dad);
	}
	public IAT youngerIAT(IAT other) {
		return other.youngerIATHelper(this, this.yob);
	}
	public IAT youngerIATHelper(IAT other, int otherYob) {
		if (otherYob > this.yob) {
			return other;
		} else {
			return this;
		}
	}
	public IAT youngestAncInGen(int gen) {
		if (gen == 0) {
			return this;
		} else {
			return this.mom.youngestAncInGen(gen - 1).youngerIAT(this.dad.youngestAncInGen(gen - 1));
		}
	}
}

interface ILoString {}

class ConsLoString implements ILoString {
	String first;
	ILoString rest;

	ConsLoString(String first, ILoString rest) {
		this.first = first;
		this.rest = rest;
	}
}

class MtLoString implements ILoString {
	MtLoString() {}
}

class ExamplesIAT {
	IAT enid = new Person("Enid", 1904, false, new Unknown(), new Unknown());
	IAT edward = new Person("Edward", 1902, true, new Unknown(), new Unknown());
	IAT emma = new Person("Emma", 1906, false, new Unknown(), new Unknown());
	IAT eustace = new Person("Eustace", 1907, true, new Unknown(), new Unknown());

	IAT david = new Person("David", 1925, true, new Unknown(), this.edward);
	IAT daisy = new Person("Daisy", 1927, false, new Unknown(), new Unknown());
	IAT dana = new Person("Dana", 1933, false, new Unknown(), new Unknown());
	IAT darcy = new Person("Darcy", 1930, false, this.emma, this.eustace);
	IAT darren = new Person("Darren", 1935, true, this.enid, new Unknown());
	IAT dixon = new Person("Dixon", 1936, true, new Unknown(), new Unknown());

	IAT clyde = new Person("Clyde", 1955, true, this.daisy, this.david);
	IAT candace = new Person("Candace", 1960, false, this.dana, this.darren);
	IAT cameron = new Person("Cameron", 1959, true, new Unknown(), this.dixon);
	IAT claire = new Person("Claire", 1956, false, this.darcy, new Unknown());

	IAT bill = new Person("Bill", 1980, true, this.candace, this.clyde);
	IAT bree = new Person("Bree", 1981, false, this.claire, this.cameron);

	IAT andrew = new Person("Andrew", 2001, true, this.bree, this.bill);

	boolean testCount(Tester t) {
		return t.checkExpect(this.andrew.count(), 16) &&
						t.checkExpect(this.david.count(), 1) &&
						t.checkExpect(this.enid.count(), 0) &&
						t.checkExpect(new Unknown().count(), 0);
	}
	boolean testFemaleAncOver40(Tester t) {
		return t.checkExpect(this.andrew.femaleAncOver40(), 7) &&
						t.checkExpect(this.bree.femaleAncOver40(), 3) &&
						t.checkExpect(this.darcy.femaleAncOver40(), 1) &&
						t.checkExpect(this.enid.femaleAncOver40(), 0) &&
						t.checkExpect(new Unknown().femaleAncOver40(), 0);
	}
	boolean testNumGens(Tester t) {
		return t.checkExpect(this.andrew.numTotalGens(), 3) &&
						t.checkExpect(this.andrew.numPartialGens(), 5) &&
						t.checkExpect(this.enid.numTotalGens(), 1) &&
						t.checkExpect(this.enid.numPartialGens(), 1) &&
						t.checkExpect(new Unknown().numTotalGens(), 0) &&
						t.checkExpect(new Unknown().numPartialGens(), 0);
	}
	boolean testWellFormed(Tester t) {
		return t.checkExpect(this.andrew.wellFormed(), true) &&
						t.checkExpect(new Unknown().wellFormed(), true) &&
						t.checkExpect(
								new Person("Zane", 2000, true, this.andrew, this.bree).wellFormed(), false);
	}
	boolean testAncNames(Tester t) {
		return t.checkExpect(this.david.ancNames(),
				new ConsLoString("David", new ConsLoString("Edward", new MtLoString()))) &&
						t.checkExpect(this.eustace.ancNames(), new ConsLoString("Eustace", new MtLoString())) &&
						t.checkExpect(new Unknown().ancNames(), new MtLoString());
	}
	boolean testYoungestGrandparent(Tester t) {
		return t.checkExpect(this.emma.youngestGrandparent(), new Unknown()) &&
						t.checkExpect(this.david.youngestGrandparent(), new Unknown()) &&
						t.checkExpect(this.claire.youngestGrandparent(), this.eustace) &&
						t.checkExpect(this.bree.youngestGrandparent(), this.dixon) &&
						t.checkExpect(this.andrew.youngestGrandparent(), this.candace) &&
						t.checkExpect(new Unknown().youngestGrandparent(), new Unknown());
	}
	boolean testYoungerIAT(Tester t) {
		return t.checkExpect(this.andrew.youngerIAT(this.bree), this.andrew) &&
						t.checkExpect(this.clyde.youngerIAT(this.candace), this.candace);
	}
	boolean testYoungestParent(Tester t) {
		return t.checkExpect(this.andrew.youngestParent(), this.bree) &&
						t.checkExpect(this.bree.youngestParent(), this.cameron);
	}

}
