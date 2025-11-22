import tester.*;
import javalib.worldimages.*;
import javalib.funworld.*;
import javalib.worldcanvas.*;
import java.awt.Color;

interface ITree {
	WorldImage draw();
	boolean isDrooping();
	boolean isDroopingHelp(double rotation);
	ITree combine(int leftLength, int rightLength, double leftTheta, double rightTheta,
			ITree otherTree);
	double getWidth();
}

class Leaf implements ITree {
	int size; // represents the radius of the leaf
	Color color; // the color to draw it
	Leaf(int size, Color color) {
		this.size = size;
		this.color = color;
	}
	public WorldImage draw() {
		return new CircleImage(this.size, OutlineMode.SOLID, this.color);
	}
	public boolean isDrooping() {
		return false;
	}
	public boolean isDroopingHelp(double rotation) {
		return false;
	}
	public ITree combine(int leftLength, int rightLength, double leftTheta, double rightTheta,
			ITree otherTree) {
		return this;
	}
	public double getWidth() {
		return 0;
	}
}

class Stem implements ITree {
	Color COLORSTEM = Color.BLACK;
	// How long this stick is
	int length;
	// The angle (in degrees) of this stem, relative to the +x axis
	double theta;
	// The rest of the tree
	ITree tree;
	Stem(int length, double theta, ITree tree) {
		this.length = length;
		this.theta = theta;
		this.tree = tree;
	}
	public WorldImage draw() {
		Utils u = new Utils();
		return u.drawTreeonLinePinhole(this.length, this.theta - 90, this.tree, 0, this.length,
				this.COLORSTEM);
		//				new RotateImage(new OverlayOffsetImage(this.tree.draw(), 0, this.length,
		//				new LineImage(new Posn(0, this.length), this.COLORSTEM)), this.theta - 90);
	}
	public boolean isDrooping() {
		return (this.theta % 360) > 180 && (this.theta % 360) < 360;
	}
	public boolean isDroopingHelp(double rotation) {
		return ((this.theta + rotation) % 360) > 180 && ((this.theta + rotation) % 360) < 360;
	}
	public ITree combine(int leftLength, int rightLength, double leftTheta, double rightTheta,
			ITree otherTree) {
		return this;
	}
	public double getWidth() {
		return length;
	}
}

class Branch implements ITree {
	Color COLORBRANCH = Color.BLACK;
	// How long the left and right branches are
	int leftLength;
	int rightLength;
	// The angle (in degrees) of the two branches, relative to the +x axis,
	double leftTheta;
	double rightTheta;
	// The remaining parts of the tree
	ITree left;
	ITree right;
	Branch(int leftLength, int rightLength, double leftTheta, double rightTheta, ITree left,
			ITree right) {
		this.leftLength = leftLength;
		this.rightLength = rightLength;
		this.leftTheta = leftTheta - 180;
		this.rightTheta = rightTheta;
		this.left = left;
		this.right = right;
	}
	public WorldImage draw() {
		Utils u = new Utils();
		return new BesideAlignImage(AlignModeY.BOTTOM,
				u.drawTreeonLinePinhole(this.leftLength, this.leftTheta, this.left, 0, this.leftLength / 2,
						this.COLORBRANCH),
				u.drawTreeonLinePinhole(this.rightLength, this.rightTheta, this.right, 0,
						this.rightLength / 2,
						this.COLORBRANCH));
		//				this.drawHalfBranch(this.leftLength, this.leftTheta, this.left),
		//				this.drawHalfBranch(this.rightLength, this.rightTheta, this.right));
	}
	public WorldImage drawHalfBranch(int l, double theta, ITree tree) {
		return new RotateImage(new OverlayOffsetImage(
				tree.draw(), 0, l / 2,
				new LineImage(new Posn(0, l), this.COLORBRANCH)), theta);
	}
	public boolean isDrooping() {
		return this.leftTheta % 360 > 180 &&	this.leftTheta % 360 < 360 ||
						this.rightTheta % 360 > 180 && this.rightTheta % 360 < 360 ||
						this.left.isDrooping() ||
						this.right.isDrooping();
	}
	public boolean isDroopingHelp(double rotation) {
		return (this.leftTheta + rotation) % 360 > 180 && (this.leftTheta + rotation) % 360 < 360 ||
						(this.rightTheta + rotation) % 360 > 180 && (this.rightTheta + rotation) % 360 < 360 ||
						this.left.isDroopingHelp(this.leftTheta + rotation) ||
						this.right.isDroopingHelp(this.rightTheta + rotation);
	}
	public ITree combine(int leftLength, int rightLength, double leftTheta, double rightTheta,
			ITree otherTree) {
		return this;
	}
	public double getWidth() {
		return this.left.getWidth() + this.right.getWidth();
	}
}

class Utils {
	Utils() {}

	public WorldImage drawTreeonLine(int l, double theta, ITree tree, double offsetX, double offsetY,
			Color color) {
		return new RotateImage(new OverlayOffsetImage(
				tree.draw(), offsetX, offsetY,
				new LineImage(new Posn(0, l), color)), theta);
	}
	public WorldImage drawTreeonLinePinhole(int l, double theta, ITree tree, double offsetX,
			double offsetY,
			Color color) {
		return new RotateImage(new OverlayImage(
				tree.draw(),
				new VisiblePinholeImage(new LineImage(new Posn(0, l), color).movePinhole(0, l))), theta);
	}
}

class ExamplesTree {
	WorldImage testImage = new CircleImage(30, OutlineMode.OUTLINE, Color.RED);

	ITree tree1 = new Branch(30, 30, 135, 40, new Leaf(10, Color.RED), new Leaf(15, Color.BLUE));
	ITree tree2 = new Branch(30, 30, 115, 65, new Leaf(15, Color.GREEN), new Leaf(8, Color.ORANGE));
	ITree tree3 = new Branch(30, 30, 181, 40, new Leaf(10, Color.RED), new Leaf(15, Color.BLUE));
	ITree tree4 = new Branch(30, 30, 135, 719, new Leaf(10, Color.RED), new Leaf(15, Color.BLUE));
	ITree stem1 = new Stem(40, 90, tree1);
	ITree stem2 = new Stem(50, 90, tree2);
	ITree stem3 = new Stem(50, 181, tree1);
	ITree stem4 = new Stem(50, 719, tree1);
	ITree combine = tree1.combine(40, 50, 150, 30, tree2);

	boolean testDrawTree(Tester t) {
		WorldCanvas c = new WorldCanvas(500, 500);
		WorldScene s = new WorldScene(500, 500);
		return c.drawScene(s.placeImageXY(stem2.draw(), 250, 250)) && c.show();
	}
	//	boolean testIsDrooping(Tester t) {
	//		return t.checkExpect(this.tree1.isDrooping(), false) &&
	//						t.checkExpect(this.tree3.isDrooping(), true) &&
	//						t.checkExpect(this.tree4.isDrooping(), true) &&
	//						t.checkExpect(this.stem1.isDrooping(), false) &&
	//						t.checkExpect(this.stem3.isDrooping(), true) &&
	//						t.checkExpect(this.stem4.isDrooping(), true);
	//	}
	//	boolean testCombine(Tester t) {
	//		return false;
	//	}
	//	boolean testGetWidth(Tester t) {
	//		return false;
	//	}
}
