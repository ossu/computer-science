import tester.Tester;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import javalib.impworld.World;
import javalib.worldimages.Posn;

class Examples {
	Cell cellSmall1;
	Cell cellSmall2;
	Cell cellSmall3;
	Cell cellSmall4;
	Board boardTest1;
	Board boardTest2;
	Board boardTest3;
	Board boardTestSmall;
	Board boardSolved;
	Board boardRandom;
	FloodItWorld gameBoard;
	FloodItWorld gameRandom;
	FloodItWorld gameRandom2;
	void init() {
		// @formatter:off
		boardTest1 = new Board(new ArrayList<Cell>(Arrays.asList(
				new Cell(0, 0, Color.red ), new Cell(1, 0, Color.blue), new Cell(2, 0, Color.blue), new Cell(3, 0, Color.blue), 
				new Cell(0, 1, Color.blue), new Cell(1, 1, Color.blue), new Cell(2, 1, Color.blue), new Cell(3, 1, Color.blue), 
				new Cell(0, 2, Color.blue), new Cell(1, 2, Color.blue), new Cell(2, 2, Color.blue), new Cell(3, 2, Color.blue), 
				new Cell(0, 3, Color.blue), new Cell(1, 3, Color.blue), new Cell(2, 3, Color.blue), new Cell(3, 3, Color.blue))),
				4);
		boardTest2 = new Board(new ArrayList<Cell>(Arrays.asList(
				new Cell(0, 0, Color.red ), new Cell(1, 0, Color.blue), new Cell(2, 0, Color.blue), new Cell(3, 0, Color.blue), 
				new Cell(0, 1, Color.blue), new Cell(1, 1, Color.blue), new Cell(2, 1, Color.blue), new Cell(3, 1, Color.blue), 
				new Cell(0, 2, Color.blue), new Cell(1, 2, Color.blue), new Cell(2, 2, Color.blue), new Cell(3, 2, Color.blue), 
				new Cell(0, 3, Color.blue), new Cell(1, 3, Color.blue), new Cell(2, 3, Color.blue), new Cell(3, 3, Color.green))),
				4);
		boardTest3 = new Board(new ArrayList<Cell>(Arrays.asList(
				new Cell(0, 0, Color.blue), new Cell(1, 0, Color.blue), new Cell(2, 0, Color.blue), new Cell(3, 0, Color.blue), 
				new Cell(0, 1, Color.blue), new Cell(1, 1, Color.blue), new Cell(2, 1, Color.blue), new Cell(3, 1, Color.blue), 
				new Cell(0, 2, Color.blue), new Cell(1, 2, Color.blue), new Cell(2, 2, Color.blue), new Cell(3, 2, Color.blue), 
				new Cell(0, 3, Color.blue), new Cell(1, 3, Color.blue), new Cell(2, 3, Color.blue), new Cell(3, 3, Color.green))),
				4);
		boardSolved = new Board(new ArrayList<Cell>(Arrays.asList(
				new Cell(0, 0, Color.blue), new Cell(1, 0, Color.blue), new Cell(2, 0, Color.blue), new Cell(3, 0, Color.blue), 
				new Cell(0, 1, Color.blue), new Cell(1, 1, Color.blue), new Cell(2, 1, Color.blue), new Cell(3, 1, Color.blue), 
				new Cell(0, 2, Color.blue), new Cell(1, 2, Color.blue), new Cell(2, 2, Color.blue), new Cell(3, 2, Color.blue), 
				new Cell(0, 3, Color.blue), new Cell(1, 3, Color.blue), new Cell(2, 3, Color.blue), new Cell(3, 3, Color.blue))),
				4);
		//                    x  y   color      flooded   left        right       top         bottom
		cellSmall1 = new Cell(0, 0, Color.red , true,    null,       cellSmall2, null,       cellSmall3);
		cellSmall2 = new Cell(1, 0, Color.blue, true,    cellSmall1, null,       null,       cellSmall4      );
		cellSmall3 = new Cell(0, 1, Color.blue, true,    null,       cellSmall4, cellSmall1, null      );
		cellSmall4 = new Cell(1, 1, Color.blue, true,    cellSmall3, null,       cellSmall2, null      );
		boardTestSmall = new Board(new ArrayList<Cell>(Arrays.asList(
				cellSmall1, cellSmall2, cellSmall3, cellSmall4)),
				2);
		// @formatter:on
		gameBoard = new FloodItWorld(boardTest1);
		// Should make the same boards
		boardRandom = new Board(16, 314);
		gameRandom = new FloodItWorld(314);
		gameRandom2 = new FloodItWorld(4, 314);
	}

