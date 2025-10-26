import tester.Tester;

interface IShape {
	boolean sameShape_old(IShape that);
	boolean sameShape(IShape that);

	boolean sameCircle(Circle that);
	boolean sameRect(Rect that);
	boolean sameSquare(Square that);
	boolean sameTriangle(Triangle that);
	boolean sameCombo(Combo that);

	// is this shape a Circle?
	boolean isCircle();
	// is this shape a Rect?
	boolean isRect();
	// is this shape a Square?
	boolean isSquare();
	boolean isTriangle();
}

abstract class AShape implements IShape {
	public boolean sameCircle(Circle that) {
		return false;
	}
	public boolean sameRect(Rect that) {
		return false;
	}
	public boolean sameSquare(Square that) {
		return false;
	}
	public boolean sameTriangle(Triangle that) {
		return false;
	}
	public boolean sameCombo(Combo that) {
		return false;
	}
	public boolean isCircle() {
		return false;
	}
	public boolean isRect() {
		return true;
	}
	public boolean isSquare() {
		return false;
	}
	public boolean isTriangle() {
		return false;
	}
}

class Circle extends AShape {
	int x, y;
	int radius;
	Circle(int x, int y, int radius) {
		this.x = x;
		this.y = y;
		this.radius = radius;
	}

	public int getX() {
		return this.x;
	}
	public int getY() {
		return this.y;
	}
	public int getRadius() {
		return this.radius;
	}

	public boolean sameShape(IShape that) {
		return that.sameCircle(this);
	}

	public boolean sameShape_old(IShape that) {
		if (that.isCircle()) {
			// that is-a Circle -- we can safely cast!
			return this.sameCircle((Circle) that);
		} else {
			// that is not a Circle
			return false;
		}
	}
	public boolean sameCircle(Circle that) {
		return this.x == that.x &&
						this.y == that.y &&
						this.radius == that.radius;
	}
}

class Rect extends AShape {
	int x, y;
	int w, h;
	Rect(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

	public boolean sameShape(IShape that) {
		return that.sameRect(this);
	}

	public boolean sameShape_old(IShape that) {
		if (that.isRect()) {
			return this.sameRect((Rect) that);
		} else {
			return false;
		}
	}

	public boolean sameRect(Rect that) {
		return this.x == that.x &&
						this.y == that.y &&
						this.w == that.w &&
						this.h == that.h;
	}
}

class Square extends Rect {
	Square(int x, int y, int s) {
		super(x, y, s, s);
	}

	public boolean sameShape(IShape that) {
		return that.sameSquare(this);
	}

	public boolean sameShape_old(IShape that) {
		if (that.isSquare()) {
			return this.sameSquare((Square) that);
		} else {
			return false;
		}
	}
	public boolean sameSquare(Square that) {
		return this.x == that.x &&
						this.y == that.y &&
						this.w == that.w; // No need to check the h field, too...
	}
	public boolean sameRect(Rect that) {
		return false;
	}
	public boolean isRect() {
		return false;
	}
	public boolean isSquare() {
		return true;
	}
}

class Triangle extends AShape {
	int x;
	int y;
	int height;
	int base;

	Triangle(int x, int y, int height, int base) {
		this.x = x;
		this.y = y;
		this.height = height;
		this.base = base;
	}

	public boolean sameShape(IShape that) {
		return that.sameTriangle(this);
	}
	public boolean sameTriangle(Triangle that) {
		return this.x == that.x &&
						this.y == that.y &&
						this.height == that.height &&
						this.base == that.base;
	}
	public boolean sameShape_old(IShape that) {
		return false;
	}
	public boolean isTriangle() {
		return true;
	}
}

class Combo extends AShape {
	IShape s1;
	IShape s2;

	Combo(IShape s1, IShape s2) {
		this.s1 = s1;
		this.s2 = s2;
	}
	public boolean sameShape(IShape that) {
		return that.sameCombo(this);
	}
	public boolean sameShape_old(IShape that) {
		return false;
	}
	public boolean sameCombo(Combo that) {
		return this.s1 == that.s1 &&
						this.s2 == that.s2;
	}
}

class ExampleShapes {
	IShape c1 = new Circle(3, 4, 5);
	IShape c2 = new Circle(4, 5, 6);
	IShape c3 = new Circle(3, 4, 5);

	IShape r1 = new Rect(3, 4, 5, 5);
	IShape r2 = new Rect(4, 5, 6, 7);
	IShape r3 = new Rect(3, 4, 5, 5);

	//In test method in an Examples class
	IShape s1 = new Square(3, 4, 5);
	IShape s2 = new Square(4, 5, 6);
	IShape s3 = new Square(3, 4, 5);

	IShape t1 = new Triangle(2, 3, 4, 4);
	IShape t2 = new Triangle(2, 3, 4, 4);

	IShape cmo1 = new Combo(c1, c2);
	IShape cmo2 = new Combo(c1, c2);

	boolean testSameShape(Tester t) {
		return t.checkExpect(c2.sameShape(c1), false) &&
						t.checkExpect(c1.sameShape(c2), false) &&
						t.checkExpect(c3.sameShape(c1), true) &&
						t.checkExpect(c1.sameShape(c3), true) &&

						t.checkExpect(r1.sameShape(r2), false) &&
						t.checkExpect(r2.sameShape(r1), false) &&
						t.checkExpect(r1.sameShape(r3), true) &&
						t.checkExpect(r3.sameShape(r1), true) &&

						t.checkExpect(s1.sameShape(s2), false) &&
						t.checkExpect(s2.sameShape(s1), false) &&
						t.checkExpect(s1.sameShape(s3), true) &&
						t.checkExpect(s3.sameShape(s1), true) &&

						t.checkExpect(s1.sameShape(r2), false) && // Good
						t.checkExpect(r2.sameShape(s1), false) && // Good
						// Comparing a Square with a Rect of the same size
						t.checkExpect(s1.sameShape(r1), false) && // Good
						t.checkExpect(r1.sameShape(s1), false) && // Not so good

						t.checkExpect(t1.sameShape(c1), false) &&
						t.checkExpect(t1.sameShape(s1), false) &&
						t.checkExpect(t1.sameShape(r1), false) &&
						t.checkExpect(t1.sameShape(t2), true) &&
						t.checkExpect(t2.sameShape(t1), true) &&

						t.checkExpect(cmo1.sameShape(c1), false) &&
						t.checkExpect(cmo1.sameShape(s1), false) &&
						t.checkExpect(cmo1.sameShape(r1), false) &&
						t.checkExpect(cmo1.sameShape(t2), false) &&
						t.checkExpect(cmo1.sameShape(cmo2), true) &&
						t.checkExpect(cmo2.sameShape(cmo1), true);

	}
}
