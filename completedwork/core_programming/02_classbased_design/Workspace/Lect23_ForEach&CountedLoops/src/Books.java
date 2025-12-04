class Book {
	String name;
	String author;
	double price;
	int year;
	Book(String name, String author, double price, int year) {
		this.name = name;
		this.author = author;
		this.price = price;
		this.year = year;
	}
	double getPrice() {
		return this.price;
	}
	// to return the discounted price of this book given the discount rate
	double discount(double rate) {
		return this.price - (rate * this.price);
	}
	// to return a new book with the same author and name as this book,
	// but with price discounted at the given rate
	Book discountedBook(double rate) {
		return new Book(this.name, this.author, this.discount(rate), this.year);
	}
	void capitalizeTitle() {
		this.name = this.name.toUpperCase();
	}
}