	void testConstructor(Tester t) {
		init();
		t.checkExpect(boardTest1.size, 4);
		t.checkExpect(boardRandom.size, 16);
		t.checkExpect(gameRandom2.board.size, 4);
		t.checkExpect(gameBoard.board, boardTest1);
		t.checkExpect(gameRandom.board, boardRandom);
		t.checkExpect(boardRandom.cells.size(), 256);      // 16*16
		t.checkExpect(gameRandom2.board.cells.size(), 16); // 4*4
	}
	void testRandomColor(Tester t) {
		init();
		t.checkExpect(boardTest1.randomColor(0), Color.red);
		t.checkExpect(boardTest1.randomColor(1), Color.yellow);
		t.checkExpect(boardTest1.randomColor(2), Color.blue);
		t.checkExpect(boardTest1.randomColor(3), Color.pink);
		t.checkExpect(boardTest1.randomColor(4), Color.orange);
		t.checkExpect(boardTest1.randomColor(5), Color.green);
		t.checkExpect(boardTest1.randomColor(100), Color.black);
	}
	void testMakeConnection(Tester t) {
		init();
		this.boardTest1.makeConnections();
		t.checkExpect(boardTest1.cells.get(0).left, null);
		t.checkExpect(boardTest1.cells.get(0).right, boardTest1.cells.get(1));
		t.checkExpect(boardTest1.cells.get(0).top, null);
		t.checkExpect(boardTest1.cells.get(0).bottom, boardTest1.cells.get(4));

		t.checkExpect(boardTest1.cells.get(6).left, boardTest1.cells.get(5));
		t.checkExpect(boardTest1.cells.get(6).right, boardTest1.cells.get(7));
		t.checkExpect(boardTest1.cells.get(6).top, boardTest1.cells.get(2));
		t.checkExpect(boardTest1.cells.get(6).bottom, boardTest1.cells.get(10));

		t.checkExpect(boardTest1.cells.get(4).left, null);
		t.checkExpect(boardTest1.cells.get(7).right, null);
		t.checkExpect(boardTest1.cells.get(3).top, null);
		t.checkExpect(boardTest1.cells.get(13).bottom, null);

		t.checkExpect(boardTest1.cells.get(15).left, boardTest1.cells.get(14));
		t.checkExpect(boardTest1.cells.get(15).right, null);
		t.checkExpect(boardTest1.cells.get(15).top, boardTest1.cells.get(11));
		t.checkExpect(boardTest1.cells.get(15).bottom, null);

		t.checkExpect(boardTest1.cells.get(0).flooded, true);
		t.checkExpect(boardTest1.cells.get(1).flooded, false);
		t.checkExpect(boardTest1.cells.get(3).flooded, false);
		t.checkExpect(boardTest1.cells.get(7).flooded, false);
		t.checkExpect(boardTest1.cells.get(11).flooded, false);
		t.checkExpect(boardTest1.cells.get(15).flooded, false);

		this.boardTest3.makeConnections();
		t.checkExpect(boardTest3.cells.get(0).flooded, true);
		t.checkExpect(boardTest3.cells.get(1).flooded, true);
		t.checkExpect(boardTest3.cells.get(3).flooded, true);
		t.checkExpect(boardTest3.cells.get(5).flooded, true);
		t.checkExpect(boardTest3.cells.get(8).flooded, true);
		t.checkExpect(boardTest3.cells.get(11).flooded, true);
		t.checkExpect(boardTest3.cells.get(14).flooded, true);
		t.checkExpect(boardTest3.cells.get(15).flooded, false);
	}
	void testFindCell(Tester t) {
		init();
		t.checkExpect(this.boardTest1.findCell(new Posn(0, 0)),
				this.boardTest1.cells.get(0));
		t.checkExpect(this.boardTest1.findCell(new Posn(65, 0)),
				this.boardTest1.cells.get(1));
		t.checkExpect(this.boardTest1.findCell(new Posn(0, 65)),
				this.boardTest1.cells.get(4));
		t.checkExpect(this.boardTest1.findCell(new Posn(127, 127)),
				this.boardTest1.cells.get(5));
		t.checkExpect(this.boardTest1.findCell(new Posn(254, 254)),
				this.boardTest1.cells.get(15));
	}
	void testUpdateColor(Tester t) {
		init();
		t.checkExpect(this.boardTestSmall.cells.get(0).color, Color.red);
		this.boardTestSmall.updateColor(Color.blue);
		t.checkExpect(this.boardTestSmall.cells.get(0).color, Color.blue);

		init();
		this.cellSmall2.color = Color.red;
		t.checkExpect(this.boardTestSmall.cells.get(1).color, Color.red);
		this.boardTestSmall.updateColor(Color.blue);
		t.checkExpect(this.boardTestSmall.cells.get(1).color, Color.blue);

		init();
		this.cellSmall3.color = Color.red;
		t.checkExpect(this.boardTestSmall.cells.get(2).color, Color.red);
		this.boardTestSmall.updateColor(Color.blue);
		t.checkExpect(this.boardTestSmall.cells.get(2).color, Color.blue);

		init();
		this.cellSmall4.color = Color.red;
		t.checkExpect(this.boardTestSmall.cells.get(3).color, Color.red);
		this.boardTestSmall.updateColor(Color.blue);
		t.checkExpect(this.boardTestSmall.cells.get(3).color, Color.blue);

	}
	void testUpdateFloodStatus(Tester t) {
		init();
		this.boardTest1.makeConnections();
		t.checkExpect(this.boardTest1.cells.get(0).flooded, true);
		t.checkExpect(this.boardTest1.cells.get(1).flooded, false);
		this.boardTest1.updateColor(Color.blue);
		this.boardTest1.updateFloodStatus(Color.blue);
		t.checkExpect(this.boardTest1.cells.get(0).flooded, true);
		t.checkExpect(this.boardTest1.cells.get(1).flooded, true);
		t.checkExpect(this.boardTest1.cells.get(11).flooded, true);
		t.checkExpect(this.boardTest1.cells.get(15).flooded, true);

		init();
		this.boardTest2.makeConnections();
		t.checkExpect(this.boardTest2.cells.get(0).flooded, true);
		t.checkExpect(this.boardTest2.cells.get(1).flooded, false);
		this.boardTest2.updateColor(Color.green);
		this.boardTest2.updateFloodStatus(Color.green);
		t.checkExpect(this.boardTest2.cells.get(0).flooded, true);
		t.checkExpect(this.boardTest2.cells.get(1).flooded, false);
		t.checkExpect(this.boardTest2.cells.get(11).flooded, false);
		t.checkExpect(this.boardTest2.cells.get(15).flooded, false);
		this.boardTest2.updateColor(Color.blue);
		this.boardTest2.updateFloodStatus(Color.blue);
		t.checkExpect(this.boardTest2.cells.get(0).flooded, true);
		t.checkExpect(this.boardTest2.cells.get(1).flooded, true);
		t.checkExpect(this.boardTest2.cells.get(11).flooded, true);
		t.checkExpect(this.boardTest2.cells.get(15).flooded, false);
	}
	void testFloodCells(Tester t) {
		init();
		// @formatter:off
		this.boardTest1.makeConnections();
		this.boardTest1.floodCells(new Posn(0,0));
		Board testAns1 = new Board(new ArrayList<Cell>(Arrays.asList(
				new Cell(0, 0, Color.red ), new Cell(1, 0, Color.blue), new Cell(2, 0, Color.blue), new Cell(3, 0, Color.blue), 
				new Cell(0, 1, Color.blue), new Cell(1, 1, Color.blue), new Cell(2, 1, Color.blue), new Cell(3, 1, Color.blue), 
				new Cell(0, 2, Color.blue), new Cell(1, 2, Color.blue), new Cell(2, 2, Color.blue), new Cell(3, 2, Color.blue), 
				new Cell(0, 3, Color.blue), new Cell(1, 3, Color.blue), new Cell(2, 3, Color.blue), new Cell(3, 3, Color.blue))),
				4);
		testAns1.makeConnections();
		t.checkExpect(this.boardTest1, testAns1);
		
		init();
		this.boardTest1.makeConnections();
		Board testAns2 = new Board(new ArrayList<Cell>(Arrays.asList(
				new Cell(0, 0, Color.blue ), new Cell(1, 0, Color.blue), new Cell(2, 0, Color.blue), new Cell(3, 0, Color.blue), 
				new Cell(0, 1, Color.blue), new Cell(1, 1, Color.blue), new Cell(2, 1, Color.blue), new Cell(3, 1, Color.blue), 
				new Cell(0, 2, Color.blue), new Cell(1, 2, Color.blue), new Cell(2, 2, Color.blue), new Cell(3, 2, Color.blue), 
				new Cell(0, 3, Color.blue), new Cell(1, 3, Color.blue), new Cell(2, 3, Color.blue), new Cell(3, 3, Color.blue))),
				4);
		testAns2.makeConnections();
		this.boardTest1.floodCells(new Posn(128, 128));
		t.checkExpect(this.boardTest1, testAns2);
		
		init();
		this.boardTest2.makeConnections();
		this.boardTest2.floodCells(new Posn(255, 255));
		Board testAns3 = new Board(new ArrayList<Cell>(Arrays.asList(
				new Cell(0, 0, Color.green), new Cell(1, 0, Color.blue), new Cell(2, 0, Color.blue), new Cell(3, 0, Color.blue), 
				new Cell(0, 1, Color.blue), new Cell(1, 1, Color.blue), new Cell(2, 1, Color.blue), new Cell(3, 1, Color.blue), 
				new Cell(0, 2, Color.blue), new Cell(1, 2, Color.blue), new Cell(2, 2, Color.blue), new Cell(3, 2, Color.blue), 
				new Cell(0, 3, Color.blue), new Cell(1, 3, Color.blue), new Cell(2, 3, Color.blue), new Cell(3, 3, Color.green))),
				4);
		testAns3.makeConnections();
		t.checkExpect(this.boardTest1, testAns3);
		
		init();
		this.boardTest3.makeConnections();
		this.boardTest3.floodCells(new Posn(224, 224));
		Board testAns4 = new Board(new ArrayList<Cell>(Arrays.asList(
				new Cell(0, 0, Color.green), new Cell(1, 0, Color.green), new Cell(2, 0, Color.green), new Cell(3, 0, Color.green), 
				new Cell(0, 1, Color.green), new Cell(1, 1, Color.green), new Cell(2, 1, Color.green), new Cell(3, 1, Color.green), 
				new Cell(0, 2, Color.green), new Cell(1, 2, Color.green), new Cell(2, 2, Color.green), new Cell(3, 2, Color.green), 
				new Cell(0, 3, Color.green), new Cell(1, 3, Color.green), new Cell(2, 3, Color.green), new Cell(3, 3, Color.green))),
				4);
		testAns4.makeConnections();
		t.checkExpect(this.boardTest3, testAns4);
		// @formatter:on
	}
	void testDrawBoard(Tester t) {
		init();
		World g = new FloodItWorld(boardRandom);
		g.bigBang(256, 256, 1);
	}
}
