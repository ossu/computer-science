import java.awt.Color;
import javalib.impworld.World;
import javalib.impworld.WorldScene;

class MazeWorld extends World {
	// Constants
	double TICKRATE = 1.0 / 2;
	static int ROOM_SIZE_PX = 64;
	static int WALL_SIZE_PX = 1;
	static Color ROOM_COLOR = Color.GRAY;
	static Color WALL_COLOR = Color.BLACK;
	static Color SEARCH_COLOR = Color.blue;

	//Variables
	Maze maze;
	int tickCount;

	MazeWorld(Maze maze) {
		this.maze = maze;
		this.tickCount = -1;
	}
	MazeWorld(int size) {
		this(new Maze(size));
	}
	MazeWorld(int cols, int rows) {
		this(new Maze(cols, rows));
	}
	void reset() {
		// reset maze to new random start
	}

	void start() {
		this.bigBang(
				(WALL_SIZE_PX + ROOM_SIZE_PX) * maze.width,
				(WALL_SIZE_PX + ROOM_SIZE_PX) * maze.height);
	}
	public WorldScene makeScene() {
		WorldScene scene = new WorldScene(
				(MazeWorld.ROOM_SIZE_PX + MazeWorld.WALL_SIZE_PX) * this.maze.width,
				(MazeWorld.ROOM_SIZE_PX + MazeWorld.WALL_SIZE_PX) * this.maze.height);
		this.maze.drawMaze(scene);
		if (this.tickCount >= 0) {
			this.maze.drawSearchTree(scene, this.tickCount);
		}
		return scene;
	}
	public void onKeyEvent(String k) {
		switch (k) {
		case "r":
			this.tickCount = -1;
			reset();
		case "b":
			this.tickCount = 0;
			this.maze.breadthFirstSearch();
		case "d":
			this.tickCount = 0;
			this.maze.depthFirstSearch();
		default:
			break;
		}
	}
	public void onTick() {
		this.tickCount++;
	}
}
