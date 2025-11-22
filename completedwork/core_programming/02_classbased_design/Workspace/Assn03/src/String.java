// CS 2510, Assignment 3

import tester.Tester;

// to represent a list of Strings
interface ILoString {
	// combine all Strings in this list into one
	String combine();
	// returns a new list, sorted in alphabetical order, treating all Strings 
	// as if they were given in all lowercase.
	ILoString sort();
	ILoString sortHelp(String that);
	// returns if this list is sorted in alphabetical order, in a case-insensitive way.
	boolean isSorted();
	boolean isSortedHelp(String that);
	// returns a list where the first, third, fifth... elements are from this
	// list, and the second, fourth, sixth... elements are from the given list.
	// When one list is longer than the other extras will  be left at the end.
	ILoString interleave(ILoString that);
	ILoString interleaveHelp(String prefix, ILoString that);
	// returns a sorted list of Strings that contains all items in both original 
	// lists, including duplicates.
	ILoString merge(ILoString that);
	// returns a list of Strings containing the list of string in reverse order.
	ILoString reverse();
	ILoString reverseHelp(ILoString rsf);
	// returns if this list contains pairs of identical strings, that is, the first 
	// and second strings are the same, the third and fourth are the same, etc.
	boolean isDoubledList();
	boolean isDoubledListHelp(String check);
	// returns whether this list contains the same words reading the list in either order.
	boolean isPalindromeList();
}

// to represent an empty list of Strings
class MtLoString implements ILoString {
	MtLoString() {}

	// combine all Strings in this list into one
	public String combine() {
		return "";
	}
	public ILoString sort() {
		return this;
	}
	public ILoString sortHelp(String that) {
		return new ConsLoString(that, this);
	}
	public boolean isSorted() {
		return true;
	}
	public boolean isSortedHelp(String that) {
		return true;
	}
	public ILoString interleave(ILoString that) {
		return that;
	}
	public ILoString interleaveHelp(String prefix, ILoString that) {
		return new ConsLoString(prefix, that);
	}
	public ILoString merge(ILoString that) {
		return that.sort();
	}
	public ILoString reverse() {
		return this;
	}
	public ILoString reverseHelp(ILoString rsf) {
		return rsf;
	}
	public boolean isDoubledList() {
		return true;
	}
	public boolean isDoubledListHelp(String check) {
		return false;
	}
	public boolean isPalindromeList() {
		return false;
	}
}

// to represent a nonempty list of Strings
class ConsLoString implements ILoString {
	String first;
	ILoString rest;

	ConsLoString(String first, ILoString rest) {
		this.first = first;
		this.rest = rest;
	}
	/*
	 TEMPLATE
	 FIELDS:
	 ... this.first ...         -- String
	 ... this.rest ...          -- ILoString
	 
	 METHODS
	 ... this.combine() ...     -- String
	 
	 METHODS FOR FIELDS
	 ... this.first.concat(String) ...        -- String
	 ... this.first.compareTo(String) ...     -- int
	 ... this.rest.combine() ...              -- String
	 
	 */

	// combine all Strings in this list into one
	public String combine() {
		return this.first.concat(this.rest.combine());
	}
	public ILoString sort() {
		return this.rest.sort().sortHelp(this.first);
	}
	public ILoString sortHelp(String that) {
		if (this.first.compareTo(that) < 0) {
			return new ConsLoString(this.first, rest.sortHelp(that));
		} else {
			return new ConsLoString(that, rest.sortHelp(this.first));
		}
	}
	public boolean isSorted() {
		return this.rest.isSortedHelp(this.first);
	}
	public boolean isSortedHelp(String that) {
		return this.first.compareTo(that) > 0 && this.rest.isSortedHelp(this.first);
	}
	public ILoString interleave(ILoString that) {
		return that.interleaveHelp(this.first, this.rest);
	}
	public ILoString interleaveHelp(String prefix, ILoString that) {
		return new ConsLoString(prefix, that.interleaveHelp(this.first, this.rest));
	}
	public ILoString merge(ILoString that) {
		return this.interleave(that).sort();
	}
	public ILoString reverse() {
		return this.rest.reverseHelp(new ConsLoString(this.first, new MtLoString()));
	}
	public ILoString reverseHelp(ILoString rsf) {
		return this.rest.reverseHelp(new ConsLoString(this.first, rsf));
	}
	public boolean isDoubledList() {
		return this.rest.isDoubledListHelp(this.first);
	}
	public boolean isDoubledListHelp(String check) {
		return check == this.first && rest.isDoubledList();
	}
	public boolean isPalindromeList() {
		return this.interleave(this.reverse()).isDoubledList();
	}
}

// to represent examples for lists of strings
class ExamplesStrings {

