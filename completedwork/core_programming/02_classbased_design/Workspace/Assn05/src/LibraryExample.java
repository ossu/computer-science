import tester.*;
import javalib.worldimages.*;
import javalib.funworld.*;
import java.awt.Color;
import java.util.Random;

class MyGame extends World {
	int WIDTH;
	int HEIGHT;
	int currentTick;
	int finalTick;

	boolean randomText;
	boolean randomCircles;
	boolean welcomeScreen;

	MyGame(int width, int height, int currentTick, int endTick) {
		if (width < 0 ||	height < 0 ||
				endTick < 2) {
			throw new IllegalArgumentException("Invalid arguments passed to constructor.");
		}
		this.WIDTH = width;
		this.HEIGHT = height;
		this.currentTick = currentTick;
		this.finalTick = endTick;
		this.randomText = false;
		this.randomCircles = false;
		this.welcomeScreen = true;
	}

	MyGame(int width, int height, int currentTick, int endTick, boolean randomText,
			boolean randomCircles) {
		if (width < 0 ||	height < 0 ||
				endTick < 2) {
			throw new IllegalArgumentException("Invalid arguments passed to constructor.");
		}
		this.WIDTH = width;
		this.HEIGHT = height;
		this.currentTick = currentTick;
		this.finalTick = endTick;
		this.randomText = randomText;
		this.randomCircles = randomCircles;
		this.welcomeScreen = false;
	}

	@Override
	public WorldScene makeScene() {
		//Make a new scene.
		WorldScene scene = new WorldScene(this.WIDTH, this.HEIGHT);

		if (this.welcomeScreen) {
			scene = this.addWelcomeMessage(scene);
		}

		if (this.randomText) {
			scene = this.addRandomTextToScene(scene);
		}

		if (this.randomCircles) {
			scene = this.addRandomCirclesToScene(scene);
		}

		scene = this.addInfoToScene(scene);

		return scene;
	}

	WorldScene addWelcomeMessage(WorldScene scene) {
		return scene.placeImageXY(new TextImage("Game will start shortly.", Color.green), 250, 250);
	}

	WorldScene addRandomTextToScene(WorldScene scene) {
		//Generate random coordinates between 0 and this.WIDTH (non inclusive)
		int randX = (new Random()).nextInt(this.WIDTH);
		int randY = (new Random()).nextInt(this.HEIGHT);

		//Create a String displaying the random coordinates
		String randomText = Integer.toString(randX) + "," + Integer.toString(randY);

		//Add it to the scene and return the scene. 
		return scene.placeImageXY(new TextImage(randomText, Color.blue), randX, randY);
	}

	WorldScene addRandomCirclesToScene(WorldScene scene) {
		//Generate random coordinates between 0 and this.WIDTH (non inclusive)
		int randX = (new Random()).nextInt(this.WIDTH);
		int randY = (new Random()).nextInt(this.HEIGHT);

		//Add a circle to the scene and return the scene. 
		return scene.placeImageXY(new CircleImage(20, OutlineMode.SOLID, Color.green), randX, randY);
	}

	WorldScene addInfoToScene(WorldScene scene) {
		return scene.placeImageXY(new TextImage("Final tick: " + Integer.toString(this.finalTick)
				+ "  Current tick: " + Integer.toString(this.currentTick), Color.black), 100, 20);
	}

	@Override
	//This method gets called every tickrate seconds ( see bellow in example class).
	public MyGame onTick() {
		return this.addRandomText().addRandomCircles().incrementGameTick();
	}

	public MyGame addRandomText() {
		return new MyGame(this.WIDTH, this.HEIGHT, this.currentTick, this.finalTick, true,
				this.randomCircles);
	}

	public MyGame addRandomCircles() {
		return new MyGame(this.WIDTH, this.HEIGHT, this.currentTick, this.finalTick, this.randomText,
				true);
	}

	public MyGame incrementGameTick() {
		return new MyGame(this.WIDTH, this.HEIGHT, this.currentTick + 1, this.finalTick,
				this.randomText, this.randomCircles);
	}

	public MyGame onKeyEvent(String key) {
		//did we press the space update the final tick of the game by 10. 
		if (key.equals(" ")) {
			return new MyGame(this.WIDTH, this.HEIGHT, this.currentTick, this.finalTick + 10, true,
					false);
		} else {
			return this;
		}
	}

	//Check to see if we need to end the game.
	@Override
	public WorldEnd worldEnds() {
		if (this.currentTick == this.finalTick) {
			return new WorldEnd(true, this.makeEndScene());
		} else {
			return new WorldEnd(false, this.makeEndScene());
		}
	}

	public WorldScene makeEndScene() {
		WorldScene endScene = new WorldScene(this.WIDTH, this.HEIGHT);
		return endScene.placeImageXY(new TextImage("Game Over", Color.red), 250, 250);

	}

}

class ExamplesMyWorldProgram {
	boolean testBigBang(Tester t) {
		MyGame world = new MyGame(500, 500, 1, 20);
		//width, height, tickrate = 0.5 means every 0.5 seconds the onTick method will get called.
		return world.bigBang(500, 500, 7.0 / 28.0);
	}
}
