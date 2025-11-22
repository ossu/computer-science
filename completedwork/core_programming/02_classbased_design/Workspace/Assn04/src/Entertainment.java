import tester.*;

interface IEntertainment {
	//compute the total price of this Entertainment
	double totalPrice();
	//computes the minutes of entertainment of this IEntertainment
	int duration();
	//produce a String that shows the name and price of this IEntertainment
	String format();
	//is this IEntertainment the same as that one?
	boolean sameEntertainment(IEntertainment that);
	boolean sameMagazine(String name, double price, String genre, int pages, int installments);
	boolean sameTvSeries(String name, double price, int installments, String corporation);
	boolean samePodcast(String name, double price, int installments);
}

abstract class AEntertainment implements IEntertainment {
	String name;
	double price;
	int installments;
	AEntertainment(String name, double price, int installments) {
		this.name = name;
		this.price = price;
		this.installments = installments;
	}
	//computes the price of a yearly subscription
	public double totalPrice() {
		return this.price * this.installments;
	}
	public int duration() {
		return this.installments * 50;
	}
	public String format() {
		return this.name + ": " + Double.toString(this.price);
	}
	public abstract boolean sameEntertainment(IEntertainment that);
	public boolean sameMagazine(String name, double price, String genre, int pages,
			int installments) {
		return false;
	}
	public boolean sameTvSeries(String name, double price, int installments, String corporation) {
		return false;
	}
	public boolean samePodcast(String name, double price, int installments) {
		return false;
	}
}

class Magazine extends AEntertainment {
	String genre;
	int pages;
	Magazine(String name, double price, String genre, int pages, int installments) {
		super(name, price, installments);
		this.genre = genre;
		this.pages = pages;
	}
	@Override
	public int duration() {
		return this.pages * 5;
	}
	public boolean sameEntertainment(IEntertainment that) {
		return that.sameMagazine(this.name, this.price, this.genre, this.pages, this.installments);
	}
	@Override
	public boolean sameMagazine(String name, double price, String genre, int pages,
			int installments) {
		return this.name == name &&
						this.price == price &&
						this.genre == genre &&
						this.pages == pages &&
						this.installments == installments;
	}
}

class TVSeries extends AEntertainment {
	String corporation;
	TVSeries(String name, double price, int installments, String corporation) {
		super(name, price, installments);
		this.corporation = corporation;
	}
	public boolean sameEntertainment(IEntertainment that) {
		return that.sameTvSeries(this.name, this.price, this.installments, this.corporation);
	}
	@Override
	public boolean sameTvSeries(String name, double price, int installments, String corporation) {
		return this.name == name &&
						this.price == price &&
						this.installments == installments &&
						this.corporation == corporation;
	}
}

class Podcast extends AEntertainment {
	Podcast(String name, double price, int installments) {
		super(name, price, installments);
	}
	public boolean sameEntertainment(IEntertainment that) {
		return that.samePodcast(this.name, this.price, this.installments);
	}
	@Override
	public boolean samePodcast(String name, double price, int installments) {
		return this.name == name &&
						this.price == price &&
						this.installments == installments;
	}
}

class ExamplesEntertainment {
	IEntertainment rollingStone = new Magazine("Rolling Stone", 2.55, "Music", 60, 12);
	IEntertainment houseOfCards = new TVSeries("House of Cards", 5.25, 13, "Netflix");
	IEntertainment serial = new Podcast("Serial", 0.0, 8);

	IEntertainment highlights = new Magazine("Highlights", 1.99, "Kids", 50, 12);
	IEntertainment taskmaster = new TVSeries("Taskmaster", .99, 10, "Youtube");
	IEntertainment dearHank = new Podcast("Dear Hank and John", 0.0, 45);

	IEntertainment highlights2 = new Magazine("Highlights", 1.99, "Kids", 50, 12);
	IEntertainment taskmaster2 = new TVSeries("Taskmaster", .99, 10, "Youtube");
	IEntertainment dearHank2 = new Podcast("Dear Hank and John", 0.0, 45);

	//testing total price method
	boolean testTotalPrice(Tester t) {
		return t.checkInexact(this.rollingStone.totalPrice(), 2.55 * 12, .0001) &&
						t.checkInexact(this.houseOfCards.totalPrice(), 5.25 * 13, .0001) &&
						t.checkInexact(this.serial.totalPrice(), 0.0, .0001) &&
						t.checkInexact(this.highlights.totalPrice(), 1.99 * 12, .0001) &&
						t.checkInexact(this.taskmaster.totalPrice(), .99 * 10, .0001) &&
						t.checkInexact(this.dearHank.totalPrice(), 0.0, .0001);
	}
	boolean testDuration(Tester t) {
		return t.checkExpect(this.rollingStone.duration(), 5 * 60) &&
						t.checkExpect(this.houseOfCards.duration(), 13 * 50) &&
						t.checkExpect(this.serial.duration(), 8 * 50) &&
						t.checkExpect(this.highlights.duration(), 5 * 50) &&
						t.checkExpect(this.taskmaster.duration(), 10 * 50) &&
						t.checkExpect(this.dearHank.duration(), 45 * 50);
	}
	boolean testFormat(Tester t) {
		return t.checkExpect(this.rollingStone.format(), "Rolling Stone: 2.55") &&
						t.checkExpect(this.houseOfCards.format(), "House of Cards: 5.25") &&
						t.checkExpect(this.serial.format(), "Serial: 0.0");
	}
	boolean testSameEntertainment(Tester t) {
		return t.checkExpect(this.rollingStone.sameEntertainment(this.rollingStone), true) &&
						t.checkExpect(this.houseOfCards.sameEntertainment(this.houseOfCards), true) &&
						t.checkExpect(this.serial.sameEntertainment(this.serial), true) &&
						t.checkExpect(this.highlights.sameEntertainment(this.highlights), true) &&
						t.checkExpect(this.taskmaster.sameEntertainment(this.taskmaster), true) &&
						t.checkExpect(this.dearHank.sameEntertainment(this.dearHank), true) &&
						t.checkExpect(this.highlights.sameEntertainment(this.highlights2), true) &&
						t.checkExpect(this.taskmaster.sameEntertainment(this.taskmaster2), true) &&
						t.checkExpect(this.dearHank.sameEntertainment(this.dearHank2), true) &&

						t.checkExpect(this.rollingStone.sameEntertainment(this.highlights), false) &&
						t.checkExpect(this.highlights.sameEntertainment(this.rollingStone), false) &&
						t.checkExpect(this.houseOfCards.sameEntertainment(this.taskmaster), false) &&
						t.checkExpect(this.serial.sameEntertainment(this.dearHank), false) &&
						t.checkExpect(this.rollingStone.sameEntertainment(this.taskmaster), false) &&
						t.checkExpect(this.rollingStone.sameEntertainment(this.dearHank), false) &&
						t.checkExpect(this.houseOfCards.sameEntertainment(this.highlights), false) &&
						t.checkExpect(this.houseOfCards.sameEntertainment(this.serial), false) &&
						t.checkExpect(this.serial.sameEntertainment(this.highlights), false) &&
						t.checkExpect(this.serial.sameEntertainment(this.dearHank), false);
	}
}