	ILoString mary = new ConsLoString("Mary ",
			new ConsLoString("had ",
					new ConsLoString("a ",
							new ConsLoString("little ",
									new ConsLoString("lamb.", new MtLoString())))));
	ILoString marySorted = new ConsLoString("Mary ",
			new ConsLoString("a ",
					new ConsLoString("had ",
							new ConsLoString("lamb.",
									new ConsLoString("little ", new MtLoString())))));
	ILoString maryReverse = new ConsLoString("lamb.",
			new ConsLoString("little ",
					new ConsLoString("a ",
							new ConsLoString("had ",
									new ConsLoString("Mary ", new MtLoString())))));

	ILoString abcLower = new ConsLoString("a",
			new ConsLoString("b", new ConsLoString("c", new MtLoString())));
	ILoString cbaLower = new ConsLoString("c",
			new ConsLoString("b", new ConsLoString("a", new MtLoString())));
	ILoString defghLower = new ConsLoString("d", new ConsLoString("e",
			new ConsLoString("f", new ConsLoString("g", new ConsLoString("h", new MtLoString())))));
	ILoString abcdefghLower = new ConsLoString("a", new ConsLoString("b",
			new ConsLoString("c", new ConsLoString("d",
					new ConsLoString("e", new ConsLoString("f",
							new ConsLoString("g", new ConsLoString("h", new MtLoString()))))))));
	ILoString abcUpper = new ConsLoString("A",
			new ConsLoString("B", new ConsLoString("C", new MtLoString())));
	ILoString acbMixed = new ConsLoString("A",
			new ConsLoString("C", new ConsLoString("b", new MtLoString())));
	ILoString bcaLower = new ConsLoString("b",
			new ConsLoString("c", new ConsLoString("a", new MtLoString())));
	ILoString bcaMixed = new ConsLoString("b",
			new ConsLoString("C", new ConsLoString("A", new MtLoString())));
	ILoString abcInterleave = new ConsLoString("a", new ConsLoString("A",
			new ConsLoString("b", new ConsLoString("B",
					new ConsLoString("c", new ConsLoString("C", new MtLoString()))))));
	ILoString adbecfghInterleave = new ConsLoString("a", new ConsLoString("d",
			new ConsLoString("b", new ConsLoString("e",
					new ConsLoString("c", new ConsLoString("f",
							new ConsLoString("g", new ConsLoString("h", new MtLoString()))))))));
	ILoString daedfcghInterleave = new ConsLoString("d", new ConsLoString("a",
			new ConsLoString("e", new ConsLoString("b",
					new ConsLoString("f", new ConsLoString("c",
							new ConsLoString("g", new ConsLoString("h", new MtLoString()))))))));
	ILoString abcbaLower = new ConsLoString("a", new ConsLoString("b",
			new ConsLoString("c", new ConsLoString("b", new ConsLoString("a", new MtLoString())))));
	ILoString abccbaLower = new ConsLoString("a", new ConsLoString("b",
			new ConsLoString("c", new ConsLoString("c",
					new ConsLoString("b", new ConsLoString("a", new MtLoString()))))));

	boolean testCombine(Tester t) {
		return t.checkExpect(this.mary.combine(), "Mary had a little lamb.");
	}
	boolean testSort(Tester t) {
		return t.checkExpect(this.bcaLower.sort(), this.abcLower) &&
						t.checkExpect(this.bcaMixed.sort(), this.acbMixed) &&
						t.checkExpect(this.mary.sort(), this.marySorted);
	}
	boolean testIsSorted(Tester t) {
		return t.checkExpect(this.abcLower.isSorted(), true) &&
						t.checkExpect(this.bcaLower.isSorted(), false) &&
						t.checkExpect(this.mary.isSorted(), false) &&
						t.checkExpect(this.marySorted.isSorted(), true);
	}
	boolean testInterleave(Tester t) {
		return t.checkExpect(this.abcLower.interleave(abcUpper), this.abcInterleave) &&
						t.checkExpect(this.abcLower.interleave(defghLower), this.adbecfghInterleave) &&
						t.checkExpect(this.defghLower.interleave(abcLower), this.daedfcghInterleave);
	}
	boolean testMerge(Tester t) {
		return t.checkExpect(this.abcLower.merge(abcUpper), this.abcInterleave.sort()) &&
						t.checkExpect(this.abcLower.merge(defghLower), this.abcdefghLower) &&
						t.checkExpect(this.defghLower.merge(abcLower), this.abcdefghLower);
	}
	boolean testReverse(Tester t) {
		return t.checkExpect(this.abcLower.reverse(), this.cbaLower) &&
						t.checkExpect(this.mary.reverse(), this.maryReverse);
	}
	boolean testIsDoubledList(Tester t) {
		return t.checkExpect(this.abcLower.merge(this.abcLower).isDoubledList(), true) &&
						t.checkExpect(this.mary.isDoubledList(), false);
	}
	boolean testIsPalindromeList(Tester t) {
		return t.checkExpect(this.abcbaLower.isPalindromeList(), true) &&
						t.checkExpect(this.abccbaLower.isPalindromeList(), true) &&
						t.checkExpect(this.mary.isPalindromeList(), false);
	}
}
