import tester.Tester;

class Date {
	int year;
	int month;
	int day;

	Date(int year, int month, int day) {
		Utils u = new Utils();
		this.year = u.checkRange(year, 1500, 2100, "Invalid year: " + Integer.toString(year));
		this.month = u.checkRange(month, 1, 12, "Invalid month: " + Integer.toString(month));
		this.day = u.checkRange(day, 1, 31, "Invalid day: " + Integer.toString(day));
	}
	Date(int month, int day) {
		this(2025, month, day);
	}
}

class Utils {
	Utils() {}

	int checkRange(int value, int min, int max, String msg) {
		if (value >= min && value <= max) {
			return value;
		} else {
			throw new IllegalArgumentException(msg);
		}
	}
}

class ExamplesDates {
	//Good dates
	Date d20100228 = new Date(2010, 2, 28);   // Feb 28, 2010
	Date d20091012 = new Date(2009, 10, 12);  // Oct 12, 2009

	boolean testConstructorExcpetion(Tester t) {
		return t.checkConstructorException(
				new IllegalArgumentException("Invalid year: -30"), "Date", -30, 1, 12) &&
						t.checkConstructorException(
								new IllegalArgumentException("Invalid year: 2110"), "Date", 2110, 1, 12) &&
						t.checkConstructorException(
								new IllegalArgumentException("Invalid month: 0"), "Date", 2000, 0, 12) &&
						t.checkConstructorException(
								new IllegalArgumentException("Invalid month: 13"), "Date", 2000, 13, 12) &&
						t.checkConstructorException(
								new IllegalArgumentException("Invalid day: 0"), "Date", 2000, 1, 0) &&
						t.checkConstructorException(
								new IllegalArgumentException("Invalid day: 32"), "Date", 2000, 1, 32) &&
						t.checkConstructorException(
								new IllegalArgumentException("Invalid day: 0"), "Date", 1, 0) &&
						t.checkConstructorException(
								new IllegalArgumentException("Invalid day: 32"), "Date", 1, 32);
	}
}
