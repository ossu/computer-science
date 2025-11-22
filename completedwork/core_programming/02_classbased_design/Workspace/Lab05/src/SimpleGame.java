import tester.*;                // The tester library
import javalib.worldimages.*;   // images, like RectangleImage or OverlayImages
import javalib.funworld.*;      // the abstract World class and the big-bang library
import java.awt.Color;          // general colors (as triples of red,green,blue values)
// and predefined colors (Color.RED, Color.GRAY, etc.)
import java.util.Random;

class MyPosn extends Posn {
	// standard constructor
	MyPosn(int x, int y) {
		super(x, y);
	}
	// constructor to convert from a Posn to a MyPosn
	MyPosn(Posn p) {
		this(p.x, p.y);
	}

	MyPosn add(MyPosn that) {
		// return a Posn with the x and y values of this and that Posn added together
		return new MyPosn(this.x + that.x, this.y + that.y);
	}
	boolean isOffscreen(int width, int height) {
		// returns true if this Posn is outside the provided screen dimensions
		return this.x <= 0 ||
						this.x >= width ||
						this.y <= 0 ||
						this.y >= height;
	}
	int getX() {
		return this.x;
	}
	int getY() {
		return this.y;
	}
}

class Circle {
	int radius;
	MyPosn position; // in pixels
	MyPosn velocity; // in pixels/tick
	String fill;
	Color color;
	String DEFAULTFILL = "solid";
	Color DEFAULTCOLOR = Color.BLACK;

	Circle(int radius, MyPosn position, MyPosn velocity, String fill, Color color) {
		this.radius = radius;
		this.position = position;
		this.velocity = velocity;
		this.fill = fill;
		this.color = color;
	}
	Circle(int radius, MyPosn position, MyPosn velocity) {
		this.radius = radius;
		this.position = position;
		this.velocity = velocity;
		this.fill = this.DEFAULTFILL;
		this.color = this.DEFAULTCOLOR;
	}

	Circle move() {
		// returns a circle after being moved one tick
		return new Circle(this.radius, this.position.add(velocity), velocity);
	}
	boolean isOffscreen(int width, int height) {
		// returns true if this is outside the provided screen dimensions
		return this.position.isOffscreen(width, height);
	}
	WorldImage draw() {
		// returns an CircleImage of this
		return new CircleImage(this.radius, this.fill, this.color);
	}
	WorldScene place(WorldScene scene) {
		// returns a worldscene with a drawing of the circle at the appropriate position.
		return scene.placeImageXY(this.draw(), this.position.getX(), this.position.getY());
	}
}

interface ILoCircle {
	// returns list of moved circles
	ILoCircle moveAll();
	// returns a count of all offscreen circles
	int countOffscreen(int width, int height);
	int countOffscreenHelper(int width, int height, int cnt);
	// returns list of circles with offscreen items removed
	ILoCircle removeOffscreen(int width, int height);
	// returns a scene with all circles drawn and placed
	WorldScene placeAll(WorldScene scene);
	// returns true if this list is empty
	boolean isEmpty();
}

class MtLoCircle implements ILoCircle {
	MtLoCircle() {}
	public ILoCircle moveAll() {
		return this;
	}
	public int countOffscreen(int width, int height) {
		return 0;
	}
	public int countOffscreenHelper(int width, int height, int cnt) {
		return cnt;
	}
	public ILoCircle removeOffscreen(int width, int height) {
		return this;
	}
	public WorldScene placeAll(WorldScene scene) {
		return scene;
	}
	public boolean isEmpty() {
		return true;
	}
}

class ConsLoCircle implements ILoCircle {
	Circle first;
	ILoCircle rest;
	ConsLoCircle(Circle first, ILoCircle rest) {
		this.first = first;
		this.rest = rest;
	}

