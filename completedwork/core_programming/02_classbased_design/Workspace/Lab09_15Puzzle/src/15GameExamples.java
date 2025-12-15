import tester.Tester;
import java.util.ArrayList;
import java.util.Arrays;
import javalib.impworld.*;
import javalib.worldimages.*;
import java.awt.Color;

class IsTrue implements IPred<Boolean> {
	public boolean apply(Boolean b) {
		return b;
	}
}

class Examples {
	int size = 120;
	Utils u;
	Board solvedBoard;
	Board randomishBoard;
	Board nearSolvedBard;
	Board testBoard;
	WorldScene blankScene;
	WorldScene solvedScene;

	void init() {
		u = new Utils();
		solvedBoard = new Board(false);
		randomishBoard = new Board(314);
		this.makeSolvedBoardScene();
	}
	void makeSolvedBoardScene() {
		solvedScene = new WorldScene(this.size, this.size);
		//background
		solvedScene.placeImageXY(
				new OverlayImage(
						new RectangleImage(this.size, this.size, OutlineMode.SOLID, Color.white),
						new RectangleImage(this.size, this.size, OutlineMode.OUTLINE, Color.black)),
				this.size / 2,
				this.size / 2);
		//tile0
		solvedScene.placeImageXY(
				new OverlayImage(new TextImage("0", Color.black),
						new OverlayImage(
								new RectangleImage(this.size / 4, this.size / 4,
										OutlineMode.SOLID, Color.white),
								new RectangleImage(this.size / 4, this.size / 4,
										OutlineMode.OUTLINE, Color.black))),
				(0 * this.size / 4) + (this.size / 8),  // position on board + offset to get middle of tile
				(0 * this.size / 4) + (this.size / 8));
		//tile1
		solvedScene.placeImageXY(
				new OverlayImage(new TextImage("1", Color.black),
						new OverlayImage(
								new RectangleImage(this.size / 4, this.size / 4,
										OutlineMode.SOLID, Color.white),
								new RectangleImage(this.size / 4, this.size / 4,
										OutlineMode.OUTLINE, Color.black))),
				(1 * this.size / 4) + (this.size / 8),  // position on board + offset to get middle of tile
				(0 * this.size / 4) + (this.size / 8));
		//tile2
		solvedScene.placeImageXY(
				new OverlayImage(new TextImage("2", Color.black),
						new OverlayImage(
								new RectangleImage(this.size / 4, this.size / 4,
										OutlineMode.SOLID, Color.white),
								new RectangleImage(this.size / 4, this.size / 4,
										OutlineMode.OUTLINE, Color.black))),
				(2 * this.size / 4) + (this.size / 8),  // position on board + offset to get middle of tile
				(0 * this.size / 4) + (this.size / 8));
		//tile3
		solvedScene.placeImageXY(
				new OverlayImage(new TextImage("3", Color.black),
						new OverlayImage(
								new RectangleImage(this.size / 4, this.size / 4,
										OutlineMode.SOLID, Color.white),
								new RectangleImage(this.size / 4, this.size / 4,
										OutlineMode.OUTLINE, Color.black))),
				(3 * this.size / 4) + (this.size / 8),  // position on board + offset to get middle of tile
				(0 * this.size / 4) + (this.size / 8));
		//tile4
		solvedScene.placeImageXY(
				new OverlayImage(new TextImage("4", Color.black),
						new OverlayImage(
								new RectangleImage(this.size / 4, this.size / 4,
										OutlineMode.SOLID, Color.white),
								new RectangleImage(this.size / 4, this.size / 4,
										OutlineMode.OUTLINE, Color.black))),
				(0 * this.size / 4) + (this.size / 8),  // position on board + offset to get middle of tile
				(1 * this.size / 4) + (this.size / 8));
		//tile5
		solvedScene.placeImageXY(
				new OverlayImage(new TextImage("5", Color.black),
						new OverlayImage(
								new RectangleImage(this.size / 4, this.size / 4,
										OutlineMode.SOLID, Color.white),
								new RectangleImage(this.size / 4, this.size / 4,
										OutlineMode.OUTLINE, Color.black))),
				(1 * this.size / 4) + (this.size / 8),  // position on board + offset to get middle of tile
				(1 * this.size / 4) + (this.size / 8));
		//tile6
		solvedScene.placeImageXY(
				new OverlayImage(new TextImage("6", Color.black),
						new OverlayImage(
								new RectangleImage(this.size / 4, this.size / 4,
										OutlineMode.SOLID, Color.white),
								new RectangleImage(this.size / 4, this.size / 4,
										OutlineMode.OUTLINE, Color.black))),
				(2 * this.size / 4) + (this.size / 8),  // position on board + offset to get middle of tile
				(1 * this.size / 4) + (this.size / 8));
		//tile7
		solvedScene.placeImageXY(
				new OverlayImage(new TextImage("7", Color.black),
						new OverlayImage(
								new RectangleImage(this.size / 4, this.size / 4,
										OutlineMode.SOLID, Color.white),
								new RectangleImage(this.size / 4, this.size / 4,
										OutlineMode.OUTLINE, Color.black))),
				(3 * this.size / 4) + (this.size / 8),  // position on board + offset to get middle of tile
				(1 * this.size / 4) + (this.size / 8));
		//tile8
		solvedScene.placeImageXY(
				new OverlayImage(new TextImage("8", Color.black),
						new OverlayImage(
								new RectangleImage(this.size / 4, this.size / 4,
										OutlineMode.SOLID, Color.white),
								new RectangleImage(this.size / 4, this.size / 4,
										OutlineMode.OUTLINE, Color.black))),
				(0 * this.size / 4) + (this.size / 8),  // position on board + offset to get middle of tile
				(2 * this.size / 4) + (this.size / 8));
		//tile9
		solvedScene.placeImageXY(
				new OverlayImage(new TextImage("9", Color.black),
						new OverlayImage(
								new RectangleImage(this.size / 4, this.size / 4,
										OutlineMode.SOLID, Color.white),
								new RectangleImage(this.size / 4, this.size / 4,
										OutlineMode.OUTLINE, Color.black))),
				(1 * this.size / 4) + (this.size / 8),  // position on board + offset to get middle of tile
				(2 * this.size / 4) + (this.size / 8));
		//tile10
		solvedScene.placeImageXY(
				new OverlayImage(new TextImage("10", Color.black),
						new OverlayImage(
								new RectangleImage(this.size / 4, this.size / 4,
										OutlineMode.SOLID, Color.white),
								new RectangleImage(this.size / 4, this.size / 4,
										OutlineMode.OUTLINE, Color.black))),
				(2 * this.size / 4) + (this.size / 8),  // position on board + offset to get middle of tile
				(2 * this.size / 4) + (this.size / 8));
		//tile11
		solvedScene.placeImageXY(
				new OverlayImage(new TextImage("11", Color.black),
						new OverlayImage(
								new RectangleImage(this.size / 4, this.size / 4,
										OutlineMode.SOLID, Color.white),
								new RectangleImage(this.size / 4, this.size / 4,
										OutlineMode.OUTLINE, Color.black))),
				(3 * this.size / 4) + (this.size / 8),  // position on board + offset to get middle of tile
				(2 * this.size / 4) + (this.size / 8));
		//tile12
		solvedScene.placeImageXY(
				new OverlayImage(new TextImage("12", Color.black),
						new OverlayImage(
								new RectangleImage(this.size / 4, this.size / 4,
										OutlineMode.SOLID, Color.white),
								new RectangleImage(this.size / 4, this.size / 4,
										OutlineMode.OUTLINE, Color.black))),
				(0 * this.size / 4) + (this.size / 8),  // position on board + offset to get middle of tile
				(3 * this.size / 4) + (this.size / 8));
		//tile13
		solvedScene.placeImageXY(
				new OverlayImage(new TextImage("13", Color.black),
						new OverlayImage(
								new RectangleImage(this.size / 4, this.size / 4,
										OutlineMode.SOLID, Color.white),
								new RectangleImage(this.size / 4, this.size / 4,
										OutlineMode.OUTLINE, Color.black))),
				(1 * this.size / 4) + (this.size / 8),  // position on board + offset to get middle of tile
				(3 * this.size / 4) + (this.size / 8));
		//tile14
		solvedScene.placeImageXY(
				new OverlayImage(new TextImage("14", Color.black),
						new OverlayImage(
								new RectangleImage(this.size / 4, this.size / 4,
										OutlineMode.SOLID, Color.white),
								new RectangleImage(this.size / 4, this.size / 4,
										OutlineMode.OUTLINE, Color.black))),
				(2 * this.size / 4) + (this.size / 8),  // position on board + offset to get middle of tile
				(3 * this.size / 4) + (this.size / 8));
		//tile15
		solvedScene.placeImageXY(
				new OverlayImage(new TextImage("15", Color.black),
						new OverlayImage(
								new RectangleImage(this.size / 4, this.size / 4,
										OutlineMode.SOLID, Color.white),
								new RectangleImage(this.size / 4, this.size / 4,
										OutlineMode.OUTLINE, Color.black))),
				(3 * this.size / 4) + (this.size / 8),  // position on board + offset to get middle of tile
				(3 * this.size / 4) + (this.size / 8));

	}

