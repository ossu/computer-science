import tester.Tester;

//+----------------------------------------+
//       |               +------------+           |
//       V               |            V           |
//+--------------+       |     +---------------+  |
//| Author       |       |     | Book          |  |
//+--------------+       |     +---------------+  |
//| String first |       |     | String title  |  |
//| String last  |       |     | int price     |  |
//| int yob      |       |     | int quantity  |  |
//| Book book    |-------+     | Author author |--+
//+--------------+             +---------------+

// Represents authors of books
class Author {
	String first;
	String last;
	int yob;
	Book book;
	Author(String fst, String lst, int yob, Book bk) {
		this.first = fst;
		this.last = lst;
		this.yob = yob;
		this.book = bk;
	}
	//Computes whether the given author has the same name and year of birth
	//as this author (i.e., we're ignoring their books)
	boolean sameAuthor(Author that) {
		return this.first.equals(that.first) &&
						this.last.equals(that.last) &&
						this.yob == that.yob;
	}
}

// Represent books
class Book {
	String title;
	int price;
	int quantity;
	Author author;
	Book(String title, int price, int quantity, Author ath) {
		this.title = title;
		this.price = price;
		this.quantity = quantity;
		this.author = ath;
	}
	boolean sameBook(Book that) {
		return this.title.equals(that.title) &&
						this.price == that.price &&
						this.quantity == that.quantity;
	}
}

class ExamplesAuthors {
	boolean testBookAuthorCycle(Tester t) {
		// Creates an Author whose book is **null**...
		Author knuth = new Author("Donald", "Knuth", 1938, null);
		// Creates a Book whose author is ok, but the author's book is still null...
		Book taocp = new Book("The Art of Computer Programming (volume 1)", 100, 2, knuth);
		// Now *change* the author's book field to be our newly created book...
		knuth.book = taocp;
		// This now passes!
		Author knuth2 = new Author("Donald", "Knuth", 1938, null);
		// Creates a Book whose author is ok, but the author's book is still null...
		Book taocp2 = new Book("The Art of Computer Programming (volume 1)", 100, 2, knuth);
		// Now *change* the author's book field to be our newly created book...
		knuth.book = taocp;
		// This now passes!
		return t.checkExpect(knuth.book.author, knuth) &&
						t.checkExpect(knuth.sameAuthor(knuth2), true) &&
						t.checkExpect(taocp.sameBook(taocp2), true);
	}
}
