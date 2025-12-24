import tester.Tester;
import java.util.HashMap;
import java.util.Objects;

class DictEntry {
	String word;
	String meaning;
}

class Book {
	Author author;
	String title;
	int year;
	public int hashCode() {
		return this.author.hashCode() * 10000 + this.year;
	}
	public boolean equals(Object other) {
		if (!(other instanceof Book)) {
			return false;
		}
		// this cast is safe, because we just checked instanceof
		Book that = (Book) other;
		return this.author.equals(that.author) && this.year == that.year &&
						this.title.equals(that.title);
	}
}

class Author {
	Book book;
	String name;
	int yob;
	public int hashCode() {
		return this.name.hashCode() * 10000 + this.yob;
	}
	public boolean equals(Object other) {
		if (!(other instanceof Author)) {
			return false;
		}
		// this cast is safe, because we just checked instanceof
		Author that = (Author) other;
		return this.name.equals(that.name) && this.yob == that.yob;
	}
}

interface IShape {
	boolean sameShape(IShape that);
	boolean sameCircle(Circle that);
	boolean sameSquare(Square that);
	boolean sameRect(Rect that);
}

abstract class AShape implements IShape {
	public boolean sameCircle(Circle that) {
		return false;
	}
	public boolean sameSquare(Square that) {
		return false;
	}
	public boolean sameRect(Rect that) {
		return false;
	}
	public boolean equals(Object other) {
		if (this.equals(other)) {
			return true;
		}
		if (!(other instanceof IShape)) {
			return false;
		}
		return this.sameShape((IShape) other);
	}
}

class Circle extends AShape {
	int radius;
	Circle(int radius) {
		this.radius = radius;
	}
	public boolean sameShape(IShape that) {
		return that.sameCircle(this);
	}
	public boolean sameCircle(Circle that) {
		return that.radius == this.radius;
	}
	public int hashCode() {
		return Objects.hash(this.radius);
	}
}

class Rect extends AShape {
	int height, width;
	Rect(int height, int width) {
		this.height = height;
		this.width = width;
	}
	public boolean sameShape(IShape that) {
		return that.sameRect(this);
	}
	public int hashCode() {
		return Objects.hash(this.height + this.width);
	}
}

class Square extends AShape {
	int width;
	Square(int width) {
		this.width = width;
	}
	public boolean sameShape(IShape that) {
		return that.sameSquare(this);
	}
	public int hashCode() {
		return Objects.hash(this.width);
	}
}

class Examples {
	void testHashMaps(Tester t) {
		HashMap<String, String> rooms = new HashMap<String, String>();
		// Put all the data into the hashtable
		rooms.put("Ben Lerner", "WVH314");
		rooms.put("Leena Razzaq", "WVH310B");
		rooms.put("Olin Shivers", "WVH308");
		rooms.put("Matthias Felleisen", "WVH308B");
		// Get the data
		t.checkExpect(rooms.get("Ben Lerner"), "WVH314");
		t.checkExpect(rooms.get("Olin Shivers"), "WVH308");
		// Check that some data is present
		t.checkExpect(rooms.containsKey("Leena Razzaq"), true);
		t.checkExpect(rooms.containsKey("Amal Ahmed"), false);
		// Data that isn't present will return null
		t.checkExpect(rooms.get("Amal Ahmed"), null);
	}
}
