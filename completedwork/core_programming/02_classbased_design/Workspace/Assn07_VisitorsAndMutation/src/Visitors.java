import tester.Tester;
import java.util.function.*;

//      +-------------------+
//      | IArith            |
//      +-------------------+
//      +-------------------+
//          /_\      /_\
//           |        |----------------------------------------------------------------
//           |        |                                                               |
//+-------------+  +------------------------------+           +------------------------------------------+
//| Const       |  | UnaryFormula                 |           |              BinaryFormula               |
//+-------------+  +------------------------------+           +------------------------------------------+
//| double num  |  | Function<Double,Double> func |           | BiFunction <Double, Double, Double> func |
//+-------------+  | String name                  |           | String name                              |
//                 | IArith child                 |           | IArith left                              |
//                 +------------------------------+           | IArith right                             |
//                                                            +------------------------------------------+

interface IArith {
	<R> R accept(IArithVisitor<R> visitor);
}

class Const implements IArith {
	double num;
	Const(Double num) {
		this.num = num;
	}
	public <R> R accept(IArithVisitor<R> visitor) {
		return visitor.visitConst(this);
	}
}

class UnaryFormula implements IArith {
	Function<Double, Double> func;
	String name;
	IArith child;
	UnaryFormula(Function<Double, Double> func, String name, IArith child) {
		this.func = func;
		this.name = name;
		this.child = child;
	}
	public <R> R accept(IArithVisitor<R> visitor) {
		return visitor.visitUnary(this);
	}
}

class BinaryFormula implements IArith {
	BiFunction<Double, Double, Double> func;
	String name;
	IArith left;
	IArith right;
	BinaryFormula(BiFunction<Double, Double, Double> func, String name, IArith left, IArith right) {
		this.func = func;
		this.name = name;
		this.left = left;
		this.right = right;
	}
	public <R> R accept(IArithVisitor<R> visitor) {
		return visitor.visitBinary(this);
	}
}

interface IArithVisitor<R> extends Function<IArith, R> {
	R visitConst(Const c);
	R visitUnary(UnaryFormula u);
	R visitBinary(BinaryFormula b);
}

// Evaluates tree to Double
class EvalVisitor implements IArithVisitor<Double> {
	public Double apply(IArith arith) {
		return arith.accept(this);
	}
	public Double visitConst(Const c) {
		return c.num;
	}
	public Double visitUnary(UnaryFormula u) {
		return u.func.apply(u.child.accept(this));
	}
	public Double visitBinary(BinaryFormula b) {
		return b.func.apply(b.left.accept(this), b.right.accept(this));
	}
}

//produces another IArith, where every Const in the tree has been doubled.
class DoublerVisitor implements IArithVisitor<IArith> {
	public IArith apply(IArith arith) {
		return arith.accept(this);
	}
	public IArith visitConst(Const c) {
		return new Const(c.num * 2);
	}
	public IArith visitUnary(UnaryFormula u) {
		return new UnaryFormula(u.func, u.name, u.child.accept(this));
	}
	public IArith visitBinary(BinaryFormula b) {
		return new BinaryFormula(b.func, b.name, b.left.accept(this), b.right.accept(this));
	}
}

// produces a String showing the fully-parenthesized expression in 
// Racket-like prefix notation (i.e. "(div (plus 1.0 2.0) (neg 1.5))")
class PrintVisitor implements IArithVisitor<String> {
	public String apply(IArith arith) {
		return arith.accept(this);
	}
	public String visitConst(Const c) {
		return Double.toString(c.num);
	}
	public String visitUnary(UnaryFormula u) {
		return "(" + u.name + " " + u.child.accept(this) + ")";
	}
	public String visitBinary(BinaryFormula b) {
		return "(" + b.name + " " + b.left.accept(this) + " " + b.right.accept(this) + ")";
	}
}

// produces true, if a negative number is never encountered at any 
// point during its evaluation.
class NoNegativeResults implements IArithVisitor<Boolean> {
	public Boolean apply(IArith arith) {
		return arith.accept(this);
	}
	public Boolean visitConst(Const c) {
		return c.num > 0;
	}
	public Boolean visitUnary(UnaryFormula u) {
		Function<Double, Double> negFunc = new Neg();
		return !negFunc.getClass().equals(u.func.getClass()) && u.child.accept(this);
	}
	public Boolean visitBinary(BinaryFormula b) {
		return b.left.accept(this) && b.right.accept(this);
	}
}

