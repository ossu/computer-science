import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import javalib.impworld.*;
import java.awt.Color;
import javalib.worldimages.*;
import java.util.Random;

class Room {
	String name; // names are needed for Kruskal's algo
	ArrayList<Hall> neighborConnections;
	ArrayList<Hall> openHalls;
	int row, col;
	Room(String name, ArrayList<Hall> neighbors, ArrayList<Hall> openHalls, int row, int col) {
		this.name = name;
		this.neighborConnections = neighbors;
		this.openHalls = openHalls;
		this.row = row;
		this.col = col;
	}
	Room(String name, int row, int col) {
		this(name, new ArrayList<Hall>(), new ArrayList<Hall>(), row, col);
	}
	Room(int row, int col) {
		this(Integer.toString(row) + Integer.toString(col), new ArrayList<Hall>(),
				new ArrayList<Hall>(), row, col);
	}
	void addNeighbor(Hall e) {
		this.neighborConnections.add(e);
	}
	void addOpenHall(Hall e) {
		this.openHalls.add(e);
	}

	void drawRoom(WorldScene scene, Color color) {
		scene.placeImageXY(
				new RectangleImage(
						MazeWorld.ROOM_SIZE_PX,
						MazeWorld.ROOM_SIZE_PX,
						OutlineMode.SOLID,
						color),
				col * (MazeWorld.ROOM_SIZE_PX + MazeWorld.WALL_SIZE_PX)
						+ (MazeWorld.ROOM_SIZE_PX + MazeWorld.WALL_SIZE_PX) / 2,
				row * (MazeWorld.ROOM_SIZE_PX + MazeWorld.WALL_SIZE_PX)
						+ (MazeWorld.ROOM_SIZE_PX + MazeWorld.WALL_SIZE_PX) / 2);
	}
}

class Hall {
	String name; // names are needed for Kruskal's algo
	Room from, to;
	int weight;	// random weights are used to facilitate maze gen
	Hall(String name, Room from, Room to, int weight) {
		this.name = name;
		this.from = from;
		this.to = to;
		this.weight = weight;
		from.addNeighbor(this);
		to.addNeighbor(this);
	}
	Hall(Room from, Room to, int weight) {
		this(from.name + to.name, from, to, weight);
	}

	void drawHall(WorldScene scene) {
		scene.placeImageXY(
				new RectangleImage(
						MazeWorld.ROOM_SIZE_PX - 1,
						MazeWorld.ROOM_SIZE_PX - 1,
						OutlineMode.SOLID,
						MazeWorld.ROOM_COLOR),
				(from.col + to.col) * (MazeWorld.ROOM_SIZE_PX + MazeWorld.WALL_SIZE_PX) / 2
						+ (MazeWorld.ROOM_SIZE_PX + MazeWorld.WALL_SIZE_PX) / 2,
				(from.row + to.row) * (MazeWorld.ROOM_SIZE_PX + MazeWorld.WALL_SIZE_PX) / 2
						+ (MazeWorld.ROOM_SIZE_PX + MazeWorld.WALL_SIZE_PX) / 2);
	}
}

class Maze {
	int width, height; // number of vertices across and down
	Random rand;

	ArrayList<Room> rooms;
	ArrayList<Hall> routeTree;
	ArrayList<Room> searchTree;

