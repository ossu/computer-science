import tester.Tester;

interface IShape {
	// To return the result of applying the given function to this shape
	<R> R accept(IShapeVisitor<R> visitor);
}

class Circle implements IShape {
	int x, y;
	int radius;
	String color;
	Circle(int x, int y, int r, String color) {
		this.x = x;
		this.y = y;
		this.radius = r;
		this.color = color;
	}
	//To return the result of applying the given function to this Circle
	public <R> R accept(IShapeVisitor<R> visitor) {
		return visitor.visitCircle(this);
	}
}

class Rect implements IShape {
	int x, y, w, h;
	String color;
	Rect(int x, int y, int w, int h, String color) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.color = color;
	}
	//To return the result of applying the given function to this Rect
	public <R> R accept(IShapeVisitor<R> visitor) {
		return visitor.visitRect(this);
	}
}

class Square implements IShape {
	int x, y, size;
	String color;
	Square(int x, int y, int size, String color) {
		this.x = x;
		this.y = y;
		this.size = size;
		this.color = color;
	}
	//To return the result of applying the given function to this square
	public <R> R accept(IShapeVisitor<R> visitor) {
		return visitor.visitSquare(this);
	}
}

class Examples {
	Circle c1 = new Circle(0, 0, 10, "Blue");
	Rect r1 = new Rect(0, 0, 4, 3, "Red");
	Square s1 = new Square(0, 0, 5, "Silver");
	IList<IShape> list1 = new ConsList<IShape>(c1,
			new ConsList<IShape>(r1, new ConsList<IShape>(s1, new MtList<IShape>())));
	IList<Double> list1_mapArea = new ConsList<Double>(314.16,
			new ConsList<Double>(12.0, new ConsList<Double>(25.0, new MtList<Double>())));
	IList<Double> list1_mapPeri = new ConsList<Double>(62.83,
			new ConsList<Double>(14.0, new ConsList<Double>(20.0, new MtList<Double>())));

	boolean testMap(Tester t) {
		return t.checkInexact(list1.map(new ShapeArea()), list1_mapArea, 0.01) &&
						t.checkInexact(list1.map(new ShapePerimeter()), list1_mapPeri, 0.01);
	}
}
