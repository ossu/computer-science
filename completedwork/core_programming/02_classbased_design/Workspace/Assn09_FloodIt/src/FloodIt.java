import java.util.ArrayList;
import javalib.impworld.*;
import java.awt.Color;
import javalib.worldimages.*;
import java.util.Random;

// Represents a single square of the game area
class Cell {
	// In logical coordinates, with the origin at the top-left corner of the screen
	int x;
	int y;
	Color color;
	boolean flooded;
	// the four adjacent cells to this one
	Cell left;
	Cell right;
	Cell top;
	Cell bottom;

	Cell(int x, int y, Color color, boolean flooded,
			Cell left, Cell right, Cell top, Cell bottom) {
		this.x = x;
		this.y = y;
		this.color = color;
		this.flooded = flooded;
		this.left = left;
		this.right = right;
		this.top = top;
		this.bottom = bottom;
	}
	Cell(int x, int y, Color color) {
		this(x, y, color, false, null, null, null, null);
	}

	//returns a rectangle image of this cell 
	WorldImage drawCell(int tileAxis) {
		return new RectangleImage(
				FloodItWorld.BOARD_SIZE / tileAxis,
				FloodItWorld.BOARD_SIZE / tileAxis,
				OutlineMode.SOLID, this.color);
	}
}

class Board {
	ArrayList<Cell> cells;
	int size; // tiles vert & horz, board is always square 
	Board(ArrayList<Cell> cells, int size) {
		this.cells = cells;
		this.size = size;
	}
	Board() {
		this.size = FloodItWorld.DEFAULT_TILE_COUNT;
		generateRandomBoard(new Random());
		makeConnections();
	}
	Board(int size) {
		this.size = size;
		generateRandomBoard(new Random());
		makeConnections();
	}
	Board(int size, int seed) {
		this.size = size;
		generateRandomBoard(new Random(seed));
		makeConnections();
	}