	public ILoCircle moveAll() {
		return new ConsLoCircle(this.first.move(), this.rest.moveAll());
	}
	public int countOffscreen(int width, int height) {
		return this.countOffscreenHelper(width, height, 0);
	}
	public int countOffscreenHelper(int width, int height, int cnt) {
		if (this.first.isOffscreen(width, height)) {
			return this.rest.countOffscreenHelper(width, height, cnt + 1);
		} else {
			return this.rest.countOffscreenHelper(width, height, cnt);
		}
	}
	public ILoCircle removeOffscreen(int width, int height) {
		if (this.first.isOffscreen(width, height)) {
			return this.rest.removeOffscreen(width, height);
		} else {
			return new ConsLoCircle(this.first, this.rest.removeOffscreen(width, height));
		}
	}
	public WorldScene placeAll(WorldScene scene) {
		return this.rest.placeAll(this.first.place(scene));
	}
	public boolean isEmpty() {
		return false;
	}
}

class MyGame extends World {
	double TICKRATE = 1.0 / 28.0;
	int VELOMAG = 10;
	int width;
	int height;
	int circlesRemoved;
	int gameOver;
	ILoCircle loCircles;
	Random rand;
	MyGame(int width, int height, int circlesRemoved, int gameOver, ILoCircle loCircles,
			Random rand) {
		this.width = width;
		this.height = height;
		this.circlesRemoved = circlesRemoved;
		this.gameOver = gameOver;
		this.loCircles = loCircles;
		this.rand = rand;
	}
	MyGame(int gameOver, int seed) {
		this.width = 500;
		this.height = 300;
		this.circlesRemoved = 0;
		this.gameOver = gameOver;
		this.loCircles = new MtLoCircle();
		this.rand = new Random(seed);
	}
	MyGame(int gameOver) {
		this.width = 500;
		this.height = 300;
		this.circlesRemoved = 0;
		this.gameOver = gameOver;
		this.loCircles = new MtLoCircle();
		this.rand = new Random();
	}
	public boolean start() {
		return this.bigBang(this.width, this.height, this.TICKRATE);
	}
	@Override
	public WorldScene makeScene() {
		WorldScene scene = new WorldScene(this.width, this.height);
		return this.loCircles.placeAll(scene);
	}
	@Override
	public MyGame onTick() {
		return this.moveCircles().removeCircles();
	}
	public MyGame removeCircles() {
		return new MyGame(this.width,
				this.height,
				this.circlesRemoved + this.loCircles.countOffscreen(this.width, this.height),
				this.gameOver,
				this.loCircles.removeOffscreen(this.width, this.height),
				this.rand);
	}
	public MyGame moveCircles() {
		return new MyGame(this.width,
				this.height,
				this.circlesRemoved,
				this.gameOver,
				this.loCircles.moveAll(),
				this.rand);
	}
	@Override
	public MyGame onMouseClicked(Posn pos) {
		return new MyGame(this.width,
				this.height,
				this.circlesRemoved,
				this.gameOver,
				new ConsLoCircle(new Circle(10, new MyPosn(pos), this.randomVelo()),
						this.loCircles),
				this.rand);
	}
	public MyPosn randomVelo() {
		switch (this.rand.nextInt(4)) {
		case 0:
			return new MyPosn(this.VELOMAG, 0);
		case 1:
			return new MyPosn(0, this.VELOMAG);
		case 2:
			return new MyPosn(this.VELOMAG * -1, 0);
		case 3:
			return new MyPosn(0, this.VELOMAG * -1);
		default:
			return new MyPosn(this.VELOMAG, 0); // should never happen
		}

	}
	@Override
	public WorldEnd worldEnds() {
		if (this.gameOver <= this.circlesRemoved) {
			return new WorldEnd(true, this.makeEndScene());
		} else {
			return new WorldEnd(false, this.makeEndScene());
		}
	}
	public WorldScene makeEndScene() {
		WorldScene endScene = new WorldScene(this.width, this.height);
		return endScene.placeImageXY(new TextImage("Game Over", Color.red), 250, 250);

	}
}

class Examples {
	MyPosn pn0 = new MyPosn(0, 0);
	MyPosn pn1 = new MyPosn(100, 100);
	MyPosn pn2 = new MyPosn(200, 200);
	MyPosn pn3_offscreen = new MyPosn(-100, 200);
	MyPosn pn4_offscreen = new MyPosn(200, -200);
	MyPosn pn5_offscreen = new MyPosn(600, 200);
	MyPosn pn6_offscreen = new MyPosn(200, 500);

