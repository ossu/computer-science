interface IPred<T> {
	boolean apply(T t);
}

interface IFunc<A, R> {
	R apply(A arg);
}

interface IFunc2<A1, A2, R> {
	R apply(A1 arg1, A2 arg2);
}

//An IShapeVisitor is a function over IShapes
interface IShapeVisitor<R> extends IFunc<IShape, R> {
	R visitCircle(Circle c);
	R visitSquare(Square s);
	R visitRect(Rect r);
}

//Implements a function taking a Shape and returning a Double,
//that computes the area of the given shape
class ShapeArea implements IShapeVisitor<Double> {
	public Double apply(IShape shape) {
		return shape.accept(this);
	}
	public Double visitCircle(Circle circle) {
		return Math.PI * circle.radius * circle.radius;
	}
	public Double visitRect(Rect rect) {
		return (double) rect.w * rect.h;
	}
	public Double visitSquare(Square square) {
		return (double) square.size * square.size;
	}
}

//Implements a function taking a Shape and returning a Double,
//that computes the perimeter of the given shape
class ShapePerimeter implements IShapeVisitor<Double> {
	public Double apply(IShape shape) {
		return shape.accept(this);
	}
	public Double visitCircle(Circle circle) {
		return Math.PI * circle.radius * 2.0;
	}
	public Double visitRect(Rect rect) {
		return rect.w * 2.0 + rect.h * 2.0;
	}
	public Double visitSquare(Square square) {
		return square.size * 4.0;
	}
}