	// generates a random board
	void generateRandomBoard(Random rand) {
		this.cells = new ArrayList<Cell>();
		for (int i = 0; i < size * size; i++) {
			this.cells.add(new Cell(i % size, i / size, randomColor(rand.nextInt(6))));
		}
	}
	// returns a random color
	Color randomColor(int rand) {
		switch (rand) {
		case 0:
			return Color.red;
		case 1:
			return Color.yellow;
		case 2:
			return Color.blue;
		case 3:
			return Color.pink;
		case 4:
			return Color.orange;
		case 5:
			return Color.green;
		default:
			return Color.black;
		}
	}
	// adds the flooded status and directional connections to each cell
	void makeConnections() {
		// first (top left / idx 0) cell is always flooded
		this.cells.get(0).flooded = true;
		for (int i = 0; i < this.cells.size(); i++) {
			Cell cell = cells.get(i);
			if (cell.x > 0) {
				cell.left = this.cells.get(i - 1);
			}
			if (cell.x < this.size - 1) {
				cell.right = this.cells.get(i + 1);
			}
			if (cell.y > 0) {
				cell.top = this.cells.get(i - this.size);
			}
			if (cell.y < this.size - 1) {
				cell.bottom = this.cells.get(i + this.size);
			}
		}
		// floods all tiles where an adjacent tile is flooded and color matches flood
		Color floodedColor = this.cells.get(0).color;
		for (Cell cell : this.cells) {
			if (((cell.left != null && cell.left.flooded) ||
						(cell.top != null && cell.top.flooded) ||
						(cell.right != null && cell.right.flooded) ||
						(cell.bottom != null && cell.bottom.flooded)) &&
					cell.color.equals(floodedColor)) {
				cell.flooded = true;
			}
		}
	}
	// returns the cell in provided the position
	Cell findCell(Posn pos) {
		int cellPxSize = (FloodItWorld.BOARD_SIZE / this.size);
		for (Cell cell : this.cells) {
			if ((pos.x >= cell.x * cellPxSize && pos.x < (cell.x + 1) * cellPxSize) &&
					(pos.y >= cell.y * cellPxSize && pos.y < (cell.y + 1) * cellPxSize)) {
				return cell;
			}
		}
		throw new IllegalArgumentException("Cannot Place Key Press" + pos.x + " " + pos.y);

		//	// !!! something is wrong with my math here and i cant figure it out
		//		int findX, findY;
		//		// protect from div 0 
		//		if (pos.x == 0) {
		//			findX = 0;
		//		} else {
		//			findX = (int) Math.floor(this.size / (int) Math.floor(FloodItWorld.BOARD_SIZE / pos.x));
		//		}
		//		// protect from div 0 
		//		if (pos.y == 0) {
		//			findY = 0;
		//		} else {
		//			findY = (int) Math.floor(this.size / (int) Math.floor(FloodItWorld.BOARD_SIZE / pos.y));
		//		}
		//		for (Cell cell : this.cells) {
		//			if (cell.x == findX && cell.y == findY) {
		//				return cell;
		//			}
		//		}
		//		throw new IllegalArgumentException("Cannot Place Key Press" + findX + " " + findY);
	}
	// sets all flooded tiles to the provided color
	void updateColor(Color color) {
		for (Cell cell : this.cells) {
			if (cell.flooded) {
				cell.color = color;
			}
		}
	}
	// sets flooded status of all tiles of the provided color and 
	// adjacent to a flooded tile
	void updateFloodStatus(Color color) {
		for (Cell cell : this.cells) {
			if (((cell.left != null && cell.left.flooded) ||
						(cell.right != null && cell.right.flooded) ||
						(cell.top != null && cell.top.flooded) ||
						(cell.bottom != null && cell.bottom.flooded)) &&
					cell.color == color) {
				cell.flooded = true;
			}
		}
	}
	// updates the color of flooded cells to match clicked cell
	// any cells with the same color as clicked will become flooded
	void floodCells(Posn pos) {
		Color floodColor = this.findCell(pos).color;
		this.updateColor(floodColor);
		this.updateFloodStatus(floodColor);
	}
	// returns a world image of all the cells stitched together
	WorldScene drawBoard(WorldScene scene) {
		int tileLength = FloodItWorld.BOARD_SIZE / this.size;
		for (Cell cell : this.cells) {
			scene.placeImageXY(
					cell.drawCell(this.size),
					(cell.x * tileLength) + (tileLength / 2),
					(cell.y * tileLength) + (tileLength / 2));
		}
		return scene;
	}
}

class FloodItWorld extends World {
	// constants
	static int BOARD_SIZE = 256;
	static int DEFAULT_TILE_COUNT = 16;
	static Color TEXT_COLOR = Color.black;
	// All the cells of the game
	Board board;
	int score;
	int time;

	FloodItWorld(Board board) {
		this.board = board;
		this.score = 0;
		this.time = 0;
	}
	FloodItWorld(int seed) {
		this(new Board(DEFAULT_TILE_COUNT, seed));
	}
	FloodItWorld(int size, int seed) {
		this(new Board(size, seed));
	}
	FloodItWorld() {
		this(new Board());
	}

	// resets the game to default start conditions
	void reset() {
		this.board = new Board();
		this.score = 0;
		this.time = 0;
	}

	// draws the game scene
	public WorldScene makeScene() {
		WorldScene scene = new WorldScene(
				FloodItWorld.BOARD_SIZE,
				FloodItWorld.BOARD_SIZE);
		scene = this.board.drawBoard(scene);
		scene.placeImageXY(
				new TextImage("" + this.score, FloodItWorld.TEXT_COLOR),
				10, 10);
		scene.placeImageXY(
				new TextImage(this.time / 3600 + ":" + this.time / 60 + ":" + this.time % 60,
						FloodItWorld.TEXT_COLOR),
				BOARD_SIZE - 30, 10);
		return scene;
	}
	// floods the board with the color at the location clicked
	public void onMouseClicked(Posn pos) {
		this.board.floodCells(pos);
		this.score++;
	}
	// reset game when r is pressed
	public void onKeyEvent(String k) {
		if (k.equals("r")) {
			reset();
		}
	}
	// increments the time counter every second
	public void onTick() {
		this.time++;
	}
}
