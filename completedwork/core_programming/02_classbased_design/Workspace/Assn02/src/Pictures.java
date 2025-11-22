import tester.Tester;

interface IPicture {
	int getWidth();
	int countShapes();
	int comboDepth();
	int comboDepthHelp(int rsf);
	IPicture mirror();
	String pictureRecipe(int depth);
}

abstract class APicture implements IPicture {}

class Shape extends APicture {
	String kind;
	int size;
	Shape(String kind, int size) {
		this.kind = kind;
		this.size = size;
	}
	public int getWidth() {
		return this.size;
	}
	public int countShapes() {
		return 1;
	}
	public int comboDepth() {
		return 0;
	}
	public int comboDepthHelp(int rsf) {
		return rsf;
	}
	public IPicture mirror() {
		return this;
	}
	public String pictureRecipe(int depth) {
		return this.kind;
	}
}

class Combo extends APicture {
	String name;
	IOperation operation;
	Combo(String name, IOperation operation) {
		this.name = name;
		this.operation = operation;
	}
	public int getWidth() {
		return this.operation.getWidth();
	}
	public int countShapes() {
		return this.operation.countShapes();
	}
	public int comboDepth() {
		return this.operation.comboDepthHelp(0);
	}
	public int comboDepthHelp(int rsf) {
		return this.operation.comboDepthHelp(rsf);
	}
	public IPicture mirror() {
		return new Combo(this.name, this.operation.mirror());
	}
	public String pictureRecipe(int depth) {
		if (depth == 0) {
			return this.name;
		} else {
			return this.operation.pictureRecipe(depth);
		}
	}
}

interface IOperation {
	int getWidth();
	int countShapes();
	int comboDepth();
	int comboDepthHelp(int rsf);
	IOperation mirror();
	String pictureRecipe(int depth);
}

abstract class AOperation implements IOperation {
	IPicture pic1;
	AOperation(IPicture pic1) {
		this.pic1 = pic1;
	}
}

class Scale extends AOperation {
	Scale(IPicture pic1) {
		super(pic1);
	}
	public int getWidth() {
		return pic1.getWidth() * 2;
	}
	public int countShapes() {
		return 1;
	}
	public int comboDepth() {
		return this.pic1.comboDepthHelp(1);
	}
	public int comboDepthHelp(int rsf) {
		return this.pic1.comboDepthHelp(rsf + 1);
	}
	public IOperation mirror() {
		return new Scale(pic1);
	}
	public String pictureRecipe(int depth) {
		return "scale(" + pic1.pictureRecipe(depth - 1) + ")";
	}
}

class Beside extends AOperation {
	IPicture pic2;
	Beside(IPicture pic1, IPicture pic2) {
		super(pic1);
		this.pic2 = pic2;
	}
	public int getWidth() {
		return pic1.getWidth() + pic2.getWidth();
	}
	public int countShapes() {
		return this.pic1.countShapes() + this.pic2.countShapes();
	}
	public int comboDepth() {
		if (this.pic1.comboDepthHelp(1) > this.pic2.comboDepthHelp(1)) {

			return this.pic1.comboDepthHelp(1);
		} else {
			return this.pic2.comboDepthHelp(1);
		}
	}
	public int comboDepthHelp(int rsf) {
		if (this.pic1.comboDepthHelp(rsf + 1) > this.pic2.comboDepthHelp(rsf + 1)) {
			return this.pic1.comboDepthHelp(rsf + 1);
		} else {
			return this.pic2.comboDepthHelp(rsf + 1);
		}
	}
	public IOperation mirror() {
		return new Beside(pic2, pic1);
	}
	public String pictureRecipe(int depth) {
		return "beside(" + pic1.pictureRecipe(depth - 1) + ", " + pic2.pictureRecipe(depth - 1) + ")";
	}
}

