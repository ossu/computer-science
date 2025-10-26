import tester.Tester;

interface IBook {
	int daysOverdue(int today);
	boolean isOverdue(int today);
	int computeFine(int today);
}

class ABook implements IBook {
	int STD_CHECKOUT = 14;
	int REF_CHECKOUT = 2;
	int STD_LATEFEE = 10;
	int AUDIO_LATEFEE = 20;

	String title;
	int dayTaken;
	ABook(String title, int dayTaken) {
		this.title = title;
		this.dayTaken = dayTaken;
	}
	// returns the number of days book is overdue (<0 for days remaining)
	public int daysOverdue(int today) {
		return today - this.dayTaken - STD_CHECKOUT;
	}
	//returns true if book is overdue
	public boolean isOverdue(int today) {
		return this.daysOverdue(today) > 0;
	}
	// returns the fine for overdue books in cents
	public int computeFine(int today) {
		if (this.isOverdue(today)) {
			return this.daysOverdue(today) * STD_LATEFEE;
		} else {
			return 0;
		}
	}
}

class Book extends ABook {
	String author;

	Book(String title, String author, int dayTaken) {
		super(title, dayTaken);
		this.author = author;
	}

}

class RefBook extends ABook {
	RefBook(String title, int dayTaken) {
		super(title, dayTaken);
	}

	public int daysOverdue(int today) {
		return today - this.dayTaken - REF_CHECKOUT;
	}
}

class AudioBook extends ABook {
	String author;
	AudioBook(String title, String author, int dayTaken) {
		super(title, dayTaken);
		this.author = author;
	}

	public int computeFine(int today) {
		if (this.isOverdue(today)) {
			return this.daysOverdue(today) * AUDIO_LATEFEE;
		} else {
			return 0;
		}
	}
}

class ExamplesBooks {

	IBook moby = new Book("Moby Dick", "Herman Melville", 7000);
	IBook frank = new AudioBook("Frankenstien", "Mary Shelly", 7000);
	IBook htdp = new RefBook("How to Design Programs", 7000);

	boolean testDaysOverdue(Tester t) {
		return t.checkExpect(this.moby.daysOverdue(7010), -4) &&
						t.checkExpect(this.frank.daysOverdue(7010), -4) &&
						t.checkExpect(this.htdp.daysOverdue(7010), 8);
	}
	boolean testIsOverdue(Tester t) {
		return t.checkExpect(this.moby.isOverdue(7010), false) &&
						t.checkExpect(this.moby.isOverdue(7015), true) &&
						t.checkExpect(this.frank.isOverdue(7010), false) &&
						t.checkExpect(this.frank.isOverdue(7015), true) &&
						t.checkExpect(this.htdp.isOverdue(7001), false) &&
						t.checkExpect(this.htdp.isOverdue(7010), true);
	}
	boolean testComputeFine(Tester t) {
		return t.checkExpect(this.moby.computeFine(7010), 0) &&
						t.checkExpect(this.moby.computeFine(7015), 10) &&
						t.checkExpect(this.frank.computeFine(7010), 0) &&
						t.checkExpect(this.frank.computeFine(7015), 20) &&
						t.checkExpect(this.htdp.computeFine(7001), 0) &&
						t.checkExpect(this.htdp.computeFine(7010), 80);

	}
}