class Plus implements BiFunction<Double, Double, Double> {
	public Double apply(Double a, Double b) {
		return a + b;
	}
}

class Minus implements BiFunction<Double, Double, Double> {
	public Double apply(Double a, Double b) {
		return a - b;
	}
}

class Mul implements BiFunction<Double, Double, Double> {
	public Double apply(Double a, Double b) {
		return a * b;
	}
}

class Div implements BiFunction<Double, Double, Double> {
	public Double apply(Double a, Double b) {
		return a / b;
	}
}

class Neg implements Function<Double, Double> {
	public Double apply(Double a) {
		return a * -1;
	}
}

class Sqr implements Function<Double, Double> {
	public Double apply(Double a) {
		return a * a;
	}
}

class ExampleVisitor {
	//                           Bi(Div() = 1)
	//                      /                   \
	//	    Bi(Add() = 2)                      Bi(Minus() = 2)
	//	       /   \                                /    \
	//	const(1)  const(1)           Unary(sqr() = 4)  const(2)
	//																	   	|
	//																	const(2)

	IArith cn1;
	IArith c1;
	IArith c2;
	IArith c4;
	IArith u1; // square c2
	IArith u1_double; // square c2
	IArith u2; // neg c1
	IArith b1; // add c1, c1
	IArith b1_double; // add c1, c1
	IArith b2; // minus u1, c2
	IArith b2_double; // minus u1, c2
	IArith b3; // divide b1, b2
	IArith b3_double;
	IArith bneg1;
	IArith bneg2;
	IArith bneg3;

	IArithVisitor<Double> evalVis = new EvalVisitor();
	IArithVisitor<String> printVis = new PrintVisitor();
	IArithVisitor<IArith> doubleVis = new DoublerVisitor();
	IArithVisitor<Boolean> noNegVis = new NoNegativeResults();

	Function<Double, Double> sqr = new Sqr();
	Function<Double, Double> neg = new Neg();
	BiFunction<Double, Double, Double> add = new Plus();
	BiFunction<Double, Double, Double> sub = new Minus();
	BiFunction<Double, Double, Double> div = new Div();

	void initCond() {
		c1 = new Const(1.0);
		c2 = new Const(2.0);
		c4 = new Const(4.0);
		cn1 = new Const(-1.0);
		u1 = new UnaryFormula(sqr, "sqr", c2);
		u1_double = new UnaryFormula(sqr, "sqr", c4);
		u2 = new UnaryFormula(neg, "neg", c1);
		b1 = new BinaryFormula(add, "add", c1, c1);
		b1_double = new BinaryFormula(add, "add", c2, c2);
		b2 = new BinaryFormula(sub, "sub", u1, c2);
		b2_double = new BinaryFormula(sub, "sub", u1_double, c4);
		b2 = new BinaryFormula(sub, "sub", u1, c2);
		b3 = new BinaryFormula(div, "div", b1, b2);
		b3_double = new BinaryFormula(div, "div", b1_double, b2_double);
		bneg1 = new BinaryFormula(add, "add", cn1, c1);
		bneg2 = new BinaryFormula(add, "add", c1, cn1);
		bneg3 = new BinaryFormula(add, "add", c1, u2);
	}

	void testVisitor(Tester t) {
		initCond();
		t.checkInexact(b3.accept(evalVis), 1.0, 0.01);
		t.checkExpect(b3.accept(printVis), "(div (add 1.0 1.0) (sub (sqr 2.0) 2.0))");
		t.checkExpect(b3.accept(noNegVis), true);
		t.checkExpect(b3.accept(doubleVis), b3_double);
		t.checkExpect(bneg1.accept(noNegVis), false);
		t.checkExpect(bneg2.accept(noNegVis), false);
		t.checkExpect(bneg3.accept(noNegVis), false);
		t.checkExpect(u2.accept(noNegVis), false);
	}

}