	Maze(int width, int height, Random rand) {
		this.width = width;
		this.height = height;
		this.rand = rand;
		genVertsAndEdges();
		genRouteTree();
	}
	Maze(int size) {
		this(size, size, new Random());
	}
	Maze(int width, int height) {
		this(width, height, new Random());
	}
	Maze(int width, int height, int seed) {
		this(width, height, new Random(seed));
	}
	void genVertsAndEdges() {
		this.rooms = new ArrayList<Room>();
		// generate all the vertices
		for (int i = 0; i < width * height; i++) {
			Room v = new Room(Integer.toString(i), i / width, i % width);
			// connect to vertex to the left (right comes for free with next vertex)
			if (i % width != 0) {
				new Hall(this.rooms.get(i - 1), v, rand.nextInt(10));
			}
			// connect to vertex above (below comes for free)
			if (i >= width) {
				new Hall(this.rooms.get(i - width), v, rand.nextInt(10));
			}
			this.rooms.add(v);
		}
	}
	//Kruskal's Tree Algo
	void genRouteTree() {
		this.routeTree = new ArrayList<Hall>();

		HashMap<String, String> setMap = new HashMap<String, String>();
		PriorityQueue<Hall> worklist = new PriorityQueue<Hall>(new ParentSmallerEdge());

		for (Room v : this.rooms) {
			// initialize every node's representative to itself
			setMap.put(v.name, v.name);
			for (Hall e : v.neighborConnections) {
				// all edges in graph, sorted by edge weights
				worklist.add(e);
			}
		}
		Util u = new Util();
		while (!u.isOneTree(this.rooms, setMap)) {
			Hall lowestCostEdge = worklist.remove();
			if (u.sameTree(lowestCostEdge.from.name, lowestCostEdge.to.name, setMap)) {
				// discard this edge  
				// they're already connected
			} else {
				routeTree.add(lowestCostEdge);
				u.union(setMap, lowestCostEdge.to.name, lowestCostEdge.from.name);
			}
		}
		for (Hall h : this.routeTree) {
			h.from.addOpenHall(h);
			h.to.addOpenHall(h);
		}
	}
	void breadthFirstSearch() {
		this.searchTree = findPath(
				this.rooms.get(0),
				this.rooms.get(rooms.size() - 1),
				new Queue<Room>());
	}
	void depthFirstSearch() {
		this.searchTree = findPath(
				this.rooms.get(0),
				this.rooms.get(rooms.size() - 1),
				new Stack<Room>());
	}
	ArrayList<Room> findPath(Room from, Room to, ICollection<Room> worklist) {
		ArrayList<Room> alreadySeen = new ArrayList<Room>();

		// Initialize the worklist with the from vertex
		worklist.add(from);
		// As long as the worklist isn't empty...
		while (!worklist.isEmpty()) {
			Room next = worklist.remove();
			if (next.equals(to)) {
				return alreadySeen; // Success!
			} else if (alreadySeen.contains(next)) {
				// do nothing: we've already seen this one
			} else {
				// add all the neighbors of next to the worklist for further processing
				for (Hall h : next.neighborConnections) {
					worklist.add(h.to);
				}
				// add next to alreadySeen, since we're done with it
				alreadySeen.add(next);
			}
		}
		// We haven't found the to vertex, and there are no more to try
		throw new RuntimeException("Could not find a valid path");
	}

	void drawMaze(WorldScene scene) {
		scene.placeImageXY(
				new RectangleImage(
						width * (MazeWorld.ROOM_SIZE_PX + MazeWorld.WALL_SIZE_PX),
						height * (MazeWorld.ROOM_SIZE_PX + MazeWorld.WALL_SIZE_PX),
						OutlineMode.SOLID,
						MazeWorld.WALL_COLOR),
				width * (MazeWorld.ROOM_SIZE_PX + MazeWorld.WALL_SIZE_PX) / 2,
				height * (MazeWorld.ROOM_SIZE_PX + MazeWorld.WALL_SIZE_PX) / 2);
		// draw each room slightly small
		Color roomColor = MazeWorld.ROOM_COLOR;
		for (Room r : this.rooms) {
			r.drawRoom(scene, roomColor);
		}
		for (Hall h : this.routeTree) {
			h.drawHall(scene);
		}
	}
	void drawSearchTree(WorldScene scene, int tickCount) {
		Color searchColor = MazeWorld.SEARCH_COLOR;
		for (int i = 0; i <= tickCount && i < this.searchTree.size(); i++) {
			this.searchTree.get(i).drawRoom(scene, searchColor);
		}
	}

}
