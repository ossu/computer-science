import tester.Tester;
import java.util.Random;

class Examples {
	Random r314 = new Random(314);
	Room v0, v1;
	Hall e01;
	Maze m3;
	MazeWorld worldM3;
	MazeWorld worldM16;

	void testConstructors(Tester t) {
		// test Vertex Constructor
		v0 = new Room(0, 0);
		t.checkExpect(v0.neighborConnections.size(), 0);
		v1 = new Room(0, 1);
		// test edge constructor incl adding edges to vertices
		e01 = new Hall(v0, v1, r314.nextInt(10));
		t.checkExpect(v0.neighborConnections.size(), 1);
		t.checkExpect(v1.neighborConnections.size(), 1);
		t.checkExpect(v0.neighborConnections.get(0), e01);
		t.checkExpect(v1.neighborConnections.get(0), e01);
		// test Maze constructor
		m3 = new Maze(3, 3, 314);
		// check all vertices were created
		t.checkExpect(m3.rooms.size(), 9);
		// check rooms were placed in correct location
		t.checkExpect(m3.rooms.get(0).row, 0);
		t.checkExpect(m3.rooms.get(0).col, 0);
		t.checkExpect(m3.rooms.get(1).row, 0);
		t.checkExpect(m3.rooms.get(1).col, 1);
		t.checkExpect(m3.rooms.get(2).row, 0);
		t.checkExpect(m3.rooms.get(2).col, 2);
		t.checkExpect(m3.rooms.get(3).row, 1);
		t.checkExpect(m3.rooms.get(3).col, 0);
		t.checkExpect(m3.rooms.get(4).row, 1);
		t.checkExpect(m3.rooms.get(4).col, 1);
		t.checkExpect(m3.rooms.get(5).row, 1);
		t.checkExpect(m3.rooms.get(5).col, 2);
		t.checkExpect(m3.rooms.get(6).row, 2);
		t.checkExpect(m3.rooms.get(6).col, 0);
		t.checkExpect(m3.rooms.get(7).row, 2);
		t.checkExpect(m3.rooms.get(7).col, 1);
		t.checkExpect(m3.rooms.get(8).row, 2);
		t.checkExpect(m3.rooms.get(8).col, 2);
		// check correct number of edges were created
		t.checkExpect(m3.rooms.get(0).neighborConnections.size(), 2);
		t.checkExpect(m3.rooms.get(1).neighborConnections.size(), 3);
		t.checkExpect(m3.rooms.get(2).neighborConnections.size(), 2);
		t.checkExpect(m3.rooms.get(3).neighborConnections.size(), 3);
		t.checkExpect(m3.rooms.get(4).neighborConnections.size(), 4);
		t.checkExpect(m3.rooms.get(5).neighborConnections.size(), 3);
		t.checkExpect(m3.rooms.get(6).neighborConnections.size(), 2);
		t.checkExpect(m3.rooms.get(7).neighborConnections.size(), 3);
		t.checkExpect(m3.rooms.get(8).neighborConnections.size(), 2);
		// check that edges connect the correct vertices
		t.checkExpect(m3.rooms.get(0).neighborConnections.get(0).to,
				m3.rooms.get(1));
		t.checkExpect(m3.rooms.get(0).neighborConnections.get(1).to,
				m3.rooms.get(3));
		t.checkExpect(m3.rooms.get(4).neighborConnections.get(0).from,
				m3.rooms.get(3));
		t.checkExpect(m3.rooms.get(4).neighborConnections.get(1).from,
				m3.rooms.get(1));
		t.checkExpect(m3.rooms.get(4).neighborConnections.get(2).to,
				m3.rooms.get(5));
		t.checkExpect(m3.rooms.get(4).neighborConnections.get(3).to,
				m3.rooms.get(7));
		t.checkExpect(m3.rooms.get(8).neighborConnections.get(0).from,
				m3.rooms.get(7));
		t.checkExpect(m3.rooms.get(8).neighborConnections.get(1).from,
				m3.rooms.get(5));
		// check that route tree was created
		t.checkExpect(m3.routeTree.size(), 8);
		t.checkExpect(m3.rooms.get(0).openHalls.get(0).to,
				m3.rooms.get(1));
		t.checkExpect(m3.rooms.get(1).openHalls.get(0).from,
				m3.rooms.get(0));
		t.checkExpect(m3.rooms.get(1).openHalls.get(1).to,
				m3.rooms.get(4));
		t.checkExpect(m3.rooms.get(1).openHalls.get(2).to,
				m3.rooms.get(2));
		t.checkExpect(m3.rooms.get(4).openHalls.get(0).from,
				m3.rooms.get(3));
		t.checkExpect(m3.rooms.get(4).openHalls.get(1).from,
				m3.rooms.get(1));

	}

	void init() {
		m3 = new Maze(3, 3, 314);
		worldM3 = new MazeWorld(m3);
		worldM16 = new MazeWorld(new Maze(16, 16, 314));
	}
	void testDrawMaze(Tester t) {
		init();
		worldM3.start();
		//		worldM16.start();
	}
}
