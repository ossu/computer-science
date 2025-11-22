import tester.Tester;

interface IShape {
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

	double getArea();
	double getPerimeter();
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
	public boolean sameCircle(Circle that) {
		return this.x == that.x &&
						this.y == that.y &&
						this.radius == that.radius;
	}
	public double getArea() {
		return this.radius * 2 * Math.PI;
	}
	public double getPerimeter() {
		return this.radius * 2 * Math.PI;
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
	public boolean sameRect(Rect that) {
		return this.x == that.x &&
						this.y == that.y &&
						this.w == that.w &&
						this.h == that.h;
	}
	public double getArea() {
		return (double) this.w * this.h;
	}
	public double getPerimeter() {
		return (this.w + this.h) * 2.0;
	}
}

class Square extends Rect {
	Square(int x, int y, int s) {
		super(x, y, s, s);
	}

	public boolean sameShape(IShape that) {
		return that.sameSquare(this);
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
	public boolean isTriangle() {
		return true;
	}
	public double getArea() {
		return this.base * this.height * 1 / 2;
	}
	public double getPerimeter() {
		return 0;
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
	public boolean sameCombo(Combo that) {
		return this.s1 == that.s1 &&
						this.s2 == that.s2;
	}
	public double getArea() {
		return s1.getArea() + s2.getArea();
	}
	public double getPerimeter() {
		return s1.getPerimeter() + s2.getPerimeter();
	}
}