class Overlay extends AOperation {
	IPicture pic2;
	Overlay(IPicture pic1, IPicture pic2) {
		super(pic1);
		this.pic2 = pic2;
	}
	public int getWidth() {
		if (pic1.getWidth() > pic2.getWidth()) {
			return pic1.getWidth();
		} else {
			return pic2.getWidth();
		}
	}
	public int countShapes() {
		return this.pic1.countShapes() + this.pic2.countShapes();
	}
	public int comboDepth() {
		if (this.pic1.comboDepthHelp(1) > this.pic2.comboDepthHelp(1)) {

			return this.pic1.comboDepthHelp(1);
		} else {
			return this.pic2.comboDepthHelp(1);
		}
	}
	public int comboDepthHelp(int rsf) {
		if (this.pic1.comboDepthHelp(rsf + 1) > this.pic2.comboDepthHelp(rsf + 1)) {
			return this.pic1.comboDepthHelp(rsf + 1);
		} else {
			return this.pic2.comboDepthHelp(rsf + 1);
		}
	}
	public IOperation mirror() {
		return new Overlay(pic1, pic2);
	}
	public String pictureRecipe(int depth) {
		return "overlay(" + pic1.pictureRecipe(depth - 1) + ", " + pic2.pictureRecipe(depth - 1) + ")";
	}
}

class ExamplesPicture {
	IPicture circ1 = new Shape("circle", 20);
	IPicture sq1 = new Shape("square", 30);
	IPicture cbo1 = new Combo("big circle", new Scale(circ1));
	IPicture cbo2 = new Combo("square on circle", new Overlay(sq1, cbo1));
	IPicture cbo3 = new Combo("doubled square on circle", new Beside(cbo2, cbo2));
	IPicture cbo4 = new Combo("square beside circle", new Beside(circ1, sq1));

	boolean testGetWidth(Tester t) {
		return t.checkExpect(this.circ1.getWidth(), 20) &&
						t.checkExpect(this.sq1.getWidth(), 30) &&
						t.checkExpect(this.cbo1.getWidth(), 20 * 2) &&
						t.checkExpect(this.cbo2.getWidth(), 40) &&
						t.checkExpect(this.cbo3.getWidth(), 80);
	}
	boolean testCountShapes(Tester t) {
		return t.checkExpect(this.circ1.countShapes(), 1) &&
						t.checkExpect(this.sq1.countShapes(), 1) &&
						t.checkExpect(this.cbo1.countShapes(), 1) &&
						t.checkExpect(this.cbo2.countShapes(), 2) &&
						t.checkExpect(this.cbo3.countShapes(), 4);
	}
	boolean testComboDepth(Tester t) {
		return t.checkExpect(this.circ1.comboDepth(), 0) &&
						t.checkExpect(this.sq1.comboDepth(), 0) &&
						t.checkExpect(this.cbo1.comboDepth(), 1) &&
						t.checkExpect(this.cbo2.comboDepth(), 2) &&
						t.checkExpect(this.cbo3.comboDepth(), 3);
	}
	boolean testMirror(Tester t) {
		return t.checkExpect(this.circ1.mirror(), this.circ1) &&
						t.checkExpect(this.sq1.mirror(), this.sq1) &&
						t.checkExpect(this.cbo1.mirror(), this.cbo1) &&
						t.checkExpect(this.cbo2.mirror(), this.cbo2) &&
						t.checkExpect(this.cbo4.mirror(),
								new Combo("square beside circle", new Beside(sq1, circ1)));
	}
	boolean testPictureRecipe(Tester t) {
		return t.checkExpect(this.circ1.pictureRecipe(0), "circle") &&
						t.checkExpect(this.cbo3.pictureRecipe(0),
								"doubled square on circle") &&
						t.checkExpect(this.cbo3.pictureRecipe(1),
								"beside(square on circle, square on circle)") &&
						t.checkExpect(this.cbo3.pictureRecipe(2),
								"beside(overlay(square, big circle), overlay(square, big circle))") &&
						t.checkExpect(this.cbo3.pictureRecipe(3),
								"beside(overlay(square, scale(circle)), overlay(square, scale(circle)))") &&
						t.checkExpect(this.cbo3.pictureRecipe(4),
								"beside(overlay(square, scale(circle)), overlay(square, scale(circle)))");

	}
}
