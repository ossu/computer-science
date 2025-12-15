import java.util.ArrayList;
import java.util.Random;
import javalib.impworld.*;
import javalib.worldimages.*;
import java.awt.Color;

class Utils {
	// returns a new ArrayList<T> containing all the items of the 
	// given list that pass the predicate.
	<T> ArrayList<T> filter(ArrayList<T> arr, IPred<T> pred) {
		ArrayList<T> result = new ArrayList<T>();
		for (T t : arr) {
			if (pred.apply(t)) {
				result.add(t);
			}
		}
		return result;
	}

	//modifies the given list to remove everything that fails the predicate
	<T> void removeExcept(ArrayList<T> arr, IPred<T> pred) {
		arr = this.filter(arr, pred);
	}
}

class Pos {
	int row, col;
	Pos(int row, int col) {
		this.row = row;
		this.col = col;
	}
}

//Represents an individual tile
class Tile {
	// The number on the tile.  Use 0 to represent the hole
	int value;
	Tile(int value) {
		this.value = value;
	}
	// Draws this tile onto the background at the specified logical coordinates
	WorldImage drawTile(int boardSize) {
		return new OverlayImage(new TextImage("" + this.value, Color.black),
				new OverlayImage(
						new RectangleImage(boardSize / 4, boardSize / 4, OutlineMode.SOLID, Color.white),
						new RectangleImage(boardSize / 4, boardSize / 4, OutlineMode.OUTLINE, Color.black)));
	}
	boolean isZero() {
		return this.value == 0;
	}
}

class Board {
	// represents the rows of tiles
	ArrayList<ArrayList<Tile>> tiles;
	Pos zeroPos;

	Board(boolean random, Random rand) {
		this.tiles = new ArrayList<ArrayList<Tile>>();
		if (random) {
			this.initBoard();
			this.randomize(rand);
			this.zeroPos = this.findZeroPos();
		} else {
			this.initBoard();
			this.zeroPos = new Pos(0, 0);
		}
	}
	Board(int seed) {
		this(true, new Random(seed));
	}
	Board(boolean randomize) {
		this(randomize, new Random());
	}
	Board(ArrayList<ArrayList<Tile>> tiles, Pos zeroPos) {
		this.tiles = tiles;
		this.zeroPos = zeroPos;
	}
	// default constructor
	Board() {
		this(true);
	}
	public void initBoard() {
		int tileCnt = 0;
		for (int i = 0; i < 4; i++) {
			ArrayList<Tile> row = new ArrayList<Tile>();
			for (int j = 0; j < 4; j++) {
				row.add(new Tile(tileCnt));
				tileCnt++;
			}
			tiles.add(row);
		}
	}
	public void randomize(Random rand) {
		//		Tile hold = this.replace(new Tile(0), rand.nextInt(3), rand.nextInt(3));
		Tile hold = new Tile(0);
		for (int i = 0; i < 30; i++) {
			// we are only going to 14 and adding one so we dont move 0,0 until the end
			int r = rand.nextInt(15) + 1;
			hold = this.replace(hold, r / 4, r % 4);
		}
		this.replace(hold, 0, 0);
	}
	public Tile getTile(int rowIndex, int colIndex) {
		return this.tiles.get(rowIndex).get(colIndex);
	}
	public Pos findZeroPos() {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (tiles.get(i).get(j).isZero()) {
					return new Pos(i, j);
				}
			}
		}
		throw new RuntimeException("Could not find 0 Tile");
	}

	public void move(String dir) {
		if (dir.equals("up")) {
			if (this.zeroPos.row > 0) {
				this.moveZero(-1, 0);
			}
		} else if (dir.equals("down")) {
			if (this.zeroPos.row < 3) {
				this.moveZero(1, 0);;
			}
		} else if (dir.equals("left")) {
			if (this.zeroPos.col > 0) {
				this.moveZero(0, -1);;
			}
		} else if (dir.equals("right")) {
			if (this.zeroPos.col < 3) {
				this.moveZero(0, 1);;
			}
		}
	}
	public void moveZero(int dRow, int dCol) {
		this.swap(this.zeroPos.row, this.zeroPos.col, this.zeroPos.row + dRow, this.zeroPos.col + dCol);
		this.zeroPos = new Pos(this.zeroPos.row + dRow, this.zeroPos.col + dCol);
	}
	public void undo(String lastMove) {
		if (lastMove.equals("up")) {
			this.move("down");
		} else if (lastMove.equals("down")) {
			this.move("up");
		} else if (lastMove.equals("left")) {
			this.move("right");
		} else if (lastMove.equals("right")) {
			this.move("left");
		} // nothing else should be an option but do nothing
	}
	public void swap(int rowIndex1, int colIndex1,
			int rowIndex2, int colIndex2) {
		Tile tile1 = this.getTile(rowIndex1, colIndex1);
		Tile tile2 = this.getTile(rowIndex2, colIndex2);
		this.replace(tile2, rowIndex1, colIndex1);
		this.replace(tile1, rowIndex2, colIndex2);
	}
	public Tile replace(Tile tile, int rowIndex, int colIndex) {
		return this.tiles.get(rowIndex).set(colIndex, tile);
	}
	public WorldScene drawBoard(WorldScene scene, int boardSize) {
		// background color and outline
		scene.placeImageXY(new OverlayImage(
				new RectangleImage(boardSize, boardSize, OutlineMode.SOLID, Color.white),
				new RectangleImage(boardSize, boardSize, OutlineMode.OUTLINE, Color.black)),
				boardSize / 2,
				boardSize / 2);
		// place tiles on background
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				scene.placeImageXY(this.getTile(i, j).drawTile(boardSize),
						(j * boardSize / 4) + (boardSize / 8),  // position on board + offset to get middle of tile
						(i * boardSize / 4) + (boardSize / 8));
			}
		}
		return scene;
	}
}

class FifteenGame extends World {
	Board board;
	int size;
	ArrayList<String> moves = new ArrayList<String>();
	FifteenGame() {
		// DEFAULT SIZE = 120
		this(new Board(), 120);
	}
	FifteenGame(Board board, int size) {
		this.board = board;
		this.size = size;
	}
	// draws the game
	public WorldScene makeScene() {
		//		return this.board.drawBoard(
		//				new WorldScene(this.size, this.size),
		//				this.size);
		WorldScene scene = new WorldScene(this.size, this.size);
		this.board.drawBoard(scene, size);
		if (this.moves.size() > 0) {
			scene.placeImageXY(
					new TextImage(this.moves.get(this.moves.size() - 1), Color.black),
					size / 2, size / 2);
		}
		return scene;
	}
	// handles keystrokes
	public void onKeyEvent(String k) {
		// needs to handle up, down, left, right to move the hole
		// extra: handle "u" to undo moves
		if (k.equals("u")) {
			if (moves.size() > 0) { // checks that there has been at least one move
				this.board.undo(moves.remove(this.moves.size() - 1));
			}
		} else if (k.equals("up") ||
								k.equals("down") ||
								k.equals("left") ||
								k.equals("right")) {
									this.moves.add(k);
									this.board.move(k);
								} // else do nothing
	}
}