	boolean testAdd(Tester t) {
		return t.checkExpect(pn0.add(pn0), pn0) &&
						t.checkExpect(pn1.add(pn0), pn1) &&
						t.checkExpect(pn1.add(pn1), pn2) &&
						t.checkExpect(pn1.add(pn2), new MyPosn(300, 300));
	}
	boolean testPosnOffscreen(Tester t) {
		return t.checkExpect(pn1.isOffscreen(500, 300), false) &&
						t.checkExpect(pn3_offscreen.isOffscreen(500, 300), true) &&
						t.checkExpect(pn4_offscreen.isOffscreen(500, 300), true) &&
						t.checkExpect(pn5_offscreen.isOffscreen(500, 300), true) &&
						t.checkExpect(pn6_offscreen.isOffscreen(500, 300), true);
	}

	Circle c1 = new Circle(1, pn1, pn0);
	Circle c2 = new Circle(1, pn1, pn1);
	Circle c3 = new Circle(1, pn2, pn1);
	Circle c4_offscreen = new Circle(1, pn4_offscreen, pn1);
	Circle c5_offscreen = new Circle(1, pn5_offscreen, pn1);
	WorldScene scene0 = new WorldScene(500, 300);
	boolean testMove(Tester t) {
		return t.checkExpect(c1.move(), c1) &&
						t.checkExpect(c2.move(), c3);
	}
	boolean testIsOffscreen(Tester t) {
		return t.checkExpect(c1.isOffscreen(500, 300), false) &&
						t.checkExpect(c2.isOffscreen(500, 300), false) &&
						t.checkExpect(c3.isOffscreen(500, 300), false) &&
						t.checkExpect(c4_offscreen.isOffscreen(500, 300), true) &&
						t.checkExpect(c5_offscreen.isOffscreen(500, 300), true);
	}
	boolean testDraw(Tester t) {
		return t.checkExpect(c1.draw(), new CircleImage(1, OutlineMode.SOLID, Color.BLACK));
	}
	boolean testPlace(Tester t) {
		return t.checkExpect(c1.place(scene0),
				scene0.placeImageXY(new CircleImage(1, OutlineMode.SOLID, Color.BLACK), 100, 100));
	}

	ILoCircle loc0 = new MtLoCircle();
	ILoCircle loc1 = new ConsLoCircle(c1, loc0);
	ILoCircle loc2 = new ConsLoCircle(c2, loc1);
	ILoCircle loc3 = new ConsLoCircle(c3, loc1);
	ILoCircle loc2_moved = new ConsLoCircle(c1, new ConsLoCircle(c3, loc0));
	ILoCircle loc2_withOffscreen = new ConsLoCircle(c4_offscreen,
			new ConsLoCircle(c5_offscreen, loc2));
	WorldScene scene1 = scene0.placeImageXY(c1.draw(), 100, 100);
	WorldScene scene2 = scene1.placeImageXY(c3.draw(), 200, 200);
	boolean testMoveAll(Tester t) {
		return t.checkExpect(this.loc0.moveAll(), this.loc0) &&
						t.checkExpect(this.loc1.moveAll(), this.loc1) &&
						t.checkExpect(this.loc2.moveAll(), this.loc2_moved);
	}
	boolean testRemoveAll(Tester t) {
		return t.checkExpect(this.loc0.removeOffscreen(500, 300), this.loc0) &&
						t.checkExpect(this.loc2.removeOffscreen(500, 300), this.loc2) &&
						t.checkExpect(this.loc2_withOffscreen.removeOffscreen(500, 300), this.loc2);
	}
	boolean testPlaceAll(Tester t) {
		return t.checkExpect(this.loc0.placeAll(scene0), scene0) &&
						t.checkExpect(this.loc1.placeAll(scene0), scene1) &&
						t.checkExpect(this.loc3.placeAll(scene0), scene2);
	}
	boolean testBigBang(Tester t) {
		MyGame world = new MyGame(10);
		return world.start();
	}
}
