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
	IList<Book> book;
	Author(String fst, String lst, int yob) {
		this.first = fst;
		this.last = lst;
		this.yob = yob;
		this.book = new MtList<Book>();
	}
	//Computes whether the given author has the same name and year of birth
	//as this author (i.e., we're ignoring their books)
	boolean sameAuthor(Author that) {
		return this.first.equals(that.first) &&
						this.last.equals(that.last) &&
						this.yob == that.yob;
	}
	//EFFECT: modifies this Author's book field to refer to the given Book
	void addBook(Book b) {
		if (!this.sameAuthor(b.author)) {
			throw new RuntimeException("book was not written by this author");
		} else {
			this.book = new ConsList<Book>(b, this.book);
		}
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
		this.author.addBook(this);
	}
	boolean sameBook(Book that) {
		return this.title.equals(that.title) &&
						this.price == that.price &&
						this.quantity == that.quantity;
	}
}

class ExamplesAuthor {
	Author knuth;
	Book taocp1;
	Author knuth2;
	Book taocp2;
	void initConditions() {
		this.knuth = new Author("Donald", "Knuth", 1938);
		// Creates a Book whose author is ok, but the author's book is still null...
		this.taocp1 = new Book("The Art of Computer Programming (volume 1)", 100, 2, knuth);
		// This now passes!
		this.knuth2 = new Author("Donald", "Knuth", 1938);
		// Creates a Book whose author is ok, but the author's book is still null...
		this.taocp2 = new Book("The Art of Computer Programming (volume 1)", 100, 2, knuth);
	}
}