	void testBoardConstructor(Tester t) {
		init();
		t.checkExpect(solvedBoard, new Board(
				new ArrayList<ArrayList<Tile>>(Arrays.asList(
						new ArrayList<Tile>(
								Arrays.asList(new Tile(0), new Tile(1), new Tile(2), new Tile(3))),
						new ArrayList<Tile>(
								Arrays.asList(new Tile(4), new Tile(5), new Tile(6), new Tile(7))),
						new ArrayList<Tile>(
								Arrays.asList(new Tile(8), new Tile(9), new Tile(10), new Tile(11))),
						new ArrayList<Tile>(
								Arrays.asList(new Tile(12), new Tile(13), new Tile(14), new Tile(15))))),
				new Pos(0, 0)));
		t.checkExpect(randomishBoard, new Board(
				new ArrayList<ArrayList<Tile>>(Arrays.asList(
						new ArrayList<Tile>(
								Arrays.asList(new Tile(13), new Tile(14), new Tile(3), new Tile(6))),
						new ArrayList<Tile>(
								Arrays.asList(new Tile(9), new Tile(5), new Tile(10), new Tile(0))),
						new ArrayList<Tile>(
								Arrays.asList(new Tile(1), new Tile(4), new Tile(8), new Tile(7))),
						new ArrayList<Tile>(
								Arrays.asList(new Tile(12), new Tile(11), new Tile(2), new Tile(15))))),
				new Pos(1, 3)));
	}
	void testGetTile(Tester t) {
		init();
		t.checkExpect(this.solvedBoard.getTile(0, 0), new Tile(0));
		t.checkExpect(this.solvedBoard.getTile(0, 3), new Tile(3));
		t.checkExpect(this.solvedBoard.getTile(1, 1), new Tile(5));
		t.checkExpect(this.solvedBoard.getTile(2, 2), new Tile(10));
		t.checkExpect(this.solvedBoard.getTile(3, 0), new Tile(12));
		t.checkExpect(this.solvedBoard.getTile(3, 3), new Tile(15));
	}
	void testSwap(Tester t) {
		// i am not moving the zeroPos here because this is not how it would work in the program
		init();
		this.solvedBoard.swap(0, 0, 0, 1);
		t.checkExpect(this.solvedBoard, new Board(
				new ArrayList<ArrayList<Tile>>(Arrays.asList(
						new ArrayList<Tile>(
								Arrays.asList(new Tile(1), new Tile(0), new Tile(2), new Tile(3))),
						new ArrayList<Tile>(
								Arrays.asList(new Tile(4), new Tile(5), new Tile(6), new Tile(7))),
						new ArrayList<Tile>(
								Arrays.asList(new Tile(8), new Tile(9), new Tile(10), new Tile(11))),
						new ArrayList<Tile>(
								Arrays.asList(new Tile(12), new Tile(13), new Tile(14), new Tile(15))))),
				new Pos(0, 0)));
		init();
		this.solvedBoard.swap(0, 0, 1, 0);
		t.checkExpect(this.solvedBoard, new Board(
				new ArrayList<ArrayList<Tile>>(Arrays.asList(
						new ArrayList<Tile>(
								Arrays.asList(new Tile(4), new Tile(1), new Tile(2), new Tile(3))),
						new ArrayList<Tile>(
								Arrays.asList(new Tile(0), new Tile(5), new Tile(6), new Tile(7))),
						new ArrayList<Tile>(
								Arrays.asList(new Tile(8), new Tile(9), new Tile(10), new Tile(11))),
						new ArrayList<Tile>(
								Arrays.asList(new Tile(12), new Tile(13), new Tile(14), new Tile(15))))),
				new Pos(0, 0)));
		init();
		this.solvedBoard.swap(0, 0, 3, 0);
		t.checkExpect(this.solvedBoard, new Board(
				new ArrayList<ArrayList<Tile>>(Arrays.asList(
						new ArrayList<Tile>(
								Arrays.asList(new Tile(12), new Tile(1), new Tile(2), new Tile(3))),
						new ArrayList<Tile>(
								Arrays.asList(new Tile(4), new Tile(5), new Tile(6), new Tile(7))),
						new ArrayList<Tile>(
								Arrays.asList(new Tile(8), new Tile(9), new Tile(10), new Tile(11))),
						new ArrayList<Tile>(
								Arrays.asList(new Tile(0), new Tile(13), new Tile(14), new Tile(15))))),
				new Pos(0, 0)));
		init();
		this.solvedBoard.swap(0, 0, 0, 3);
		t.checkExpect(this.solvedBoard, new Board(
				new ArrayList<ArrayList<Tile>>(Arrays.asList(
						new ArrayList<Tile>(
								Arrays.asList(new Tile(3), new Tile(1), new Tile(2), new Tile(0))),
						new ArrayList<Tile>(
								Arrays.asList(new Tile(4), new Tile(5), new Tile(6), new Tile(7))),
						new ArrayList<Tile>(
								Arrays.asList(new Tile(8), new Tile(9), new Tile(10), new Tile(11))),
						new ArrayList<Tile>(
								Arrays.asList(new Tile(12), new Tile(13), new Tile(14), new Tile(15))))),
				new Pos(0, 0)));
		init();
		this.solvedBoard.swap(0, 0, 3, 3);
		t.checkExpect(this.solvedBoard, new Board(
				new ArrayList<ArrayList<Tile>>(Arrays.asList(
						new ArrayList<Tile>(
								Arrays.asList(new Tile(15), new Tile(1), new Tile(2), new Tile(3))),
						new ArrayList<Tile>(
								Arrays.asList(new Tile(4), new Tile(5), new Tile(6), new Tile(7))),
						new ArrayList<Tile>(
								Arrays.asList(new Tile(8), new Tile(9), new Tile(10), new Tile(11))),
						new ArrayList<Tile>(
								Arrays.asList(new Tile(12), new Tile(13), new Tile(14), new Tile(0))))),
				new Pos(0, 0)));
		init();
		this.solvedBoard.swap(3, 3, 0, 0);
		t.checkExpect(this.solvedBoard, new Board(
				new ArrayList<ArrayList<Tile>>(Arrays.asList(
						new ArrayList<Tile>(
								Arrays.asList(new Tile(15), new Tile(1), new Tile(2), new Tile(3))),
						new ArrayList<Tile>(
								Arrays.asList(new Tile(4), new Tile(5), new Tile(6), new Tile(7))),
						new ArrayList<Tile>(
								Arrays.asList(new Tile(8), new Tile(9), new Tile(10), new Tile(11))),
						new ArrayList<Tile>(
								Arrays.asList(new Tile(12), new Tile(13), new Tile(14), new Tile(0))))),
				new Pos(0, 0)));
	}
	void testMove(Tester t) {
		init();
		this.solvedBoard.move("up");
		t.checkExpect(this.solvedBoard, new Board(
				new ArrayList<ArrayList<Tile>>(Arrays.asList(
						new ArrayList<Tile>(
								Arrays.asList(new Tile(0), new Tile(1), new Tile(2), new Tile(3))),
						new ArrayList<Tile>(
								Arrays.asList(new Tile(4), new Tile(5), new Tile(6), new Tile(7))),
						new ArrayList<Tile>(
								Arrays.asList(new Tile(8), new Tile(9), new Tile(10), new Tile(11))),
						new ArrayList<Tile>(
								Arrays.asList(new Tile(12), new Tile(13), new Tile(14), new Tile(15))))),
				new Pos(0, 0)));
		this.solvedBoard.move("left");
		t.checkExpect(this.solvedBoard, new Board(
				new ArrayList<ArrayList<Tile>>(Arrays.asList(
						new ArrayList<Tile>(
								Arrays.asList(new Tile(0), new Tile(1), new Tile(2), new Tile(3))),
						new ArrayList<Tile>(
								Arrays.asList(new Tile(4), new Tile(5), new Tile(6), new Tile(7))),
						new ArrayList<Tile>(
								Arrays.asList(new Tile(8), new Tile(9), new Tile(10), new Tile(11))),
						new ArrayList<Tile>(
								Arrays.asList(new Tile(12), new Tile(13), new Tile(14), new Tile(15))))),
				new Pos(0, 0)));
		this.solvedBoard.move("right");
		t.checkExpect(this.solvedBoard, new Board(
				new ArrayList<ArrayList<Tile>>(Arrays.asList(
						new ArrayList<Tile>(
								Arrays.asList(new Tile(1), new Tile(0), new Tile(2), new Tile(3))),
						new ArrayList<Tile>(
								Arrays.asList(new Tile(4), new Tile(5), new Tile(6), new Tile(7))),
						new ArrayList<Tile>(
								Arrays.asList(new Tile(8), new Tile(9), new Tile(10), new Tile(11))),
						new ArrayList<Tile>(
								Arrays.asList(new Tile(12), new Tile(13), new Tile(14), new Tile(15))))),
				new Pos(0, 1)));
		this.solvedBoard.move("down");
		t.checkExpect(this.solvedBoard, new Board(
				new ArrayList<ArrayList<Tile>>(Arrays.asList(
						new ArrayList<Tile>(
								Arrays.asList(new Tile(1), new Tile(5), new Tile(2), new Tile(3))),
						new ArrayList<Tile>(
								Arrays.asList(new Tile(4), new Tile(0), new Tile(6), new Tile(7))),
						new ArrayList<Tile>(
								Arrays.asList(new Tile(8), new Tile(9), new Tile(10), new Tile(11))),
						new ArrayList<Tile>(
								Arrays.asList(new Tile(12), new Tile(13), new Tile(14), new Tile(15))))),
				new Pos(1, 1)));
	}
	void testDrawTiles(Tester t) {
		init();
		t.checkExpect(solvedBoard.getTile(0, 0).drawTile(this.size),
				new OverlayImage(new TextImage("0", Color.black),
						new OverlayImage(
								new RectangleImage(this.size / 4, this.size / 4,
										OutlineMode.SOLID, Color.white),
								new RectangleImage(this.size / 4, this.size / 4,
										OutlineMode.OUTLINE, Color.black))));
		t.checkExpect(solvedBoard.getTile(0, 3).drawTile(this.size),
				new OverlayImage(new TextImage("3", Color.black),
						new OverlayImage(
								new RectangleImage(this.size / 4, this.size / 4,
										OutlineMode.SOLID, Color.white),
								new RectangleImage(this.size / 4, this.size / 4,
										OutlineMode.OUTLINE, Color.black))));
		t.checkExpect(solvedBoard.getTile(1, 1).drawTile(this.size),
				new OverlayImage(new TextImage("5", Color.black),
						new OverlayImage(
								new RectangleImage(this.size / 4, this.size / 4,
										OutlineMode.SOLID, Color.white),
								new RectangleImage(this.size / 4, this.size / 4,
										OutlineMode.OUTLINE, Color.black))));
		t.checkExpect(solvedBoard.getTile(2, 2).drawTile(this.size),
				new OverlayImage(new TextImage("10", Color.black),
						new OverlayImage(
								new RectangleImage(this.size / 4, this.size / 4,
										OutlineMode.SOLID, Color.white),
								new RectangleImage(this.size / 4, this.size / 4,
										OutlineMode.OUTLINE, Color.black))));
		t.checkExpect(solvedBoard.getTile(3, 0).drawTile(this.size),
				new OverlayImage(new TextImage("12", Color.black),
						new OverlayImage(
								new RectangleImage(this.size / 4, this.size / 4,
										OutlineMode.SOLID, Color.white),
								new RectangleImage(this.size / 4, this.size / 4,
										OutlineMode.OUTLINE, Color.black))));
		t.checkExpect(solvedBoard.getTile(3, 3).drawTile(this.size),
				new OverlayImage(new TextImage("15", Color.black),
						new OverlayImage(
								new RectangleImage(this.size / 4, this.size / 4,
										OutlineMode.SOLID, Color.white),
								new RectangleImage(this.size / 4, this.size / 4,
										OutlineMode.OUTLINE, Color.black))));
		t.checkExpect(solvedBoard.drawBoard(new WorldScene(this.size, this.size), size), solvedScene);
	}
	void testGame(Tester t) {
		init();
		World g = new FifteenGame();
		g.bigBang(size, size);

	}
}
